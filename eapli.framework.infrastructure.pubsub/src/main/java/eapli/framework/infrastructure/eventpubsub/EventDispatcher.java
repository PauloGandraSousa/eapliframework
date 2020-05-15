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
package eapli.framework.infrastructure.eventpubsub;

import eapli.framework.domain.events.DomainEvent;

/**
 * An Event dispatcher allows interested parties of the system (Observers) to register their
 * interest in certain Domain Events and be notified when those events occur.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public interface EventDispatcher {

    /**
     * Subscribe interest to certain events in the domain. when those events happen in the system the
     * observer will be notified via the {@link EventHandler#onEvent(DomainEvent)} method.
     *
     * @param observer
     *                 the interested party
     * @param events
     *                 the events it is interested in
     */
    void subscribe(EventHandler observer, @SuppressWarnings("unchecked") Class<? extends DomainEvent>... events);

    /**
     * Unsubscribe from certain events.
     *
     * @param observer
     *                 a previous registered observer
     * @param events
     *                 the events it is unsubscribing from. if some event was not subscribed in the
     *                 first place it is simply ignored and no exception is thrown
     */
    void unsubscribe(EventHandler observer, @SuppressWarnings("unchecked") Class<? extends DomainEvent>... events);

    /**
     * Unsubscribe from all previously subscribed events.
     *
     * @param observer
     */
    void unsubscribe(EventHandler observer);

    /**
     * Signals the event dispatcher that the application no longer wants to process any event (typically
     * because it is closing) and that any pending thread should stop execution as soon as possible.
     */
    void shutdown();
}