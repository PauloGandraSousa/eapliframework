/**
 * @author pgsou_000
 */
/*

@startuml

interface DomainEvent [[java:eapli.framework.domain.events.DomainEvent]] {
    Calendar occurredAt()
    Calendar registeredAt()
}

interface ValueObject [[java:eapli.framework.domain.ValueObject]] {
}

ValueObject <|-- DomainEvent

interface Serializable [[java:java.io.Serializable]] {
}

Serializable <|-- DomainEvent

interface EventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.EventDispatcher]] {
    void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    void unsubscribe(EventHandler observer)
    void shutdown()
}

interface EventHandler [[java:eapli.framework.infrastructure.eventpubsub.EventHandler]] {
    void onEvent(DomainEvent domainEvent)
}

interface EventPublisher [[java:eapli.framework.infrastructure.eventpubsub.EventPublisher]] {
    void publish(DomainEvent event)
}

DomainEvent <.. EventPublisher
DomainEvent <.. EventDispatcher
DomainEvent <.. EventHandler
EventHandler <.. EventDispatcher

@enduml

 */
package eapli.framework.infrastructure.eventpubsub;