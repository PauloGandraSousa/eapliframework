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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;

/**
 * A Tree-based implementation of temporal series, i.e., a series of temporal events.
 *
 * @author Paulo Gandra Sousa 2023.05.08
 *
 * @param <D>
 *            the date dimension, e.g., LocalDate
 * @param <T>
 *            the value of the series, e.g., Money
 */
@EqualsAndHashCode
public class TreeTemporalSeries<D extends Temporal, T> implements TemporalSeries<D, T> {

    private final SortedMap<D, T> data;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * helper constructor. client code should use {@link TreeTemporalSeriesBuilder}.
     *
     * @param data
     */
    TreeTemporalSeries(final Map<D, T> data) {
        this.data = new TreeMap<>(data);
    }

    /**
     * helper
     *
     * @param data
     * @param when
     * @param event
     * @param combinator
     */
    private TreeTemporalSeries(final Map<D, T> data, final D when, final T event, final BinaryOperator<T> combinator) {
        this.data = new TreeMap<>(data);
        this.data.merge(when, event, combinator);
    }

    /**
     * helper
     *
     * @param data
     * @param when
     * @param event
     * @param combinator
     */
    private TreeTemporalSeries(final Map<D, T> data, final TemporalSeries<D, T> other,
            final BinaryOperator<T> combinator) {
        this.data = new TreeMap<>(data);

        other.forEach(cf -> this.data.merge(cf.occurredAt(), cf.event(), combinator));
    }

    /**
     * helper constructor
     */
    private TreeTemporalSeries() {
        this.data = new TreeMap<>();
    }

    /**
     * Empty series.
     *
     * @param <S>
     * @param <U>
     *
     * @return
     */
    public static <S extends Temporal, U> TemporalSeries<S, U> empty() {
        return new TreeTemporalSeries<>();
    }

    @Override
    public Iterator<TemporalEvent<D, T>> iterator() {
        return stream().iterator();
    }

    @Override
    public TemporalSeries<D, T> add(final D when, final T event, final BinaryOperator<T> combinator) {
        return new TreeTemporalSeries<>(data, when, event, combinator);
    }

    @Override
    public TemporalSeries<D, T> merge(final TemporalSeries<D, T> other, final BinaryOperator<T> combinator) {
        return new TreeTemporalSeries<>(data, other, combinator);
    }

    @Override
    public T fold(final T initial, final BinaryOperator<T> folder) {
        return data.values().stream().reduce(initial, folder);
    }

    @Override
    public TemporalSeries<D, T> shift(final UnaryOperator<D> adjuster) {
        // O(n)
        final var s = stream().map(t -> TemporalEvent.of(adjuster.apply(t.occurredAt()), t.event()));
        // O(n)
        return new TreeTemporalSeries<>(
                s.collect(Collectors.toMap(TemporalEvent::occurredAt, TemporalEvent::event)));
    }

    @Override
    public TemporalSeries<D, T> shift(final TemporalAmount adjustement) {
        // O(n)
        @SuppressWarnings("unchecked")
        final var s = stream().map(t -> TemporalEvent.of((D) (t.occurredAt().plus(adjustement)), t.event()));
        // O(n)
        return new TreeTemporalSeries<>(
                s.collect(Collectors.toMap(TemporalEvent::occurredAt, TemporalEvent::event)));
    }

    @Override
    public TemporalSeries<D, T> between(final D lower, final D upper) {
        return new TreeTemporalSeries<>(data.subMap(lower, upper));
    }

    @Override
    public TemporalSeries<D, T> filter(final Predicate<TemporalEvent<D, T>> filter) {
        // O(n)
        final var s = stream().filter(filter);
        // O(n)
        return new TreeTemporalSeries<>(
                s.collect(Collectors.toMap(TemporalEvent::occurredAt, TemporalEvent::event)));
    }

    @Override
    public Optional<T> valueAt(final D when) {
        return Optional.ofNullable(data.get(when));
    }

    @Override
    public Optional<TemporalEvent<D, T>> nth(final int idx) {
        if (idx < 1) {
            return Optional.empty();
        }

        // O(n)
        int i = 1;
        for (final Entry<D, T> cf : data.entrySet()) {
            if (idx == i) {
                return Optional.of(TemporalEvent.of(cf.getKey(), cf.getValue()));
            }
            i++;
        }

        return Optional.empty();
    }

    @Override
    public Optional<D> dateOf(final T event) {
        // O(n)
        for (final Entry<D, T> cf : data.entrySet()) {
            if (event.equals(cf.getValue())) {
                return Optional.of(cf.getKey());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<D> dateOf(final T event, final D after) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<Integer> indexOf(final D when) {
        // O(n)
        int idx = 1;
        for (final D cf : data.keySet()) {
            if (cf.equals(when)) {
                return Optional.of(idx);
            }
            idx++;
        }
        return Optional.empty();
    }

    @Override
    public Stream<TemporalEvent<D, T>> stream() {
        return data.entrySet().stream().map(e -> TemporalEvent.of(e.getKey(), e.getValue()));
    }

    @Override
    public int length() {
        return data.size();
    }

    @Override
    public <S extends Temporal, U> TemporalSeries<S, U> map(
            final Function<TemporalEvent<D, T>, TemporalEvent<S, U>> mapper) {
        // O(n)
        final var s = stream().map(mapper);
        // O(n)
        return new TreeTemporalSeries<>(
                s.collect(Collectors.toMap(TemporalEvent::occurredAt, TemporalEvent::event)));
    }

    @Override
    public <U> Map<U, TemporalSeries<D, T>> slice(final Function<TemporalEvent<D, T>, U> slicer,
            final BinaryOperator<T> combinator) {
        final Map<U, TemporalSeries<D, T>> ret = new HashMap<>();

        for (final Entry<D, T> cf : data.entrySet()) {
            final U idx = slicer.apply(TemporalEvent.of(cf.getKey(), cf.getValue()));

            final TemporalSeries<D, T> one = new TreeTemporalSeriesBuilder<D, T>(combinator)
                    .with(cf.getKey(), cf.getValue())
                    .build();
            final TemporalSeries<D, T> s = ret.get(idx);
            if (s == null) {
                ret.put(idx, one);
            } else {
                ret.put(idx, s.merge(one, combinator));
            }
        }

        return ret;
    }

}
