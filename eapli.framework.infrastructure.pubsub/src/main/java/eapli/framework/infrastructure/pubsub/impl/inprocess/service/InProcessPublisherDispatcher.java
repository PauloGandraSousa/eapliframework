/*
 * Copyright (c) 2013-2024 the original author or authors.
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
package eapli.framework.infrastructure.pubsub.impl.inprocess.service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import eapli.framework.infrastructure.pubsub.EventHandler;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.validations.Preconditions;

/**
 * A simple global event dispatcher to be used for in-process event dispatching.
 * <p>
 * Note that event dispatcher will keep a reference to the event handler unless
 * it is unsubscribed, potentially causing a "memory leak" if you forget to
 * unsubscribe as the garbage collector won't be able to reclaim the used
 * memory.
 * <p>
 * Publishing an event is performed in a separate thread of execution from the
 * calling thread.
 * <p>
 * This class can be used as a Spring Component.
 *
 * @author Paulo Gandra de Sousa
 * @see InProcessPubSub
 */
@Component
public class InProcessPublisherDispatcher implements EventDispatcher, EventPublisher {

	private static final Logger LOGGER = LogManager.getLogger(InProcessPublisherDispatcher.class);

	private final Map<Class<? extends DomainEvent>, Queue<EventHandler>> handlers = new ConcurrentHashMap<>();

	// thread executor pool for the events
	private final ExecutorService executor = Executors.newFixedThreadPool(5);

	/* package */ InProcessPublisherDispatcher() {
		// ensure package instantiation only
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eapli.framework.domain.events.EventDispatcher#subscribe(eapli.framework.
	 * domain.events.EventHandler, java.lang.Class)
	 */
	@Override
	public void subscribe(final EventHandler observer,
			@SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
		Preconditions.nonNull(observer);

		for (final Class<? extends DomainEvent> classz : events) {
			final Queue<EventHandler> observers = new ConcurrentLinkedQueue<>();
			observers.add(observer);
			handlers.merge(classz, observers, (o, n) -> {
				o.add(observer);
				return o;
			});
		}
		LOGGER.debug("{} subscribed to events {}", observer.getClass().getSimpleName(), events);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
	 * framework. domain.events.EventHandler, java.lang.Class)
	 */
	@Override
	public void unsubscribe(final EventHandler observer,
			@SuppressWarnings("unchecked") final Class<? extends DomainEvent>... events) {
		Preconditions.nonNull(observer);

		for (final Class<? extends DomainEvent> classz : events) {
			final var observers = handlers.get(classz);
			if (observers != null) {
				observers.remove(observer);
			}
		}
		LOGGER.debug("{} unsubscribed to events {}", observer.getClass().getSimpleName(), events);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eapli.framework.domain.events.EventDispatcher#unsubscribe(eapli.
	 * framework. domain.events.EventHandler)
	 */
	@Override
	public void unsubscribe(final EventHandler observer) {
		handlers.values().forEach(l -> l.remove(observer));
		LOGGER.debug("{} subscribed to all events", observer.getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eapli.framework.domain.events.EventDispatcher#publish(eapli.framework.
	 * domain. events.DomainEvent)
	 */
	@Override
	public void publish(final DomainEvent event) {
		Preconditions.nonNull(event, "Cannot publish a null event");

		final var observers = handlers.get(event.getClass());
		if (observers != null) {
			publishToSubscribers(event, observers);
		}
	}

	private void publishToSubscribers(final DomainEvent event, final Queue<EventHandler> observers) {
		// Publish the event in a separate thread to the different subscribers. This way
		// the "main" thread can continue even if the events are not yet published.
		executor.execute(() -> {
			LOGGER.debug("Publishing event {} to subscribed observers (in thread {})", event,
					Thread.currentThread().getId());
			observers.forEach(observer -> publishToOneSubscriber(event, observer));
		});
	}

	private void publishToOneSubscriber(final DomainEvent event, final EventHandler observer) {
		// Each event handler in turn, will be called in a separate thread so each
		// subscriber can act isolated and without order dependency on each other.
		executor.execute(() -> {
			LOGGER.debug("Publishing event {} to observer {} in thread {}", event, observer,
					Thread.currentThread().getId());
			observer.onEvent(event);
		});
	}

	@Override
	public void shutdown() {
		executor.shutdown();
		// wait at most 60 seconds for all threads to finish. if not, force the
		// termination
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (final InterruptedException ex) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
