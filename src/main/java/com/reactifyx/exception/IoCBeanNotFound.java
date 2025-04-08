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
package com.reactifyx.exception;

/**
 * Exception thrown when the IoC container fails to locate a requested bean.
 * <p>
 * This typically occurs when:
 * <ul>
 * <li>No matching {@code @Component} or {@code @Bean} was found.</li>
 * <li>A required qualifier was not resolved.</li>
 * <li>The bean was not registered in the context.</li>
 * </ul>
 * <p>
 * This exception is crucial for debugging misconfigured components or missing
 * dependencies.
 */
public class IoCBeanNotFound extends Exception {

    /**
     * Constructs a new {@code IoCBeanNotFound} exception with the specified detail
     * message.
     *
     * @param message
     *            detailed message indicating the missing bean or cause
     */
    public IoCBeanNotFound(String message) {
        super(message);
    }
}
