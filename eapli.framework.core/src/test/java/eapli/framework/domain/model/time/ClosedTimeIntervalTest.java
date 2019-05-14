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
package eapli.framework.domain.model.time;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import eapli.framework.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class ClosedTimeIntervalTest {

    private static final Calendar START = Calendars.of(2017, 05, 01);
    private static final Calendar END = Calendars.of(2017, 05, 15);
    private static TimeInterval INSTANCE = new TimeInterval(START, END);

    @Test
    public void ensureIncludesStart() {
        assertTrue("Start is not included", INSTANCE.includes(START));
    }

    @Test
    public void ensureIncludesEnd() {
        assertTrue("End is not included", INSTANCE.includes(END));
    }

    @Test
    public void ensureIncludesMid() {
        final Calendar mid = (Calendar) START.clone();
        mid.add(Calendar.DATE, 2);
        assertTrue("Mid is not included", INSTANCE.includes(mid));
    }

    @Test
    public void ensureDoesntIncludeBeforeStart() {
        final Calendar before = (Calendar) START.clone();
        before.add(Calendar.DATE, -2);
        assertFalse("Mid is not included", INSTANCE.includes(before));
    }

    @Test
    public void ensureDoesntIncludeAfterEnd() {
        final Calendar after = (Calendar) END.clone();
        after.add(Calendar.DATE, 2);
        assertFalse("Mid is not included", INSTANCE.includes(after));
    }
}
