/**
 *
 */
package eapli.framework.domain.model.math;

import eapli.framework.util.Preconditions;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;

/**
 * @author SOU03408
 *
 */
public class StandardNumeralSystem implements ValueObject, NumeralSystem {

    private static final long serialVersionUID = 1L;

    private final int base;
    private static final String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final NumeralSystem BINARY = new StandardNumeralSystem(2);
    public static final NumeralSystem OCTAL = new StandardNumeralSystem(8);
    public static final NumeralSystem DECIMAL = new StandardNumeralSystem(10);
    public static final NumeralSystem HEXADECIMAL = new StandardNumeralSystem(16);

    public StandardNumeralSystem(int base) {
        Preconditions.ensure(() -> base >= 2 && base <= SYMBOLS.length(),
                "base must be between 2 and " + SYMBOLS.length());

        this.base = base;
    }

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.domain.math.NumeralSystem#symbols()
     */
    @Override
    public String symbols() {
        return SYMBOLS.substring(0, base);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!NumeralSystem.class.isInstance(other)) {
            return false;
        }

        final NumeralSystem that = (NumeralSystem) other;
        return this.symbols().equals(that.symbols());
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(base).code();
    }
}
