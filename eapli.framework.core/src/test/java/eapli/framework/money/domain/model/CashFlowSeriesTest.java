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
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eapli.framework.math.util.NumberGenerator;

/**
 * @author Paulo Gandra Sousa 2023.04.24
 */
class CashFlowSeriesTest {

    private static final CashFlow CASH_FLOW_2_15 = new CashFlow(LocalDate.of(2023, 2, 15), Money.euros(30));
    private static final CashFlow CASH_FLOW_2_5 = new CashFlow(LocalDate.of(2023, 2, 5), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_20 = new CashFlow(LocalDate.of(2023, 1, 20), Money.euros(-30));
    private static final CashFlow CASH_FLOW_1_10 = new CashFlow(LocalDate.of(2023, 1, 10), Money.euros(20));
    private static final CashFlow CASH_FLOW_1_1 = new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(10));

    private CashFlowSeries ts;

    @BeforeAll
    void setup() {
        // creation of time series
        ts = CashFlowSeries.of(CASH_FLOW_1_1, CASH_FLOW_1_10, CASH_FLOW_1_20,
                CASH_FLOW_2_5, CASH_FLOW_2_15);
        
        
        int n = NumberGenerator.anInt(100);
    }

    @AfterEach
    void ensureDoesNotChange() {
        assertEquals(5, ts.length());

    }

    @Test
    void ensureFolding() {
        final Money expected = Money.euros(50);
        final Money result = ts.fold(Money.euros(0), (a, b) -> b.add(a));

        assertEquals(expected, result);
    }

    @Test
    void testAlt2() {
        fail("Not yet implemented");

        // adding a new event places it in the right temporal order
        final var cf = new CashFlow(LocalDate.of(2023, 1, 7), Money.euros(15));
        final CashFlowSeries ts2 = ts.add(cf);
        assert ts2.indexOf(LocalDate.of(2023, 1, 7)) == 2;

        // adding a new event for an existing date point combines the values
        final CashFlowSeries ts3 = ts.add(new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(15)));
        assert ts3.valueAt(LocalDate.of(2023, 1, 1)).equals(Money.euros(25));
        assert ts3.nth(1).amount().equals(Money.euros(25));

        // transforming the time series creates a new time series with the same date points but
        // transformed values
        ts.map(a -> new CashFlow(a.occurredAt(), a.amount().multiply(2)));

        // shifting a time series, adjusts the date points of the series but keeps the values
        ts.shift(t -> t.plus(Duration.ofDays(2)));
        ts.shift(Duration.ofDays(2));

        // filter
        ts.filter(c -> c.amount().isLessThan(Money.euros(0))
                && c.occurredAt().compareTo(LocalDate.of(2023, 1, 31)) < 0);

        // merging two time series combines the values of the same date point
        ts.merge(ts2);

        // slicing a time series by a temporal "dimension"
        ts.slice(e -> e.occurredAt().getMonthValue());

        // subtimeseries, a window
        ts.between(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 31));
    }

}
