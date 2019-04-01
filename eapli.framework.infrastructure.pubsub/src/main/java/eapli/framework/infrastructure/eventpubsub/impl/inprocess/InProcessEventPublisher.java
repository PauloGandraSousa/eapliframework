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

import org.springframework.stereotype.Component;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.util.Singleton;

/**
 * A simple global event publisher to be used for in-process event dispatching.
 *
 * publishing an event is performed in a separate thread of execution from the
 * calling thread
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Component
public final class InProcessEventPublisher implements Singleton, EventPublisher {

    private static class LazyHolder {
        private static final EventPublisher INSTANCE = new InProcessEventPublisher();

        private LazyHolder() {
        }
    }

    private InProcessEventPublisher() {
        // ensure global "singleton"
    }

    /**
     * provides access to the component if you are not using dependency injection,
     * e.g, Spring
     *
     * @return
     */
    public static EventPublisher instance() {
        return LazyHolder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#publish(eapli.framework.
     * domain. events.DomainEvent)
     */
    @Override
    public void publish(final DomainEvent event) {
        InProcessPubSub.publisher().publish(event);
    }
}
