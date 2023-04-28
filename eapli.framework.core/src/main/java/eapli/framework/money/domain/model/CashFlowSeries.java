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
import java.time.temporal.TemporalAmount;
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

/**
 * A cash flow series.
 *
 * @author Paulo Gandra Sousa 2023.04.21
 */
public class CashFlowSeries implements ValueObject, Iterable<CashFlow> {

    private static final long serialVersionUID = 1L;

    private final List<CashFlow> data;

    /**
     * @return
     */
    public static CashFlowSeries of(final CashFlow... cashFlows) {
        // O(n)
        return new CashFlowSeries(Arrays.asList(cashFlows));
    }

    /**
     * Constructs an empty series.
     */
    public CashFlowSeries() {
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
     * @param mapper
     *
     * @return
     */
    public CashFlowSeries map(final UnaryOperator<CashFlow> mapper) {
        // O(n)
        return new CashFlowSeries(stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * @param adjuster
     *
     * @return
     */
    public CashFlowSeries shift(final UnaryOperator<LocalDate> adjuster) {
        // O(n)
        return new CashFlowSeries(stream().map(cf -> new CashFlow(adjuster.apply(cf.occurredAt()), cf.amount()))
                .collect(Collectors.toList()));
    }

    public CashFlowSeries shift(final TemporalAmount adjustement) {
        return null;
    }

    public CashFlowSeries between(final LocalDate lower, final LocalDate upper) {
        return null;
    }

    /**
     * @param filter
     *
     * @return
     */
    public CashFlowSeries filter(final Predicate<CashFlow> filter) {
        // O(n)
        return new CashFlowSeries(stream().filter(filter).collect(Collectors.toList()));
    }

    /**
     * @return
     */
    public Stream<CashFlow> stream() {
        return data.stream();
    }

    /**
     * @param of
     *
     * @return
     */
    public int indexOf(final LocalDate of) {
        // O(n)
        int idx = 1;
        for (final CashFlow cf : data) {
            if (cf.occurredAt().equals(of)) {
                return idx;
            }
            idx++;
        }

        return -1;
    }

    /**
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

    public CashFlowSeries[] slice(final Function<CashFlow, Integer> slicer) {
        return null;
    }

    /**
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
     * @param cf
     *
     * @return
     */
    public boolean contains(final CashFlow cf) {
        return data.contains(cf);
    }
}
