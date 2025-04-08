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

import com.reactifyx.exception.IoCException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A container responsible for storing and retrieving bean instances.
 *
 * <p>
 * This class maintains a mapping between class types and their corresponding
 * bean instances, optionally supporting multiple beans of the same type
 * identified by a unique name (usually the class name or qualifier).
 * </p>
 *
 * <p>
 * It supports dependency injection scenarios including default and named beans.
 * This class is not thread-safe and assumes single-threaded usage during the
 * bootstrap phase.
 * </p>
 */
public class BeanContainer {

    /**
     * Internal storage for beans. Maps each class to a map of named bean instances
     * (usually by class name or @Qualifier).
     */
    public final Map<Class<?>, Map<String, Object>> beans = new HashMap<>(10);

    /**
     * Stores a bean instance using the class name as the default key.
     *
     * @param clazz
     *            the class type of the bean
     * @param instance
     *            the actual bean instance
     */
    public void putBean(Class<?> clazz, Object instance) {
        putBean(clazz, instance, clazz.getName());
    }

    /**
     * Stores a bean instance with a specific name for disambiguation.
     *
     * @param clazz
     *            the class type of the bean
     * @param instance
     *            the actual bean instance
     * @param name
     *            the name/key for the bean (e.g., a qualifier)
     */
    public void putBean(Class<?> clazz, Object instance, String name) {
        Map<String, Object> map = beans.computeIfAbsent(clazz, k -> new TreeMap<>());
        map.putIfAbsent(name, instance);
    }

    /**
     * Checks whether any bean of the given type exists in the container.
     *
     * @param clazz
     *            the class type to check
     * @return true if a bean of the given class exists, false otherwise
     */
    public boolean containsBean(Class<?> clazz) {
        return containsBean(clazz, clazz.getName());
    }

    /**
     * Checks whether a bean of the given type and name exists.
     *
     * @param clazz
     *            the class type of the bean
     * @param name
     *            the name/key of the bean
     * @return true if the bean exists, false otherwise
     */
    public boolean containsBean(Class<?> clazz, String name) {
        return beans.get(clazz) != null;
    }

    /**
     * Retrieves the default bean instance for a given class. Uses the class name as
     * the default key.
     *
     * @param clazz
     *            the class type of the desired bean
     * @return the bean instance
     * @throws IoCException
     *             if the bean is not found
     */
    public Object getBean(Class<?> clazz) {
        return getBean(clazz, clazz.getName());
    }

    /**
     * Retrieves a named bean instance for a given class type. Supports scenarios
     * where multiple beans of the same type exist.
     *
     * @param clazz
     *            the class type of the bean
     * @param name
     *            the name/key of the desired bean
     * @return the bean instance
     * @throws IoCException
     *             if the bean is not found, or if multiple beans exist and no
     *             matching name is provided
     */
    public Object getBean(Class<?> clazz, String name) {
        Map<String, Object> map = beans.get(clazz);

        if (map == null || map.isEmpty()) {
            throw new IoCException("No bean found for class " + clazz);
        }

        if (map.size() == 1) {
            return map.values().iterator().next();
        }

        Object bean = map.get(name);
        if (bean == null) {
            String errorMessage = "There are " + map.size() + " of bean " + name
                    + " Expected single implementation or make use of" + " @Qualifier to resolve conflict";
            throw new IoCException(errorMessage);
        }

        return bean;
    }
}
