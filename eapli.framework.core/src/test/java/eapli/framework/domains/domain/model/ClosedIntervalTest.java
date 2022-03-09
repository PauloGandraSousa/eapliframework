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
package eapli.framework.domains.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
class ClosedIntervalTest extends IntervalTestBase {

    @BeforeAll
    static void setUpClass() {
        System.out.println("closedRange");
        subject = Interval.closedFrom(START).closedTo(END);
    }

    @Test
    void ensureStartIsInRange() {
        System.out.println("ensureStartIsInRange");
        final Long target = Long.valueOf(START_VALUE);
        final boolean result = subject.includes(target);
        assertTrue(result, "start must be part of a closed range");
    }

    @Test
    void ensureEndIsInRange() {
        System.out.println("ensureEndIsInRange");
        final Long target = Long.valueOf(END_VALUE);
        final boolean result = subject.includes(target);
        assertTrue(result, "end must be part of a closed range");
    }

    @Test
    void ensureStringRepresentationIsOk() {
        final String expected = "[" + START + ", " + END + "]";
        final String actual = subject.toString();
        assertEquals(expected, actual);
    }
}
