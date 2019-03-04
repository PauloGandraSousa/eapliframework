/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import eapli.framework.domain.repositories.Repository;

/**
 * @author pgsou_000
 *
 */
interface EventRecordRepository extends Repository<EventRecord, Long> {

    Iterable<EventRecord> findNotConsumed(String instanceKey);

}
