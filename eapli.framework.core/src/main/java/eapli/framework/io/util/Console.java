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
package eapli.framework.io.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.strings.util.StringPredicates;
import eapli.framework.time.util.Calendars;
import eapli.framework.util.Utility;

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
 * @author Paulo Gandra Sousa 19/05/2020
 */
@Utility
@SuppressWarnings({ "squid:S106", "squid:S4829" })
public final class Console {

    private static final Logger LOGGER = LogManager.getLogger(Console.class);

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
        try {
            System.out.println(prompt);
            final var converter = new InputStreamReader(System.in);
            final var in = new BufferedReader(converter);

            return in.readLine();
        } catch (final IOException ex) {
            LOGGER.warn("Ignoring but this is really strange that it even happened.", ex);
            return "";
        }
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
        do {
            try {
                System.out.println(prompt);
                final var converter = new InputStreamReader(System.in);
                final var in = new BufferedReader(converter);

                final String text = in.readLine();
                if (StringPredicates.isNullOrEmpty(text)) {
                    System.out.println(message);
                } else {
                    return text;
                }
            } catch (final IOException ex) {
                LOGGER.warn("Ignoring but this is really strange that it even happened.", ex);
                return "";
            }
        } while (true);
    }

    /**
     * Reads input from the console as an integer value. Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @return the integer that was read
     */
    public static int readInteger(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Integer.parseInt(input);
            } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    /**
     * Reads input from the console as a long value. Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @return the long that was read
     */
    public static long readLong(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Long.parseLong(input);
            } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    /**
     * Reads input from the console as a boolean (Y, S, N). Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @return the boolean that was read
     */
    public static boolean readBoolean(final String prompt) {
        do {
            try {
                final String strBool = readLine(prompt).toLowerCase();
                if ("y".equals(strBool) || "s".equals(strBool)) {
                    return true;
                } else if ("n".equals(strBool)) {
                    return false;
                }
            } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
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
        int option;
        do {
            option = Console.readInteger("Select an option: ");
            if (option == exit) {
                break;
            }
        } while (option < low || option > high);
        return option;
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @return the date that was read
     */
    public static Date readDate(final String prompt) {
        return readDate(prompt, "yyyy/MM/dd");
    }

    /**
     * Reads a date from the console.
     *
     * @param prompt
     * @param dateFormat
     * @return the date that was read
     */
    public static Date readDate(final String prompt, final String dateFormat) {
        do {
            try {
                final String strDate = readLine(prompt);
                final var df = new SimpleDateFormat(dateFormat);
                return df.parse(strDate);
            } catch (@SuppressWarnings("unused") final ParseException ex) {
                // nothing to do
            }
        } while (true);
    }

    /**
     * Reads a date from the console. Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @return the date that was read
     */
    public static Calendar readCalendar(final String prompt) {
        return readCalendar(prompt, "dd-MM-yyyy");
    }

    /**
     * Reads a date from the console. Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @param dateFormat
     * @return the date that was read
     */
    public static Calendar readCalendar(final String prompt, final String dateFormat) {
        do {
            try {
                final String strDate = readLine(prompt);
                final var df = new SimpleDateFormat(dateFormat);
                return Calendars.fromDate(df.parse(strDate));
            } catch (@SuppressWarnings("unused") final ParseException ex) {
                // nothing to do
            }
        } while (true);
    }

    /**
     * Reads a double form the console. Will keep asking for input if the user
     * enters invalid input, i.e., input that cannot be parsed.
     *
     * @param prompt
     * @return the double that was read
     */
    public static double readDouble(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Double.parseDouble(input);
            } catch (@SuppressWarnings("unused") final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }
}
