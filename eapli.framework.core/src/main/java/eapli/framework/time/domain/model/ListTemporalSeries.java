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
import java.time.temporal.TemporalAmount;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A List-based implementation of temporal series, i.e., a series of temporal events.
 *
 * @author Paulo Gandra Sousa 2023.05.08
 *
 * @param <D>
 *            the date dimension, e.g., LocalDate
 * @param <T>
 *            the value of the series, e.g., Money
 */
public class ListTemporalSeries<D extends Temporal, T> implements TemporalSeries<D, T> {

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> add(final D when, final T event, final BinaryOperator<T> combinator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> merge(final TemporalSeries<D, T> other, final BinaryOperator<T> combinator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R> R fold(final R initial, final BiFunction<R, T, R> folder) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Temporal, U> TemporalSeries<S, U> map(final Function<T, U> mapper) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> shift(final UnaryOperator<D> adjuster) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> shift(final TemporalAmount adjustement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> between(final D lower, final D upper) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T> filter(final BiPredicate<D, T> filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TemporalSeries<D, T>[] slice(final BiFunction<D, T, Integer> slicer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<T> valueAt(final D when) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<T> nth(final int idx) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Integer> indexOf(final T event) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<D> dateOf(final T event) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Integer> indexOf(final D when) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Stream<T> stream() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        return 0;
    }

}
