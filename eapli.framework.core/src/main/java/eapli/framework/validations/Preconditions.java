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
package eapli.framework.validations;

import java.util.Collection;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

import eapli.framework.actions.Actions;
import eapli.framework.util.Utilitarian;

/**
 * Utility class for validating preconditions, usually on function parameters.
 * if the precondition is not met, an IllegalArgumentException is thrown
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Preconditions extends Validations implements Utilitarian {
    private Preconditions() {
        // ensure utility
    }

    /**
     * ensures all object references are not null
     *
     * @param arg
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonNull(final Object... arg) {
        nonNull(Actions.THROW_ARGUMENT, arg);
    }

    /**
     * ensures a value is non negative
     *
     * @param arg
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonNegative(final long arg) {
        nonNegative(arg, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures a value is non negative
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonNegative(final long arg, final String msg) {
        nonNegative(arg, Actions.throwArgument(msg));
    }

    /**
     * ensures two values are equal
     *
     * @param a
     * @param b
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void areEqual(final long a, final long b) {
        areEqual(a, b, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures two objects are equal
     *
     * @param a
     * @param b
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void areEqual(final Object a, final Object b, final String msg) {
        areEqual(a, b, Actions.throwArgument(msg));
    }

    /**
     * ensures a string matches a regular expression
     *
     * @param regex
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void matches(final Pattern regex, final String arg, final String msg) {
        matches(regex, arg, Actions.throwArgument(msg));
    }

    /**
     * asserts true
     *
     * @param b
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final boolean b) {
        ensure(() -> b);
    }

    /**
     * asserts true
     *
     * @param b
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final boolean b, final String msg) {
        ensure(() -> b, Actions.throwArgument(msg));
    }

    /**
     * asserts true
     *
     * @param test
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final BooleanSupplier test) {
        ensure(test, Actions.THROW_ARGUMENT);
    }

    /**
     * asserts true
     *
     * @param test
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void ensure(final BooleanSupplier test, final String msg) {
        ensure(test, Actions.throwArgument(msg));
    }

    /**
     * ensures a value is positive
     *
     * @param arg
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void isPositive(final long arg) {
        isPositive(arg, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures a value is positive
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void isPositive(final long arg, final String msg) {
        isPositive(arg, Actions.throwArgument(msg));
    }

    /**
     * ensures an object reference is not null
     *
     * @param arg
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonNull(final Object arg) {
        nonNull(arg, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures an object reference is not null
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonNull(final Object arg, final String msg) {
        nonNull(arg, Actions.throwArgument(msg));
    }

    /**
     * ensures a collection is non null and contains at least an element
     *
     * @param items
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final Collection<?> items) {
        nonEmpty(items, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures a collection is non null and contains at least an element
     *
     * @param items
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final Collection<?> items, final String msg) {
        nonEmpty(items, Actions.throwArgument(msg));
    }

    /**
     * ensures a string is neither null nor empty
     *
     * @param arg
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final String arg) {
        nonEmpty(arg, Actions.THROW_ARGUMENT);
    }

    /**
     * ensures a string is neither null nor empty
     *
     * @param arg
     * @param msg
     *            the message text to include in the throw exception
     * @throws IllegalArgumentException
     *             if the assertion is unfulfilled
     */
    public static void nonEmpty(final String arg, final String msg) {
        nonEmpty(arg, Actions.throwArgument(msg));
    }
}
