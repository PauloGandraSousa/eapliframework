/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.inprocess;

import org.springframework.stereotype.Component;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.util.Singleton;

/**
 * A simple global event dispatcher to be used for in-process event dispatching.
 *
 * Note that event dispatcher will keep a reference to the event handler unless
 * it is unsubscribed, potentially causing a "memory leak" if you forget to
 * unsubscribe as the garbage collector won't be able to reclaim the used
 * memory.
 *
 *
 * @author SOU03408
 *
 */
@Component
public final class InProcessEventDispatcher implements Singleton, EventDispatcher {
    private static class LazyHolder {
        private static final EventDispatcher INSTANCE = new InProcessEventDispatcher();

        private LazyHolder() {
        }
    }

    private InProcessEventDispatcher() {
        // ensure global "singleton"
    }

    /**
     * provides access to the component if you are not using dependency injection,
     * e.g, Spring
     *
     * @return
     */
    public static EventDispatcher instance() {
        return LazyHolder.INSTANCE;
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#subscribe(eapli.framework.
     * domain.events.EventHandler, java.lang.Class)
     */
    @Override
    public synchronized void subscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
        InProcessPubSub.dispatcher().subscribe(observer, events);
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
     * framework. domain.events.EventHandler, java.lang.Class)
     */
    @Override
    public void unsubscribe(final EventHandler observer, final Class<? extends DomainEvent>... events) {
        InProcessPubSub.dispatcher().unsubscribe(observer, events);
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
     * framework. domain.events.EventHandler)
     */
    @Override
    public void unsubscribe(final EventHandler observer) {
        InProcessPubSub.dispatcher().unsubscribe(observer);
    }

    @Override
    public void shutdown() {
        InProcessPubSub.dispatcher().shutdown();
    }
}
