/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.eventpubsub.impl.inprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.util.Preconditions;
import eapli.framework.util.Singleton;

/**
 * A simple global event dispatcher to be used for in-process event dispatching.
 *
 * Note that event dispatcher will keep a reference to the event handler unless
 * it is unsubscribed, potentially causing a "memory leak" if you forget to
 * unsubscribe as the garbage collector won't be able to reclaim the used
 * memory.
 *
 * publishing an event is performed in a separate thread of execution from the
 * calling thread
 *
 * @author Paulo Gandra de Sousa
 *
 */
final class InProcessPubSub implements Singleton, EventDispatcher, EventPublisher {

    private static final Logger LOGGER = LogManager.getLogger(InProcessPubSub.class);

    private final Map<Class<? extends DomainEvent>, List<EventHandler>> handlers = new ConcurrentHashMap<>();

    private static class LazyHolder {
        private static final InProcessPubSub INSTANCE = new InProcessPubSub();

        private LazyHolder() {
        }
    }

    private InProcessPubSub() {
        // ensure global "singleton"
    }

    public static EventDispatcher dispatcher() {
        return LazyHolder.INSTANCE;
    }

    public static EventPublisher publisher() {
        return LazyHolder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#subscribe(eapli.framework.
     * domain.events.EventHandler, java.lang.Class)
     */
    @Override
    public synchronized void subscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
        Preconditions.nonNull(observer);

        for (final Class<? extends DomainEvent> classz : events) {
            final List<EventHandler> observers = new ArrayList<>();
            observers.add(observer);
            handlers.merge(classz, observers, (o, n) -> {
                o.add(n.get(0));
                return o;
            });
        }
        LOGGER.debug("{} subscribed to events {}", observer.getClass().getSimpleName(), events);
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
     * framework. domain.events.EventHandler, java.lang.Class)
     */
    @Override
    public void unsubscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
        Preconditions.nonNull(observer);

        for (final Class<? extends DomainEvent> classz : events) {
            final List<EventHandler> observers = handlers.get(classz);
            if (observers != null) {
                observers.remove(observer);
            }
        }
        LOGGER.debug("{} unsubscribed to events {}", observer.getClass().getSimpleName(), events);
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
     * framework. domain.events.EventHandler)
     */
    @Override
    public void unsubscribe(final EventHandler observer) {
        handlers.values().forEach(l -> l.remove(observer));
        LOGGER.debug("{} subscribed to all events", observer.getClass().getSimpleName());
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#publish(eapli.framework.
     * domain. events.DomainEvent)
     */
    @Override
    public void publish(final DomainEvent event) {
        Preconditions.nonNull(event, "cannot publish null");

        final List<EventHandler> observers = handlers.get(event.getClass());
        if (observers != null) {
            new Thread(() -> observers.forEach(observer -> observer.onEvent(event))).start();
        }
        LOGGER.debug("Publishing event {} to subscribed observers", event);
    }

    @Override
    public void shutdown() {
        // nothing to do
    }
}
