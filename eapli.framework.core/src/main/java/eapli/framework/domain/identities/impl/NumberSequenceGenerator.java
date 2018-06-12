/**
 *
 */
package eapli.framework.domain.identities.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.domain.identities.IdentityGenerator;

/**
 * @author sou03408
 *
 */
public class NumberSequenceGenerator implements IdentityGenerator<Long> {
    private static final Logger LOGGER = LogManager.getLogger(NumberSequenceGenerator.class);

    private volatile Long lastId;

    public NumberSequenceGenerator() {
        lastId = Long.valueOf(0L);
    }

    public NumberSequenceGenerator(Long seed) {
        lastId = Long.valueOf(seed);
    }

    @Override
    public Long newId() {
        synchronized (this) {
            lastId++;
        }
        LOGGER.trace("Generated id {}", lastId);
        return lastId;
    }
}
