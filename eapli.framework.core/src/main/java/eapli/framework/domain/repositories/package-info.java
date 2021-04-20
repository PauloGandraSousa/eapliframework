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
 * Provides generic interfaces for building persistence layers following the
 * Repository pattern (acts as an in memory collection of entities).
 * <p>
 * It is important to understand that a Repository is in fact a Port in the Ports and Connectors
 * architecture. That is the Repository is an interface; not to be confused with the repository
 * implementation (the connector).
 * <p>
 * <img src="repository-ports-connectors.svg">
 * <p>
 * <img src="framework-overview-repositories.png">
 * </p>
 *
 * @author Paulo Gandra Sousa
 *
 *         <!--
 * @startuml repository-ports-connectors.svg
 *
 *           package Presentation {
 *           class UseCaseXUI
 *           }
 *           package Application {
 *           class UseCaseXController
 *           UseCaseXUI .> UseCaseXController
 *
 *           note right of UseCaseXController
 *           the implementation of the repository is injected
 *           thru a dependency injection container
 *           or similar
 *           end note
 *           }
 *           package Domain {
 *           class Xpto <<root>>
 *           }
 *           package Repositories {
 *           interface XptoRepository
 *           XptoRepository .> Xpto
 *           }
 *           package RepositoriesImpl {
 *           class XptoRepositoryImpl
 *           XptoRepositoryImpl .up.|> XptoRepository
 *           XptoRepositoryImpl .up.> Xpto
 *           }
 *
 *           UseCaseXController ..> Xpto
 *           UseCaseXController ..> XptoRepository
 *
 *           'Presentation .> Application
 *           'Application .> Repositories
 *           'Application ..> Domain
 *           'Repositories .> Domain
 *           'RepositoriesImpl .up.> Domain
 *           'RepositoriesImpl .up.|> Repositories
 *
 * @enduml
 *         -->
 *         <!--
 * @startuml framework-overview-repositories.png
 *
 *           DomainRepository <|-up- IterableRepository
 *
 *           class TransactionalContext
 *           class IntegrityViolationException
 *           class ConcurrencyException
 *
 *           DomainRepository ..> IntegrityViolationException : might throw
 *           DomainRepository ..> ConcurrencyException : might throw
 *           DomainRepository .right.> TransactionalContext : can engage in
 *
 * @enduml
 *         -->
 */
package eapli.framework.domain.repositories;