/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import org.springframework.data.repository.CrudRepository;

/**
 * @author SOU03408
 *
 */
interface SpringDataEventConsumptionRepository
        extends EventConsumptionRepository, CrudRepository<EventConsumption, Long> {

}
