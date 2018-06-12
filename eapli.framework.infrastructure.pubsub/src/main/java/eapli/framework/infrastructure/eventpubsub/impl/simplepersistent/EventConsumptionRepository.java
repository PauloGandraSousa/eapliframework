/**
 *
 */
package eapli.framework.infrastructure.eventpubsub.impl.simplepersistent;

import eapli.framework.persistence.repositories.DataRepository;

/**
 * @author pgsou_000
 *
 */
interface EventConsumptionRepository extends DataRepository<EventConsumption, Long> {

}
