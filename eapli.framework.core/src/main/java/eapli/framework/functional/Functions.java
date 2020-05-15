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

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import eapli.framework.actions.TimedActions;
import eapli.framework.util.Utility;

/**
 * A utility class to build and work with Functions.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
public final class Functions {
    private Functions() {
        // ensure utility
    }

    /**
     * Constant function. Builds a {@code Function} that will always return the same
     * value {@code c}
     *
     * <pre>
     * <code>
     * T -> T : x -> c
     * </code>
     * </pre>
     *
     * @param c
     * @return a {@code Function} that will always return the same value {@code c}
     */
    public static <T> Function<T, T> constant(final T c) {
        return ignored -> c;
    }

    /**
     * Composes two functions. Mathematically speaking, {@code g o f}, performs
     * {@code g} after {@code f}, or {@code g(f(x))}.
     *
     * @param g
     * @param f
     * @return a function which is the composition of g after f
     */
    public static <A, B, C> Function<A, C> composition(final Function<B, C> g,
            final Function<A, B> f) {
        return x -> g.apply((f.apply(x)));
    }

    /**
     * Builds a function that gets a value from one of two suppliers based on a
     * predicate.
     *
     * @param condition
     *            the predicate to meet
     * @param success
     *            the supplier in case of success of the predicate
     * @param failure
     *            the supplier in case of insuccess of the predicate
     * @return the value supplied by either {@code success} or {@code failure}
     */
    public static <T, R> Function<T, R> conditionalGet(final Predicate<T> condition,
            final Supplier<R> success,
            final Supplier<R> failure) {
        return arg -> condition.test(arg) ? success.get() : failure.get();
    }

    //
    // ========================================================================
    //

    /**
     * Retries an operation with a given interval time between invocations up to a
     * certain number of attempts. The retry stops if the operation returned
     * optional contains a value or the maximum number of attempts is reached.
     *
     * @param operation
     * @param sleep
     *            seed time in milliseconds to wait for retrying. it will
     *            progressively increase between attempts if progressive is
     *            set to true
     * @param maxAttempts
     * @param progressive
     * @return an optional with a value, an empty optional if the maximum number of
     *         attempts has been reached
     */
    public static <T> Optional<T> retry(final Supplier<Optional<T>> operation, final int sleep,
            final int maxAttempts,
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

    /**
     * Retries an operation with a given interval time between invocations up to a
     * certain number of attempts. The retry stops if the operation returned
     * optional contains a value or the maximum number of attempts is reached.
     *
     * @param operation
     * @param sleep
     *            seed time in milliseconds to wait for retrying. it will
     *            progressively increase between attempts if progressive is
     *            set to true
     * @param maxAttempts
     * @return an optional with a value, an empty optional if the maximum number of
     *         attempts has been reached
     */
    public static <T> Optional<T> retry(final Supplier<Optional<T>> operation, final int sleep,
            final int maxAttempts) {
        return retry(operation, sleep, maxAttempts, true);
    }
}
