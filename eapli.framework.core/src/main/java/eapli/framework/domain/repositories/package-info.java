/**
 * Provides generic interfaces for building persistence layers following the
 * Repository pattern (acts as an in memory collection of entities).
 * <p>
 * <img src="doc-files/overview-repositories.png"/>
 * </p>
 * @author Paulo Gandra Sousa
 *
 */
package eapli.framework.domain.repositories;
/*
 * @startuml doc-files/overview-repositories.png
 *
 * DomainRepository <|-- IterableRepository 
 *
 * class TransactionalContext
 * class IntegrityViolationException
 * class ConcurrencyException
 *
 * DomainRepository ..> IntegrityViolationException : might throw
 * DomainRepository ..> ConcurrencyException : might throw
 * DomainRepository ..> TransactionalContext : can engage in
 *
 * @enduml
 */