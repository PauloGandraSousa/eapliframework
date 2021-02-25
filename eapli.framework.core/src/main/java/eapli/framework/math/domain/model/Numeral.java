/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.math.domain.model;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * An integer number in any base. Allows to easily convert to/from decimal,
 * binary, octal and hexadecimal bases.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class Numeral implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final NumeralSystem system;
    private final transient long value;
    private final String representation;

    /**
     * Constructs from a decimal value into a certain numeral system.
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
     * Constructs from a certain base.
     *
     * @param value
     * @param base
     */
    private Numeral(final String value, final NumeralSystem system) {
        Preconditions.nonEmpty(value, "value must have content");
        Preconditions.nonNull(system, "You need to specify a numeral system");
        Preconditions.ensure(system.isValidNumeral(value),
                "value '" + value + "' (base " + system.base()
                        + ") has invalid symbol(s). Allowed symbols are: " + system.symbols());

        this.system = system;
        this.value = NumeralConverter.decimalValue(value, system);
        representation = value;
    }

    /**
     * Factory method to construct a new numeral from a string representation in
     * a certain numeral system.
     *
     * @param value
     *            the representation of the number
     * @param system
     *            the numeral system used in the representation
     * @return the new Numeral
     */
    public static Numeral valueOf(final String value, final NumeralSystem system) {
        return new Numeral(value, system);
    }

    /**
     * Utility factory method that uses the {@link StandardNumeralSystem}.
     *
     * @param value
     * @param base
     * @return the new Numeral
     */
    public static Numeral valueOf(final String value, final int base) {
        return new Numeral(value, new StandardNumeralSystem(base));
    }

    /**
     * Factory method for decimals.
     *
     * @param decimal
     * @return the new Numeral
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
        return new HashCoder().with(system).with(value).code();
    }

    @Override
    public String toString() {
        return representation;
    }

    /**
     * Returns the decimal value of this number.
     *
     * @return the decimal value of this number.
     */
    public long decimalValue() {
        return value;
    }

    public NumeralSystem system() {
        return system;
    }
}
