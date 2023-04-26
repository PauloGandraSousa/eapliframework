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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
public class CashFlowSeries implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final Set<CashFlow> data;

    /**
     * @return
     */
    public static CashFlowSeries of(final CashFlow... cashFlows) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Constructs an empty series.
     */
    public CashFlowSeries() {
        data = new TreeSet<>();
    }

    /**
     * helper constructor
     *
     * @param collect
     */
    private CashFlowSeries(final Set<CashFlow> collect) {
        data = new TreeSet<>(collect);
    }

    /**
     * @param event
     *
     * @return
     */
    public CashFlowSeries add(final CashFlow event) {
        return new CashFlowSeries(copyMerge(data, event).collect(Collectors.toSet()));
    }

    private Stream<CashFlow> copyMerge(final Set<CashFlow> values, final CashFlow newElement) {
        return values.stream().map(cf -> cf.isSameDate(newElement) ? cf.add(newElement) : cf);
    }

    /**
     * @param other
     *
     * @return
     */
    public CashFlowSeries merge(final CashFlowSeries other) {
        final Map<LocalDate, CashFlow> s = new HashMap<>();

        data.forEach(cf -> s.put(cf.occurredAt(), cf));

        other.data.forEach(cf -> s.merge(cf.occurredAt(), cf, CashFlow::add));

        return new CashFlowSeries(s.values().stream().collect(Collectors.toSet()));
    }

    /**
     * @param initial
     * @param folder
     *
     * @return
     */
    public Money fold(final Money initial, final BinaryOperator<Money> folder) {
        return stream().map(CashFlow::amount).reduce(initial, folder);
    }

    /**
     * @param mapper
     *
     * @return
     */
    public CashFlowSeries map(final UnaryOperator<CashFlow> mapper) {
        return new CashFlowSeries(stream().map(mapper).collect(Collectors.toSet()));
    }

    /**
     * @param adjuster
     *
     * @return
     */
    public CashFlowSeries shift(final UnaryOperator<LocalDate> adjuster) {
        return new CashFlowSeries(stream().map(cf -> new CashFlow(adjuster.apply(cf.occurredAt()), cf.amount()))
                .collect(Collectors.toSet()));
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
        return new CashFlowSeries(stream().filter(filter).collect(Collectors.toSet()));
    }

    public Money valueAt(final LocalDate when) {
        return null;
    }

    /**
     * @return
     */
    public Stream<CashFlow> stream() {
        return data.stream();
    }

    public int indexOf(final LocalDate of) {
        // TODO Auto-generated method stub
        return 0;
    }

    public CashFlow nth(final int i) {
        // TODO Auto-generated method stub
        return null;
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

}
