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

import eapli.framework.domain.model.Immutable;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * A standard numeral system using digits and the letters of the Roman alphabet
 * as symbols in their natural order.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Immutable
public class StandardNumeralSystem implements NumeralSystem {

    private static final long serialVersionUID = 1699227365061045769L;

    private final int base;

    private static final String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * the binary system, i.e., {0, 1}
     */
    public static final NumeralSystem BINARY = new StandardNumeralSystem(2);

    /**
     * the octal system, i.e., 0 - 7
     */
    public static final NumeralSystem OCTAL = new StandardNumeralSystem(8);

    /**
     * the decimal system, i.e., 0 - 9
     */
    public static final NumeralSystem DECIMAL = new StandardNumeralSystem(10);

    /**
     * the hexadecimal system, i.e., 0 - F
     */
    public static final NumeralSystem HEXADECIMAL = new StandardNumeralSystem(16);

    /**
     * constructs a numeral system.
     *
     * @param base
     *            the base of representation
     */
    public StandardNumeralSystem(final int base) {
        Preconditions.ensure(base >= 2 && base <= SYMBOLS.length(), "base must be between 2 and " + SYMBOLS.length());

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
    public boolean equals(final Object other) {
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
        return new HashCoder().with(base).code();
    }
}
