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

import org.junit.jupiter.api.Test;

public class MoneyMultiplyTest {

    @Test
    public void ensureMultiplyBy0() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(0);
        assertEquals(expected, subject.multiply(0));
    }

    @Test
    public void ensureMultiplyBy1() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(10);
        assertEquals(expected, subject.multiply(1));
    }

    @Test
    public void ensureMultiplyByHalf() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(5);
        assertEquals(expected, subject.multiply(0.5));
    }

    @Test
    public void ensureMultiplyBy12() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(120);
        assertEquals(expected, subject.multiply(12));
    }
}
