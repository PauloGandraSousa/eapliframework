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

import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;

/**
 * Base class for domain events.
 *
 * @author Paulo Gandra Sousa
 */
public abstract class DomainEventBase implements DomainEvent {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    private final Calendar occuredAt;

    @Temporal(TemporalType.TIMESTAMP)
    private final Calendar registeredAt;

    /**
     * Constructs the object registering its occurrence at the current instant.
     */
    protected DomainEventBase() {
        occuredAt = registeredAt = Calendars.now();
    }

    /**
     * Constructs the object registering its occurrence at the specified instant.
     *
     * @param occuredAt
     */
    protected DomainEventBase(final Calendar occuredAt) {
        Preconditions.nonNull(occuredAt);

        this.occuredAt = occuredAt;
        registeredAt = Calendars.now();
    }

    @Override
    public Calendar occurredAt() {
        return occuredAt;
    }

    @Override
    public Calendar registeredAt() {
        return registeredAt;
    }
}
