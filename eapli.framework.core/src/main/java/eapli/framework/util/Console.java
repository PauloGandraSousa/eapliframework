/*
 * Copyright (c) 2013-2019 the original author or authors.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for reading different data types from the Console.
 *
 * see <a href=
 * "https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS4829&rule_key=squid%3AS4829">squid:S24829</a>
 * regarding potential security pitfalls when reading input from the outside of
 * the application
 *
 * based on code from Nuno Silva
 *
 * @author Paulo Gandra Sousa
 */
@Utility
@SuppressWarnings({ "squid:S106", "squid:S4829" })
public final class Console {

    private static final Logger LOGGER = LogManager.getLogger(Console.class);

    private Console() {
        // to make sure this is an utility class
    }

    /**
     * reads input from the console as a string.
     *
     * @param prompt
     * @return the input read from the console
     */
    public static String readLine(final String prompt) {
        try {
            System.out.println(prompt);
            final InputStreamReader converter = new InputStreamReader(System.in);
            final BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (final IOException ex) {
            LOGGER.warn("Ignoring but this is really strange that it even happened.", ex);
            return "";
        }
    }

    public static int readInteger(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Integer.parseInt(input);
            } catch (final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    public static long readLong(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Long.parseLong(input);
            } catch (final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    public static boolean readBoolean(final String prompt) {
        do {
            try {
                final String strBool = readLine(prompt).toLowerCase();
                if ("y".equals(strBool) || "s".equals(strBool)) {
                    return true;
                } else if ("n".equals(strBool)) {
                    return false;
                }
            } catch (final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

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

    public static Date readDate(final String prompt) {
        return readDate(prompt, "yyyy/MM/dd");
    }

    public static Date readDate(final String prompt, final String dateFormat) {
        do {
            try {
                final String strDate = readLine(prompt);
                final SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                return df.parse(strDate);
            } catch (final ParseException ex) {
                // nothing to do
            }
        } while (true);
    }

    public static Calendar readCalendar(final String prompt) {
        return readCalendar(prompt, "dd-MM-yyyy");
    }

    public static Calendar readCalendar(final String prompt, final String dateFormat) {
        do {
            try {
                final String strDate = readLine(prompt);
                final SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                return Calendars.calendarFromDate(df.parse(strDate));
            } catch (final ParseException ex) {
                // nothing to do
            }
        } while (true);
    }

    public static double readDouble(final String prompt) {
        do {
            try {
                final String input = readLine(prompt);
                return Double.parseDouble(input);
            } catch (final NumberFormatException ex) {
                // nothing to do
            }
        } while (true);
    }

    @SuppressWarnings("squid:S1166")
    public static void waitForKey(final String prompt) {
        System.out.println(prompt);
        try {
            System.in.read();
        } catch (final IOException ex) {
            // nothing to do
        }
    }
}
