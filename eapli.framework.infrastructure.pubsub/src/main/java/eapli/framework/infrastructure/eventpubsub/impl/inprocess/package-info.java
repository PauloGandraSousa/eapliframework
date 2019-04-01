/**
 * @author Paulo Gandra de Sousa
 */
/*
 *
@startuml

class InProcessEventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessEventDispatcher]] {
    -InProcessEventDispatcher()
    +{static}EventDispatcher instance()
}

interface Singleton [[java:eapli.framework.util.Singleton]] {
}

Singleton <|.. InProcessEventDispatcher

interface EventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.EventDispatcher]] {
}

EventDispatcher <|.. InProcessEventDispatcher

class InProcessEventPublisher [[java:eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessEventPublisher]] {
    -InProcessEventPublisher()
    +{static}EventPublisher instance()
}

Singleton <|.. InProcessEventPublisher

interface EventPublisher [[java:eapli.framework.infrastructure.eventpubsub.EventPublisher]] {
}

EventPublisher <|.. InProcessEventPublisher

class InProcessPubSub [[java:eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub]] {
    -{static}Logger LOGGER
    -Map<Class<? extends DomainEvent>,List<EventHandler>> handlers
    -InProcessPubSub()
    +{static}EventDispatcher dispatcher()
    +{static}EventPublisher publisher()
    +void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    +void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
    +void unsubscribe(EventHandler observer)
    +void publish(DomainEvent event)
    +void shutdown()
}

Singleton <|.. InProcessPubSub

EventDispatcher <|.. InProcessPubSub
EventPublisher <|.. InProcessPubSub

InProcessPubSub <.. InProcessEventDispatcher
InProcessPubSub <.. InProcessEventPublisher

@enduml
 */
package eapli.framework.infrastructure.eventpubsub.impl.inprocess;