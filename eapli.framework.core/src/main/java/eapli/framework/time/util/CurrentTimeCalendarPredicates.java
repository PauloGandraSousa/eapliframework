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
public final class CurrentTimeCalendarPredicates {

	/**
	 * Checks if the calendar date (Year, Month and Day) is before today.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The calendar to check.
	 * @return {@code true} if the calendar is before today or {@code false}
	 *         otherwise.
	 */
	public static boolean isBeforeToday(final Calendar a) {
		return CalendarPredicates.isBefore(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar date (Year, Month and Day) is today or before.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The calendar to check.
	 * @return {@code true} if the calendar is today or before or {@code false}
	 *         otherwise.
	 */
	public static boolean isUntilToday(final Calendar a) {
		return CalendarPredicates.isUntil(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar date (Year, Month and Day) is today.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The calendar to check.
	 * @return {@code true} if the calendar is equal to today or {@code false}
	 *         otherwise.
	 */
	public static boolean isToday(final Calendar a) {
		return CalendarPredicates.areSameDate(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar date (Year, Month and Day) is today or after.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The calendar to check.
	 * @return {@code true} if the calendar is today or after or {@code false}
	 *         otherwise.
	 */
	public static boolean isTodayOnwards(final Calendar a) {
		return CalendarPredicates.isOnwards(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar date (Year, Month and Day) is after today.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The calendar to check.
	 * @return {@code true} if the calendar is after today or {@code false}
	 *         otherwise.
	 */
	public static boolean isAfterToday(final Calendar a) {
		return CalendarPredicates.isAfter(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar time (Hour, Minute, Second and Millisecond) is before
	 * the current time.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The one calendar.
	 * @return It returns {@code true} if the one calendar is before the current
	 *         time or {@code false} otherwise.
	 */
	public static boolean isBeforeNow(final Calendar a) {
		return CalendarPredicates.isBeforeTime(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar time (Hour, Minute, Second and Millisecond) is the
	 * current time or before.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The one calendar.
	 * @return {@code true} if the one calendar is the current time or before or
	 *         {@code false} otherwise.
	 */
	public static boolean isUntilNow(final Calendar a) {
		return CalendarPredicates.isUntilTime(a, CurrentTimeCalendars.now());
	}

	/**
	 * Checks if the calendar time (Hour, Minute, Second and Millisecond) is the
	 * current time or before.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The one calendar.
	 * @return {@code true} if the one calendar is the current time or before or
	 *         {@code false} otherwise.
	 */
	public static boolean isNow(final Calendar a) {
		return CalendarPredicates.haveSameTime(a, CurrentTimeCalendars.now());
	}

	/**
	 * checks if the calendar time (Hour, Minute, Second and Millisecond) is the
	 * current time or after.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The one calendar.
	 * @return It returns {@code true} if the one calendar is the current time or
	 *         after or {@code false} otherwise.
	 */
	public static boolean isNowOnwards(final Calendar a) {
		return CalendarPredicates.isTimeOnwards(a, CurrentTimeCalendars.now());
	}

	/**
	 * checks if the calendar time (Hour, Minute, Second and Millisecond) is after
	 * the current time.
	 *
	 * <p>
	 * Author Manuel Meireles (2DD, EAPLI 2016/2017)
	 *
	 * @param a The one calendar.
	 * @return {@code true} if the one calendar is after the current time or
	 *         {@code false} otherwise.
	 */
	public static boolean isAfterNow(final Calendar a) {
		return CalendarPredicates.isAfterTime(a, CurrentTimeCalendars.now());
	}

	private CurrentTimeCalendarPredicates() {
		// ensure utility
	}
}