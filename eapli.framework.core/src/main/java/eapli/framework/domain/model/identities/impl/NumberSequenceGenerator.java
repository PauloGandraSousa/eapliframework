/**
 *
 */
package eapli.framework.domain.model.identities.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.domain.model.identities.IdentityGenerator;
import eapli.framework.util.Invariants;

/**
 * @author sou03408
 *
 */
public class NumberSequenceGenerator implements IdentityGenerator<Long> {
    private static final Logger LOGGER = LogManager.getLogger(NumberSequenceGenerator.class);

    private volatile long lastId;

    public NumberSequenceGenerator() {
        lastId = 0L;
    }

    public NumberSequenceGenerator(final Long seed) {
        Invariants.nonNull(seed);
        lastId = seed;
    }

    @Override
    public Long newId() {
        synchronized (this) {
            lastId++;
        }
        LOGGER.trace("Generated id {}", lastId);
        return Long.valueOf(lastId);
    }
}
