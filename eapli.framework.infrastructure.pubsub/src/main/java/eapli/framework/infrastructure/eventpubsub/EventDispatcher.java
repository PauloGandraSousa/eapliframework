package eapli.framework.infrastructure.eventpubsub;

import eapli.framework.domain.events.DomainEvent;

/**
 * An Event dispatcher allows interested parties of the system (Observers) to
 * register their interest in certain Domain Events and be notified when those
 * events occur.
 *
 * @author pgsou_000
 *
 */
public interface EventDispatcher {

    /**
     * subscribe interest to certain events in the domain. when those events happen
     * in the system the observer will be notified via the EventHandler onEvent()
     * method
     *
     * @param observer
     *            the interested party
     * @param events
     *            the events it is interested in
     */
    void subscribe(EventHandler observer, Class<? extends DomainEvent>... events);

    /**
     * unsubscribe from certain events.
     *
     * @param observer
     *            a previous registered observer
     * @param events
     *            the events it is unsubscribing from. if some event was not
     *            subscribed in the first place it is simply ignored and no
     *            exception is thrown
     */
    void unsubscribe(EventHandler observer, Class<? extends DomainEvent>... events);

    /**
     * unsubscribe from all previously subscribed events
     *
     * @param observer
     */
    void unsubscribe(EventHandler observer);

    /**
     * Signals the event dispatcher that the application no longer wants to process
     * any event (typically because it is closing) and that any pending thread
     * should stop execution as soon as possible
     */
    void shutdown();
}