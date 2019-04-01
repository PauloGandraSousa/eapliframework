/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.util.Preconditions;

/**
 * @author Paulo Gandra de Sousa
 *
 */
@Entity
@Table(name = "PUBSUB_EVENT_CONSUMPTION")
class EventConsumption implements AggregateRoot<Long> {
    @Id
    @GeneratedValue
    private Long pk;

    @ManyToOne
    private EventRecord event;
    private String consumerName;

    public EventConsumption(final String consumerName, final EventRecord event) {
        Preconditions.nonNull(event);
        Preconditions.nonEmpty(consumerName);

        this.consumerName = consumerName;
        this.event = event;
    }

    protected EventConsumption() {
        // for ORM
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof EventConsumption)) {
            return false;
        }
        final EventConsumption that = (EventConsumption) other;
        return new EqualsBuilder().append(pk, that.pk).append(event, that.event).append(consumerName, that.consumerName)
                .isEquals();
    }

    @Override
    public Long id() {
        return pk;
    }

    @Override
    public String toString() {
        return consumerName + " consumed event " + event;
    }
}
