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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import eapli.framework.infrastructure.pubsub.EventHandler;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.model.EventConsumption;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.model.EventRecord;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventConsumptionRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventRecordRepository;

/**
 * A simple event dispatcher using database tables to persist events and allow
 * for out of process publish-subscribe.
 * <p>
 * This class is just for demonstration purposes. you should consider using a
 * true pub/sub mechanism such as message queueing instead of this class.
 * <p>
 * This class is prepared to be used by Spring Dependency Injection. The client application must
 * enable spring scheduling thru the use of <code>@EnableScheduling</code>
 *
 * @author Paulo Gandra de Sousa
 *
 * @see SimplePersistentPubSub
 */
@Component
public class SimplePersistentPublisherDispatcher implements EventDispatcher, EventPublisher {

    private static final Logger LOGGER = LogManager.getLogger(SimplePersistentPublisherDispatcher.class);

    private final EventRecordRepository eventRepo;

    private final EventConsumptionRepository consumptionRepo;

    // reuse in process dispatcher
    private final EventDispatcher inprocDispatcher = InProcessPubSub.dispatcher();

    // reuse in process publisher
    private final EventPublisher inprocPublisher = InProcessPubSub.publisher();

    /**
     * Identify the consumer application instance. useful if you have multiple
     * instances of the same application and want to dispatch events to all of them.
     */
    @Value("${eapli.framework.pubsub.instanceKey}")
    private String instanceKey;

    /**
     * Interval (in milliseconds) between poolings to the event store.
     */
    @Value("${eapli.framework.pubsub.poolInterval}")
    private int poolInterval;

    @Autowired
    public SimplePersistentPublisherDispatcher(final EventRecordRepository eventRepo,
            final EventConsumptionRepository consumptionRepo) {
        this.eventRepo = eventRepo;
        this.consumptionRepo = consumptionRepo;
    }

    /**
     * Helper method for scenarios without Spring
     *
     * @param instanceKey
     */
    /* package */void setInstanceKey(final String instanceKey) {
        this.instanceKey = instanceKey;
    }

    /**
     * This method is public but "not published" - it should never be
     * called directly from client code.
     * <p>
     * The method needs to be public due to the use of the
     * {@link org.springframework.transaction.annotation.Transactional Transactional} annotation.
     *
     * @return <code>true</code>
     */
    @Transactional
    @Scheduled(fixedDelayString = "${eapli.framework.pubsub.poolInterval}")
    public boolean pollEvents() {
        LOGGER.trace("Polling events from database");
        try {
            final var notConsumedEvents = eventRepo.findNotConsumed(instanceKey);
            notConsumedEvents.forEach(this::storeConsumptionAndPublish);
        } catch (final Exception e) {
            LOGGER.error("Error polling events", e);
            // TODO we should have some kind of exception handling here, but since this an
            // asynchronous call it is tricky...
        }
        return true;
    }

    private void storeConsumptionAndPublish(final EventRecord eventRecord) {
        final var entity = new EventConsumption(instanceKey, eventRecord);
        try {
            consumptionRepo.save(entity);
            LOGGER.trace("Stored event consumption {}", entity);
            inprocPublisher.publish(eventRecord.event());
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.error("Error storing consumption {} of event {}", entity, eventRecord, e);
            // TODO we should have some kind of exception handling here, but since this an
            // asynchronous call it is tricky...
        }
    }

    @Override
    @Transactional
    public void publish(final DomainEvent event) {
        storeEvent(event);

        // we just store the event; it will be polled and marked as consumed by the scheduled taks
        // and will be propagated in memory
    }

    private void storeEvent(final DomainEvent event) {
        final var eventRecord = new EventRecord(instanceKey, event);
        try {
            eventRepo.save(eventRecord);
            LOGGER.trace("Stored event {}", event);
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.error("Error storing event {}", event, e);
            throw e;
        }
    }

    @Override
    public void subscribe(final EventHandler observer,
            @SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {

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
        // NOP
    }
}
