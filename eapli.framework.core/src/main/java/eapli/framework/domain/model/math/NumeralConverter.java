/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.domain.model.math;

import eapli.framework.util.Utilitarian;

/**
 * utility class (private to the package) to extract the conversion logic from
 * the Numeral class and enforce the Single Responsibility Principle
 *
 * @author SOU03408
 *
 */
class NumeralConverter implements Utilitarian {
    private NumeralConverter() {
        // ensure utility
    }

    /**
     * Algorithm
     *
     * Let n be the number of digits in the number. For example, 104 has 3 digits,
     * so n=3.
     *
     * Let b be the base of the number. For example, 104 is decimal so b = 10.
     *
     * Let s be a running total, initially 0.
     *
     * For each digit in the number, working left to right do:
     *
     * Subtract 1 from n.
     *
     * Multiply the digit times b^n and add it to s.
     *
     * end for
     *
     * When your done with all the digits in the number, its decimal value will be s
     *
     * @param value
     * @param base
     * @return
     */
    public static long decimalValue(final String value, final NumeralSystem system) {
        final int base = system.base();
        int n = value.length();
        long s = 0;
        for (int i = 0; i < value.length(); i++) {
            n--;
            s += system.digit(value.charAt(i)) * eapli.framework.util.Math.pow(base, n);
        }
        return s;
    }

    /**
     * Algorithm
     *
     * Let n be the decimal number.
     *
     * Let m be the number, initially empty, that we are converting to. We'll be
     * composing it right to left.
     *
     * Let b be the base of the number we are converting to.
     *
     * Repeat until n becomes 0
     *
     * Divide n by b, letting the result be d and the remainder be r.
     *
     * Write the remainder, r, as the leftmost digit of b.
     *
     * Let d be the new value of n.
     *
     * end repeat
     *
     * @param value
     * @param fromBase
     * @return
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
