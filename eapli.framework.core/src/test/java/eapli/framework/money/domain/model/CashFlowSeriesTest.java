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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eapli.framework.math.util.NumberGenerator;

/**
 * @author Paulo Gandra Sousa 2023.04.24
 */
class CashFlowSeriesTest {

    private static final int REF_YEAR = NumberGenerator.anInt(2000, 2100);

    private static final CashFlow CASH_FLOW_2_15 = new CashFlow(LocalDate.of(2023, 2, 15), Money.euros(30));
    private static final CashFlow CASH_FLOW_2_15b = new CashFlow(LocalDate.of(2023, 2, 15), Money.euros(40));
    private static final CashFlow CASH_FLOW_2_5 = new CashFlow(LocalDate.of(2023, 2, 5), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_20 = new CashFlow(LocalDate.of(2023, 1, 20), Money.euros(-30));
    private static final CashFlow CASH_FLOW_1_10 = new CashFlow(LocalDate.of(2023, 1, 10), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_1 = new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(10));
    private static final CashFlow CASH_FLOW_1_1b = new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(20));

    private static CashFlowSeries subject;

    private static int initialNumberOfElements;
    private static CashFlow[] data;
    private static final Set<Integer> dataMonths = new HashSet<>();

    @BeforeAll
    static void setup() {
        // creation of a random series
        initialNumberOfElements = NumberGenerator.anInt(20, 100);

        // generate n distinct pairs
        final Set<Pair<Integer, Integer>> unique = new HashSet<>();
        while (unique.size() < initialNumberOfElements) {
            final int d = NumberGenerator.anInt(1, 29);
            final int m = NumberGenerator.anInt(1, 13);

            dataMonths.add(m);
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
    void ensureDoesNotChangeOriginalSeries() {
        // keeps the same number of elements
        assertEquals(initialNumberOfElements, subject.length());

        // respects ordering (date sorting)
        assertRespectsOrdering(subject);

        // keeps same data
        for (final var cf : data) {
            assertTrue(subject.contains(cf));
        }
    }

    private void assertRespectsOrdering(final CashFlowSeries s) {
        CashFlow previous = null;
        for (final var cf : s) {
            assertTrue(previous == null || cf.compareTo(previous) >= 0);
            previous = cf;
        }
    }

    @Test
    void ensureCreatingOfWithDistinctDatesOrdered() {
        // ARRANGE - ACT
        final var result = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15);

        // ASSERT
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(50), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeEqualCashFlowsOrdered() {
        // ARRANGE - ACT
        final var result = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_1, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15, CASH_FLOW_2_15);

        // ASSERT
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeCommonDatesOrdered() {
        // ARRANGE - ACT
        final var result = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_1b, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15, CASH_FLOW_2_15b);

        // ASSERT
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(110), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeCommonDatesUnordered() {
        // ARRANGE - ACT
        final var result = CashFlowSeries.of(CASH_FLOW_1_10, CASH_FLOW_1_1, CASH_FLOW_1_20,
                CASH_FLOW_2_15, CASH_FLOW_2_5, CASH_FLOW_2_15, CASH_FLOW_1_1);

        // ASSERT
        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFolding() {
        // ARRANGE
        final Money expected = Money.euros(initialNumberOfElements);

        // ACT
        final Money result = subject.fold(Money.euros(0), (a, b) -> b.add(a));

        // ASSERT
        assertEquals(expected, result);
    }

    @Test
    void ensureAddingAnElementToTheStart() {
        addElementAndAssert(REF_YEAR - 10, 1);
    }

    @Test
    void ensureAddingAnElementToTheEnd() {
        addElementAndAssert(REF_YEAR + 10, initialNumberOfElements + 1);
    }

    private void addElementAndAssert(final int year, final int expectedIndex) {
        // ARRANGE
        final int d = NumberGenerator.anInt(1, 29);
        final int m = NumberGenerator.anInt(1, 13);
        final var cf = new CashFlow(LocalDate.of(year, m, d), Money.euros(1));

        // ACT
        final var result = subject.add(cf);

        // ASSERT
        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(cf, result.nth(expectedIndex).orElseThrow());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementPlacesItInTheRightTemporalOrder() {
        // ARRANGE
        int d, m;
        do {
            d = NumberGenerator.anInt(1, 29);
            m = NumberGenerator.anInt(1, 13);
        } while (subject.valueAt(LocalDate.of(REF_YEAR, m, d)).isPresent());

        final var cf = new CashFlow(LocalDate.of(REF_YEAR, m, d), Money.euros(1));

        // ACT
        final var result = subject.add(cf);

        // ASSERT
        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementForAnExistingDateCombinesTheValues() {
        // ARRANGE
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var d = subject.nth(idx).orElseThrow().occurredAt();
        final var amt = Money.euros(NumberGenerator.anInt());

        final var expectedAmount = amt.add(subject.nth(idx).orElseThrow().amount());

        // ACT
        final CashFlowSeries result = subject.add(new CashFlow(d, amt));

        // ASSERT
        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(expectedAmount, result.valueAt(d).orElseThrow());
        assertEquals(expectedAmount, result.nth(idx).orElseThrow().amount());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureTransforms() {
        // ARRANGE
        final int amt = NumberGenerator.anInt();

        // ACT
        final var result = subject.map(a -> new CashFlow(a.occurredAt(), Money.euros(amt)));

        // ASSERT
        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertTrue(result.stream().allMatch(cf -> cf.amount().equals(Money.euros(amt))));
        assertTrue(result.stream().allMatch(cf -> subject.indexOf(cf.occurredAt()).isPresent()));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureShift() {
        // ARRANGE
        final UnaryOperator<LocalDate> adjuster = t -> t.plusDays(2);

        // ACT
        final var result = subject.shift(adjuster);

        // ASSERT
        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertHasShifted(result);
        assertRespectsOrdering(result);
    }

    private void assertHasShifted(final CashFlowSeries result) {
        final var its = subject.iterator();
        for (final CashFlow resultCashFlow : result) {
            final var originalCashFlow = its.next();

            assertEquals(originalCashFlow.amount(), resultCashFlow.amount());
            assertEquals(originalCashFlow.occurredAt().plusDays(2), resultCashFlow.occurredAt());
        }
    }

    @Test
    void ensureFilterAnEmptySeriesResultsInEmptySeries() {
        final var result = CashFlowSeries.EMPTY.filter(t -> true);

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureFilteringAnElementRemovesAllOtherElements() {
        final var cf = subject.nth(1).orElseThrow();
        final var result = subject.filter(t -> t.equals(cf));

        assertNotNull(result);
        assertEquals(1, result.length());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFilteringOutAnElementRemovesThatElement() {
        final var cf = subject.nth(1).orElseThrow();
        final var result = subject.filter(t -> !t.equals(cf));

        assertNotNull(result);
        assertEquals(initialNumberOfElements - 1, result.length());
        assertFalse(result.contains(cf));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFilteringOutAllResultsInEmptySeries() {
        final var result = subject.filter(t -> false);

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureFilteringNoneResultsInTheOriginalSeries() {
        final var result = subject.filter(t -> true);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingWithEmptySeriesResultsInTheOriginalSeries() {
        final var result = subject.merge(CashFlowSeries.EMPTY);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingTheEmptySeriesWithAnySeriesResultsInTheOtherSeries() {
        final var result = CashFlowSeries.EMPTY.merge(subject);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingWithDistinctElementsResultsInUnionOfSeries() {
        final int d = NumberGenerator.anInt(1, 29);
        final int m = NumberGenerator.anInt(1, 13);
        final var cf = new CashFlow(LocalDate.of(REF_YEAR + 10, m, d), Money.euros(1));

        final var ts = CashFlowSeries.of(cf);

        final var result = subject.merge(ts);

        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(cf, result.nth(initialNumberOfElements + 1).orElseThrow());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureMergingWithCommonElementsAddsTheValuesOnTheSameDate() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var d = subject.nth(idx).orElseThrow().occurredAt();
        final var amt = Money.euros(NumberGenerator.anInt());
        final var ts = CashFlowSeries.of(new CashFlow(d, amt));

        final var expectedAmount = amt.add(subject.nth(idx).orElseThrow().amount());

        final CashFlowSeries result = subject.merge(ts);

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(expectedAmount, result.valueAt(d).orElseThrow());
        assertEquals(expectedAmount, result.nth(idx).orElseThrow().amount());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureSlice() {
        // slicing a time series by a temporal "dimension"
        final var result = subject.slice(e -> e.occurredAt().getMonthValue());

        assertNotNull(result);
        assertEquals(dataMonths.size(), result.size());
        assertTotalCountOfElementsNoElementsAreLost(result);
        assertAllValuesArePresentAndEachSlotHasTheSameMonth(result);
    }

    private void assertTotalCountOfElementsNoElementsAreLost(final Map<Integer, CashFlowSeries> result) {
        // total count of elements - no elements are lost
        int totalCount = 0;
        for (final Entry<Integer, CashFlowSeries> e : result.entrySet()) {
            totalCount += e.getValue().length();
        }
        assertEquals(initialNumberOfElements, totalCount);
    }

    private void assertAllValuesArePresentAndEachSlotHasTheSameMonth(final Map<Integer, CashFlowSeries> result) {
        // all values are present and each slot has the same month
        for (final Entry<Integer, CashFlowSeries> e : result.entrySet()) {
            final int currentMonth = e.getKey();
            for (final CashFlow cf : e.getValue()) {
                // each slot has same month
                assertEquals(currentMonth, cf.occurredAt().getMonthValue());
                // value is present in the original series
                assertTrue(subject.contains(cf));
            }
        }
    }

    @Test
    void ensureSlicingAllInTheSameSlot() {
        final var result = subject.slice(e -> 0);

        assertNotNull(result);
        assertEquals(1, result.size());
        final var ts = result.get(0);
        assertEquals(subject, ts);
    }

    @Test
    void ensureBetweenFulfillsCriteria() {
        final var n = NumberGenerator.anInt(1, initialNumberOfElements);
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1 - n);
        final var lower = subject.nth(idx).orElseThrow().occurredAt();
        final var upper = subject.nth(idx + n - 1).orElseThrow().occurredAt();

        final var result = subject.between(lower, upper);

        assertNotNull(result);
        assertEquals(n - 1, result.length());
        // all elements in the range
        int i = idx;
        for (final CashFlow cf : result) {
            final var expected = subject.nth(i).orElseThrow();
            assertEquals(expected, cf);
            i++;
        }
    }

    @Test
    void ensureBetweenSingleDate() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements);
        final var expectedCashFlow = subject.nth(idx);
        final var date = expectedCashFlow.orElseThrow().occurredAt();
        final var nextDate = subject.nth(idx + 1).orElseThrow().occurredAt();

        final var result = subject.between(date, nextDate);

        assertNotNull(result);
        assertEquals(1, result.length());
        assertEquals(expectedCashFlow, result.nth(1));
    }

    @Test
    void ensureBetweenSameDateReturns0() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var expectedCashFlow = subject.nth(idx);
        final var date = expectedCashFlow.orElseThrow().occurredAt();

        final var result = subject.between(date, date);

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureBetweenFindsNoElements() {
        final var result = subject.between(LocalDate.of(REF_YEAR - 1, 1, 1), LocalDate.of(REF_YEAR - 1, 1, 31));

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureBetweenIncludesAllElements() {
        final var result = subject.between(LocalDate.of(REF_YEAR - 1, 1, 1), LocalDate.of(REF_YEAR + 1, 1, 31));

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(subject, result);
    }

    void missingTests() {
        // TODO

        // nth, indexOf, valueAt
        // <1
        // > size()
        // empty series
    }
}
