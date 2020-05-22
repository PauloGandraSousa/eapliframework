/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
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
package eapli.framework.time.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import eapli.framework.time.util.Calendars;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class CalendarsTest {
    @BeforeClass
    public static void setup() {
        // setup test date to be July 26th, 2019
        Calendars.setNow(new GregorianCalendar(2019, Calendar.JULY, 26));
    }

    @Test
    public void ensureFakeNow() {
        final Calendar expected = new GregorianCalendar(2019, Calendar.JULY, 26);
        assertEquals(expected, Calendars.now());
    }

    /**
     * Test of weekNumber method, of class DateTime.
     */
    @Test
    public void ensureJanuaryFirst2014IsWeekOne() {
        System.out.println("weekNumber");
        final Calendar date = new GregorianCalendar(2014, 0, 1);
        final int expResult = 1;
        final int result = Calendars.weekNumber(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of weekNumber method, of class DateTime.
     */
    @Test
    public void ensureMarch21st2014IsWeek12() {
        System.out.println("weekNumber");
        final Calendar date = new GregorianCalendar(2014, 2, 21);
        final int expResult = 12;
        final int result = Calendars.weekNumber(date);
        assertEquals(expResult, result);
    }

    private String toString(final Calendar d) {
        return d.get(Calendar.YEAR) + "/" + (d.get(Calendar.MONTH) + 1) + "/" + d.get(Calendar.DATE);
    }

    /**
     * Test of beginningOfWeek method, of class DateTime.
     *
     */
    @Test
    @Ignore("due to differences in time zones with bitbucket pipeline")
    public void ensureFirstDayOfWeek13Of2014Is24() {
        System.out.println("beginningOfWeek");
        final int year = 2014;
        final int week = 13;
        final Calendar expResult = Calendars.of(2014, 3, 24);
        System.out.println("beginningOfWeek - expected: " + toString(expResult));
        final Calendar result = Calendars.beginningOfWeek(year, week);
        System.out.println("beginningOfWeek - actual: " + toString(result));
        assertEquals(expResult.getTimeInMillis(), result.getTimeInMillis());
    }

    /**
     * Test of endOfWeek method, of class DateTime.
     *
     */
    @Ignore("due to differences in time zones with bitbucket pipeline")
    @Test
    public void ensureLastDayOfWeek13Of2014Is30() {
        System.out.println("endOfWeek");
        final int year = 2014;
        final int week = 13;
        final Calendar expResult = Calendars.of(2014, 3, 30);
        // gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        // gregorianCalendar.setMinimalDaysInFirstWeek(4);

        System.out.println("endOfWeek - expected: " + toString(expResult));
        final Calendar result = Calendars.endOfWeek(year, week);
        System.out.println("endOfWeek - actual: " + toString(result));
        assertEquals(expResult.getTimeInMillis(), result.getTimeInMillis());
    }

    /**
     * Test of endOfMonth method, of class DateTime.
     */
    @Test
    public void ensureLastDayOfMarch2014is31() {
        System.out.println("endOfMonth");
        final Calendar reference = Calendars.of(2014, 3, 21);
        final Calendar expResult = Calendars.of(2014, 3, 31);
        final Calendar result = Calendars.endOfMonth(reference);
        assertEquals(expResult, result);
    }

    /**
     * Test of endOfMonth method, of class DateTime.
     */
    @Test
    public void ensureLastDayOfFebruary2014is28() {
        System.out.println("endOfMonth");
        final Calendar reference = Calendars.of(2014, 2, 21);
        final Calendar expResult = Calendars.of(2014, 2, 28);
        final Calendar result = Calendars.endOfMonth(reference);
        assertEquals(expResult, result);
    }

    /**
     * Test of endOfMonth method, of class DateTime.
     */
    @Test
    public void ensureLastDayOfFebruary2012is29() {
        System.out.println("endOfMonth");
        final Calendar reference = Calendars.of(2012, 2, 21);
        final Calendar expResult = Calendars.of(2012, 2, 29);
        final Calendar result = Calendars.endOfMonth(reference);
        assertEquals(expResult, result);
    }

    /**
     * Test of parseDate method, of class DateTime.
     */
    @Test
    public void ensureParseDate_String_String() {
        System.out.println("parseDate");
        final String aDateString = "2014-03-21";
        final String format = "yyyy-MM-dd";
        final Calendar expResult = Calendars.of(2014, 3, 21);
        final Calendar result = Calendars.parse(aDateString, format);
        assertEquals(expResult, result);
    }
}
