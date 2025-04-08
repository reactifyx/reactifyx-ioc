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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Container class that maps interface types to their implementation classes. It
 * supports storing and retrieving the implementation class for a given
 * interface.
 *
 * <p>
 * This is useful in an IoC context when multiple implementations exist, and we
 * need to resolve a specific one either by field name or using a
 * {@code @Qualifier}.
 * </p>
 */
public class ImplementationContainer {

    /**
     * A map holding the relationship between implementation classes and their
     * associated interface types. The key is the implementation class, the value is
     * the interface it implements.
     */
    private final Map<Class<?>, Class<?>> implementationsMap = new HashMap<>(10);

    /**
     * Registers an implementation class for a given interface.
     *
     * @param implementationClass
     *            the actual implementation class
     * @param interfaceClass
     *            the interface that this implementation should be associated with
     */
    public void putImplementationClass(Class<?> implementationClass, Class<?> interfaceClass) {
        implementationsMap.put(implementationClass, interfaceClass);
    }

    /**
     * Retrieves the implementation class associated with the provided interface.
     *
     * <p>
     * If multiple implementations are found, this method attempts to resolve the
     * correct one using either the {@code qualifier} (if provided) or the
     * {@code fieldName}.
     * </p>
     *
     * @param interfaceClass
     *            the interface for which the implementation is needed
     * @param fieldName
     *            the name of the field being injected (used for resolution if
     *            qualifier is missing)
     * @param qualifier
     *            the qualifier to disambiguate multiple implementations
     * @return the resolved implementation class
     * @throws IoCException
     *             if no implementation is found, or if multiple exist without a way
     *             to disambiguate
     */
    public Class<?> getImplementationClass(Class<?> interfaceClass, final String fieldName, final String qualifier) {
        Set<Map.Entry<Class<?>, Class<?>>> implementationClasses = implementationsMap.entrySet().stream()
                .filter(entry -> entry.getValue() == interfaceClass)
                .collect(Collectors.toSet());
        String errorMessage;
        if (implementationClasses.isEmpty()) {
            errorMessage = "No implementation found for interface " + interfaceClass.getName();
        } else if (implementationClasses.size() == 1) {
            Optional<Map.Entry<Class<?>, Class<?>>> optional =
                    implementationClasses.stream().findFirst();
            return optional.get().getKey();
        } else {
            final String findBy = (qualifier == null || qualifier.trim().isEmpty()) ? fieldName : qualifier;
            Optional<Map.Entry<Class<?>, Class<?>>> optional = implementationClasses.stream()
                    .filter(entry -> entry.getKey().getSimpleName().equalsIgnoreCase(findBy))
                    .findAny();
            if (optional.isPresent()) {
                return optional.get().getKey();
            } else {
                errorMessage = "There are " + implementationClasses.size() + " of interface " + interfaceClass.getName()
                        + " Expected single implementation or make use of" + " @Qualifier to resolve conflict";
            }
        }
        throw new IoCException(errorMessage);
    }
}
