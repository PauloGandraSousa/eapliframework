/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.domain.services;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A Domain Service. When a responsibility/behavior is not naturally assigned to an existing domain
 * object, create a domain service to implement that behavior. Care should be taken not to
 * overpopulate the domain model with too many services - the domain entities and value objects
 * should be the main place to implement the business logic.
 * <p>
 * Services should be stateless (no properties) except for injected dependencies, e.g., other
 * services.
 * </p>
 *
 * <p>
 * For example, transferring money between two accounts in the same bank. Should this operation be
 * assigned to an {@code Account} object? If so, should it be named {code transferTo} or
 * {@code transferFrom}? Is one side of the relation more important than another? Are the business
 * rules about money transfers really part of the account? For instance, transferring money between
 * two accounts in different currencies?
 *
 * <p>
 * <img src="domain-service.svg">
 * <p>
 * Services should preferably be stateless (no properties) except for injected dependencies, e.g.,
 * repositories
 * and services.
 * </p>
 *
 *
 * @author Paulo Gandra Sousa 01/06/2020
 *
 *         <!--
 * @startuml domain-service.svg
 *
 *           class IntraBankTransferService << domain service>>
 *           class Account <<aggregate root>>
 *
 *           IntraBankTransferService ..> Account: from
 *           IntraBankTransferService ..> Account: to
 *
 * @enduml
 *         -->
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE })
public @interface DomainService {
    // empty
}
