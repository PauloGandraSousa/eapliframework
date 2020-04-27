/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.general.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eapli.framework.general.domain.model.Money;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class MoneyComparisionTest {
    @Test
    public void ensure1CompareTo0() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(0);
        assertEquals(1, subject.compareTo(other));
    }

    @Test
    public void ensure0CompareTo1() {
        final Money subject = Money.euros(0);
        final Money other = Money.euros(1);
        assertEquals(-1, subject.compareTo(other));
    }

    @Test
    public void ensure1CompareTo1() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertEquals(0, subject.compareTo(other));
    }

    @Test
    public void ensure1GreaterThan0() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(0);
        assertTrue(subject.isGreaterThan(other));
    }

    @Test
    public void ensure1NotGreaterThan1() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertFalse(subject.isGreaterThan(other));
    }

    @Test
    public void ensure0NotGreaterThan1() {
        final Money subject = Money.euros(0);
        final Money other = Money.euros(1);
        assertFalse(subject.isGreaterThan(other));
    }

    @Test
    public void ensure1NotLessThan0() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(0);
        assertFalse(subject.isLessThan(other));
    }

    @Test
    public void ensure1NotLessThan1() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertFalse(subject.isLessThan(other));
    }

    @Test
    public void ensure0LessThan1() {
        final Money subject = Money.euros(0);
        final Money other = Money.euros(1);
        assertTrue(subject.isLessThan(other));
    }

    @Test
    public void ensure2GreaterThanOrEqualTo1() {
        final Money subject = Money.euros(2);
        final Money other = Money.euros(1);
        assertTrue(subject.isGreaterThanOrEqual(other));
    }

    @Test
    public void ensure1GreaterThanOrEqualTo1() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertTrue(subject.isGreaterThanOrEqual(other));
    }

    @Test
    public void ensure0NotGreaterThanOrEqualTo1() {
        final Money subject = Money.euros(0);
        final Money other = Money.euros(1);
        assertFalse(subject.isGreaterThanOrEqual(other));
    }

    @Test
    public void ensure2NotLessThanOrEqualTo1() {
        final Money subject = Money.euros(2);
        final Money other = Money.euros(1);
        assertFalse(subject.isLessThanOrEqual(other));
    }

    @Test
    public void ensure1LessThanOrEqualTo1() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertTrue(subject.isLessThanOrEqual(other));
    }

    @Test
    public void ensure0LessThanOrEqualTo1() {
        final Money subject = Money.euros(0);
        final Money other = Money.euros(1);
        assertTrue(subject.isLessThanOrEqual(other));
    }
}
