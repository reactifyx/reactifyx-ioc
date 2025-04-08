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

public class BeanContainer {
    public final Map<Class<?>, Map<String, Object>> beans = new HashMap<>(10);

    public void putBean(Class<?> clazz, Object instance) {
        putBean(clazz, instance, clazz.getName());
    }

    public void putBean(Class<?> clazz, Object instance, String name) {
        Map<String, Object> map = beans.computeIfAbsent(clazz, k -> new TreeMap<>());
        map.putIfAbsent(name, instance);
    }

    public boolean containsBean(Class<?> clazz) {
        return containsBean(clazz, clazz.getName());
    }

    public boolean containsBean(Class<?> clazz, String name) {
        return beans.get(clazz) != null;
    }

    public Object getBean(Class<?> clazz) {
        return getBean(clazz, clazz.getName());
    }

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
