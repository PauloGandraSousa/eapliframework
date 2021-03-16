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
package eapli.framework.functional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import eapli.framework.validations.Invariants;

/**
 * A history Monad that keeps track of the history changes to a value. Example
 * usage:
 *
 * <pre>
 * <code>
 * ValueHistory&lt;Integer&gt; c = ValueHistory.of(5)
 *                                       .map(x -> x*2)   // 10
 *                                       .map(x -> x+1)   // 11
 *                                       .map(x -> x * x) // 121
 *                                       .value();        // 121
 * </code>
 * </pre>
 *
 * It is possible to traverse the history using the provided iterator. The
 * history will contain the current value as well as all the previous values in
 * reverse chronological order.
 *
 * <pre>
 * <code>
 * for(ValueHistory&lt;Integer&gt; each: c.history()) {
 *      System.out.println(each);
 * }
 * </code>
 * </pre>
 *
 * Will output (date and time varying of course)
 *
 * <pre>
 * <code>
 * 121, [] @ 2019-06-25T18:44:50+0
 * 11, [] @ 2019-06-25T18:44:40+0
 * 10, [] @ 2019-06-25T18:44:30+0
 * 5, [] @ 2019-06-25T18:44:20+0
 * </code>
 * </pre>
 *
 * It is possible to have branches in the history, which is ok since each entry
 * is immutable and the value is supposed to be exactly that, a value, i.e., an
 * immutable object.
 *
 * <pre>
 * <code>
 * ValueHistory&lt;Integer&gt; c = ValueHistory.of(5)
 *                                       .map(x -> x*2)   // 10
 *                                       .map(x -> x+1);  // 11
 * ValueHistory&lt;Integer&gt; d = c;
 *
 *                       c.map(x -> x * x) // 121
 *                        .value();
 *
 *                       d.map(x -> x + 31) // 42
 *                        .value();
 * </code>
 * </pre>
 *
 * @param <T>
 * @author Paulo Gandra de Sousa 25/06/2019
 *
 */
public class ValueHistory<T> {

    // TODO dependency to PC clock - hard to test
    private static final Supplier<Instant> clockNow = Instant::now;

    private final Instant occurredAt;
    private final T value;
    private final String message;
    private final ValueHistory<T> previous;

    /**
     * Constructs an initial (i.e., first) value history.
     *
     * @param value
     * @param message
     */
    private ValueHistory(final T value, final String message) {
        occurredAt = clockNow.get();
        this.value = value;
        this.message = message;
        previous = null;
    }

    /**
     * Constructs a new value history attaching it to an existing history.
     *
     * @param previous
     * @param value
     * @param message
     */
    private ValueHistory(final ValueHistory<T> previous, final T value, final String message) {
        this(previous, value, message, clockNow.get());
    }

    /**
     * Constructs a new value history by merging two histories. mainly for
     * supporting {@link #flatMap(Function)}
     *
     * @param previous
     * @param next
     */
    private ValueHistory(final ValueHistory<T> previous, final T value, final String message,
            final Instant when) {
        Invariants.nonNull(previous);
        this.occurredAt = when;
        this.value = value;
        this.message = message;
        this.previous = previous;
    }

    /**
     * Factory method to create a new history.
     *
     * @param startValue
     * @return the new value history
     */
    public static <T> ValueHistory<T> of(final T startValue) {
        return new ValueHistory<>(startValue, null);
    }

    /**
     * Factory method to create a new history.
     *
     * @param startValue
     * @param message
     * @return the new value history
     */
    public static <T> ValueHistory<T> of(final T startValue, final String message) {
        return new ValueHistory<>(startValue, message);
    }

    /**
     *
     * @return
     */
    public Instant when() {
        return occurredAt;
    }

    /**
     *
     * @return
     */
    public T value() {
        return value;
    }

    /**
     *
     * @return
     */
    public Optional<String> message() {
        return Optional.ofNullable(message);
    }

    /**
     * Helper method to create a new value keeping the current history.
     *
     * @param newValue
     * @return a new History
     */
    public ValueHistory<T> andThen(final T newValue) {
        return map(x -> newValue);
    }

    /**
     * Helper method to create a new value keeping the current history.
     *
     * @param newValue
     * @param message
     * @return a new History
     */
    public ValueHistory<T> andThen(final T newValue, final String message) {
        return map(x -> newValue, message);
    }

    /**
     * Maps the current value and creates a new history.
     *
     * @param mapper
     * @return a new History
     */
    public ValueHistory<T> map(final UnaryOperator<T> mapper) {
        return new ValueHistory<>(this, mapper.apply(value()), null);
    }

    /**
     * Maps the current value and creates a new history.
     *
     * @param mapper
     * @param message
     * @return a new History
     */
    public ValueHistory<T> map(final UnaryOperator<T> mapper, final String message) {
        return new ValueHistory<>(this, mapper.apply(value()), message);
    }

    /**
     *
     * @param mapper
     * @return
     */
    public ValueHistory<T> flatMap(final Function<T, ValueHistory<T>> mapper) {
        final ValueHistory<T> mapped = mapper.apply(value());

        final Deque<ValueHistory<T>> reverseHistory = new ArrayDeque<>();
        for (final ValueHistory<T> each : mapped.history()) {
            reverseHistory.push(each);
        }

        ValueHistory<T> head = this;
        for (final ValueHistory<T> each : reverseHistory) {
            head = new ValueHistory<>(head, each.value(), each.message().orElse(null), each.when());
        }

        return head;
    }

    @Override
    public String toString() {
        return value.toString() + " [" + (message != null ? message : "") + "] @ "
                + ZonedDateTime.from(occurredAt);
    }

    /**
     * History iterator.
     *
     * @author Paulo Gandra de Sousa 25/06/2019
     *
     * @param <U>
     */
    private class HistoryIterator<U> implements Iterator<ValueHistory<U>> {
        private ValueHistory<U> where;

        private HistoryIterator(final ValueHistory<U> history) {
            where = history;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return where != null;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.util.Iterator#next()
         */
        @Override
        public ValueHistory<U> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            final ValueHistory<U> entry = where;
            where = entry.previous;
            return entry;
        }
    }

    /**
     * Returns the full history of this value including the current history
     * entry.
     *
     * @return the history.
     */
    public Iterable<ValueHistory<T>> history() {
        return () -> new HistoryIterator<>(this);
    }

    /**
     * Returns the past history of this value, i.e., everything before the
     * current history entry.
     *
     * @return the history.
     */
    public Iterable<ValueHistory<T>> pastHistory() {
        return () -> new HistoryIterator<>(this.previous);
    }

    /**
     * Returns the previous history value.
     *
     * @return the previous history value
     */
    public Optional<ValueHistory<T>> previous() {
        return Optional.ofNullable(previous);
    }
}
