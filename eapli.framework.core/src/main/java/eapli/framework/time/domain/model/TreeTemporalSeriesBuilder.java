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

import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

import eapli.framework.util.Factory;

/**
 * A builder for Tree-based implementation of temporal series.
 *
 * @author Paulo Gandra Sousa 2023.05.10
 *
 * @param <D>
 *            the date dimension, e.g., LocalDate
 * @param <T>
 *            the value of the series, e.g., Money
 */
public class TreeTemporalSeriesBuilder<D extends Temporal, T> implements Factory<TemporalSeries<D, T>> {

    private final Map<D, T> data;
    private final BinaryOperator<T> combinator;

    /**
     * Constructor.
     *
     * @param combinator
     *
     * @return
     */
    public TreeTemporalSeriesBuilder(final BinaryOperator<T> combinator) {
        data = new HashMap<>();
        this.combinator = combinator;
    }

    public TreeTemporalSeriesBuilder<D, T> with(final D when, final T event) {
        data.merge(when, event, combinator);
        return this;
    }

    @Override
    public TemporalSeries<D, T> build() {
        return new TreeTemporalSeries<>(data);
    }

}
