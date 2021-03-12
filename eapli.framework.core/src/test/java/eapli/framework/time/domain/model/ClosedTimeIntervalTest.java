/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.time.domain.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import eapli.framework.time.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class ClosedTimeIntervalTest {

    private static final Calendar START = Calendars.of(2017, 05, 01);
    private static final Calendar END = Calendars.of(2017, 05, 15);
    private static TimeInterval INSTANCE = new TimeInterval(START, END);

    @Test
    void ensureIncludesStart() {
        assertTrue(INSTANCE.includes(START), "Start is not included");
    }

    @Test
    void ensureIncludesEnd() {
        assertTrue(INSTANCE.includes(END), "End is not included");
    }

    @Test
    void ensureIncludesMid() {
        final Calendar mid = (Calendar) START.clone();
        mid.add(Calendar.DATE, 2);
        assertTrue(INSTANCE.includes(mid), "Mid is not included");
    }

    @Test
    void ensureDoesntIncludeBeforeStart() {
        final Calendar before = (Calendar) START.clone();
        before.add(Calendar.DATE, -2);
        assertFalse(INSTANCE.includes(before), "Mid is not included");
    }

    @Test
    void ensureDoesntIncludeAfterEnd() {
        final Calendar after = (Calendar) END.clone();
        after.add(Calendar.DATE, 2);
        assertFalse(INSTANCE.includes(after), "Mid is not included");
    }
}
