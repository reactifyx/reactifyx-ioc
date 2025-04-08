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
 * Custom runtime exception used within the IoC container.
 * <p>
 * Thrown when the dependency injection framework encounters an unrecoverable
 * error during component scanning, instantiation, or wiring.
 * <p>
 * This exception wraps lower-level exceptions to provide meaningful context and
 * consistent error handling across the IoC framework.
 */
public class IoCException extends RuntimeException {

    /**
     * Constructs a new IoCException with the specified cause.
     *
     * @param cause
     *            the underlying exception that caused this failure
     */
    public IoCException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new IoCException with the specified message.
     *
     * @param message
     *            a detailed message describing the failure
     */
    public IoCException(String message) {
        super(message);
    }
}
