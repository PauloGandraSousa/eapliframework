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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eapli.framework.domain.model.ValueObject;
import lombok.EqualsAndHashCode;

/**
 * A cash flow series, i.e., an ordered list of cash flows. If repeated values are added to the
 * series a single value is kept with the sum of the amounts. The series is temporal-ordered.
 * <p>
 * This is a value object, as such there are no mutable operations. All methods return a new series
 * and keep this object unmodified.
 *
 * @author Paulo Gandra Sousa 2023.04.21
 */
@EqualsAndHashCode
public class CashFlowSeries implements ValueObject, Iterable<CashFlow> {

    private static final long serialVersionUID = 1L;

    private final List<CashFlow> data;

    /**
     * Factory method. If cash flows of the same date are passed as parameter, the resulting series
     * will include a cash flow for the same date and the sum of the amounts.
     *
     * @return
     */
    public static CashFlowSeries of(final CashFlow... cashFlows) {
        // O(n)
        return new CashFlowSeries(Arrays.asList(cashFlows));
    }

    /**
     * Empty cash flow series.
     */
    public static final CashFlowSeries EMPTY = new CashFlowSeries();

    /**
     * Constructs an empty series.
     */
    private CashFlowSeries() {
        data = new LinkedList<>();
    }

    /**
     * helper constructor
     *
     * @param collect
     */
    private CashFlowSeries(final Collection<CashFlow> collect) {
        // O(n)
        final Map<LocalDate, CashFlow> s = new HashMap<>();
        collect.forEach(cf -> s.merge(cf.occurredAt(), cf, CashFlow::add));

        // O(n) + O(n)
        data = new LinkedList<>(s.values());
        Collections.sort(data);
    }

    /**
     * Creates a new series with the element added. If a cash flow already exists for the same date,
     * the resulting series will include a cash flow with the same date and sum of the amounts.
     *
     * @param event
     *
     * @return
     */
    public CashFlowSeries add(final CashFlow event) {
        final List<CashFlow> other = new ArrayList<>();
        other.add(event);
        return merge(data, other);
    }

    /**
     * Merges two series. Cash flows on the same date are summed together.
     *
     * @param other
     *
     * @return
     */
    public CashFlowSeries merge(final CashFlowSeries other) {
        return merge(data, other.data);
    }

    private CashFlowSeries merge(final Collection<CashFlow> one, final Collection<CashFlow> other) {
        final Map<LocalDate, CashFlow> s = new HashMap<>();

        // O(n)
        one.forEach(cf -> s.merge(cf.occurredAt(), cf, CashFlow::add));

        // O(n)
        other.forEach(cf -> s.merge(cf.occurredAt(), cf, CashFlow::add));

        // O(n)
        return new CashFlowSeries(s.values());
    }

    /**
     * Folds (reduces) the series given an initial value.
     *
     * @param initial
     * @param folder
     *
     * @return
     */
    public Money fold(final Money initial, final BinaryOperator<Money> folder) {
        // O(n)
        return stream().map(CashFlow::amount).reduce(initial, folder);
    }

    /**
     * Transforms the series.
     *
     * @param mapper
     *
     * @return
     */
    public CashFlowSeries map(final UnaryOperator<CashFlow> mapper) {
        // O(n)
        return new CashFlowSeries(stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * Shifts the temporal dimension of the series keeping the amounts.
     *
     * @param adjuster
     *
     * @return
     */
    public CashFlowSeries shift(final UnaryOperator<LocalDate> adjuster) {
        // O(n)
        return new CashFlowSeries(stream().map(cf -> (CashFlow) cf.shift(adjuster)).collect(Collectors.toList()));
    }

    /**
     * Filters the series between (inclusive) two dates.
     *
     * @param lower
     * @param upper
     *
     * @return
     */
    public CashFlowSeries between(final LocalDate lower, final LocalDate upper) {
        return filter(cf -> cf.occurredAt().compareTo(lower) >= 0 && cf.occurredAt().compareTo(upper) <= 0);
    }

    /**
     * Filter the series according to a criteria.
     *
     * @param criteria
     *
     * @return
     */
    public CashFlowSeries filter(final Predicate<CashFlow> criteria) {
        // O(n)
        return new CashFlowSeries(stream().filter(criteria).collect(Collectors.toList()));
    }

    /**
     * Returns the ordered stream of cash flows.
     *
     * @return
     */
    public Stream<CashFlow> stream() {
        return data.stream();
    }

    /**
     * Returns the index of the cash flow at a certain date.
     *
     * @param of
     *
     * @return
     */
    public Optional<Integer> indexOf(final LocalDate of) {
        // O(n)
        int idx = 1;
        for (final CashFlow cf : data) {
            if (cf.occurredAt().equals(of)) {
                return Optional.of(idx);
            }
            idx++;
        }

        return Optional.empty();
    }

    /**
     * Returns the amount of the cash flow at a certain date.
     *
     * @param when
     *
     * @return
     */
    public Optional<Money> valueAt(final LocalDate when) {
        // O(n)
        for (final CashFlow cf : data) {
            if (cf.occurredAt().equals(when)) {
                return Optional.of(cf.amount());
            }
        }

        return Optional.empty();
    }

    /**
     * Return the <em>nth</em> cash flow of the series.
     *
     * @param i
     *
     * @return
     */
    public Optional<CashFlow> nth(final int i) {
        if (i < 1) {
            return Optional.empty();
        }

        // O(n)
        int idx = 1;
        for (final CashFlow cf : data) {
            if (idx == i) {
                return Optional.of(cf);
            }
            idx++;
        }

        return Optional.empty();
    }

    /**
     * Slices the series in several series according to a criteria.
     *
     * @param slicer
     *
     * @return
     */
    public <U> Map<U, CashFlowSeries> slice(final Function<CashFlow, U> slicer) {
        final Map<U, CashFlowSeries> ret = new HashMap<>();

        for (final CashFlow cf : data) {
            final U idx = slicer.apply(cf);
            final CashFlowSeries s = ret.get(idx);
            if (s == null) {
                ret.put(idx, CashFlowSeries.of(cf));
            } else {
                ret.put(idx, s.merge(CashFlowSeries.of(cf)));
            }
        }

        return ret;
    }

    /**
     * Return the length of the series, ie.e, the number of elements.
     *
     * @return
     */
    public int length() {
        return data.size();
    }

    @Override
    public Iterator<CashFlow> iterator() {
        return data.iterator();
    }

    /**
     * Checks if a cash flow is part of this series.
     *
     * @param cf
     *
     * @return
     */
    public boolean contains(final CashFlow cf) {
        return data.contains(cf);
    }
}
