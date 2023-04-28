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
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A temporal series, i.e., a series of temporal events.
 *
 * @author Paulo Gandra Sousa 2023.04.14
 *
 * @param <D>
 * @param <V>
 * @param <T>
 */
public interface TemporalSeries<D extends Temporal, V, T extends TemporalEvent<D, V>> {

    TemporalSeries<D, V, T> add(T event);

    TemporalSeries<D, V, T> merge(TemporalSeries<D, V, T> other, BinaryOperator<T> combinator);

    <R> R fold(R initial, BiFunction<R, T, R> folder);

    <S extends Temporal, U, R extends TemporalEvent<S, U>> TemporalSeries<S, U, R> map(Function<T, R> mapper);

    TemporalSeries<D, V, T> shift(UnaryOperator<D> adjuster);

    TemporalSeries<D, V, T> shift(TemporalAmount adjustement);

    TemporalSeries<D, V, T> between(D lower, D upper);

    TemporalSeries<D, V, T> filter(Predicate<T> filter);

    V valueAt(D when);

    Stream<T> stream();
}
