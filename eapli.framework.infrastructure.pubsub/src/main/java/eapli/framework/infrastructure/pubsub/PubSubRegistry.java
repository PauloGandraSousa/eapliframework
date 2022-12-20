/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.infrastructure.pubsub;

import eapli.framework.util.Utility;
import eapli.framework.validations.Invariants;

/**
 * Simple global registry of the Pub/Sub engine for scenarios without dependency injection.
 *
 * @author Paulo Gandra de Sousa 13/06/2019
 */
@Utility
public final class PubSubRegistry {

    private static EventDispatcher dispatcher;
    private static EventPublisher publisher;

    private PubSubRegistry() {
        // avoid instantiation
    }

    /**
     * Helper method to initialize the registry.
     *
     * @param dispatcher
     * @param publisher
     */
    public static void configure(final EventDispatcher dispatcher, final EventPublisher publisher) {
        Invariants.ensure(PubSubRegistry.dispatcher == null && PubSubRegistry.publisher == null);
        PubSubRegistry.dispatcher = dispatcher;
        PubSubRegistry.publisher = publisher;
    }

    /**
     * Provides access to the component if you are not using dependency
     * injection, e.g, Spring.
     *
     * @return the dispatcher
     */
    public static EventDispatcher dispatcher() {
        Invariants.noneNull(dispatcher, publisher);
        return dispatcher;
    }

    /**
     * Provides access to the component if you are not using dependency
     * injection, e.g, Spring.
     *
     * @return the publisher
     */
    public static EventPublisher publisher() {
        Invariants.noneNull(dispatcher, publisher);
        return publisher;
    }
}
