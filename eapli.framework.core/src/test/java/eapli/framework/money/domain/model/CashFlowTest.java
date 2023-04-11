/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.money.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 2023.03.31
 */
class CashFlowTest {

    @Test
    void ensureMoneyIsMandatory() {
        final var now = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new CashFlow(null, now));
    }

    @Test
    void ensureDateIsMandatory() {
        final var two = Money.dollars(2);
        assertThrows(IllegalArgumentException.class, () -> new CashFlow(two, null));
    }

    @Test
    void ensureAddOnTheSameDay() {
        final var today = LocalDate.now();
        final var a = new CashFlow(Money.dollars(1), today);
        final var b = new CashFlow(Money.dollars(2), today);
        final var expected = new CashFlow(Money.dollars(3.00), today);
        final var result = a.add(b);
        assertEquals(expected, result);
    }

    @Test
    void ensureCannotAddOnDifferentDays() {
        final var today = LocalDate.now();
        final var a = new CashFlow(Money.dollars(1), today);
        final var b = new CashFlow(Money.dollars(2), today.minusDays(1));
        assertThrows(IllegalArgumentException.class, () -> a.add(b));
    }

    @Test
    void ensureSubtractOnTheSameDay() {
        final var today = LocalDate.now();
        final var a = new CashFlow(Money.dollars(1), today);
        final var b = new CashFlow(Money.dollars(2), today);
        final var expected = new CashFlow(Money.dollars(-1.00), today);
        final var result = a.subtract(b);
        assertEquals(expected, result);
    }

    @Test
    void ensureCannotSubtractOnDifferentDays() {
        final var today = LocalDate.now();
        final var a = new CashFlow(Money.dollars(1), today);
        final var b = new CashFlow(Money.dollars(2), today.minusDays(1));
        assertThrows(IllegalArgumentException.class, () -> a.subtract(b));
    }

    @Test
    void whenTwoCashFlowsHaveSameAmountAndSameDateThenCompareIs0() {
        final var today = LocalDate.now();
        final var amt = Money.dollars(1);
        final var a = new CashFlow(amt, today);
        final var b = new CashFlow(amt, today);
        assertEquals(0, a.compareTo(b));
    }

    @Test
    void whenACashFlowHasPreviousDateThanAnotherThenCompareIsMinus1() {
        final var today = LocalDate.now();
        final var amt = Money.dollars(1);
        final var a = new CashFlow(amt, today.minusDays(1));
        final var b = new CashFlow(amt, today);
        assertEquals(-1, a.compareTo(b));
    }

    @Test
    void whenACashFlowHasPosteriorDateThanAnotherThenCompareIs1() {
        final var today = LocalDate.now();
        final var amt = Money.dollars(1);
        final var a = new CashFlow(amt, today.plusDays(1));
        final var b = new CashFlow(amt, today);
        assertEquals(1, a.compareTo(b));
    }

    @Test
    void whenTwoCashFlowsHaveTheSameDateButOneHasASmallerAmountThenCompareIsMinus1() {
        final var today = LocalDate.now();
        final var amt1 = Money.dollars(1);
        final var amt2 = Money.dollars(2);
        final var a = new CashFlow(amt1, today);
        final var b = new CashFlow(amt2, today);
        assertEquals(-1, a.compareTo(b));
    }

    @Test
    void whenTwoCashFlowsHaveTheSameDateButOneHasABiggerAmountThenCompareIs1() {
        final var today = LocalDate.now();
        final var amt1 = Money.dollars(10);
        final var amt2 = Money.dollars(2);
        final var a = new CashFlow(amt1, today);
        final var b = new CashFlow(amt2, today);
        assertEquals(1, a.compareTo(b));
    }
}
