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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class MoneyDivideTest {

    @Test
    void ensureCannotDivideBy0() {
        final Money subject = Money.euros(1);
        assertThrows(ArithmeticException.class, () -> subject.divide(0));
    }

    @Test
    void ensureDivide100By2() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(50), Money.euros(50) };
        final Money[] actuals = subject.divide(2);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    void ensureDivide100By1() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(100) };
        final Money[] actuals = subject.divide(1);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    void ensureDivide100By3() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(33.34), Money.euros(33.33), Money.euros(33.33) };
        final Money[] actuals = subject.divide(3);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    void ensureDivide99By2() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(49.50), Money.euros(49.50) };
        final Money[] actuals = subject.divide(2);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    void ensureDivide99By1() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(99) };
        final Money[] actuals = subject.divide(1);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    void ensureDivide99By3() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(33), Money.euros(33), Money.euros(33) };
        final Money[] actuals = subject.divide(3);
        assertArrayEquals(expecteds, actuals);
    }
}
