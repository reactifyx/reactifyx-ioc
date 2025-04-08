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
 * Configures the packages to scan for component classes to be registered by the
 * dependency injection container.
 * <p>
 * Classes within the specified packages annotated with {@link Component} (or
 * other stereotypes) will be detected and registered as beans.
 *
 * <p>
 * This annotation is typically placed on configuration classes to bootstrap the
 * scanning process at application startup.
 *
 * <p>
 * <strong>Example:</strong>
 *
 * <pre>
 * {
 * 	&#64;code
 * 	&#64;ComponentScan({"com.example.services", "com.example.repositories"})
 * 	@Configuration
 * 	public class AppConfig {
 * 	}
 * }
 * </pre>
 *
 * <p>
 * <strong>Target:</strong> Types (classes). <br>
 * <strong>Retention:</strong> Runtime.
 *
 * @see Component
 * @see Configuration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {
    String[] value() default {};
}
