/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.time.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Supplier;

import eapli.framework.util.Utility;

/**
 * Utility class for Calendar manipulation and several date and time related
 * functions.
 * <p>
 * If you are using Java 8+, you should use the Time API:
 * <li><a href=
 * "https://docs.oracle.com/javase/tutorial/datetime/TOC.html">Oracle's Java 8
 * Date Time Tutorial</a>
 * <li><a href=
 * "https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html">Oracle's
 * Java 8 Documentation</a>
 *
 * @author Paulo Gandra Sousa
 */
@Utility
public final class CurrentTimeCalendars {

    /**
     * Mock the system clock for unit testing.
     * <p>
     * suppress warning squid:S2885 - we are protecting the variable with
     * synchronize getter
     */
    @SuppressWarnings("squid:S2885") //
    private static Calendar frozenTime;

    private static Supplier<Calendar> nowFunction = CurrentTimeCalendars::realNow;

    private static synchronized Calendar fakeNow() {
        return frozenTime;
    }

    private static Calendar realNow() {
        return new GregorianCalendar();
    }

    private CurrentTimeCalendars() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Overrides the use of the system clock to allow unit testing.
     */
    /* package */ static synchronized void setNow(final Calendar frozenTime) {
        CurrentTimeCalendars.frozenTime = frozenTime;
        nowFunction = CurrentTimeCalendars::fakeNow;
    }

    /**
     * Returns the current date of the system.
     *
     * @return the current date of the system.
     */
    public static Calendar now() {
        return nowFunction.get();
    }

    public static int currentWeekNumber() {
        return Calendars.weekNumber(now());
    }

    /**
     * Returns the date of the last day of the current month.
     *
     * @return the date of the last day of the current month.
     */
    public static Calendar endOfCurrentMonth() {
        return Calendars.endOfMonth(now());
    }

    /**
     * returns the current year.
     *
     * @return the current year
     */
    public static int currentYear() {
        return now().get(Calendar.YEAR);
    }

    /**
     * returns the current month of the year
     *
     * @return current month (1 - 12) of the year
     */
    public static int currentMonth() {
        return now().get(Calendar.MONTH) + 1;
    }

    /**
     * Returns yesterday's date.
     *
     * @return yesterday's date.
     */
    public static Calendar yesterday() {
        final Calendar yesterday = now();
        yesterday.add(Calendar.DATE, -1);
        return yesterday;
    }

    /**
     * Returns tomorrow's date.
     *
     * @return tomorrow's date
     */
    public static Calendar tomorrow() {
        final Calendar yesterday = now();
        yesterday.add(Calendar.DATE, 1);
        return yesterday;
    }
}
