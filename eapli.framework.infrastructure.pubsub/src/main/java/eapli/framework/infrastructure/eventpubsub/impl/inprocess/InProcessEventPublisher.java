/**
 *
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
 * @author SOU03408
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
