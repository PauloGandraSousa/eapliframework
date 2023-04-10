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
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import eapli.framework.functional.Either;

/**
 * Builder class for chainable construction of {@link eapli.framework.functional.Either
 * Either} monads.
 * <p>
 * For example:
 * <p>
 *
 * <pre>
 * return new Check<String, EmailAddress>()
 *         .failIf()
 *         .nonEmpty(address, () -> "email address should neither be null nor empty")
 *         .not(StringPredicates.isEmail(address), () -> "Invalid E-mail format")
 *         .elseSucceeds(() -> new EmailAddress(address));
 * </pre>
 *
 * @author Paulo Gandra de Sousa 2023.03.24
 *
 * @see CheckIf
 */
public class Check<E, T> {

    //
    // more fluent interface?
    //
    // return new Try<String, EmailAddress>(() -> new EmailAddress(address))
    // .ensuring()
    // .NonNull(address, () -> "address must not be null")
    // .NonEmpty(address, () -> "email address should neither be null nor empty")
    // .and(StringPredicates.isEmail(address), () -> "Invalid E-mail format")
    //

    /**
     * Special case
     *
     * @author Paulo Gandra de Sousa 2023.03.28
     *
     * @param <E>
     * @param <T>
     */
    private static class FailedTry<E, T> extends Check<E, T> {
        private final Supplier<E> error;

        FailedTry(final Supplier<E> error) {
            this.error = error;
        }

        @Override
        public Either<E, T> elseSucceed(final Supplier<T> success) {
            return Either.left(this.error.get());
        }

        @Override
        public Check<E, T> not(final boolean test, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> notEqual(final long a, final long b, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> notEqual(final Object a, final Object b, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> notMatches(final Pattern regex, final String arg, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> someNull(final Supplier<E> error, final Object... objects) {
            return this;
        }

        @Override
        public Check<E, T> isEmpty(final Collection<?> arg, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> isEmpty(final String arg, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> notPositive(final long arg, final Supplier<E> error) {
            return this;
        }

        @Override
        public Check<E, T> isNegative(final long arg, final Supplier<E> error) {
            return this;
        }
    }

    /**
     * Special case
     *
     * @author Paulo Gandra de Sousa 2023.03.28
     *
     * @param <E>
     * @param <T>
     */
    private static class FollowupTry<E, T> extends Check<E, T> {
        @Override
        public Either<E, T> elseSucceed(final Supplier<T> success) {
            return Either.right(success.get());
        }
    }

    /**
     * "Connector" for a fluent interface.
     *
     * @return
     */
    public Check<E, T> failIf() {
        return this;
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
    public Either<E, T> elseSucceed(final Supplier<T> success) {
        throw new IllegalStateException("At least one fail condition must be specified");
    }

    /**
     * @param test
     * @param error
     *
     * @return
     */
    public Check<E, T> not(final boolean test, final Supplier<E> error) {
        if (!test) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * @param test
     * @param error
     *
     * @return
     */
    public Check<E, T> not(final BooleanSupplier test, final Supplier<E> error) {
        if (!test.getAsBoolean()) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
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
    public Check<E, T> notEqual(final long a, final long b, final Supplier<E> error) {
        if (!Validations.areEqual(a, b)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if two objects are equal.
     *
     * @param a
     * @param b
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> notEqual(final Object a, final Object b, final Supplier<E> error) {
        if (!Validations.areEqual(a, b)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if a string matches a regular expression.
     *
     * @param regex
     * @param arg
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> notMatches(final Pattern regex, final String arg, final Supplier<E> error) {
        if (!Validations.matches(regex, arg)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if all object references are not null.
     *
     * @param objects
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> someNull(final Supplier<E> error, final Object... objects) {
        boolean allNull = true;
        for (final Object each : objects) {
            if (!Validations.nonNull(each)) {
                allNull = false;
                break;
            }
        }
        if (!allNull) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if a Collection is non null and contains at least an element.
     *
     * @param arg
     *            the collection to test
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> isEmpty(final Collection<?> arg, final Supplier<E> error) {
        if (!Validations.isEmpty(arg)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if a string is neither null nor empty nor just white space.
     *
     * @param arg
     *            the string to test
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> isEmpty(final String arg, final Supplier<E> error) {
        if (!Validations.nonEmpty(arg)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if a value is positive, that is n > 0.
     *
     * @param arg
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> notPositive(final long arg, final Supplier<E> error) {
        if (!Validations.isPositive(arg)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }

    /**
     * Checks if a value is non negative, that is n >= 0.
     *
     * @param arg
     *            s
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Check<E, T> isNegative(final long arg, final Supplier<E> error) {
        if (!Validations.isNonNegative(arg)) {
            return new FailedTry<>(error);
        } else {
            return new FollowupTry<>();
        }
    }
}
