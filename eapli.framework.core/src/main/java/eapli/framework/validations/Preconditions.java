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
 * utility class for validating preconditions, usually on function parameters.
 * if the precondition is not met, an IllegalArgumentException is thrown
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Preconditions extends Validations implements Utilitarian {
    private Preconditions() {
    }

    public static void nonNull(final Object... arg) {
        nonNull(Actions.THROW_ARGUMENT, arg);
    }

    public static void nonNegative(final long arg) {
        nonNegative(arg, Actions.THROW_ARGUMENT);
    }

    public static void areEqual(final int a, final int b) {
        ensure(() -> a == b);
    }

    public static void areEqual(final Object a, final Object b, final String msg) {
        ensure(() -> a.equals(b), msg);
    }

    public static void matches(final Pattern regex, final String arg, final String msg) {
        ensure(() -> regex.matcher(arg).find(), msg);
    }

    public static void ensure(final boolean b) {
        ensure(() -> b);
    }

    public static void ensure(final boolean b, final String msg) {
        ensure(() -> b, Actions.throwArgument(msg));
    }

    public static void ensure(final BooleanSupplier test) {
        ensure(test, Actions.THROW_ARGUMENT);
    }

    public static void ensure(final BooleanSupplier test, final String msg) {
        ensure(test, Actions.throwArgument(msg));
    }

    public static void isPositive(final long arg) {
        isPositive(arg, Actions.THROW_ARGUMENT);
    }

    public static void isPositive(final long arg, final String msg) {
        isPositive(arg, Actions.throwArgument(msg));
    }

    public static void nonNull(final Object arg) {
        nonNull(arg, Actions.THROW_ARGUMENT);
    }

    public static void nonNull(final Object arg, final String msg) {
        nonNull(arg, Actions.throwArgument(msg));
    }

    /**
     * checks if a collection is non null and contains at least an element
     *
     * @param items
     */
    public static void nonEmpty(final Collection<?> items) {
        nonEmpty(items, Actions.THROW_ARGUMENT);
    }

    /**
     * checks if a collection is non null and contains at least an element
     *
     * @param items
     */
    public static void nonEmpty(final Collection<?> items, final String msg) {
        nonEmpty(items, Actions.throwArgument(msg));
    }

    /**
     * checks if a string is neither null nor empty
     *
     * @param arg
     */
    public static void nonEmpty(final String arg) {
        nonEmpty(arg, Actions.THROW_ARGUMENT);
    }

    /**
     * checks if a string is neither null nor empty
     *
     * @param arg
     * @param msg
     */
    public static void nonEmpty(final String arg, final String msg) {
        nonEmpty(arg, Actions.throwArgument(msg));
    }

    public static void nonNegative(final long arg, final String msg) {
        nonNegative(arg, Actions.throwArgument(msg));
    }
}
