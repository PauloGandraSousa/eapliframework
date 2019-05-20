/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.util.function;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
     * Nill Operation
     */
    public static void nop() {
        // do noting
    }

    /**
     * Nil operation, i.e., do nothing on the parameter of the function.
     *
     * @param x
     * @param <T>
     */
    @SuppressWarnings("squid:S1172")
    public static <T> void ignore(final T x) {
        // do noting
    }

    /**
     * Gets the value of a supplier based a predicate
     *
     * @param arg
     *                  the argument to test against the predicate
     * @param condition
     *                  the predicate to meet
     * @param success
     *                  the supplier in case of success of the predicate
     * @param failure
     *                  the supplier in case of insuccess of the predicate
     * @return
     */
    public static <T, R> R getIfOrElse(final T arg, final Predicate<T> condition, final Supplier<R> success,
            final Supplier<R> failure) {
        if (condition.test(arg)) {
            return success.get();
        } else {
            return failure.get();
        }
    }

    /**
     * Gets the value of a supplier based on a predicate.
     *
     * @param <R>
     *                  the type of value to return
     * @param condition
     *                  the predicate to test
     * @param success
     *                  the supplier to use in case the predicate test succeeds
     * @param failure
     *                  the supplier to use in case the predicate test fails
     * @return the value supplied by either {@code success} or {@code failure}
     */
    public static <R> R getIfOrElse(final BooleanSupplier condition, final Supplier<R> success,
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
     * Retries an operation with a given interval time between invocations up to a certain number of
     * attempts. the retry stops if the operation returned optional contains a value or the maximum
     * number of attempts is reached.
     *
     * @param operation
     * @param sleep
     *                    seed time in milliseconds to wait for retrying. it will progressively increase
     *                    between attempts if progressive is set to true
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
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
                : -1;

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
