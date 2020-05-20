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
/**
 * Simple Publish/Subscribe implementation with a database.
 *
 * <p>
 * <img src="overview-pubsub.svg">
 * </p>
 *
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml overview-pubsub.svg
 *
 *           class SimplePersistentEventPubSub {
 *           -{static}int POLL_EVENTS_INTERVAL
 *           -{static}Logger LOGGER
 *           -EventRecordRepository eventRepo
 *           -EventConsumptionRepository consumptionRepo
 *           -EventDispatcher inprocDispatcher
 *           -EventPublisher inprocPublisher
 *           -String instanceKey
 *           #SimplePersistentEventPubSub(String instanceKey)
 *           -void storeConsumptionAndPublish(EventRecord x)
 *           -boolean pollEvents()
 *           -void init()
 *           +void publish(DomainEvent event)
 *           -void storeEvent(DomainEvent event)
 *           +void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer)
 *           +void shutdown()
 *           }
 *
 *           interface EventDispatcher {
 *           }
 *
 *           EventDispatcher <|.. SimplePersistentEventPubSub
 *
 *           interface EventPublisher {
 *           }
 *
 *           EventPublisher <|.. SimplePersistentEventPubSub
 *
 *           interface Singleton [[java:eapli.framework.io.util.Singleton]] {
 *           }
 *
 *           Singleton <|.. SimplePersistentEventPubSub
 *
 *           interface EventRecordRepository {
 *           +Iterable<EventRecord> findNotConsumed(String instanceKey)
 *           }
 *
 *           interface "DataRepository<EventRecord,Long>" as DataRepository_EventRecord_Long_ {
 *           }
 *
 *           DataRepository_EventRecord_Long_ <|-- EventRecordRepository
 *
 *           EventRecordRepository <-- SimplePersistentEventPubSub
 *
 *           class EventRecord {
 *           -Long pk
 *           -String eventName
 *           -byte[] event
 *           +EventRecord(DomainEvent event)
 *           #EventRecord()
 *           +DomainEvent event()
 *           +boolean sameAs(Object other)
 *           +Long id()
 *           +String toString()
 *           }
 *
 *           interface "AggregateRoot<Long>" as AggregateRoot_Long_ {
 *           }
 *
 *           AggregateRoot_Long_ <|.. EventRecord
 *
 *           EventRecord <.. EventRecordRepository
 *           EventRecord <.. SimplePersistentEventPubSub
 *
 *           interface DomainEvent
 *
 *           DomainEvent <.. EventRecord
 *
 *           interface EventConsumptionRepository {
 *           }
 *
 *           interface "DataRepository<EventConsumption,Long>" as DataRepository_EventConsumption_Long_ {
 *           }
 *
 *           DataRepository_EventConsumption_Long_ <|-- EventConsumptionRepository
 *
 *           class EventConsumption {
 *           -Long pk
 *           -EventRecord event
 *           -String consumerName
 *           +EventConsumption(String consumerName, EventRecord event)
 *           #EventConsumption()
 *           +boolean sameAs(Object other)
 *           +Long id()
 *           +String toString()
 *           }
 *
 *           AggregateRoot_Long_ <|.. EventConsumption
 *
 *           EventRecord <-- EventConsumption
 *
 *           EventConsumption <.. EventConsumptionRepository
 *           EventConsumptionRepository <-- SimplePersistentEventPubSub
 *
 *           class InProcessEventDispatcher
 *           InProcessEventDispatcher <-- SimplePersistentEventPubSub
 *
 *           class InProcessEventPublisher
 *           InProcessEventPublisher <-- SimplePersistentEventPubSub
 *
 * @enduml
 *         -->
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;