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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eapli.framework.math.util.NumberGenerator;

/**
 * @author Paulo Gandra Sousa 2023.04.24
 */
class CashFlowSeriesTest {

    private static final int REF_YEAR = 2022;

    private static final CashFlow CASH_FLOW_2_15 = new CashFlow(LocalDate.of(2023, 2, 15), Money.euros(30));
    private static final CashFlow CASH_FLOW_2_5 = new CashFlow(LocalDate.of(2023, 2, 5), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_20 = new CashFlow(LocalDate.of(2023, 1, 20), Money.euros(-30));
    private static final CashFlow CASH_FLOW_1_10 = new CashFlow(LocalDate.of(2023, 1, 10), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_1 = new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(10));

    private static CashFlowSeries subject;
    private static int initialNumberOfElements;
    private static CashFlow[] data;

    @BeforeAll
    static void setup() {
        // creation of a random series
        initialNumberOfElements = NumberGenerator.anInt(20, 100);

        // generate n distinct pairs
        final Set<Pair<Integer, Integer>> unique = new HashSet<>();
        while (unique.size() < initialNumberOfElements) {
            final int d = NumberGenerator.anInt(1, 29);
            final int m = NumberGenerator.anInt(1, 13);
            unique.add(Pair.of(m, d));
        }

        // create data set for series
        data = new CashFlow[initialNumberOfElements];
        int n = 0;
        for (final var p : unique) {
            final var cf = new CashFlow(LocalDate.of(REF_YEAR, p.getLeft(), p.getRight()), Money.euros(1));
            data[n++] = cf;
        }
        subject = CashFlowSeries.of(data);
    }

    @AfterEach
    void ensureDoesNotChange() {
        // keeps the same number of elements
        assertEquals(initialNumberOfElements, subject.length());

        // respects ordering (date sorting)
        ensureRespectsOrdering(subject);

        // keeps same data
        for (final var cf : data) {
            assertTrue(subject.contains(cf));
        }
    }

    private void ensureRespectsOrdering(final CashFlowSeries s) {
        CashFlow previous = null;
        for (final var cf : s) {
            assertTrue(previous == null || cf.compareTo(previous) >= 0);
            previous = cf;
        }
    }

    @Test
    void ensureCreatingOfWithDistinctDatesOrdered() {
        final var result = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15);
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(50), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        ensureRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeCommonDatesOrdered() {
        final var result = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_1, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15, CASH_FLOW_2_15);
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        ensureRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeCommonDatesUnordered() {
        final var result = CashFlowSeries.of(CASH_FLOW_1_10, CASH_FLOW_1_1, CASH_FLOW_1_20,
                CASH_FLOW_2_15, CASH_FLOW_2_5, CASH_FLOW_2_15, CASH_FLOW_1_1);
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        ensureRespectsOrdering(result);
    }

    @Test
    void ensureFolding() {
        final Money expected = Money.euros(initialNumberOfElements);
        final Money result = subject.fold(Money.euros(0), (a, b) -> b.add(a));

        assertEquals(expected, result);
    }

    @Test
    void ensureAddingAnElementToTheStart() {
        final int d = NumberGenerator.anInt(1, 28);
        final int m = NumberGenerator.anInt(1, 12);
        final var cf = new CashFlow(LocalDate.of(REF_YEAR - 10, m, d), Money.euros(1));

        final var result = subject.add(cf);
        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(cf, result.nth(1).orElseThrow());
        ensureRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementToTheEnd() {
        final int d = NumberGenerator.anInt(28);
        final int m = NumberGenerator.anInt(12);
        final var cf = new CashFlow(LocalDate.of(REF_YEAR + 10, m, d), Money.euros(1));

        final var result = subject.add(cf);
        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(cf, result.nth(initialNumberOfElements + 1).orElseThrow());
        ensureRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementForAnExistingDateCombinesTheValues() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var d = subject.nth(idx).orElseThrow().occurredAt();
        final CashFlowSeries result = subject.add(new CashFlow(d, Money.euros(7)));

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(Money.euros(8), result.valueAt(d).orElseThrow());
        assertEquals(Money.euros(8), result.nth(idx).orElseThrow().amount());
        ensureRespectsOrdering(result);
    }

    @Test
    void testAlt2() {
        fail("Not yet implemented");

        // adding a new event places it in the right temporal order
        final var cf = new CashFlow(LocalDate.of(2023, 1, 7), Money.euros(15));
        final CashFlowSeries ts2 = subject.add(cf);
        assert ts2.indexOf(LocalDate.of(2023, 1, 7)) == 2;

        // transforming the time series creates a new time series with the same date points but
        // transformed values
        subject.map(a -> new CashFlow(a.occurredAt(), a.amount().multiply(2)));

        // shifting a time series, adjusts the date points of the series but keeps the values
        subject.shift(t -> t.plus(Duration.ofDays(2)));
        subject.shift(Duration.ofDays(2));

        // filter
        subject.filter(c -> c.amount().isLessThan(Money.euros(0))
                && c.occurredAt().compareTo(LocalDate.of(2023, 1, 31)) < 0);

        // merging two time series combines the values of the same date point
        subject.merge(ts2);

        // slicing a time series by a temporal "dimension"
        subject.slice(e -> e.occurredAt().getMonthValue());

        // subtimeseries, a window
        subject.between(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 31));

        // nth, indexOf, valueAt
        // <1
        // > size()

        // empty series
    }

}
