/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import eapli.framework.domain.AggregateRoot;
import eapli.framework.domain.events.DomainEvent;
import eapli.framework.util.Preconditions;

/**
 * @author pgsou_000
 *
 */
@Entity
@Table(name = "PUBSUB_EVENT_RECORD")
class EventRecord implements AggregateRoot<Long> {

    @Id
    @GeneratedValue
    private Long pk;

    private final String eventName;

    private final String publisherName;

    @Lob
    private final byte[] event;

    public EventRecord(final String publisherName, final DomainEvent event) {
        Preconditions.nonNull(event);

        this.publisherName = publisherName;
        eventName = event.getClass().getName();
        this.event = SerializationUtils.serialize(event);
    }

    protected EventRecord() {
        // for ORM
        eventName = null;
        event = null;
        publisherName = null;
    }

    public DomainEvent event() {
        return SerializationUtils.deserialize(event);
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
    public Long id() {
        return pk;
    }

    @Override
    public String toString() {
        return "Record event " + event;
    }
}
