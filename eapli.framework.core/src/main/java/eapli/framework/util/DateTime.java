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
package eapli.framework.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for Calendar manipulation and several date and time related
 * functions.
 *
 * If you are using Java 8+, you should use the Time API:
 * <li>https://docs.oracle.com/javase/tutorial/datetime/TOC.html
 * <li>https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
 *
 * @author Paulo Gandra Sousa
 */
public final class DateTime implements Utilitarian {
    private static final Logger LOGGER = LogManager.getLogger(DateTime.class);

    private static final int DAYS_TILL_END_OF_WEEK = 6;

    private DateTime() {
        // to make sure this is an utility class
    }

    /**
     * returns the current date of the system
     *
     * @return
     */
    public static Calendar now() {
        return new GregorianCalendar();
    }

    /**
     * returns the number of the week in the year given a certain date
     *
     * @param date
     * @return
     */
    public static int weekNumber(final Calendar date) {
        return date.get(Calendar.WEEK_OF_YEAR);
    }

    public static Calendar calendarFromDate(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static int currentWeekNumber() {
        return weekNumber(now());
    }

    /**
     * returns the date of the first day of a week
     *
     * @param year
     * @param week
     * @return
     */
    public static Calendar beginningOfWeek(final int year, final int week) {
        final Calendar date = new GregorianCalendar();
        date.clear();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.WEEK_OF_YEAR, week);
        return date;
    }

    /**
     * returns the date of the last day of a week
     *
     * @param year
     * @param week
     * @return
     */
    public static Calendar endOfWeek(final int year, final int week) {
        final Calendar date = beginningOfWeek(year, week);
        date.add(Calendar.DATE, DAYS_TILL_END_OF_WEEK);
        return date;
    }

    /**
     * returns the date of the last day of the current month
     *
     * @return
     */
    public static Calendar endOfCurrentMonth() {
        return endOfMonth(now());
    }

    /**
     * returns the date of the last day of a certain month
     *
     * @param reference
     *            a date to be used as reference month
     * @return
     */
    public static Calendar endOfMonth(final Calendar reference) {
        final Calendar lastDay = new GregorianCalendar();
        lastDay.setTime(reference.getTime());
        final int last = lastDay.getActualMaximum(Calendar.DAY_OF_MONTH);
        lastDay.set(Calendar.DAY_OF_MONTH, last);
        return lastDay;
    }

    /**
     * returns the date of the last day of a certain month
     *
     */
    public static Calendar endOfMonth(final int year, final int month) {
        final Calendar date = newCalendar(year, month, year);
        return endOfMonth(date);
    }

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
     * Creates a new Calendar object set to a specific date
     *
     * @param year
     *            the year
     * @param month
     *            the month (1 - 12). note that the Calendar class uses months
     *            from 0 to 11, not 1 to 12
     * @param day
     *            the day of the month
     * @return a newly create Calendar object
     */
    public static Calendar newCalendar(final int year, final int month, final int day) {
        return new GregorianCalendar(year, month - 1, day);
    }

    /**
     * creates a new Calendar instance with the date part set (year, month,
     * date) from the source object
     *
     * @param source
     * @return
     */
    public static Calendar datePart(final Calendar source) {
        return newCalendar(source.get(Calendar.YEAR), source.get(Calendar.MONTH) + 1, source.get(Calendar.DATE));
    }

    /**
     * parses a string that contains a date in a certain format
     *
     * @param aDateString
     * @param format
     * @return a Date object or null if there was an error parsing the string
     */
    public static Calendar parseDate(final String aDateString, final String format) {
        try {
            final SimpleDateFormat df = new SimpleDateFormat(format);
            final Date date = df.parse(aDateString);
            return calendarFromDate(date);
        } catch (final ParseException ex) {
            LOGGER.error(ex);
        }
        return null;
    }

    public static Calendar parseDate(final String aDateString) {
        return parseDate(aDateString, "dd-MM-yyyy");
    }

    public static String format(final Calendar ocurrs) {
        return format(ocurrs, "YYYY/MM/dd");
    }

    /**
     * Formats a date according to ISO8601.
     *
     * based on
     * https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute/33532945
     *
     * @param when
     * @return
     */
    public static String iso8601(final Calendar when) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX").withZone(ZoneOffset.UTC).format(when.toInstant());
    }

    /**
     * Converts a Calendar object to Java 8 LocalDateTime
     *
     * Taken from
     * https://www.logicbig.com/how-to/java-8-date-time-api/calender-to-localdatetime.html
     *
     * and https://www.baeldung.com/java-date-to-localdate-and-localdatetime
     *
     * @param calendar
     * @return
     */
    public static LocalDateTime toLocalDateTime(final Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        final TimeZone tz = calendar.getTimeZone();
        final ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

    /**
     * returns the current time according to ISO8601.
     *
     * based on
     * https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute/33532945
     *
     * @param when
     * @return
     */
    public static String iso8601() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmX").withZone(ZoneOffset.UTC).format(Instant.now());
    }

    public static String format(final Calendar ocurrs, final String dateFormat) {
        final SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        return formater.format(ocurrs.getTime());
    }

    public static String weekdayName(final Calendar calendar) {
        final String[] dayNames = new DateFormatSymbols().getWeekdays();

        return dayNames[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    public static Calendar yesterday() {
        final Calendar yesterday = now();
        yesterday.add(Calendar.DATE, -1);
        return yesterday;
    }

    public static Calendar tomorrow() {
        final Calendar yesterday = now();
        yesterday.add(Calendar.DATE, 1);
        return yesterday;
    }

    /**
     * Creates and returns a {@link Calendar} instance by parsing a given string
     * base on a data format.
     *
     * @param value
     *            the date in string format
     * @param dateFormat
     *            the format to use when parsing the dateString
     *
     * @return the generated {@link Calendar} instance.
     * @throws ParseException
     */
    public static Calendar parse(final String value, final DateFormat dateFormat) throws ParseException {
        final Calendar calendar = Calendar.getInstance();
        final Date date = dateFormat.parse(value);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * <p>
     * Creates and returns a {@link String} instance by parsing a given Calendar
     * base on a data format.
     * </p>
     *
     *
     * @param calendar
     *            the Calendar object to parse
     * @param dateFormat
     *            the format to use when parsing the Calendar object
     *
     * @return the generated {@link String} instance.
     */
    @SuppressWarnings("squid:S1488")
    public static String toString(final Calendar calendar, final DateFormat dateFormat) {
        final Date date = calendar.getTime();
        final String dateString = dateFormat.format(date);
        return dateString;
    }
}
