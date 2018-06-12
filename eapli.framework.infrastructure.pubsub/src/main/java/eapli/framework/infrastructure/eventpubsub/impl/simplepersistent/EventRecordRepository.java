/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import eapli.framework.persistence.repositories.DataRepository;

/**
 * @author pgsou_000
 *
 */
interface EventRecordRepository extends DataRepository<EventRecord, Long> {

    Iterable<EventRecord> findNotConsumed(String instanceKey);

}
