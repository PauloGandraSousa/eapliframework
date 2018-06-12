/**
 *
 */
package eapli.framework.domain.math;

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
