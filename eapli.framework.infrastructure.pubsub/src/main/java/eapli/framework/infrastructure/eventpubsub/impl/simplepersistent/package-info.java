/**
 *
 */
/**
 * @author pgsou_000
 *
@startuml
class SimplePersistentEventPubSub [[java:eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.SimplePersistentEventPubSub]] {
    -{static}int POLL_EVENTS_INTERVAL
    -{static}Logger LOGGER
    -EventRecordRepository eventRepo
    -EventConsumptionRepository consumptionRepo
    -EventDispatcher inprocDispatcher
    -EventPublisher inprocPublisher
    -String instanceKey
    #SimplePersistentEventPubSub(String instanceKey)
    -void storeConsumptionAndPublish(EventRecord x)
    -boolean pollEvents()
    -void init()
    +void publish(DomainEvent event)
    -void storeEvent(DomainEvent event)
    +void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    +void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    +void unsubscribe(EventHandler observer)
    +void shutdown()
}

interface EventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.EventDispatcher]] {
}

EventDispatcher <|.. SimplePersistentEventPubSub

interface EventPublisher [[java:eapli.framework.infrastructure.eventpubsub.EventPublisher]] {
}

EventPublisher <|.. SimplePersistentEventPubSub

interface Singleton [[java:eapli.framework.util.Singleton]] {
}

Singleton <|.. SimplePersistentEventPubSub

interface EventRecordRepository [[java:eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.EventRecordRepository]] {
    Iterable<EventRecord> findNotConsumed(String instanceKey)
}

interface "DataRepository<EventRecord,Long>" as DataRepository_EventRecord_Long_ {
}

DataRepository_EventRecord_Long_ <|-- EventRecordRepository

EventRecordRepository <-- SimplePersistentEventPubSub

class EventRecord [[java:eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.EventRecord]] {
    -Long pk
    -String eventName
    -byte[] event
    +EventRecord(DomainEvent event)
    #EventRecord()
    +DomainEvent event()
    +boolean sameAs(Object other)
    +Long id()
    +String toString()
}

interface "AggregateRoot<Long>" as AggregateRoot_Long_ {
}

AggregateRoot_Long_ <|.. EventRecord

EventRecord <.. EventRecordRepository
EventRecord <.. SimplePersistentEventPubSub

interface DomainEvent

DomainEvent <.. EventRecord

interface EventConsumptionRepository [[java:eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.EventConsumptionRepository]] {
}

interface "DataRepository<EventConsumption,Long>" as DataRepository_EventConsumption_Long_ {
}

DataRepository_EventConsumption_Long_ <|-- EventConsumptionRepository

class EventConsumption [[java:eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.EventConsumption]] {
    -Long pk
    -EventRecord event
    -String consumerName
    +EventConsumption(String consumerName, EventRecord event)
    #EventConsumption()
    +boolean sameAs(Object other)
    +Long id()
    +String toString()
}

AggregateRoot_Long_ <|.. EventConsumption

EventRecord <-- EventConsumption

EventConsumption <.. EventConsumptionRepository
EventConsumptionRepository <-- SimplePersistentEventPubSub

class InProcessEventDispatcher
InProcessEventDispatcher <-- SimplePersistentEventPubSub

class InProcessEventPublisher
InProcessEventPublisher <-- SimplePersistentEventPubSub

@enduml
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;