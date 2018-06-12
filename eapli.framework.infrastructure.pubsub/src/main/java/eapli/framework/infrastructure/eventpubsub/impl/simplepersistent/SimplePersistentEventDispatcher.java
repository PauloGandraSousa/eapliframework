package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.actions.TimedActions;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessEventDispatcher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessEventPublisher;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import eapli.framework.util.Singleton;

/**
 * a simple event dispatcher using database tables to persist events and allow
 * for out of process publish-subscribe.
 *
 * this class is just for demonstration purposes. you should consider using a
 * true pub/sub mechanism such as message queueing instead of this class.
 *
 * this class is only prepared to be used by Spring Dependency Injection
 *
 * @author pgsou_000
 *
 */
@Component
public class SimplePersistentEventDispatcher implements EventDispatcher, EventPublisher, Singleton {

    private static final int POLL_EVENTS_INTERVAL = 60000;

    private static final Logger LOGGER = LogManager.getLogger(SimplePersistentEventDispatcher.class);

    @Autowired
    private EventRecordRepository eventRepo;
    @Autowired
    private EventConsumptionRepository consumptionRepo;

    private final EventDispatcher inprocDispatcher = InProcessEventDispatcher.instance();
    private final EventPublisher inprocPublisher = InProcessEventPublisher.instance();

    private volatile String instanceKey;

    /**
     *
     * @param instanceKey
     *            used to identify the consumer application instance. useful if you
     *            have multiple instances of the same application and want to
     *            dispatch events to all of them
     */
    protected SimplePersistentEventDispatcher(final String instanceKey) {
        this.instanceKey = instanceKey;

        init();
    }

    private void storeConsumptionAndPublish(final EventRecord x) {
        final EventConsumption entity = new EventConsumption(instanceKey, x);
        try {
            consumptionRepo.save(entity);
            LOGGER.trace("Stored event consumption {}", entity);
            inprocPublisher.publish(x.event());
        } catch (DataConcurrencyException | DataIntegrityViolationException e) {
            LOGGER.error("Error storing consumption {} of event {}", entity, x, e);
        }
    }

    private boolean pollEvents() {
        LOGGER.debug("Polling events from database");
        try {
            final Iterable<EventRecord> notConsumedEvents = eventRepo.findNotConsumed(instanceKey);
            notConsumedEvents.forEach(this::storeConsumptionAndPublish);
        } catch (final Exception e) {
            LOGGER.error("Error polling events", e);
        }
        return true;
    }

    private void init() {
        TimedActions.atFixedRate(this::pollEvents, POLL_EVENTS_INTERVAL);
    }

    @Override
    public void publish(final DomainEvent event) {
        storeEvent(event);

        // we do not immediately publish the event as it will be polled and
        // marked as consumed soon enough
    }

    private void storeEvent(final DomainEvent event) {
        final EventRecord record = new EventRecord(event);
        try {
            eventRepo.save(record);
            LOGGER.trace("Stored event {}", event);
        } catch (DataConcurrencyException | DataIntegrityViolationException e) {
            LOGGER.error("Error storing event {}", event, e);
        }
    }

    @Override
    public void subscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
        inprocDispatcher.subscribe(observer, events);
    }

    @Override
    public void unsubscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
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
