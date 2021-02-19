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
package eapli.framework.general.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class MoneySignalTest {
    @Test
    public void testNegateAPositive() {
        final Money subject = Money.euros(1);
        final Money expected = Money.euros(-1);
        assertEquals(expected, subject.negate());
    }

    @Test
    public void testNegateANegative() {
        final Money subject = Money.euros(-1);
        final Money expected = Money.euros(1);
        assertEquals(expected, subject.negate());
    }

    @Test
    public void ensureSignumOfPositive() {
        final Money subject = Money.euros(150);
        assertEquals(1, subject.signum());
    }

    @Test
    public void ensureSignumOfNegative() {
        final Money subject = Money.euros(-0.99);
        assertEquals(-1, subject.signum());
    }

    @Test
    public void ensureSignumOfZero() {
        final Money subject = Money.euros(0);
        assertEquals(0, subject.signum());
    }

    @Test
    public void ensureNegativeIsNegative() {
        final Money subject = Money.euros(-150);
        assertTrue(subject.isNegative());
    }

    @Test
    public void ensurePositiveIsNotNegative() {
        final Money subject = Money.euros(150);
        assertFalse(subject.isNegative());
    }

    @Test
    public void ensureZeroIsNotNegative() {
        final Money subject = Money.euros(0);
        assertFalse(subject.isNegative());
    }

    @Test
    public void ensureZeroIsNotPositive() {
        final Money subject = Money.euros(0);
        assertFalse(subject.isPositive());
    }

    @Test
    public void ensureNegativeIsNotPositive() {
        final Money subject = Money.euros(-150);
        assertFalse(subject.isPositive());
    }

    @Test
    public void ensurePositiveIsPositive() {
        final Money subject = Money.euros(150);
        assertTrue(subject.isPositive());
    }

    @Test
    public void ensureZeroIsZero() {
        final Money subject = Money.euros(0);
        assertTrue(subject.isZero());
    }

    @Test
    public void ensureNegativeIsNotZero() {
        final Money subject = Money.euros(-150);
        assertFalse(subject.isZero());
    }

    @Test
    public void ensurePositiveIsNotZero() {
        final Money subject = Money.euros(150);
        assertFalse(subject.isZero());
    }
}
