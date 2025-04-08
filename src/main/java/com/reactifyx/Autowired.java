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

import java.lang.annotation.*;

/**
 * Marks a constructor, field, or method to be autowired by a dependency
 * injection container.
 * <p>
 * When used on a field or method, the container will attempt to resolve and
 * inject the appropriate dependency. When used on a constructor, it indicates
 * that the constructor should be used for instantiating the bean with injected
 * dependencies.
 * <p>
 * This annotation is typically processed at runtime by IoC frameworks such as
 * Spring.
 *
 * <p>
 * <strong>Applicable to:</strong> methods, constructors, and fields.
 *
 * @see Qualifier
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {}
