/*
 * Copyright (c) 2013-2020 the original author or authors.
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

import eapli.framework.util.Utility;

/**
 * Utility class (private to the package) to extract the conversion logic from
 * the Numeral class and enforce the Single Responsibility Principle.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
/* package */class NumeralConverter {

    private NumeralConverter() {
        // ensure utility
    }

    /**
     * Converts a value from a given base to decimal.
     *
     * <p>
     * Algorithm:
     *
     * <ol>
     * <li>Let {@code n} be the number of digits in the number. For example, 104
     * has 3 digits, so n=3.
     * <li>Let {@code b} be the base of the number. For example, 104 decimal is
     * base 10, so b = 10.
     * <li>Let {@code s} be a running total, initially 0.
     * <li>For each {@code digit} in the number, working left to right do:
     * <ol>
     * <li>Subtract 1 from {@code n}.
     * <li>Multiply the {@code digit} times {@code b^n} and add it to {@code s}.
     * </ol>
     * </li> end for
     * </ol>
     *
     * <p>
     * When your done with all the digits in the number, its decimal value will
     * be {@code s}
     *
     * @param value
     * @param system
     * @return the decimal value
     */
    public static long decimalValue(final String value, final NumeralSystem system) {
        final int base = system.base();
        int n = value.length();
        long s = 0;
        for (int i = 0; i < value.length(); i++) {
            n--;
            s += system.digit(value.charAt(i)) * eapli.framework.math.util.Math.pow(base, n);
        }
        return s;
    }

    /**
     * Converts a number from base 10 to a given base.
     *
     * <p>
     * Algorithm:
     *
     * <ol>
     * <li>Let {@code n} be the decimal number.
     * <li>Let {@code m} be the number, initially empty, that we are converting
     * to. We'll be composing it right to left.
     * <li>Let {@code b} be the base of the number we are converting to.
     * <li>Repeat until {@code n} becomes 0
     * <ol>
     * <li>Divide {@code n} by {@code b}, letting the result be {@code d} and
     * the remainder be {@code r}.
     * <li>Write the remainder, {@code r}, as the leftmost digit of {@code b}.
     * <li>Let {@code d} be the new value of {@code n}.
     * </ol>
     * <li>end repeat
     * </ol>
     *
     * @param value
     * @param system
     * @return the representation of the decimal value in the specified base
     */
    public static String representationOf(final long value, final NumeralSystem system) {
        if (value == 0) {
            return system.zero();
        }

        final long b = system.base();
        long n = value;
        final StringBuilder m = new StringBuilder();
        while (n > 0) {
            final long d = n / b;
            final int r = (int) (n % b);
            m.append(system.symbol(r));
            n = d;
        }
        return m.reverse().toString();
    }
}
