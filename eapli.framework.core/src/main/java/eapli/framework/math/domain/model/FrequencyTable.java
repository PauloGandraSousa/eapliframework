/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.math.domain.model;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A frequency table, that is, the number of occurrences of a certain value.
 *
 * <p>
 * This class is thread-safe.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 * @param <T>
 */
public class FrequencyTable<T> {

    private final Map<T, BigInteger> frequencies = new ConcurrentHashMap<>();

    private final Function<T, BigInteger> collector;

    /**
     * Constructs an empty frequency table that will collect any value.
     */
    public FrequencyTable() {
        collector = this::collectAny;
    }

    /**
     * Initializes the frequency table with the expected values. Each expected value will have its
     * frequency count as 0.
     *
     * @param valuesToConsider
     * @param onlyTheseValues
     *            if {@code true} only the specified values will be counted and any unknown value
     *            passed to collect will be ignored
     */
    public FrequencyTable(final Iterable<T> valuesToConsider, final boolean onlyTheseValues) {
        valuesToConsider.forEach(this::consider);
        if (onlyTheseValues) {
            collector = this::collectIfPresent;
        } else {
            collector = this::collectAny;
        }
    }

    /**
     * Increments the count of an entry in the table by one. If the value is not yet present in the
     * table it will be added and its frequency set to 1.
     * <p>
     * If the frequency table was initialized to ignore unknown values, and if the value is not yet
     * present in the table, the value is ignored.
     *
     * @param one
     * @return
     */
    public BigInteger collect(final T one) {
        return collector.apply(one);
    }

    private BigInteger collectAny(final T one) {
        return frequencies.merge(one, BigInteger.ONE, (o, a) -> o.add(a));
    }

    private BigInteger collectIfPresent(final T one) {
        return frequencies.computeIfPresent(one, (w, o) -> o.add(BigInteger.ONE));
    }

    /**
     * Considers that a certain value should be considered but it might not be present at all in the
     * source. That is, its frequency is 0.
     * <p>
     * Useful when 0 frequencies must be taken into account. Typically the calling object
     * initializes the frequency table with the expected values and afterwards counts the frequency
     * from a certain source.
     *
     * @param one
     * @return
     */
    public BigInteger consider(final T one) {
        return frequencies.merge(one, BigInteger.ZERO, (o, a) -> o.add(a));
    }

    /**
     *
     * @param what
     * @return
     */
    public BigInteger frequencyOf(final T what) {
        return frequencies.getOrDefault(what, BigInteger.ZERO);
    }

    /**
     *
     * @return
     */
    public Set<T> values() {
        return frequencies.keySet();
    }
}
