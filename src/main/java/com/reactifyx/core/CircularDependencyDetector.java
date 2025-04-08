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

public class CircularDependencyDetector {
    private final Set<Class<?>> instantiationInProgress = new HashSet<>();

    public void startInstantiation(Class<?> clazz) throws IoCCircularDepException {
        if (instantiationInProgress.contains(clazz)) {
            throw new IoCCircularDepException("Circular dependency detected while instantiating " + clazz.getName());
        }
        instantiationInProgress.add(clazz);
    }

    public void finishInstantiation(Class<?> clazz) {
        instantiationInProgress.remove(clazz);
    }
}
