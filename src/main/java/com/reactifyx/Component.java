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
package com.reactifyx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a component to be automatically detected and registered by
 * the dependency injection container during classpath scanning.
 * <p>
 * Classes annotated with {@code @Component} are treated as candidates for
 * instantiation and lifecycle management by the container. The container
 * resolves their dependencies and may inject them into other components.
 *
 * <p>
 * <strong>Usage example:</strong>
 *
 * <pre>{@code
 * @Component
 * public class UserService {
 * 	// Injected dependencies and business logic
 * }
 * }</pre>
 *
 * <p>
 * <strong>Target:</strong> Types (classes). <br>
 * <strong>Retention:</strong> Runtime.
 *
 * @see Autowired
 * @see Bean
 * @see Configuration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {}
