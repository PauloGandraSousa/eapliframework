/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.util;

import java.math.BigDecimal;

/**
 *
 * @author Paulo Gandra Sousa
 */
public final class Numbers implements Utilitarian {

    private Numbers() {
        // to make sure this is an utility class
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
     * Checks if a number is positive (greater than zero) or zero
     *
     * @param number
     *            the number to check
     * @return true if number is positive or zero
     */
    public static boolean isNonNegative(final long number) {
        return number >= 0;
    }

    /**
     * checks if a number is zero or negative
     *
     * @param number
     * @return
     */
    public static boolean isNonPositive(final long number) {
        return number <= 0;
    }

    /**
     * determines if a number is odd.
     *
     * @param number
     *            the number to be tested
     * @return
     */
    public static boolean isOdd(final long number) {
        return number % 2 != 0;
    }

    /**
     * determines if a number is even.
     *
     * @param number
     *            the number to be tested
     * @return
     */
    public static boolean isEven(final long number) {
        return number % 2 == 0;
    }

    public static String format(final BigDecimal amount) {
        // TODO use number formatter
        return amount.toString();
    }
}
