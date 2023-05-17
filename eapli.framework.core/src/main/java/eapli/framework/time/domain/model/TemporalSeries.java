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
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import eapli.framework.domain.model.ValueObject;

/**
 * A temporal series, i.e., a series of temporal events. The series is ordered by date.
 *
 * @author Paulo Gandra Sousa 2023.04.14
 *
 * @param <D>
 *            the date dimension, e.g., LocalDate
 * @param <T>
 *            the value of the series, e.g., Money
 */
public interface TemporalSeries<D extends Temporal, T>
        extends ValueObject, Iterable<TemporalEvent<D, T>> {

    /**
     * Creates a new series with the element added. If a value already exists for the same date,
     * the resulting series will include a single value with the same date and the combination of
     * the values.
     *
     * @param when
     * @param event
     *
     * @return
     */
    TemporalSeries<D, T> add(D when, T event, BinaryOperator<T> combinator);

    /**
     * Merges two series. Values on the same date are combined together.
     *
     * @param other
     * @param combinator
     *
     * @return
     */
    TemporalSeries<D, T> merge(TemporalSeries<D, T> other, BinaryOperator<T> combinator);

    /**
     * Folds (reduces) the series given an initial value.
     *
     * @param initial
     * @param folder
     *
     * @return
     */
    T fold(T initial, BinaryOperator<T> folder);

    /**
     * Transforms the series.
     *
     * @param <S>
     * @param <U>
     * @param mapper
     *
     * @return
     */
    <S extends Temporal, U> TemporalSeries<S, U> map(Function<TemporalEvent<D, T>, TemporalEvent<S, U>> mapper);

    /**
     * Shifts the temporal dimension of the series keeping the values.
     */
    TemporalSeries<D, T> shift(UnaryOperator<D> adjuster);

    /**
     * Shifts the temporal dimension of the series keeping the values.
     *
     * @param adjustement
     *
     * @return
     */
    TemporalSeries<D, T> shift(TemporalAmount adjustement);

    /**
     * Filters the series between {@code lower} (inclusive) and {@code upper} (excluding) dates.
     *
     * @param lower
     * @param upper
     *
     * @return
     */
    TemporalSeries<D, T> between(D lower, D upper);

    /**
     * Filter the series according to a criteria.
     *
     * @param filter
     *
     * @return
     */
    TemporalSeries<D, T> filter(Predicate<TemporalEvent<D, T>> filter);

    /**
     * Removes a date point and respective value from the series.
     *
     * @param when
     *
     * @return
     */
    default TemporalSeries<D, T> without(final D when) {
        return filter(e -> e.occurredAt().equals(when));
    }

    /**
     * Slices the series in several series according to a criteria. Implementations may require to
     * create and merge series while slicing.
     *
     * @param slicer
     * @param combinator
     *
     * @return
     */
    <U> Map<U, TemporalSeries<D, T>> slice(final Function<TemporalEvent<D, T>, U> slicer, BinaryOperator<T> combinator);

    /**
     * Returns the value at a certain date.
     *
     * @param when
     *
     * @return
     */
    Optional<T> valueAt(D when);

    /**
     * Return the <em>nth</em> value of the series.
     *
     * @param i
     *
     * @return
     */
    Optional<TemporalEvent<D, T>> nth(int idx);

    /**
     * Returns the index of a certain date.
     *
     * @param event
     *
     * @return
     */
    Optional<Integer> indexOf(D when);

    /**
     * Checks if a value is part of this series.
     *
     * @param event
     *
     * @return
     */
    default boolean contains(final T event) {
        return dateOf(event).isPresent();
    }

    /**
     * Checks if a date is part of this series.
     *
     * @param event
     *
     * @return
     */
    default boolean hasDate(final D when) {
        return indexOf(when).isPresent();
    }

    /**
     * Returns the first date of the value.
     *
     * @param event
     *
     * @return
     */
    Optional<D> dateOf(T event);

    /**
     * Returns the date a value appears after a certain date.
     *
     * @param event
     * @param after
     *
     * @return
     */
    Optional<D> dateOf(T event, D after);

    /**
     * Returns the ordered stream of values.
     *
     * @return
     */
    Stream<TemporalEvent<D, T>> stream();

    /**
     * Return the length of the series, ie.e, the number of elements.
     *
     * @return
     */
    int length();
}
