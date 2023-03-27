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
import java.util.Objects;
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
 * return new Try<String, EmailAddress>()
 *         .failIfNonEmpty(address, () -> "email address should neither be null nor empty")
 *         .failIfNot(StringPredicates.isEmail(address), () -> "Invalid E-mail format")
 *         .elseSucceeds(() -> new EmailAddress(address));
 * </pre>
 *
 * @author Paulo Gandra de Sousa 2023.03.24
 *
 * @see TryIf
 */
public class Try<E, T> {
    private final Supplier<E> error;

    protected Try(final Supplier<E> error) {
        this.error = error;
    }

    public Try() {
        error = null;
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
    public Either<E, T> elseSucceeds(final Supplier<T> success) {
        if (Objects.isNull(error)) {
            return Either.right(success.get());
        } else {
            return Either.left(error.get());
        }
    }

    public Try<E, T> failIfNot(final boolean test, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }

        if (!test) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNotEqual(final long a, final long b, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.areEqual(a, b)) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNotEqual(final Object a, final Object b, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.areEqual(a, b)) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNotMatches(final Pattern regex, final String arg, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.matches(regex, arg)) {
            return new Try<>(error);
        } else {
            return this;
        }
    }

    /**
     * Checks if all object references are not null.
     *
     * @param objects
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Try<E, T> failIfSomeNull(final Supplier<E> error, final Object... objects) {
        boolean allNull = true;
        for (final Object each : objects) {
            if (!Validations.nonNull(each)) {
                allNull = false;
                break;
            }
        }
        if (!allNull) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNonEmpty(final Collection<?> arg, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.isEmpty(arg)) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNonEmpty(final String arg, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.nonEmpty(arg)) {
            return new Try<>(error);
        } else {
            return this;
        }
    }

    /**
     * Checks if a value is positive, that is n > 0.
     *
     * @param arg
     * @param error
     *            the action to execute and return value if the test fails
     */
    public Try<E, T> failIfNotPositive(final long arg, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.isPositive(arg)) {
            return new Try<>(error);
        } else {
            return this;
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
    public Try<E, T> failIfNonNegative(final long arg, final Supplier<E> error) {
        if (!Objects.isNull(error)) {
            return this;
        }
        if (!Validations.isNonNegative(arg)) {
            return new Try<>(error);
        } else {
            return this;
        }
    }
}
