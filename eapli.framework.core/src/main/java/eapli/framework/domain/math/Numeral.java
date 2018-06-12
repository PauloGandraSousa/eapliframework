/**
 *
 */
package eapli.framework.domain.math;

import eapli.framework.domain.ValueObject;
import eapli.framework.util.Preconditions;
import eapli.framework.util.HashCoder;

/**
 * an integer number in any base. allows to easily convert to/from decimal,
 * binary, octal and hexadecimal bases
 *
 * @author sou03408
 *
 */
public class Numeral implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final NumeralSystem system;
    private final transient long value;
    private final String representation;

    /**
     * constructs from a decimal value into a certain numeral system
     *
     * @param value
     *            the decimal value of the number
     */
    private Numeral(final long value, final NumeralSystem system) {
        Preconditions.nonNull(system, "You need to specify a numeral system");
        Preconditions.nonNegative(value, "value must be non negative");

        this.value = value;
        this.system = system;
        representation = NumeralConverter.representationOf(value, system);
    }

    /**
     * constructs from a certain base
     *
     * @param value
     * @param base
     */
    private Numeral(final String value, final NumeralSystem system) {
        Preconditions.nonEmpty(value, "value must have content");
        Preconditions.nonNull(system, "You need to specify a numeral system");
        Preconditions.ensure(() -> system.isValidNumeral(value), "value '" + value + "' (base " + system.base()
                + ") has invalid symbol(s). Allowed symbols are: " + system.symbols());

        this.system = system;
        this.value = NumeralConverter.decimalValue(value, system);
        representation = value;
    }

    /**
     * factory method to construct a new numeral from a string representation in a
     * certain numeral system
     *
     * @param value
     *            the representation of the number
     * @param system
     *            the numeral system used in the representation
     * @return
     */
    public static Numeral valueOf(final String value, final NumeralSystem system) {
        return new Numeral(value, system);
    }

    /**
     * utility factory method that uses the StandardNumeralSystem
     *
     * @param value
     * @param base
     * @return
     */
    public static Numeral valueOf(final String value, final int base) {
        return new Numeral(value, new StandardNumeralSystem(base));
    }

    /**
     *
     * @param decimal
     * @return
     */
    public static Numeral valueOf(final long decimal) {
        return new Numeral(decimal, StandardNumeralSystem.DECIMAL);
    }

    public Numeral toBase2() {
        return new Numeral(value, StandardNumeralSystem.BINARY);
    }

    public Numeral toBase8() {
        return new Numeral(value, StandardNumeralSystem.OCTAL);
    }

    public Numeral toBase10() {
        return new Numeral(value, StandardNumeralSystem.DECIMAL);
    }

    public Numeral toBase16() {
        return new Numeral(value, StandardNumeralSystem.HEXADECIMAL);
    }

    public Numeral toBase(final int base) {
        return new Numeral(value, new StandardNumeralSystem(base));
    }

    public Numeral toBase(final NumeralSystem base) {
        return new Numeral(value, base);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Numeral)) {
            return false;
        }
        final Numeral that = (Numeral) other;
        return value == that.value && system.equals(that.system);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(system).of(value).code();
    }

    @Override
    public String toString() {
        return representation;
    }

    /**
     * returns the decimal value of this number
     *
     * @return
     */
    public long decimalValue() {
        return value;
    }

    public NumeralSystem system() {
        return system;
    }
}
