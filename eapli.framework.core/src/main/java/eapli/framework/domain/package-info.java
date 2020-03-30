/**
 * Main package for DDD and general domain classes of the framework
 *
 * @startuml
 *
 * interface DomainEntity
 *
 * interface DomainEvent extends ValueObject
 *
 * interface AggregateRoot
 *
 * interface ValueObject
 *
 * interface Identifiable
 *
 * interface DomainFactory
 *
 * interface DomainRepository {
 * +save()
 * +find()
 * }
 *
 * AggregateRoot --|> DomainEntity
 * DomainEntity --|> Identifiable
 * DomainFactory ..> DomainEntity : creates
 * DomainRepository ..> AggregateRoot
 * DomainEntity *-- ValueObject : attributes
 *
 * @enduml
 *
 * @author Paulo Gandra Sousa
 *
 */
package eapli.framework.domain;