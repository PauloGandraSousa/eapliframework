/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.util.function;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import eapli.framework.actions.Action;
import eapli.framework.actions.TimedActions;
import eapli.framework.util.Utilitarian;

/**
 * A utility class with some handy functions
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Functions implements Utilitarian {

    private Functions() {
        // ensure utility
    }

    public static final Action THROW_ARGUMENT = () -> {
        throw new IllegalArgumentException();
    };

    public static final Action THROW_STATE = () -> {
        throw new IllegalStateException();
    };

    public static final Consumer<String> THROW_ARGUMENT_WITH_MESSAGE = m -> {
        throw new IllegalArgumentException(m);
    };

    public static final Consumer<String> THROW_STATE_WITH_MESSAGE = m -> {
        throw new IllegalStateException(m);
    };

    /**
     * Identity function
     *
     * @param x
     * @return the same object passed as input parameter
     */
    public static <T> T id(final T x) {
        return x;
    }

    /**
     * No-Op
     */
    public static void nop() {
        // no-op
    }

    /**
     * No-Op
     */
    @SuppressWarnings("squid:S1172")
    public static <T> void ignore(final T x) {
        // no-op
    }

    /**
     * Ensures a certain condition is met. Executes the predicate on the
     * parameter arg and if the predicate is not met, the action is executed.
     *
     * @param arg
     * @param condition
     *            the invariant to validate arg
     * @param onFail
     * @return
     */
    public static <T> void ensure(final T arg, final Predicate<T> condition, final Action onFail) {
        if (!condition.test(arg)) {
            onFail.execute();
        }
    }

    /**
     * Ensures a certain condition is met. If the predicate is not met, the
     * action is executed.
     *
     * @param arg
     * @param validator
     *            the invariant to validate arg
     * @param onFail
     * @return
     */
    public static void ensure(final BooleanSupplier condition, final Action onFail) {
        if (!condition.getAsBoolean()) {
            onFail.execute();
        }
    }

    /**
     * Executes the predicate on the parameter arg. If the predicate is not met,
     * the action is executed passing the parameter message
     *
     * @param arg
     * @param condition
     * @param consequence
     * @param msg
     */
    public static <T> void ifNotThen(final T arg, final Predicate<T> condition, final Consumer<String> consequence,
            final String msg) {
        if (!condition.test(arg)) {
            consequence.accept(msg);
        }
    }

    /**
     * Executes the predicate and if the predicate is not met, the action is
     * executed passing the parameter message
     *
     * @param arg
     * @param validator
     * @param consequence
     * @param msg
     */
    public static void ifNotThen(final BooleanSupplier condition, final Consumer<String> consequence,
            final String msg) {
        if (!condition.getAsBoolean()) {
            consequence.accept(msg);
        }
    }

    /**
     * Treating an If-Then-Else structure as an expression
     *
     * @param arg
     * @param condition
     * @param success
     * @param failure
     * @return
     */
    public static <T, R> R ifThenElse(final T arg, final Predicate<T> condition, final Supplier<R> success,
            final Supplier<R> failure) {
        if (condition.test(arg)) {
            return success.get();
        } else {
            return failure.get();
        }
    }

    /**
     * Treating an If-Then-Else structure as an expression
     *
     * @param arg
     * @param test
     * @param success
     * @param failure
     * @return
     */
    public static <R> R ifThenElse(final BooleanSupplier condition, final Supplier<R> success,
            final Supplier<R> failure) {
        if (condition.getAsBoolean()) {
            return success.get();
        } else {
            return failure.get();
        }
    }

    //
    // ========================================================================
    //

    /**
     * Retries an operation with a given interval time between invocations up to
     * a certain number of attempts. the retry stops if the operation returned
     * optional contains a value or the maximum number of attempts is reached.
     *
     * @param operation
     * @param sleep
     *            seed time in milliseconds to wait for retrying. it will
     *            progressively increase between attempts if progressive is set
     *            to true
     * @param maxAttempts
     * @param progressive
     * @return
     */
    public static <T> Optional<T> retry(final Supplier<Optional<T>> operation, final int sleep, final int maxAttempts,
            final boolean progressive) {
        int atempts = 1;
        Optional<T> u = operation.get();
        while (!u.isPresent() && atempts <= maxAttempts) {
            atempts++;
            if (progressive) {
                TimedActions.delay(sleep, atempts);
            } else {
                TimedActions.delay(sleep);
            }
            u = operation.get();
        }
        return u;
    }

    public static <T> Optional<T> retry(final Supplier<Optional<T>> operation, final int sleep, final int maxAttempts) {
        return retry(operation, sleep, maxAttempts, true);
    }

    //
    // ========================================================================
    //

    /**
     * zips two streams
     *
     * extracted from
     * https://stackoverflow.com/questions/17640754/zipping-streams-using-jdk8-with-lambda-java-util-stream-streams-zip/32342172
     *
     * @param a
     * @param b
     * @param zipper
     * @return
     */
    public static <A, B, C> Stream<C> zip(final Stream<? extends A> a, final Stream<? extends B> b,
            final BiFunction<? super A, ? super B, ? extends C> zipper) {
        Objects.requireNonNull(zipper);
        final Spliterator<? extends A> aSpliterator = Objects.requireNonNull(a).spliterator();
        final Spliterator<? extends B> bSpliterator = Objects.requireNonNull(b).spliterator();

        // Zipping looses DISTINCT and SORTED characteristics
        final int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics()
                & ~(Spliterator.DISTINCT | Spliterator.SORTED);

        final long zipSize = ((characteristics & Spliterator.SIZED) != 0)
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown()) : -1;

        final Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
        final Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
        final Iterator<C> cIterator = new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return aIterator.hasNext() && bIterator.hasNext();
            }

            @Override
            public C next() {
                return zipper.apply(aIterator.next(), bIterator.next());
            }
        };

        final Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
        return (a.isParallel() || b.isParallel()) ? StreamSupport.stream(split, true)
                : StreamSupport.stream(split, false);
    }
}
