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
package eapli.framework.time.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eapli.framework.math.util.NumberGenerator;
import eapli.framework.money.domain.model.CashFlowSeries;
import eapli.framework.money.domain.model.Money;

/**
 * @author Paulo Gandra Sousa 2023.04.14
 */
class TreeTemporalSeriesTest {

    private static final int REF_YEAR = NumberGenerator.anInt(2000, 2100);

    private static final LocalDate CASH_FLOW_DATE_2_15 = LocalDate.of(2023, 2, 15);
    private static final LocalDate CASH_FLOW_DATE_2_5 = LocalDate.of(2023, 2, 5);
    private static final LocalDate CASH_FLOW_DATE_1_20 = LocalDate.of(2023, 1, 20);
    private static final LocalDate CASH_FLOW_DATE_1_10 = LocalDate.of(2023, 1, 10);
    private static final LocalDate CASH_FLOW_DATE_1_1 = LocalDate.of(2023, 1, 1);

    private static final Money CASH_FLOW_MONEY_2_15 = Money.euros(30);
    private static final Money CASH_FLOW_MONEY_2_15b = Money.euros(40);
    private static final Money CASH_FLOW_MONEY_2_5 = Money.euros(20);
    private static final Money CASH_FLOW_MONEY_1_20 = Money.euros(-30);
    private static final Money CASH_FLOW_MONEY_1_10 = Money.euros(20);
    private static final Money CASH_FLOW_MONEY_1_1 = Money.euros(10);
    private static final Money CASH_FLOW_MONEY_1_1b = Money.euros(20);

    private static TemporalSeries<LocalDate, Money> subject;

    private static int initialNumberOfElements;
    private static LocalDate[] data;
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
        final TreeTemporalSeriesBuilder<LocalDate, Money> builder = new TreeTemporalSeriesBuilder<>(Money::add);
        data = new LocalDate[initialNumberOfElements];
        int n = 0;
        for (final var p : unique) {
            final var cf = LocalDate.of(REF_YEAR, p.getLeft(), p.getRight());
            data[n++] = cf;

            builder.with(cf, Money.euros(1));
        }
        // build the series
        subject = builder.build();
    }

    @AfterEach
    void ensureDoesNotChangeOriginalSeries() {
        // keeps the same number of elements
        assertEquals(initialNumberOfElements, subject.length());

        // respects ordering (date sorting)
        assertRespectsOrdering(subject);

        // keeps same data
        for (final var cf : data) {
            assertTrue(subject.indexOf(cf).isPresent());
            assertEquals(Money.euros(1), subject.valueAt(cf).orElseThrow());
        }
    }

    private void assertRespectsOrdering(final TemporalSeries<LocalDate, Money> s) {
        LocalDate previous = null;
        for (final var cf : s) {
            assertTrue(previous == null || cf.occurredAt().compareTo(previous) >= 0);
            previous = cf.occurredAt();
        }
    }

    @Test
    void ensureCreatingOfWithDistinctDatesOrdered() {
        final var result = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .with(CASH_FLOW_DATE_1_10, CASH_FLOW_MONEY_1_10)
                .with(CASH_FLOW_DATE_1_20, CASH_FLOW_MONEY_1_20)
                .with(CASH_FLOW_DATE_2_5, CASH_FLOW_MONEY_2_5)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .build();

        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(50), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeEqualCashFlowsOrdered() {
        final var result = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .with(CASH_FLOW_DATE_1_10, CASH_FLOW_MONEY_1_10)
                .with(CASH_FLOW_DATE_1_20, CASH_FLOW_MONEY_1_20)
                .with(CASH_FLOW_DATE_2_5, CASH_FLOW_MONEY_2_5)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .build();

        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), Money::add));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeCommonDatesOrdered() {
        final var result = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1b)
                .with(CASH_FLOW_DATE_1_10, CASH_FLOW_MONEY_1_10)
                .with(CASH_FLOW_DATE_1_20, CASH_FLOW_MONEY_1_20)
                .with(CASH_FLOW_DATE_2_5, CASH_FLOW_MONEY_2_5)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15b)
                .build();

        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(110), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureCreatingOfWithSomeEqualCashFlowsUnordered() {
        final var result = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(CASH_FLOW_DATE_1_10, CASH_FLOW_MONEY_1_10)
                .with(CASH_FLOW_DATE_1_20, CASH_FLOW_MONEY_1_20)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .with(CASH_FLOW_DATE_2_5, CASH_FLOW_MONEY_2_5)
                .with(CASH_FLOW_DATE_2_15, CASH_FLOW_MONEY_2_15)
                .with(CASH_FLOW_DATE_1_1, CASH_FLOW_MONEY_1_1)
                .build();

        assertNotNull(result);
        assertEquals(5, result.length());
        assertEquals(Money.euros(90), result.fold(Money.euros(0), (a, b) -> b.add(a)));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFolding() {
        final Money expected = Money.euros(initialNumberOfElements);
        final Money result = subject.fold(Money.euros(0), (a, b) -> b.add(a));

        assertEquals(expected, result);
    }

    @Test
    void ensureFoldingAnEmptySeriesResultsInTheInitalValue() {
        final var amt = NumberGenerator.anInt();
        final Money expected = Money.euros(amt);

        final Money result = TreeTemporalSeries.<LocalDate, Money>empty().fold(expected, Money::add);

        assertEquals(expected, result);
    }

    @Test
    void ensureAddingAnElementToTheStart() {
        addAnElementAndAssert(-10, 1);
    }

    @Test
    void ensureAddingAnElementToTheEnd() {
        addAnElementAndAssert(10, initialNumberOfElements + 1);
    }

    private void addAnElementAndAssert(final int dateOffset, final int expectedIndex) {
        final int d = NumberGenerator.anInt(1, 29);
        final int m = NumberGenerator.anInt(1, 13);

        final var expectedMoney = Money.euros(1);
        final var expectedDate = LocalDate.of(REF_YEAR + dateOffset, m, d);
        final var result = subject.add(expectedDate, expectedMoney, Money::add);

        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(expectedMoney, result.nth(expectedIndex).orElseThrow().event());
        assertEquals(expectedIndex, result.indexOf(expectedDate).orElseThrow());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementPlacesItInTheRightTemporalOrder() {
        int d, m;
        do {
            d = NumberGenerator.anInt(1, 29);
            m = NumberGenerator.anInt(1, 13);
        } while (subject.valueAt(LocalDate.of(REF_YEAR, m, d)).isPresent());

        final var result = subject.add(LocalDate.of(REF_YEAR, m, d), Money.euros(1), Money::add);

        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureAddingAnElementForAnExistingDateCombinesTheValues() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var expectedDate = subject.nth(idx).orElseThrow().occurredAt();
        final var amt = Money.euros(NumberGenerator.anInt());

        final var expectedAmount = amt.add(subject.nth(idx).orElseThrow().event());

        final var result = subject.add(expectedDate, amt, Money::add);

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(expectedAmount, result.valueAt(expectedDate).orElseThrow());
        assertEquals(expectedDate, result.nth(idx).orElseThrow().occurredAt());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureTransforms() {
        final int amt = NumberGenerator.anInt();
        final var result = subject.map(a -> TemporalEvent.of(a.occurredAt(), Money.euros(amt)));

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertTrue(result.stream().allMatch(cf -> cf.event().equals(Money.euros(amt))));
        assertTrue(result.stream().allMatch(cf -> subject.hasDate(cf.occurredAt())));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureShift() {
        final var result = subject.shift(t -> t.plusDays(2));

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertHasShifted(result);
        assertRespectsOrdering(result);
    }

    private void assertHasShifted(final TemporalSeries<LocalDate, Money> result) {
        final var its = subject.iterator();
        for (final TemporalEvent<LocalDate, Money> cfr : result) {
            final var cfs = its.next();

            assertEquals(cfs.event(), cfr.event());
            assertEquals(cfs.occurredAt().plusDays(2), cfr.occurredAt());
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
        final var result = subject.filter(e -> e.occurredAt().equals(cf.occurredAt()) && e.event().equals(cf.event()));

        assertNotNull(result);
        assertEquals(1, result.length());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFilteringOutAnElementRemovesThatElement() {
        final var cf = subject.nth(1).orElseThrow();
        final var result = subject
                .filter(e -> (!e.occurredAt().equals(cf.occurredAt()) || !e.event().equals(cf.event())));

        assertNotNull(result);
        assertEquals(initialNumberOfElements - 1, result.length());
        assertFalse(result.hasDate(cf.occurredAt()));
        assertRespectsOrdering(result);
    }

    @Test
    void ensureFilteringOutAllResultsInEmptySeries() {
        final var result = subject.filter(e -> false);

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureFilteringNoneResultsInTheOriginalSeries() {
        final var result = subject.filter(e -> true);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingWithEmptySeriesResultsInTheOriginalSeries() {
        final var result = subject.merge(TreeTemporalSeries.empty(), Money::add);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingTheEmptySeriesWithAnySeriesResultsInTheOtherSeries() {
        final var result = TreeTemporalSeries.<LocalDate, Money>empty().merge(subject, Money::add);

        assertEquals(subject, result);
    }

    @Test
    void ensureMergingWithDistinctElementsResultsInUnionOfSeries() {
        final int d = NumberGenerator.anInt(1, 29);
        final int m = NumberGenerator.anInt(1, 13);
        final var cf = LocalDate.of(REF_YEAR + 10, m, d);

        final var ts = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(LocalDate.of(REF_YEAR + 10, m, d), Money.euros(1))
                .build();

        final var result = subject.merge(ts, Money::add);

        assertNotNull(result);
        assertEquals(initialNumberOfElements + 1, result.length());
        assertEquals(initialNumberOfElements + 1, result.indexOf(cf).orElseThrow());
        assertEquals(Money.euros(1), result.valueAt(cf).orElseThrow());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureMergingWithCommonElementsAddsTheValuesOnTheSameDate() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var d = subject.nth(idx).orElseThrow().occurredAt();
        final var amt = Money.euros(NumberGenerator.anInt());
        final var ts = new TreeTemporalSeriesBuilder<LocalDate, Money>(Money::add)
                .with(d, amt)
                .build();

        final var expectedAmount = amt.add(subject.nth(idx).orElseThrow().event());

        final TemporalSeries<LocalDate, Money> result = subject.merge(ts, Money::add);

        assertNotNull(result);
        assertEquals(initialNumberOfElements, result.length());
        assertEquals(expectedAmount, result.valueAt(d).orElseThrow());
        assertEquals(expectedAmount, result.nth(idx).orElseThrow().event());
        assertRespectsOrdering(result);
    }

    @Test
    void ensureSlice() {
        // slicing a time series by a temporal "dimension"
        final var result = subject.slice(e -> e.occurredAt().getMonthValue(), Money::add);

        assertNotNull(result);
        assertEquals(dataMonths.size(), result.size());
        assertTotalCountOfElementsNoElementsAreLost(result);
        assertAllValuesArePresentAndEachSlotHasTheSameMonth(result);
    }

    private void assertTotalCountOfElementsNoElementsAreLost(
            final Map<Integer, TemporalSeries<LocalDate, Money>> result) {
        // total count of elements - no elements are lost
        int totalCount = 0;
        for (final Entry<Integer, TemporalSeries<LocalDate, Money>> e : result.entrySet()) {
            totalCount += e.getValue().length();
        }
        assertEquals(initialNumberOfElements, totalCount);
    }

    private void assertAllValuesArePresentAndEachSlotHasTheSameMonth(
            final Map<Integer, TemporalSeries<LocalDate, Money>> result) {
        // all values are present and each slot has the same month
        for (final Entry<Integer, TemporalSeries<LocalDate, Money>> e : result.entrySet()) {
            final int currentMonth = e.getKey();
            for (final TemporalEvent<LocalDate, Money> cf : e.getValue()) {
                // each slot has same month
                assertEquals(currentMonth, cf.occurredAt().getMonthValue());
                // value is present in the original series
                assertTrue(subject.contains(cf.event()));
            }
        }
    }

    @Test
    void ensureSlicingAllInTheSameSlot() {
        final var result = subject.slice(e -> 0, Money::add);

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
        for (final TemporalEvent<LocalDate, Money> cf : result) {
            final var expected = subject.nth(i).orElseThrow();
            assertEquals(expected, cf);
            i++;
        }
    }

    @Test
    void ensureBetweenSingleDateResultsInEmptySeries() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var expectedCashFlow = subject.nth(idx);
        final var date = expectedCashFlow.orElseThrow().occurredAt();

        final var result = subject.between(date, date);

        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    void ensureBetweenSingle() {
        final var idx = NumberGenerator.anInt(1, initialNumberOfElements + 1);
        final var expectedCashFlow = subject.nth(idx);
        final var date = expectedCashFlow.orElseThrow().occurredAt();

        final var upperCashFlow = subject.nth(idx + 1);
        final var upperDate = upperCashFlow.orElseThrow().occurredAt();

        final var result = subject.between(date, upperDate);

        assertNotNull(result);
        assertEquals(1, result.length());
        assertEquals(expectedCashFlow, result.nth(1));
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
