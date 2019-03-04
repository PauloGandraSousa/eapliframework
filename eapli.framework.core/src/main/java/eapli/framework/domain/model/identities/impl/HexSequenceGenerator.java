/**
 *
 */
package eapli.framework.domain.model.identities.impl;

import eapli.framework.domain.model.identities.IdentityGenerator;

/**
 * An id generator that produces an hexadecimal representation
 *
 * @author sou03408
 *
 */
public class HexSequenceGenerator implements IdentityGenerator<String> {

    private final NumberSequenceGenerator generator;

    public HexSequenceGenerator() {
        generator = new NumberSequenceGenerator();
    }

    public HexSequenceGenerator(Long seed) {
        generator = new NumberSequenceGenerator(seed);
    }

    @Override
    public String newId() {
        return String.format("%x", generator.newId());
    }
}
