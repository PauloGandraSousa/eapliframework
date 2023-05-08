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

import eapli.framework.money.domain.model.Money;

/**
 * @author Paulo Gandra Sousa 2023.04.14
 */
class ListTemporalSeriesTest {

    void testAlt1() {
        // TODO
        fail("Not yet implemented");

        //
        // ALTERNATIVE: time series of (date,money) pairs
        //
        final TemporalSeries<LocalDate, Money> ts = null;
        /*
         * // creation of time series
         * final TemporalSeries<LocalDate, Money> ts = new TemporalSeriesBuilder<LocalDate, Money>()
         * // acummulator function if two values are added for the same date point
         * .accumulateWith(Money::add)
         * // data
         * .with(LocalDate.of(2023, 1, 1), Money.euros(10))
         * .with(LocalDate.of(2023, 1, 10), Money.euros(20))
         * .with(LocalDate.of(2023, 1, 20), Money.euros(-30))
         * .with(LocalDate.of(2023, 2, 5), Money.euros(20))
         * .with(LocalDate.of(2023, 2, 15), Money.euros(30))
         * .build();
         */

        ts.fold(Money.euros(0), (a, b) -> a.add(b));

        // adding a new event places it in the right temporal order
        // does not change original series
        final TemporalSeries<LocalDate, Money> ts2 = ts.add(LocalDate.of(2023, 1, 7), Money.euros(15),
                (a, b) -> a.add(b));
        assert ts2.indexOf(LocalDate.of(2023, 1, 7)).orElseThrow() == 2;

        // adding a new event for an existing date point combines the values
        // does not change original series
        final TemporalSeries<LocalDate, Money> ts3 = ts.add(LocalDate.of(2023, 1, 1), Money.euros(15),
                (a, b) -> a.add(b));
        assert ts3.valueAt(LocalDate.of(2023, 1, 1)).orElseThrow().equals(Money.euros(25));
        assert ts3.nth(1).orElseThrow().equals(Money.euros(25));

        ts.map(a -> a.multiply(2));

        ts.shift(t -> t.plusDays(2));
        ts.shift(Duration.ofDays(2));

        ts.filter((t, a) -> a.isLessThan(Money.euros(0)) && t.compareTo(LocalDate.of(2023, 1, 31)) < 0);

        ts.merge(ts2, (a, b) -> a.add(b));

        ts.slice((d, v) -> d.getMonthValue());

        ts.between(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 31));
    }
}
