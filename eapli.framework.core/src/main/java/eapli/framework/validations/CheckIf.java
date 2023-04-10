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
package eapli.framework.validations;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import eapli.framework.functional.Either;
import eapli.framework.util.Utility;

/**
 * Utility class for trying to perform an action based on a validation and either returning a
 * success or error. The methods in this class make use of
 * {@link eapli.framework.functional.Either<E,T> Either<E,T>}.
 *
 * @author Paulo Gandra de Sousa 2023.03.24
 *
 * @see Check
 */
@Utility
public class CheckIf {
    protected CheckIf() {
        // ensure utility
    }

    /**
     * Asserts the "trueness" of a function's result returning either a success or error result.
     *
     * @param test
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> succeeds(final boolean test, final Supplier<T> success,
            final Supplier<E> error) {
        if (test) {
            return Either.right(success.get());
        } else {
            return Either.left(error.get());
        }
    }

    /**
     * Checks if two values are equal.
     *
     * @param a
     * @param b
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> areEqual(final long a, final long b, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.areEqual(a, b), success, error);
    }

    /**
     * Checks if two objects are equal.
     *
     * @param a
     * @param b
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> areEqual(final Object a, final Object b, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.areEqual(a, b), success, error);
    }

    /**
     * Checks if a string matches a regular expression.
     *
     * @param regex
     * @param arg
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> matches(final Pattern regex, final String arg,
            final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.matches(regex, arg), success, error);
    }

    /**
     * Checks if all object references are not null.
     *
     * @param objects
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> noneNull(final Supplier<T> success,
            final Supplier<E> error, final Object... objects) {
        boolean allNull = true;
        for (final Object each : objects) {
            if (!Validations.nonNull(each)) {
                allNull = false;
                break;
            }
        }
        if (allNull) {
            return Either.right(success.get());
        } else {
            return Either.left(error.get());
        }
    }

    /**
     * Checks if a Collection is non null and contains at least an element.
     *
     * @param arg
     *            the collection to test
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> nonEmpty(final Collection<?> arg, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.isEmpty(arg), success, error);
    }

    /**
     * Checks if a string is neither null nor empty nor just white space.
     *
     * @param arg
     *            the string to test
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> nonEmpty(final String arg, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.nonEmpty(arg), success, error);
    }

    /**
     * Checks if a value is positive, that is n > 0.
     *
     * @param arg
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> isPositive(final long arg, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.isPositive(arg), success, error);
    }

    /**
     * Checks if a value is non negative, that is n >= 0.
     *
     * @param arg
     * @param success
     *            the action to execute and return value if the test succeeds
     * @param error
     *            the action to execute and return value if the test fails
     */
    public static <E, T> Either<E, T> nonNegative(final long arg, final Supplier<T> success,
            final Supplier<E> error) {
        return succeeds(Validations.isNonNegative(arg), success, error);
    }
}
