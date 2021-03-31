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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
class EmptyIntervalTest extends IntervalTestBase {

    @BeforeAll
    static void setUpClass() {
        System.out.println("EmptyRange");
        subject = Interval.empty();
    }

    @Test
    void ensureIsEmpty() {
        assertTrue(subject.isEmpty());
    }

    @Test
    void ensureDoesNotInclude() {
        final boolean result = LongStream.range(START_VALUE * -2L, END_VALUE * -2L).noneMatch(x -> subject.includes(x));
        assertTrue(result);
    }

    @Test
    void ensureNotFromInfinity() {
        assertFalse(subject.startsAtInfinity());
    }

    @Test
    void ensureNotToInfinity() {
        assertFalse(subject.endsAtInfinity());
    }

    @Test
    void ensureStart() {
        assertThrows(IllegalStateException.class, () -> subject.start());
    }

    @Test
    void ensureEnd() {
        assertThrows(IllegalStateException.class, () -> subject.end());
    }

    @Test
    void ensureStringRepresentationIsOk() {
        final String expected = "[]";
        final String actual = subject.toString();
        assertEquals(expected, actual);
    }
}
