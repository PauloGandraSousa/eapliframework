/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author SOU03408
 *
 */
interface SpringDataEventRecordRepository extends EventRecordRepository, CrudRepository<EventRecord, Long> {
    @Override
    @Query("SELECT e FROM EventRecord e WHERE e NOT IN (SELECT e FROM EventRecord e, EventConsumption c WHERE c.event = e AND c.consumerName = :instance)")
    Iterable<EventRecord> findNotConsumed(@Param("instance") String instanceKey);
}
