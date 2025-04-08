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
package com.reactifyx.utils;

import com.reactifyx.Autowired;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for reflection-based scanning of annotated constructors,
 * fields, and methods.
 * <p>
 * Commonly used in dependency injection frameworks to locate elements annotated
 * with annotations like {@link Autowired}, etc.
 */
public class FinderUtil {

    /**
     * Finds the constructor of the given class that is annotated with
     * {@link Autowired}.
     * <p>
     * If multiple constructors are annotated, the first one found will be returned.
     * Returns {@code null} if no such constructor is present.
     *
     * @param clazz
     *            the class to inspect
     * @return the annotated constructor, or {@code null} if not found
     */
    public static Constructor<?> findAnnotatedConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }

    /**
     * Finds all fields in the given class (and its superclasses) annotated with the
     * specified annotation.
     * <p>
     * This method walks up the class hierarchy to collect inherited annotated
     * fields.
     *
     * @param clazz
     *            the class to scan
     * @param annotationClass
     *            the annotation to look for
     * @return a set of fields annotated with the given annotation
     */
    public static Set<Field> findFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Set<Field> set = new HashSet<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass)) {
                    field.setAccessible(true);
                    set.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return set;
    }

    /**
     * Finds all methods in the given class (and its superclasses) annotated with
     * the specified annotation.
     * <p>
     * Useful for detecting factory methods (e.g., {@code @Bean}) or lifecycle
     * callbacks.
     *
     * @param clazz
     *            the class to inspect
     * @param annotationClass
     *            the annotation to find
     * @return a set of annotated methods found in the class hierarchy
     */
    public static Set<Method> findMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Set<Method> set = new HashSet<>();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotationClass)) {
                    method.setAccessible(true);
                    set.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return set;
    }
}
