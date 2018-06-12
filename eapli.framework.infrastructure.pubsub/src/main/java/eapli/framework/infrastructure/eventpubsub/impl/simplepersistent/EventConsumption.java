/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import eapli.framework.domain.AggregateRoot;
import eapli.framework.util.Preconditions;

/**
 * @author pgsou_000
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
