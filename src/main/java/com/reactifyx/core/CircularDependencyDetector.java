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

import com.reactifyx.exception.IoCCircularDepException;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class used to detect circular dependencies during the instantiation
 * of beans.
 *
 * <p>
 * This class keeps track of classes currently being instantiated, and throws an
 * exception if an attempt is made to instantiate a class that is already in
 * progress—indicating a circular dependency.
 * </p>
 *
 * <p>
 * Usage of this class helps prevent infinite recursion or stack overflow errors
 * when two or more beans directly or indirectly depend on each other.
 * </p>
 */
public class CircularDependencyDetector {

    /**
     * Set of classes currently being instantiated. Acts as a guard against circular
     * references.
     */
    private final Set<Class<?>> instantiationInProgress = new HashSet<>();

    /**
     * Marks the beginning of an instantiation process for a class.
     *
     * @param clazz
     *            the class being instantiated
     * @throws IoCCircularDepException
     *             if the class is already in the process of instantiation,
     *             indicating a circular dependency
     */
    public void startInstantiation(Class<?> clazz) throws IoCCircularDepException {
        if (instantiationInProgress.contains(clazz)) {
            throw new IoCCircularDepException("Circular dependency detected while instantiating " + clazz.getName());
        }
        instantiationInProgress.add(clazz);
    }

    /**
     * Marks the end of an instantiation process for a class. Removes the class from
     * the current set of in-progress instantiations.
     *
     * @param clazz
     *            the class that has finished instantiation
     */
    public void finishInstantiation(Class<?> clazz) {
        instantiationInProgress.remove(clazz);
    }
}
