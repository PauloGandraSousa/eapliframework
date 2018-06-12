/**
 *
 */
package eapli.framework.infrastructure.eventpubsub;

import eapli.framework.domain.events.DomainEvent;

/**
 * an observer of domain events
 *
 * @author SOU03408
 *
 */
@FunctionalInterface
public interface EventHandler {

    void onEvent(DomainEvent domainEvent);
}
