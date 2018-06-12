/**
 *
 */
package eapli.framework.domain.identities.impl;

import eapli.framework.domain.identities.IdentityGenerator;
import eapli.framework.util.Preconditions;

/**
 * An id generator that uses a prefix and a sequence number, e.g., AX00001
 *
 * @author sou03408
 *
 */
public class PrefixedNumberSequenceGenerator implements IdentityGenerator<String> {

    private final String prefix;
    private final String mask;
    private final NumberSequenceGenerator numberPartGenerator;

    public PrefixedNumberSequenceGenerator(String prefix, int length) {
        this(prefix, length, 0L);
    }

    public PrefixedNumberSequenceGenerator(String prefix, int length, Long seed) {
        Preconditions.nonEmpty(prefix);
        Preconditions.isPositive(length);

        this.prefix = prefix;
        mask = "%s%0" + length + "d";
        numberPartGenerator = new NumberSequenceGenerator(seed);
    }

    @Override
    public String newId() {
        return String.format(mask, prefix, numberPartGenerator.newId());
    }
}
