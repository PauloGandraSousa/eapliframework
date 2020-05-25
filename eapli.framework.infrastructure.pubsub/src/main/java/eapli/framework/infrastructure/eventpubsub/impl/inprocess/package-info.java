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
 * In-memory pub/sub implementation.
 *
 * <p>
 * <img src="pubsub-inmemory.svg">
 * </p>
 *
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml pubsub-inmemory.svg
 *
 *
 *           interface EventDispatcher {
 *           }
 *           interface EventPublisher {
 *           }
 *
 *           EventDispatcher <|.. InProcessPublisherDispatcher
 *           EventPublisher <|.. InProcessPublisherDispatcher
 *
 *           class InProcessPubSub <<utility>>{
 *           -InProcessPubSub()
 *           +{static}EventDispatcher dispatcher()
 *           +{static}EventPublisher publisher()
 *           }
 *
 *           class InProcessPublisherDispatcher <<package private>>{
 *           -{static}Logger LOGGER
 *           -Map<Class<? extends DomainEvent>,List<EventHandler>> handlers
 *           -InProcessPubSub()
 *           +void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer)
 *           +void publish(DomainEvent event)
 *           +void shutdown()
 *           }
 *
 *           InProcessPubSub ..> InProcessPublisherDispatcher
 *           InProcessPubSub ..> EventDispatcher
 *           InProcessPubSub ..> EventPublisher
 *
 * @enduml
 *         -->
 */
package eapli.framework.infrastructure.eventpubsub.impl.inprocess;