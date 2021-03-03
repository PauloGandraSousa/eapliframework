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
package eapli.framework.validations;

import java.util.Collection;
import java.util.regex.Pattern;

import eapli.framework.actions.Actions;
import eapli.framework.util.Utility;

/**
 * Utility class for expressing and ensuring class/method's invariants. If the
 * invariant does not hold, an {@link IllegalStateException} is thrown.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
public final class Invariants {
    private Invariants() {
        // ensure utility
    }

    /**
     * Ensures two values are equal.
     *
     * @param a
     * @param b
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void areEqual(final long a, final long b) {
        Validations.ensureAreEqual(a, b, Actions.THROW_STATE);
    }

    /**
     * Ensures two objects are equal.
     *
     * @param a
     * @param b
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void areEqual(final Object a, final Object b, final String msg) {
        Validations.ensureAreEqual(a, b, Actions.throwState(msg));
    }

    /**
     * Ensures a string matches a regular expression.
     *
     * @param regex
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void matches(final Pattern regex, final String arg, final String msg) {
        Validations.ensureMatches(regex, arg, Actions.throwState(msg));
    }

    /**
     * Asserts the trueness.
     *
     * @param b
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final boolean b) {
        Validations.ensure(b, Actions.THROW_STATE);
    }

    /**
     * Asserts the trueness.
     *
     * @param b
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final boolean b, final String msg) {
        Validations.ensure(b, Actions.throwState(msg));
    }

    /**
     * Ensures an object reference is not null.
     *
     * @param arg
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonNull(final Object arg) {
        Validations.ensureNoneNull(Actions.THROW_STATE, arg);
    }

    /**
     * Ensures an object reference is not null.
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonNull(final Object arg, final String msg) {
        Validations.ensureNoneNull(Actions.throwState(msg), arg);
    }

    /**
     * Ensures all object references are not null.
     *
     * @param arg
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void noneNull(final Object... arg) {
        Validations.ensureNoneNull(Actions.THROW_STATE, arg);
    }

    /**
     * Checks if a collection is non null and contains at least an element.
     *
     * @param items
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final Collection<?> items) {
        Validations.ensureNonEmpty(items, Actions.THROW_STATE);
    }

    /**
     * Ensures a collection is non null and contains at least an element.
     *
     * @param items
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final Collection<?> items, final String msg) {
        Validations.ensureNonEmpty(items, Actions.throwState(msg));
    }

    /**
     * asserts that a string is neither null nor empty
     *
     * @param arg
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final String arg) {
        Validations.ensureNonEmpty(arg, Actions.THROW_STATE);
    }

    /**
     * asserts that a string is neither null nor empty
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final String arg, final String msg) {
        Validations.ensureNonEmpty(arg, Actions.throwState(msg));
    }

    /**
     * ensures a value is positive
     *
     * @param arg
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void isPositive(final long arg) {
        Validations.ensureIsPositive(arg, Actions.THROW_STATE);
    }

    /**
     * ensures a value is positive
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void isPositive(final long arg, final String msg) {
        Validations.ensureIsPositive(arg, Actions.throwState(msg));
    }

    /**
     * ensures a value is non negative
     *
     * @param arg
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonNegative(final long arg) {
        Validations.ensureNonNegative(arg, Actions.THROW_STATE);
    }

    /**
     * ensures a value is non negative
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalStateException
     *             if the assertion is unfulfilled
     */
    public static void nonNegative(final long arg, final String msg) {
        Validations.ensureNonNegative(arg, Actions.throwState(msg));
    }
}
