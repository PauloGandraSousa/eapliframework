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
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;

/**
 * A record of an event.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Entity
@Table(name = "PUBSUB_EVENT_RECORD")
class EventRecord implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    private final String eventName;
    private final String publisherName;
    @Temporal(TemporalType.TIMESTAMP)
    private final Calendar occurredAt;

    @Lob
    private final byte[] event;

    public EventRecord(final String publisherName, final DomainEvent event) {
        Preconditions.nonNull(event);

        this.publisherName = publisherName;
        eventName = event.getClass().getName();
        this.event = SerializationUtils.serialize(event);
        occurredAt = Calendars.now();
    }

    protected EventRecord() {
        // for ORM
        eventName = null;
        event = null;
        publisherName = null;
        occurredAt = null;
    }

    public DomainEvent event() {
        return SerializationUtils.deserialize(event);
    }

    public String publisher() {
        return publisherName;
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof EventRecord)) {
            return false;
        }
        final EventRecord that = (EventRecord) other;
        return new EqualsBuilder().append(pk, that.pk).append(event, that.event).append(eventName, that.eventName)
                .isEquals();
    }

    @Override
    public Long identity() {
        return pk;
    }

    @Override
    public String toString() {
        return "Record event " + event;
    }
}
