/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eapli.framework.actions.TimedActions;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;

/**
 * A simple event dispatcher using database tables to persist events and allow for out of process
 * publish-subscribe.
 * <p>
 * This class is just for demonstration purposes. you should consider using a true pub/sub mechanism
 * such as message queueing instead of this class.
 * <p>
 * This class is only prepared to be used by Spring Dependency Injection
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Component
public class SimplePersistentEventPubSub implements EventDispatcher, EventPublisher {

    private static final Logger LOGGER = LogManager.getLogger(SimplePersistentEventPubSub.class);

    @Autowired
    private EventRecordRepository eventRepo;

    @Autowired
    private EventConsumptionRepository consumptionRepo;

    private final EventDispatcher inprocDispatcher = InProcessPubSub.dispatcher();
    private final EventPublisher inprocPublisher = InProcessPubSub.publisher();

    /**
     * identify the consumer application instance. useful if you have multiple instances of the same
     * application and want to dispatch events to all of them
     */
    @Value("${eapli.framework.pubsub.instanceKey}")
    private String instanceKey;

    /**
     * interval (in milliseconds) between poolings to the event store
     */
    @Value("${eapli.framework.pubsub.poolInterval}")
    private int poolInterval;

    private volatile boolean timerInitialized;

    private void storeConsumptionAndPublish(final EventRecord record) {
        final EventConsumption entity = new EventConsumption(instanceKey, record);
        try {
            consumptionRepo.save(entity);
            LOGGER.trace("Stored event consumption {}", entity);
            inprocPublisher.publish(record.event());
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.error("Error storing consumption {} of event {}", entity, record, e);
        }
    }

    private boolean pollEvents() {
        LOGGER.trace("Polling events from database");
        try {
            final Iterable<EventRecord> notConsumedEvents = eventRepo.findNotConsumed(instanceKey);
            notConsumedEvents.forEach(this::storeConsumptionAndPublish);
        } catch (final Exception e) {
            LOGGER.error("Error polling events", e);
        }
        return true;
    }

    private void initTimersIfNeeded() {
        if (!timerInitialized) {
            TimedActions.atFixedRate(this::pollEvents, poolInterval);
            timerInitialized = true;
        }
    }

    @Override
    public void publish(final DomainEvent event) {
        initTimersIfNeeded();

        storeEvent(event);

        // we do not immediately publish the event in process as it will be
        // polled and marked as consumed soon enough
    }

    private void storeEvent(final DomainEvent event) {
        final EventRecord record = new EventRecord(instanceKey, event);
        try {
            eventRepo.save(record);
            LOGGER.trace("Stored event {}", event);
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.error("Error storing event {}", event, e);
        }
    }

    @Override
    public void subscribe(final EventHandler observer,
            @SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
        initTimersIfNeeded();

        inprocDispatcher.subscribe(observer, events);
    }

    @Override
    public void unsubscribe(final EventHandler observer,
            @SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
        inprocDispatcher.unsubscribe(observer, events);
    }

    @Override
    public void unsubscribe(final EventHandler observer) {
        inprocDispatcher.unsubscribe(observer);
    }

    @Override
    public void shutdown() {
        TimedActions.shutdownRecurringTasks();
        LOGGER.debug("Shuting down the event dispatcher");
    }
}
