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
package eapli.framework.domain.events;

import java.io.Serializable;
import java.util.Calendar;

import eapli.framework.domain.model.ValueObject;

/**
 * A Domain event is a domain/driven design pattern to signal significant events that have happened
 * in the domain.
 * <p>
 * These are not technical issues/events but meaningful domain concepts.
 * </p>
 *
 * Quoting
 * <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Eric
 * Evans</a>:
 * <blockquote>
 * Model information about activity in the domain as a series of discrete events. Represent
 * each event as a domain object. These are distinct from system events that reflect activity
 * within the software itself, although often a system event is associated with a domain
 * event, either as part of a response to the domain event or as a way of carrying information
 * about the domain event into the system.
 * <p>
 * A domain event is a full-fledged part of the domain model, a representation of something
 * that happened in the domain. Ignore irrelevant domain activity while making explicit the
 * events that the domain experts want to track or be notified of, or which are associated
 * with state change in the other model objects.
 * </blockquote>
 *
 * <p>
 * Domain events are published by {@link eapli.framework.domain.model.AggregateRoot AggregateRoots}
 * or {@link eapli.framework.domain.services.DomainService Domain} or
 * {@link eapli.framework.application.ApplicationService Application services} and are handled
 * by <a href="https://refactoring.guru/design-patterns/observer">Observers</a>.
 *
 * @author Paulo Gandra Sousa
 */
public interface DomainEvent extends ValueObject, Serializable {

    /**
     * Returns the date and time when the event occurred in the domain.
     *
     * @return the date and time when the event occurred in the domain
     */
    Calendar occurredAt();

    /**
     * Returns the date and time when the event was recorded in the system.
     *
     * @return the date and time when the event was recorded in the system
     */
    Calendar registeredAt();
}
