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

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import eapli.framework.money.domain.model.CashFlow;
import eapli.framework.money.domain.model.Money;

/**
 * @author Paulo Gandra Sousa 2023.04.14
 */
class TemporalSeriesTest {

    @Test
    void testAlt1() {
        fail("Not yet implemented");

        //
        // ALTERNATIVE: time series of (date,money) pairs
        //

        // creation of time series
        TemporalSeries<LocalDate, Money> ts = new TemporalSeriesBuilder<LocalDate, Money>()
                // acummulator function if two values are added for the same date point
                .accumulateWith(Money::add)
                // data
                .with(LocalDate.of(2023, 1, 1), Money.euros(10))
                .with(LocalDate.of(2023, 1, 10), Money.euros(20))
                .with(LocalDate.of(2023, 1, 20), Money.euros(-30))
                .with(LocalDate.of(2023, 2, 5), Money.euros(20))
                .with(LocalDate.of(2023, 2, 15), Money.euros(30))
                .build();

        // folding the time series reduces the values of the series
        final Money total = ts.fold(Money.euros(0), (a, b) -> a.add(b));

        // adding a new event places it in the right temporal order
        // does not change original series
        TemporalSeries<LocalDate, Money> ts2 = ts.add(LocalDate.of(2023, 1, 7), Money.euros(15));
        assert ts2.indexOf(LocalDate.of(2023, 1, 7)) == 2;

        // adding a new event for an existing date point combines the values
        // does not change original series
        TemporalSeries<LocalDate, Money> ts3 = ts.add(LocalDate.of(2023, 1, 1), Money.euros(15));
        assert ts3.valueAt(LocalDate.of(2023, 1, 1)).equals(Money.euros(25));
        assert ts3.nth(1).equals(Money.euros(25));

        // transforming the time series creates a new time series with the same date points but
        // transformed values
        // does not change original series
        TemporalSeries<LocalDate, Money> ts4 = ts.map(a -> a.multiply(2));

        // shifting a time series, adjusts the date points of the series but keeps the values
        TemporalSeries<LocalDate, Money> ts5 = ts.shift(t -> t.addDays(2));
        final TemporalSeries<LocalDate, Money> ts5b = ts.shift(Duration.ofDays(2));

        // filter
        // does not change original series
        TemporalSeries<LocalDate, Money> ts6 = ts
                .filter((t, a) -> a.lessThan(0) && t.compareTo(LocalDate.of(2023, 1, 31)) < 0);

        // merging two time series combines the values of the same date point
        // does not change original series
        TemporalSeries<LocalDate, Money> ts7 = ts.merge(ts2, (a, b) -> a.add(b));
        TemporalSeries<LocalDate, Money> ts7b = ts.merge(ts2);

        // slicing a time series by a temporal "dimension"
        // does not change original series
        TemporalSeries<LocalDate, Money> ts8[] = ts.slice(MONTH);

        // subtimeseries, a window
        // does not change original series
        TemporalSeries<LocalDate, Money> ts9 = ts.between(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 31));
    }

    @Test
    void testAlt2() {
        fail("Not yet implemented");
        //
        // ALTERNATIVE: time series of cashflows
        //

        // creation of time series
        final TemporalSeries<LocalDate, Money, CashFlow> ts = new TemporalSeriesBuilder<LocalDate, Money, CashFlow>()
                // acummulator function if two values are added for the same date point
                .accumulateWith(CashFlow::add)
                // data
                .with(new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(10)))
                .with(new CashFlow(LocalDate.of(2023, 1, 10), Money.euros(20)))
                .with(new CashFlow(LocalDate.of(2023, 1, 20), Money.euros(-30)))
                .with(new CashFlow(LocalDate.of(2023, 2, 5), Money.euros(20)))
                .with(new CashFlow(LocalDate.of(2023, 2, 15), Money.euros(30)))
                .build();

        // folding the time series
        // initial value must be the "value" part of the cashflow
        final Money total = ts.fold(Money.euros(0), (a, b) -> b.amount().add(a));

        // adding a new event places it in the right temporal order
        // does not change original series
        final var cf = new CashFlow(LocalDate.of(2023, 1, 7), Money.euros(15));
        final TemporalSeries<CashFlow> ts2 = ts.add(cf);
        assert ts2.indexOf(LocalDate.of(2023, 1, 7)) == 2;

        // adding a new event for an existing date point combines the values
        // does not change original series
        final TemporalSeries<CashFlow> ts3 = ts.add(new CashFlow(LocalDate.of(2023, 1, 1), Money.euros(15)));
        assert ts3.valueAt(LocalDate.of(2023, 1, 1)).equals(Money.euros(25));
        assert ts3.nth(1).amount().equals(Money.euros(25));

        // transforming the time series creates a new time series with the same date points but
        // transformed values
        // does not change original series
        final TemporalSeries<CashFlow> ts4 = ts.map(a -> new CashFlow(a.occurredAt(), a.amount().multiply(2)));

        // shifting a time series, adjusts the date points of the series but keeps the values
        final TemporalSeries<CashFlow> ts5 = ts.shift(t -> t.plus(Duration.ofDays(2)));
        final TemporalSeries<CashFlow> ts5b = ts.shift(Duration.ofDays(2));

        // filter
        // does not change original series
        final TemporalSeries<CashFlow> ts6 = ts
                .filter(c -> c.amount().isLessThan(Money.euros(0))
                        && c.occurredAt().compareTo(LocalDate.of(2023, 1, 31)) < 0);

        // merging two time series combines the values of the same date point
        // does not change original series
        final TemporalSeries<CashFlow> ts7 = ts.merge(ts2, (a, b) -> a.add(b));

        // slicing a time series by a temporal "dimension"
        // does not change original series
        final TemporalSeries<CashFlow> ts8[] = ts.slice(MONTH);

        // subtimeseries, a window
        // does not change original series
        final TemporalSeries<CashFlow> ts9 = ts.between(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 31));
    }

}
