/*
 * Copyright (c) 2013-2019 the original author or authors.
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

import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.UnaryOperator;

import eapli.framework.validations.Invariants;

/**
 * A history Monad that keeps track of the history changes to a value. Since
 * each entry is immutable it is possible to have branches in the history.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * <code>
 * HistoryLog<Integer> h = HistoryLog.of(5)
 *                                   .map(x -> x*2)   // 10
 *                                   .map(x -> x+1)   // 11
 *                                   .map(x -> x * x) // 121
 *                                   .value();        // 121
 * </code>
 * </pre>
 *
 * It is possible to traverse the history using the provided iterator
 *
 * <pre>
 * <code>
 * for(ValueHistory<Integer> each: h.history()) {
 *      System.out.println(each);
 * }
 * </code>
 * </pre>
 *
 * @author Paulo Gandra de Sousa 25/06/2019
 * @param <T>
 *
 */
public class ValueHistory<T> {

    private final ZonedDateTime when;
    private final T value;
    private final Optional<String> message;
    private final Optional<ValueHistory<T>> previous;

    /**
     * @param value
     */
    private ValueHistory(final T value) {
        when = ZonedDateTime.now();
        this.value = value;
        message = Optional.empty();
        previous = Optional.empty();
    }

    private ValueHistory(final T value, final String message) {
        when = ZonedDateTime.now();
        this.value = value;
        this.message = Optional.of(message);
        previous = Optional.empty();
    }

    private ValueHistory(final ValueHistory<T> previous, final T value) {
        Invariants.nonNull(previous);
        when = ZonedDateTime.now();
        this.value = value;
        message = Optional.empty();
        this.previous = Optional.of(previous);
    }

    private ValueHistory(final ValueHistory<T> previous, final T value, final String message) {
        Invariants.nonNull(previous);
        when = ZonedDateTime.now();
        this.value = value;
        this.message = Optional.of(message);
        this.previous = Optional.of(previous);
    }

    /**
     * Factory method to create a new history.
     *
     * @param startValue
     * @return the new value history
     */
    public static <T> ValueHistory<T> of(final T startValue) {
        return new ValueHistory<>(startValue);
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

    public ZonedDateTime when() {
        return when;
    }

    public T value() {
        return value;
    }

    public Optional<String> message() {
        return message;
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
        return new ValueHistory<>(this, mapper.apply(value()));
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

    @Override
    public String toString() {
        return value.toString() + " [" + message.orElse("") + "] @ " + when;
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
            where = entry.previous.orElse(null);
            return entry;
        }
    }

    /**
     * Returns the history of this value.
     *
     * @return the history.
     */
    public Iterable<ValueHistory<T>> history() {
        return () -> new HistoryIterator<>(this);
    }

    /**
     * Returns the previous history value.
     *
     * @return the previous history value
     */
    public ValueHistory<T> undo() {
        return previous.orElseThrow(IllegalStateException::new);
    }

    /**
     * Checks if there is a previous history value.
     * 
     * @return true if there is a previous history value
     */
    public boolean canUndo() {
        return previous.isPresent();
    }
}
