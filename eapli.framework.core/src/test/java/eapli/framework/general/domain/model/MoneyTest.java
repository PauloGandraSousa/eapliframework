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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Currency;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class MoneyTest {

    @Test
    void ensureDollarsHasRightCurrency() {
        final Money subject = Money.dollars(1);
        assertEquals(Currency.getInstance("USD"), subject.currency());
    }

    @Test
    void ensureEurosHasRightCurrency() {
        final Money subject = Money.euros(1);
        assertEquals(Currency.getInstance("EUR"), subject.currency());
    }

    @Test
    void ensureDollarsHasRightAmount() {
        final Money subject = Money.dollars(1);
        assertEquals(1, subject.amountAsDouble(), 0.01);
    }

    @Test
    void ensureEurosHasRightAmount() {
        final Money subject = Money.euros(1);
        assertEquals(1, subject.amountAsDouble(), 0.01);
    }

    @Test
    void ensureEqualsObject() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(1);
        assertEquals(subject, other);
    }

    @Test
    void ensureNotEqualIfDifferentCurrency() {
        final Money subject = Money.euros(1);
        final Money other = Money.dollars(2);
        assertNotEquals(subject, other);
    }

    @Test
    void ensureNotEqualIfDifferentAmount() {
        final Money subject = Money.euros(1);
        final Money other = Money.euros(2);
        assertNotEquals(subject, other);
    }
}
