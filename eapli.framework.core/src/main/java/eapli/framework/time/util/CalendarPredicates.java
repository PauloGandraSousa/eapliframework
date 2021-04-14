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
package eapli.framework.time.util;

import java.util.Calendar;

import eapli.framework.util.Utility;

/**
 * Utility predicates handling dates. Predicates are functions that test a
 * condition and return a boolean value; in this case the test is done over a
 * Calendar argument.
 *
 * @author Paulo Gandra de Sousa
 * @author Manuel Meireles (2DD, EAPLI 2016/2017)
 *
 */
@Utility
public final class CalendarPredicates {

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is equal
     * to the other calendar.
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before the other calendar or
     *         {@code false} otherwise.
     */
    public static boolean haveSameTime(final Calendar a, final Calendar b) {
        return compareTimes(a, b) == 0;
    }

    /**
     * Checks if the two calendar instances represent dates of the same year.
     *
     * @param a
     * @param b
     * @return {@code true} if the two calendar instances represent dates of the same year.
     */
    public static boolean areSameYear(final Calendar a, final Calendar b) {
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR);
    }

    /**
     * Checks if the two calendar instances represent dates of the same month
     * (regardless of the year).
     *
     * @param a
     * @param b
     * @return true if the two calendar instances represent dates of the same month.
     */
    public static boolean areSameMonth(final Calendar a, final Calendar b) {
        return a.get(Calendar.MONTH) == b.get(Calendar.MONTH);
    }

    /**
     * Checks if the two calendar instances represent dates of the same day
     * (regardless of the year and the month).
     *
     * @param a
     * @param b
     * @return {@code true} if the two calendar instances represent dates of the same day.
     */
    public static boolean areSameDay(final Calendar a, final Calendar b) {
        return a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Checks if the two calendar instances represent the same dates, i.e. if both
     * have the same year, month and day.
     *
     * @param a
     * @param b
     * @return true if the two calendar instances represent the same dates
     */
    public static boolean areSameDate(final Calendar a, final Calendar b) {
        return areSameYear(a, b) && areSameMonth(a, b) && areSameDay(a, b);
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is before the other
     * calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before the other calendar or
     *         {@code false} otherwise.
     */
    public static boolean isBefore(final Calendar a, final Calendar b) {
        return compareDates(a, b) < 0;
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is before the other
     * calendar or both have the same date.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before/equal the other
     *         calendar or {@code false} otherwise.
     */
    public static boolean isUntil(final Calendar a, final Calendar b) {
        return compareDates(a, b) <= 0;
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is after the other
     * calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before the other calendar or
     *         {@code false} otherwise.
     */
    public static boolean isAfter(final Calendar a, final Calendar b) {
        return compareDates(a, b) > 0;
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is after the other calendar
     * or both have the same date.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is after/equal the other
     *         calendar or {@code false} otherwise.
     */
    public static boolean isOnwards(final Calendar a, final Calendar b) {
        return compareDates(a, b) >= 0;
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is before today.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The calendar to check.
     * @return {@code true} if the calendar is before today or {@code false}
     *         otherwise.
     */
    public static boolean isBeforeToday(final Calendar a) {
        return isBefore(a, Calendars.now());
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is today or before.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The calendar to check.
     * @return {@code true} if the calendar is today or before or {@code false}
     *         otherwise.
     */
    public static boolean isUntilToday(final Calendar a) {
        return isUntil(a, Calendars.now());
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is today.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The calendar to check.
     * @return {@code true} if the calendar is equal to today or {@code false}
     *         otherwise.
     */
    public static boolean isToday(final Calendar a) {
        return areSameDate(a, Calendars.now());
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is today or after.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The calendar to check.
     * @return {@code true} if the calendar is today or after or {@code false}
     *         otherwise.
     */
    public static boolean isTodayOnwards(final Calendar a) {
        return isOnwards(a, Calendars.now());
    }

    /**
     * Checks if the calendar date (Year, Month and Day) is after today.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The calendar to check.
     * @return {@code true} if the calendar is after today or {@code false}
     *         otherwise.
     */
    public static boolean isAfterToday(final Calendar a) {
        return isAfter(a, Calendars.now());
    }

    /**
     * Compares a Calendar field between the two calendars.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar
     * @param b
     *            The other calendar.
     * @param dateField
     *            The Calendar field to compare (it must be an integer field).
     * @return "1" if the one calendar field is greater, "-1" if lesser
     *         or "0" if equal.
     */
    private static int compareField(final Calendar a, final Calendar b, final int dateField) {
        final int difference = a.get(dateField) - b.get(dateField);
        if (difference > 0) {
            return 1;
        }
        return difference < 0 ? -1 : 0;
    }

    /**
     * It compares the dates (regardless of the time).
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return "1" if the one calendar's date is greater, "-1" if lesser
     *         or "0" if equal.
     */
    private static int compareDates(final Calendar a, final Calendar b) {
        int result = compareField(a, b, Calendar.YEAR);
        if (result == 0) {
            result = compareField(a, b, Calendar.MONTH);
            if (result == 0) {
                result = compareField(a, b, Calendar.DAY_OF_MONTH);
            }
        }
        return result;
    }

    /**
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     * @param b
     * @return
     */
    protected static int compareTimes(final Calendar a, final Calendar b) {
        int result = compareField(a, b, Calendar.HOUR_OF_DAY);
        if (result == 0) {
            result = compareField(a, b, Calendar.MINUTE);
            if (result == 0) {
                result = compareField(a, b, Calendar.SECOND);
                if (result == 0) {
                    result = compareField(a, b, Calendar.MILLISECOND);
                }
            }
        }
        return result;
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is before
     * the other calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before the other calendar or
     *         {@code false} otherwise.
     */
    public static boolean isBeforeTime(final Calendar a, final Calendar b) {
        return compareTimes(a, b) < 0;
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is before
     * or equal the other calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return It returns {@code true} if the one calendar is before or equal the other
     *         calendar or {@code false} otherwise.
     */
    public static boolean isUntilTime(final Calendar a, final Calendar b) {
        return compareTimes(a, b) <= 0;
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is after
     * the other calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is before the other calendar or
     *         {@code false} otherwise.
     */
    public static boolean isAfterTime(final Calendar a, final Calendar b) {
        return compareTimes(a, b) > 0;
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is after
     * or equal the other calendar.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @param b
     *            The other calendar.
     * @return {@code true} if the one calendar is after or equal the other
     *         calendar or {@code false} otherwise.
     */
    public static boolean isTimeOnwards(final Calendar a, final Calendar b) {
        return compareTimes(a, b) >= 0;
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is before
     * the current time.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @return It returns {@code true} if the one calendar is before the current time or
     *         {@code false} otherwise.
     */
    public static boolean isBeforeNow(final Calendar a) {
        return isBeforeTime(a, Calendars.now());
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is the
     * current time or before.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @return {@code true} if the one calendar is the current time or before
     *         or {@code false} otherwise.
     */
    public static boolean isUntilNow(final Calendar a) {
        return isUntilTime(a, Calendars.now());
    }

    /**
     * Checks if the calendar time (Hour, Minute, Second and Millisecond) is the
     * current time or before.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @return {@code true} if the one calendar is the current time or before
     *         or {@code false} otherwise.
     */
    public static boolean isNow(final Calendar a) {
        return haveSameTime(a, Calendars.now());
    }

    /**
     * checks if the calendar time (Hour, Minute, Second and Millisecond) is the
     * current time or after.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @return It returns {@code true} if the one calendar is the current time or after or
     *         {@code false} otherwise.
     */
    public static boolean isNowOnwards(final Calendar a) {
        return isTimeOnwards(a, Calendars.now());
    }

    /**
     * checks if the calendar time (Hour, Minute, Second and Millisecond) is after
     * the current time.
     *
     * <p>
     * Author Manuel Meireles (2DD, EAPLI 2016/2017)
     *
     * @param a
     *            The one calendar.
     * @return {@code true} if the one calendar is after the current time or
     *         {@code false} otherwise.
     */
    public static boolean isAfterNow(final Calendar a) {
        return isAfterTime(a, Calendars.now());
    }

    private CalendarPredicates() {
        // ensure utility
    }
}