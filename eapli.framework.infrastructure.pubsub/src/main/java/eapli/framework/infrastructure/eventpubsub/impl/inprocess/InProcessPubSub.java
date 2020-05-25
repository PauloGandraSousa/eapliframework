/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
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

import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.util.Utility;

/**
 * Provides access to the in process event publisher and dispatcher for
 * scenarios without spring dependency injection.
 *
 * @author Paulo Gandra de Sousa 13/06/2019
 *
 */
@Utility
public final class InProcessPubSub {
    // make sure that there is only one instance of the Pub/sub
    private static class LazyHolder {
        private static final InProcessPublisherDispatcher INSTANCE = new InProcessPublisherDispatcher();

        private LazyHolder() {
        }
    }

    private InProcessPubSub() {
        // avoid instantiation
    }

    /**
     * Provides access to the component if you are not using dependency
     * injection, e.g, Spring.
     *
     * @return the dispatcher
     */
    public static EventDispatcher dispatcher() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Provides access to the component if you are not using dependency
     * injection, e.g, Spring.
     *
     * @return the publisher
     */
    public static EventPublisher publisher() {
        return LazyHolder.INSTANCE;
    }
}
