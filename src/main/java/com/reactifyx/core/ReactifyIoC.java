/*
 * Copyright 2024-2025 the original author Hoàng Anh Tiến.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reactifyx.core;

import com.reactifyx.*;
import com.reactifyx.exception.IoCBeanNotFound;
import com.reactifyx.exception.IoCCircularDepException;
import com.reactifyx.exception.IoCException;
import com.reactifyx.utils.ClassLoaderUtil;
import com.reactifyx.utils.FinderUtil;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

/**
 * The main IoC container class for managing and injecting beans. This class
 * scans the classpath for components, configurations, and beans, handles their
 * instantiation, and supports constructor, field, and setter injection.
 */
public class ReactifyIoC {

    /** Container that holds all initialized beans. */
    private final BeanContainer beanContainer = new BeanContainer();

    /** Container that maps interfaces to their concrete implementation classes. */
    private final ImplementationContainer implementationContainer = new ImplementationContainer();

    /** Detector for preventing circular dependencies during bean instantiation. */
    private final CircularDependencyDetector circularDependencyDetector = new CircularDependencyDetector();

    /** Private constructor for singleton pattern-like instantiation. */
    private ReactifyIoC() {}

    /**
     * Initializes the IoC container by scanning the provided class and optional
     * predefined beans.
     *
     * @param mainClass
     *            the entry point class annotated with @ComponentScan
     * @param predefinedBeans
     *            manually instantiated beans to register
     * @return the initialized instance of ReactifyIoC
     */
    public static ReactifyIoC initBeans(Class<?> mainClass, Object... predefinedBeans) {
        try {
            ReactifyIoC instance = new ReactifyIoC();
            instance.initWrapper(mainClass, predefinedBeans);
            return instance;
        } catch (IOException
                | ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | IoCBeanNotFound
                | IoCCircularDepException
                | URISyntaxException e) {
            throw new IoCException(e);
        }
    }

    /**
     * Retrieves a bean instance by its class.
     *
     * @param clazz
     *            the class of the bean to retrieve
     * @return an instance of the requested bean
     */
    public <T> T getBean(Class<T> clazz) {
        try {
            return _getBean(clazz);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException
                | IoCBeanNotFound
                | IoCCircularDepException e) {
            throw new IoCException(e);
        }
    }

    /**
     * Internal initialization wrapper that handles scanning and loading of all
     * beans.
     */
    private void initWrapper(Class<?> mainClass, Object[] predefinedBeans)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
                    NoSuchMethodException, InvocationTargetException, IoCBeanNotFound, IoCCircularDepException,
                    URISyntaxException {
        // Register manually provided beans
        if (predefinedBeans != null) {
            for (Object bean : predefinedBeans) {
                Class<?>[] interfaces = bean.getClass().getInterfaces();
                if (interfaces.length == 0) {
                    implementationContainer.putImplementationClass(bean.getClass(), bean.getClass());
                } else {
                    for (Class<?> interfaceClass : interfaces) {
                        implementationContainer.putImplementationClass(bean.getClass(), interfaceClass);
                    }
                }
                beanContainer.putBean(bean.getClass(), bean);
            }
        }

        // Scan packages for components
        ComponentScan scan = mainClass.getAnnotation(ComponentScan.class);
        if (scan != null) {
            String[] packages = scan.value();
            for (String packageName : packages) {
                init(packageName);
            }
        } else {
            init(mainClass.getPackage().getName());
        }
    }

    /**
     * Core initialization logic that scans and registers all components and
     * configurations.
     */
    private void init(String packageName)
            throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException,
                    InvocationTargetException, IoCBeanNotFound, IoCCircularDepException, URISyntaxException,
                    ClassNotFoundException {
        beanContainer.putBean(ReactifyIoC.class, this);
        implementationContainer.putImplementationClass(ReactifyIoC.class, ReactifyIoC.class);
        List<Class<?>> classes = ClassLoaderUtil.getClasses(packageName);
        scanImplementations(packageName);
        scanConfigurationClass(classes);
        scanComponentClasses(classes);
    }

    /**
     * Scans for @Component and @Configuration classes and registers their
     * implementation mappings.
     */
    private void scanImplementations(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> implementationClass : componentClasses) {
            Class<?>[] interfaces = implementationClass.getInterfaces();
            if (interfaces.length == 0) {
                implementationContainer.putImplementationClass(implementationClass, implementationClass);
            } else {
                for (Class<?> interfaceClass : interfaces) {
                    implementationContainer.putImplementationClass(implementationClass, interfaceClass);
                }
            }
        }
        Set<Class<?>> configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        for (Class<?> configurationClass : configurationClasses) {
            Set<Method> methods = FinderUtil.findMethods(configurationClass, Bean.class);
            for (Method method : methods) {
                Class<?> returnType = method.getReturnType();
                implementationContainer.putImplementationClass(returnType, returnType);
            }
        }
    }

    private void scanConfigurationClass(List<Class<?>> classes)
            throws IoCCircularDepException, InvocationTargetException, IllegalAccessException, InstantiationException,
                    NoSuchMethodException {
        Deque<Class<?>> configurationClassesQ = new ArrayDeque<>(5);
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                configurationClassesQ.add(clazz);
            }
        }
        while (!configurationClassesQ.isEmpty()) {
            Class<?> configurationClass = configurationClassesQ.removeFirst();
            try {
                Object instance = configurationClass.getConstructor().newInstance();
                scanConfigurationBeans(configurationClass, instance);
            } catch (IoCBeanNotFound e) {
                configurationClassesQ.addLast(configurationClass);
            }
        }
    }

    private void scanComponentClasses(List<Class<?>> classes)
            throws IoCCircularDepException, InvocationTargetException, IllegalAccessException, InstantiationException,
                    NoSuchMethodException, IoCBeanNotFound {
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                newInstanceWrapper(clazz);
            }
        }
    }

    private void scanConfigurationBeans(Class<?> clazz, Object classInstance)
            throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException,
                    IoCBeanNotFound, IoCCircularDepException {
        Set<Method> methods = FinderUtil.findMethods(clazz, Bean.class);
        Set<Field> fields = FinderUtil.findFields(clazz, Autowired.class);

        for (Field field : fields) {
            String qualifier = field.isAnnotationPresent(Qualifier.class)
                    ? field.getAnnotation(Qualifier.class).value()
                    : null;
            Object fieldInstance = _getBean(field.getType(), field.getName(), qualifier, false);
            field.set(classInstance, fieldInstance);
        }

        for (Method method : methods) {
            Class<?> beanType = method.getReturnType();
            Object beanInstance = method.invoke(classInstance);
            String name = method.getAnnotation(Bean.class).value() != null
                    ? method.getAnnotation(Bean.class).value()
                    : beanType.getName();
            beanContainer.putBean(beanType, beanInstance, name);
        }
    }

    private Object newInstanceWrapper(Class<?> clazz)
            throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException,
                    IoCBeanNotFound, IoCCircularDepException {
        circularDependencyDetector.startInstantiation(clazz);

        try {
            if (beanContainer.containsBean(clazz)) {
                return beanContainer.getBean(clazz);
            }

            Object instance = newInstance(clazz);
            beanContainer.putBean(clazz, instance);
            fieldInject(clazz, instance);
            setterInject(clazz, instance);
            return instance;
        } finally {
            circularDependencyDetector.finishInstantiation(clazz);
        }
    }

    private Object newInstance(Class<?> clazz)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException,
                    IoCBeanNotFound, IoCCircularDepException {
        Constructor<?> annotatedConstructor = FinderUtil.findAnnotatedConstructor(clazz);
        Object instance;
        if (annotatedConstructor == null) {
            try {
                Constructor<?> defaultConstructor = clazz.getConstructor();
                defaultConstructor.setAccessible(true);
                instance = clazz.newInstance();
                return instance;
            } catch (NoSuchMethodException e) {
                throw new IoCException("There is no default constructor in class " + clazz.getName());
            }
        } else {
            Object[] parameters = new Object[annotatedConstructor.getParameterCount()];
            for (int i = 0; i < parameters.length; i++) {
                String qualifier = annotatedConstructor.getParameters()[i].isAnnotationPresent(Qualifier.class)
                        ? annotatedConstructor
                                .getParameters()[i]
                                .getAnnotation(Qualifier.class)
                                .value()
                        : null;
                Object depInstance = _getBean(
                        annotatedConstructor.getParameterTypes()[i],
                        annotatedConstructor.getParameterTypes()[i].getName(),
                        qualifier,
                        true);
                parameters[i] = depInstance;
            }
            instance = annotatedConstructor.newInstance(parameters);
        }
        return instance;
    }

    private void setterInject(Class<?> clazz, Object classInstance)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException,
                    IoCBeanNotFound, IoCCircularDepException {
        Set<Method> methods = FinderUtil.findMethods(clazz, Autowired.class);
        for (Method method : methods) {
            Object[] parameters = new Object[method.getParameterCount()];
            for (int i = 0; i < parameters.length; i++) {
                String qualifier = method.getParameters()[i].isAnnotationPresent(Qualifier.class)
                        ? method.getParameters()[i]
                                .getAnnotation(Qualifier.class)
                                .value()
                        : null;
                Object instance = _getBean(
                        method.getParameterTypes()[i], method.getParameterTypes()[i].getName(), qualifier, true);
                parameters[i] = instance;
            }
            method.invoke(classInstance, parameters);
        }
    }

    private void fieldInject(Class<?> clazz, Object classInstance)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException,
                    IoCBeanNotFound, IoCCircularDepException {
        Set<Field> fields = FinderUtil.findFields(clazz, Autowired.class);
        for (Field field : fields) {
            String qualifier = field.isAnnotationPresent(Qualifier.class)
                    ? field.getAnnotation(Qualifier.class).value()
                    : null;
            Object fieldInstance = _getBean(field.getType(), field.getName(), qualifier, true);
            field.set(classInstance, fieldInstance);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T _getBean(Class<T> interfaceClass)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
                    IoCBeanNotFound, IoCCircularDepException {
        return (T) _getBean(interfaceClass, null, null, false);
    }

    private <T> Object _getBean(Class<T> interfaceClass, String fieldName, String qualifier, boolean createIfNotFound)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException,
                    IoCBeanNotFound, IoCCircularDepException {
        Class<?> implementationClass = interfaceClass.isInterface()
                ? implementationContainer.getImplementationClass(interfaceClass, fieldName, qualifier)
                : interfaceClass;
        if (beanContainer.containsBean(implementationClass)) {
            if (qualifier != null) {
                return beanContainer.getBean(implementationClass, qualifier);
            }
            return beanContainer.getBean(implementationClass);
        }
        if (createIfNotFound) {
            synchronized (beanContainer) {
                return newInstanceWrapper(implementationClass);
            }
        } else {
            throw new IoCBeanNotFound("Cannot found bean for " + interfaceClass.getName());
        }
    }
}
