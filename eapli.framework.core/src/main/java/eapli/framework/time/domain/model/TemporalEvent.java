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

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.function.UnaryOperator;

import eapli.framework.money.domain.model.Money;

/**
 * A timed event, i.e., something that occurred at a specific point in time.
 *
 * @author Paulo Gandra Sousa 2023.04.14
 *
 * @param <T>
 *            the type of the temporal date point, e.g., {@link LocalDate}
 * @param <S>
 *            the type of value, e.g., {@link Money}
 */
public interface TemporalEvent<T extends Temporal, S> {
    /**
     * Returns the instant when the event occurred.
     *
     * @return the instant when the event occurred
     */
    T occurredAt();

    /**
     * @return
     */
    S event();

    /**
     * Merges two temporal events for the same date. It is the responsibility of the implementing
     * class to check if the two events occur in the same date.
     *
     * @param other
     *
     * @return
     */
    TemporalEvent<T, S> merge(TemporalEvent<T, S> other);

    /**
     * Shifts the date of the event.
     *
     * @param adjuster
     *
     * @return a new instance of the temporal event with its date adjusted but with the same value
     */
    TemporalEvent<T, S> shift(final UnaryOperator<T> adjuster);

    /**
     * @param other
     *
     * @return
     */
    default boolean isSameDate(final TemporalEvent<T, S> other) {
        return occurredAt().equals(other.occurredAt());
    }
}
