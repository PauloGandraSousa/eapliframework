/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.infrastructure.pubsub.impl.simplepersistent.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.actions.TimedActions;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import eapli.framework.infrastructure.pubsub.EventHandler;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventConsumptionRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventRecordRepository;
import eapli.framework.util.Utility;
import eapli.framework.validations.Invariants;

/**
 * Provides access to the persistent event publisher and dispatcher for
 * scenarios without spring dependency injection.
 * <p>
 * In order to properly use the Pub/Sub it is necessary to call its
 * {@link #configure} method in the start of the application to inject the proper
 * repository implementations.
 *
 * @author Paulo Gandra de Sousa 10/05/2022
 *
 * @see SimplePersistentPublisherDispatcher
 */
@Utility
public final class SimplePersistentPubSub {

    private static final Logger LOGGER = LogManager.getLogger(SimplePersistentPubSub.class);

    /**
     * @author Paulo Gandra de Sousa 10/05/2022
     */
    private static class PubSubWrapper implements EventDispatcher, EventPublisher {
        private final SimplePersistentPublisherDispatcher pubsub;

        private volatile boolean timerInitialized;

        public PubSubWrapper(final EventRecordRepository eventRepo, final EventConsumptionRepository consumptionRepo,
                final String instanceKey, final int poolInterval) {
            pubsub = new SimplePersistentPublisherDispatcher(eventRepo, consumptionRepo);
            pubsub.setInstanceKey(instanceKey);
            if (!timerInitialized) {
                TimedActions.atFixedRate(pubsub::pollEvents, poolInterval);
                timerInitialized = true;
            }
        }

        @Override
        public void publish(final DomainEvent event) {
            pubsub.publish(event);
        }

        @Override
        public void subscribe(final EventHandler observer,
                @SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
            pubsub.subscribe(observer, events);
        }

        @Override
        public void unsubscribe(final EventHandler observer,
                @SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
            pubsub.unsubscribe(observer, events);
        }

        @Override
        public void unsubscribe(final EventHandler observer) {
            pubsub.unsubscribe(observer);
        }

        @Override
        public void shutdown() {
            TimedActions.shutdownRecurringTasks();
            LOGGER.debug("Shuting down the event dispatcher");
        }
    }

    private static PubSubWrapper instance;

    private SimplePersistentPubSub() {
        // avoid instantiation
    }

    /**
     * Helper method to initialize the registry in case you are not using Spring Dependency
     * Injection. This method should be called <strong>only once</strong> on application
     * startup.
     *
     * @param eventRepo
     * @param consumptionRepo
     * @param instanceKey
     * @param poolInterval
     */
    public static synchronized void configure(final EventRecordRepository eventRepo,
            final EventConsumptionRepository consumptionRepo,
            final String instanceKey, final int poolInterval) {
        Invariants.ensure(instance == null, "Pub/Sub already configured in this application");
        instance = new PubSubWrapper(eventRepo, consumptionRepo, instanceKey, poolInterval);
    }

    public static EventDispatcher dispatcher() {
        return instance();
    }

    public static EventPublisher publisher() {
        return instance();
    }

    private static PubSubWrapper instance() {
        Invariants.noneNull(instance, "Pub/Sub was  not configured");
        return instance;
    }
}
