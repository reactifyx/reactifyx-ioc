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
 * Indicates that the annotated method produces a bean to be managed by the
 * dependency injection container.
 * <p>
 * Methods annotated with {@code @Bean} are invoked at runtime to provide
 * objects that should be registered in the container, typically with singleton
 * scope unless configured otherwise.
 * <p>
 * The optional {@code value} can be used to explicitly specify the bean's
 * identifier. If not set, the container may derive the name based on method
 * name or other conventions.
 *
 * <p>
 * <strong>Usage example:</strong>
 *
 * <pre>{@code
 * @Bean("myService")
 * public Service service() {
 * 	return new ServiceImpl();
 * }
 * }</pre>
 *
 * <p>
 * <strong>Target:</strong> Methods only. <br>
 * <strong>Retention:</strong> Runtime.
 *
 * @see Autowired
 * @see Component
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {
    String value() default "";
}
