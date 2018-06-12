package eapli.framework.infrastructure.eventpubsub;

import eapli.framework.domain.events.DomainEvent;

/**
 * An Event publisher publishes events to interested parties. Parties would have
 * mentioned their interest via an EventDispatcher.
 *
 * @author pgsou_000
 *
 */
public interface EventPublisher {
    /**
     * publishes an event in the system, triggering notification of all interested
     * parties. each implementation may deal differently with asynchronicity of this
     * operation; for instance, by executing the notification in a separate thread
     * or in a parallel way
     *
     * @param event
     */
    void publish(DomainEvent event);
}