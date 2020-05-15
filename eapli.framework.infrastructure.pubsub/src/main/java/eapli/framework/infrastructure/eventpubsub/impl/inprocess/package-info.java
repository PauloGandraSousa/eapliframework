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
 *           class InProcessEventDispatcher {
 *           -InProcessEventDispatcher()
 *           +{static}EventDispatcher instance()
 *           }
 *
 *           interface Singleton [[java:eapli.framework.util.Singleton]] {
 *           }
 *
 *           Singleton <|.. InProcessEventDispatcher
 *
 *           interface EventDispatcher [[java:eapli.framework.infrastructure.eventpubsub.EventDispatcher]] {
 *           }
 *
 *           EventDispatcher <|.. InProcessEventDispatcher
 *
 *           class InProcessEventPublisher{
 *           -InProcessEventPublisher()
 *           +{static}EventPublisher instance()
 *           }
 *
 *           Singleton <|.. InProcessEventPublisher
 *
 *           interface EventPublisher [[java:eapli.framework.infrastructure.eventpubsub.EventPublisher]] {
 *           }
 *
 *           EventPublisher <|.. InProcessEventPublisher
 *
 *           class InProcessPubSub [[java:eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub]] {
 *           -{static}Logger LOGGER
 *           -Map<Class<? extends DomainEvent>,List<EventHandler>> handlers
 *           -InProcessPubSub()
 *           +{static}EventDispatcher dispatcher()
 *           +{static}EventPublisher publisher()
 *           +void subscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer, Class<? extends DomainEvent>[] events)
 *           +void unsubscribe(EventHandler observer)
 *           +void publish(DomainEvent event)
 *           +void shutdown()
 *           }
 *
 *           Singleton <|.. InProcessPubSub
 *
 *           EventDispatcher <|.. InProcessPubSub
 *           EventPublisher <|.. InProcessPubSub
 *
 *           InProcessPubSub <.. InProcessEventDispatcher
 *           InProcessPubSub <.. InProcessEventPublisher
 *
 * @enduml
 *         -->
 */
package eapli.framework.infrastructure.eventpubsub.impl.inprocess;