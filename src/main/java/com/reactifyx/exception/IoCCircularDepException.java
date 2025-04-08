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
 * Exception thrown when a circular dependency is detected in the IoC container.
 * <p>
 * A circular dependency occurs when two or more beans depend on each other in a
 * cycle, which prevents proper instantiation and injection.
 * <p>
 * Example:
 *
 * <pre>
 * class A {
 * 	&#64;Autowired
 * 	B b;
 * }
 * class B {
 * 	@Autowired
 * 	A a;
 * }
 * </pre>
 */
public class IoCCircularDepException extends Exception {

    /**
     * Constructs a new {@code IoCCircularDepException} with the specified detail
     * message.
     *
     * @param message
     *            a description of the circular dependency detected
     */
    public IoCCircularDepException(String message) {
        super(message);
    }
}
