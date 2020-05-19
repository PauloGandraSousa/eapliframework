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
package eapli.framework.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for reading different data types from the Console.
 * <p>
 * See <a href=
 * "https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS4829&rule_key=squid%3AS4829">squid:S24829</a>
 * regarding potential security pitfalls when reading input from the outside of
 * the application
 * </p>
 * <p>
 * Based on code from Nuno Silva
 * </p>
 *
 * @author Paulo Gandra Sousa
 * @deprecated use {@link eapli.framework.io.util.Console} instead
 * @see eapli.framework.io.util.Console
 */
@Deprecated
@Utility
@SuppressWarnings({ "squid:S106", "squid:S4829" })
public final class Console {

    private Console() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Reads input from the console as a string.
     *
     * @param prompt
     * @return the input read from the console
     */
    public static String readLine(final String prompt) {
        return eapli.framework.io.util.Console.readLine(prompt);
    }

    /**
     * Reads input from the console as a string ensuring that the string is not
     * empty or composed just of whitespace.
     *
     * @param prompt
     *            the prompt for the input
     * @param message
     *            the message to show in case of bad input
     * @return the input read from the console
     */
    public static String readNonEmptyLine(final String prompt, final String message) {
        return eapli.framework.io.util.Console.readNonEmptyLine(prompt, message);
    }

    /**
     * Reads input from the console as an integer value.
     *
     * @param prompt
     * @return the integer that was read
     */
    public static int readInteger(final String prompt) {
        return eapli.framework.io.util.Console.readInteger(prompt);
    }

    /**
     * Reads input from the console as a long value.
     *
     * @param prompt
     * @return the long that was read
     */
    public static long readLong(final String prompt) {
        return eapli.framework.io.util.Console.readLong(prompt);
    }

    /**
     * Reads input from the console as a boolean (Y, S, N).
     *
     * @param prompt
     * @return the boolean that was read
     */
    public static boolean readBoolean(final String prompt) {
        return eapli.framework.io.util.Console.readBoolean(prompt);
    }

    /**
     * Reads input from the console as an integer value between <code>low</code>
     * and <code>high</code> or <code>exit</code>.
     *
     * @param low
     * @param high
     * @param exit
     * @return the integer that was read
     */
    public static int readOption(final int low, final int high, final int exit) {
        return eapli.framework.io.util.Console.readOption(low, high, exit);
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @return the date that was read
     */
    public static Date readDate(final String prompt) {
        return eapli.framework.io.util.Console.readDate(prompt);
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @param dateFormat
     * @return the date that was read
     */
    public static Date readDate(final String prompt, final String dateFormat) {
        return eapli.framework.io.util.Console.readDate(prompt, dateFormat);
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @return the date that was read
     */
    public static Calendar readCalendar(final String prompt) {
        return eapli.framework.io.util.Console.readCalendar(prompt);
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @param dateFormat
     * @return the date that was read
     */
    public static Calendar readCalendar(final String prompt, final String dateFormat) {
        return eapli.framework.io.util.Console.readCalendar(prompt, dateFormat);
    }

    /**
     * Reads a double form the console.
     *
     * @param prompt
     * @return the double that was read
     */
    public static double readDouble(final String prompt) {
        return eapli.framework.io.util.Console.readDouble(prompt);
    }

    /**
     *
     * @param prompt
     */
    @SuppressWarnings("squid:S1166")
    public static void waitForKey(final String prompt) {
        System.out.println(prompt);
        try {
            System.in.read();
        } catch (@SuppressWarnings("unused") final IOException ex) {
            // nothing to do
        }
    }
}
