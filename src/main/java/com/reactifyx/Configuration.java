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
 * Indicates that the annotated class declares one or more {@link Bean} methods
 * and may be processed by the dependency injection container to generate bean
 * definitions.
 * <p>
 * Configuration classes are typically used to bootstrap the application
 * context, define bean creation logic, and specify component scanning via
 * {@link ComponentScan}.
 *
 * <p>
 * <strong>Usage example:</strong>
 *
 * <pre>
 * {
 * 	&#64;code
 * 	&#64;Configuration
 * 	&#64;ComponentScan("com.example.app")
 * 	public class AppConfig {
 *
 * 		@Bean
 * 		public MyService myService() {
 * 			return new MyServiceImpl();
 * 		}
 * 	}
 * }
 * </pre>
 *
 * <p>
 * <strong>Target:</strong> Types (classes only). <br>
 * <strong>Retention:</strong> Runtime.
 *
 * @see Bean
 * @see ComponentScan
 * @see Autowired
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {}
