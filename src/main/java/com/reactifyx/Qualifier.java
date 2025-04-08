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
 * Used to distinguish between multiple candidates when injecting dependencies
 * of the same type.
 * <p>
 * When multiple beans of the same type exist in the container,
 * {@code @Qualifier} can be used alongside {@link Autowired} (or equivalent) to
 * specify exactly which bean should be injected.
 *
 * <p>
 * <strong>Usage example:</strong>
 *
 * <pre>
 * {@code
 *   &#64;Component
 *   &#64;Qualifier("fastRepo")
 *   public class FastRepository implements Repository { ... }
 *
 *   &#64;Component
 *   &#64;Qualifier("safeRepo")
 *   public class SafeRepository implements Repository { ... }
 *
 *   &#64;Autowired
 *   public MyService(@Qualifier("safeRepo") Repository repo) {
 *       this.repo = repo;
 *   }
 * }
 * </pre>
 *
 * <p>
 * <strong>Target:</strong> Constructors, fields, methods, parameters, types,
 * and annotations. <br>
 * <strong>Retention:</strong> Runtime. <br>
 * <strong>Inherited:</strong> Yes.
 *
 * @see Autowired
 * @see Component
 */
@Target({
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.PARAMETER,
    ElementType.TYPE,
    ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
