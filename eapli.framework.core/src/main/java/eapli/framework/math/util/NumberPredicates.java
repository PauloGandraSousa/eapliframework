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
package eapli.framework.math.util;

import eapli.framework.util.Utility;

/**
 * Number Predicates. Predicates are functions that test a condition and return a boolean value. in
 * this case the test is done over numeric (long) arguments.
 *
 * <p>
 * The function signature is
 *
 * <pre>
 * Long -> Boolean
 * </pre>
 *
 * @author Paulo Gandra Sousa
 */
@Utility
public final class NumberPredicates {

    private NumberPredicates() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Checks whether a number is prime or not.
     *
     * <p>
     * Scrapped from
     * <a href ="http://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/">mkyong</a>
     *
     * <p>
     * See also <a href=
     * "https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math4/primes/Primes.html">org.apache.commons.math4.primes.Primes</a>
     *
     * @param number
     * @return whether a number is prime or not
     */
    public static boolean isPrime(final long number) {
        if (isEven(number)) {
            return false;
        }
        // if not, then just check the odds
        for (long i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a number is positive (greater than zero)
     *
     * @param number
     *            the number to check
     * @return true if number is positive
     */
    public static boolean isPositive(final long number) {
        return number > 0;
    }

    /**
     * Checks if a number is negative (less than zero)
     *
     * @param number
     *            the number to check
     * @return true if number is positive
     */
    public static boolean isNegative(final long number) {
        return number < 0;
    }

    /**
     * Checks if a number is positive (greater than zero) or zero.
     *
     * @param number
     *            the number to check
     * @return true if number is positive or zero
     */
    public static boolean isNonNegative(final long number) {
        return number >= 0;
    }

    /**
     * Checks if a number is zero or negative.
     *
     * @param number
     * @return true if a number is zero or negative
     */
    public static boolean isNonPositive(final long number) {
        return number <= 0;
    }

    /**
     * Checks if a number is odd.
     *
     * @param number
     *            the number to be tested
     * @return wether a number is odd or not
     */
    public static boolean isOdd(final long number) {
        return number % 2 != 0;
    }

    /**
     * checks if a number is even.
     *
     * @param number
     *            the number to be tested
     * @return true if a number is even.
     */
    public static boolean isEven(final long number) {
        return number % 2 == 0;
    }

    /**
     * Checks whether a number is within a certain margin of another
     *
     * @param x
     * @param axis
     * @param epsilon
     * @return whether a number is within a certain margin of another
     */
    public static boolean isWithinMargin(final double x, final double axis, final double epsilon) {
        return x >= axis - epsilon && x <= axis + epsilon;
    }
}
