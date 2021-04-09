/*
 * Copyright (c) 2013-2021 the original author or authors.
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
/**
 * Main package for DDD Domain Model.
 * <p>
 * <img src="overview-ddd.svg">
 * </p>
 * Quoting
 * <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Eric
 * Evans</a>:
 * <blockquote>
 * Isolate the expression of the domain model and the business logic, and eliminate any
 * dependency on infrastructure, user interface, or even application logic that is not business
 * logic. Partition a complex program into layers. Develop a design within each layer that is
 * cohesive and that depends only on the layers below. Follow standard architectural patterns
 * to provide loose coupling to the layers above. Concentrate all the code related to the
 * domain model in one layer and isolate it from the user interface, application, and
 * infrastructure code. The domain objects, free of the responsibility of displaying themselves,
 * storing themselves, managing application tasks, and so forth, can be focused on expressing
 * the domain model. This allows a model to evolve to be rich enough and clear enough to
 * capture essential business knowledge and put it to work.
 * </blockquote>
 *
 * @author Paulo Gandra Sousa
 *
 *         <!--
 *
 * @startuml overview-ddd.svg
 *
 *           interface DomainEntity
 *
 *           interface DomainEvent extends ValueObject
 *
 *           interface AggregateRoot
 *
 *           interface ValueObject
 *
 *           interface Identifiable
 *
 *           interface DomainFactory
 *
 *           interface DomainRepository {
 *           +save()
 *           +find()
 *           }
 *
 *           AggregateRoot --|> DomainEntity
 *
 *           DomainEntity --|> Identifiable
 *
 *           DomainFactory ..> DomainEntity : creates
 *
 *           DomainRepository ..> AggregateRoot
 *
 *           DomainEntity *-- ValueObject : attributes
 *
 *           AggregateRoot ..> DomainEvent: publishes
 *
 * @enduml
 *         -->
 *
 */
package eapli.framework.domain.model;