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
 * Pub/Sub.
 *
 * <p>
 * <img src="pubsub.svg">
 * </p>
 *
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml pubsub.svg
 *
 *           interface DomainEvent [[java:eapli.framework.domain.events.DomainEvent]] {
 *           Calendar occurredAt()
 *           Calendar registeredAt()
 *           }
 *
 *           interface ValueObject [[java:eapli.framework.domain.ValueObject]] {
 *           }
 *
 *           ValueObject <|-- DomainEvent
 *
 *           interface Serializable [[java:java.io.Serializable]] {
 *           }
 *
 *           Serializable <|-- DomainEvent
 *
 *           interface EventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.EventDispatcher]] {
 *           void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           void unsubscribe(EventHandler observer)
 *           void shutdown()
 *           }
 *
 *           interface EventHandler [[java:eapli.framework.infrastructure.eventpubsub.EventHandler]] {
 *           void onEvent(DomainEvent domainEvent)
 *           }
 *
 *           interface EventPublisher [[java:eapli.framework.infrastructure.eventpubsub.EventPublisher]] {
 *           void publish(DomainEvent event)
 *           }
 *
 *           DomainEvent <.. EventPublisher
 *           DomainEvent <.. EventDispatcher
 *           DomainEvent <.. EventHandler
 *           EventHandler <.. EventDispatcher
 *
 * @enduml
 *         -->
 *
 */
package eapli.framework.infrastructure.eventpubsub;
