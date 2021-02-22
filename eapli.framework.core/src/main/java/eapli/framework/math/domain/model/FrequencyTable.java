/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import eapli.framework.validations.Preconditions;

/**
 * A frequency table, that is, the number of occurrences of a certain "token", e.g., a word in a
 * text.
 * <p>
 * Conceptually, a frequency table can be seen as a map from "token" to number, as such this class
 * exposes some methods common in the {@link java.util.Map Map} interface such as
 * {@link java.util.Map#forEach(BiConsumer) forEach}.
 * <p>
 * This class is thread-safe.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 *
 * @param <T>
 *            the type of object to count the frequency of. This should be a class with proper
 *            {@code hashCode} and {@code equals}, preferably an immutable class but at least
 *            one whose hash code does not change thru out the object's life-cycle even if the
 *            object is mutable.
 */
public class FrequencyTable<T> {

    private final Map<T, Long> frequencies = new ConcurrentHashMap<>();

    private final Function<T, Long> collector;

    /**
     * Constructs an empty frequency table that will collect any "token".
     */
    public FrequencyTable() {
        collector = this::collectAny;
    }

    /**
     * Initializes the frequency table with the expected "tokens". Each expected "token" will have
     * its frequency count as 0.
     *
     * @param tokensToConsider
     * @param onlyTheseTokens
     *            if {@code true} only the specified "tokens" will be counted and any unknown
     *            "token"
     *            passed to collect will be ignored
     */
    public FrequencyTable(final Iterable<T> tokensToConsider, final boolean onlyTheseTokens) {
        tokensToConsider.forEach(this::consider);
        if (onlyTheseTokens) {
            collector = this::collectIfKnownToken;
        } else {
            collector = this::collectAny;
        }
    }

    /**
     * Increments the count of an entry in the table by one. If the "token" is not yet present in
     * the table it will be added and its frequency set to 1.
     * <p>
     * If the frequency table was initialized to ignore unknown "tokens", and if the "token" is not
     * yet present in the table, the "token" is ignored.
     *
     * @param token
     * @return the frequency count of that "token" after collecting it
     */
    public Long collect(final T token) {
        Preconditions.nonNull(token);

        return collector.apply(token);
    }

    private Long collectAny(final T token) {
        return frequencies.merge(token, 1L, (old, accumulate) -> old + accumulate);
    }

    private Long collectIfKnownToken(final T token) {
        return frequencies.computeIfPresent(token, (key, currentFrequency) -> currentFrequency + 1L);
    }

    /**
     * A certain "token" should be considered but it might not be present at all in the
     * source. That is, its frequency is 0.
     * <p>
     * Useful when 0 frequencies must be taken into account. Typically the calling object
     * initializes the frequency table with the expected "tokens" and afterwards counts the
     * frequency from a certain source.
     * <p>
     * If the "token" is already present in the table, its frequency won't be changed.
     *
     * @param token
     * @return the frequency count of that "token" after considering it
     */
    public Long consider(final T token) {
        return frequencies.merge(token, 0L, (o, a) -> o + a);
    }

    /**
     * Returns the frequency of a certain "token". If the token does not exist in the table returns
     * 0.
     *
     * @param token
     *            the desired "token"
     * @return the frequency of the specified "token"
     */
    public long frequencyOf(final T token) {
        return frequencies.getOrDefault(token, 0L);
    }

    /**
     * Returns the "tokens" present in the frequency table.
     *
     * @return the "tokens" present in the frequency table
     */
    public Set<T> tokens() {
        return frequencies.keySet();
    }

    /**
     * Returns the number of tokens in the table. If there are more than
     * {@Integer.MAX_VALUE} entries it returns {@Integer.MAX_VALUE}.
     *
     * @return the number of tokens in the table
     */
    public int size() {
        return frequencies.size();
    }

    /**
     * Checks if a token is present in the table.
     *
     * @param token
     * @return {@code token} if the token is present in the table
     */
    public boolean contains(final T token) {
        return frequencies.containsKey(token);
    }

    /**
     * Returns all the entries in the table as a tuple ("token", frequency).
     *
     * @return all the entries in the table
     */
    public Set<Entry<T, Long>> entries() {
        return frequencies.entrySet();
    }

    /**
     * Returns a stream of tuples ordered by the "token" (ascending order).
     *
     * @return a stream of tuples ordered by the "token"
     */
    public Stream<Entry<T, Long>> sortedEntriesByToken() {
        final Map<T, Long> sorted = new TreeMap<>(frequencies);
        return sorted.entrySet().stream();
    }

    /**
     * Returns a stream of tuples ordered by the frequency (descending order).
     *
     * @return a stream of tuples ordered by the frequency
     */
    public Stream<Entry<T, Long>> sortedEntriesByFrequency() {
        return frequencies.entrySet().stream().sorted(Entry.comparingByValue());
    }

    /**
     * Executes an action for each entry in the table.
     *
     * @param action
     */
    @SuppressWarnings("java:S4276")
    public void forEach(final BiConsumer<T, Long> action) {
        frequencies.forEach(action);
    }

    /**
     * Returns the total occurrences collected by this table. That is, the sum of all frequencies.
     *
     * @return the total occurrences
     */
    public long totalOccurrences() {
        return entries().stream().map(Entry::getValue).reduce(0L, (a, b) -> a + b);
    }

    /**
     * Ideas:
     *
     * public Set<Entry<T, Long>> topPercentile(int percentile) {
     * }
     * public Set<Entry<T, Long>> top(int n) {
     * }
     *
     * public Set<Entry<T, Long>> bottomPercentile() {
     * }
     *
     * public FrequencyTable<T> slice(int topPercentile, int bottomPercentile) {
     * }
     *
     * public FrequencyTable<T> filter(Predicate<T, long> filter) {
     * }
     */
}
