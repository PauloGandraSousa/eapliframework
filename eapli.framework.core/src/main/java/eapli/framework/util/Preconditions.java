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
package eapli.framework.util;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

import eapli.framework.actions.Actions;
import eapli.framework.util.predicates.NumberPredicates;
import eapli.framework.util.predicates.StringPredicates;

/**
 * utility class for validating preconditions, usually on function parameters.
 * if the precondition is not met, an IllegalArgumentException is thrown
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Preconditions implements Utilitarian {
    private Preconditions() {
    }

    public static void ensure(final boolean b) {
        ensure(() -> b);
    }

    public static void ensure(final BooleanSupplier test) {
        ensure(test, "");
    }

    public static void ensure(final BooleanSupplier test, final String msg) {
        Actions.doIfNot(Actions.throwArgument(msg), test);
    }

    public static void nonNull(final Object arg) {
        nonNull(arg, "");
    }

    public static void nonNull(final Object arg, final String msg) {
        Actions.doIfNot(Actions.throwArgument(msg), Objects::nonNull, arg);
    }

    public static void nonNull(final Object... arg) {
        for (final Object each : arg) {
            nonNull(each);
        }
    }

    /**
     * checks if a collection is non null and contains at least an element
     *
     * @param items
     */
    public static void nonEmpty(final Collection<?> items) {
        nonNull(items);
        ensure(!items.isEmpty());
    }

    /**
     * checks if a string is neither null nor empty
     *
     * @param arg
     */
    public static void nonEmpty(final String arg) {
        nonEmpty(arg, "");
    }

    /**
     * checks if a string is neither null nor empty
     *
     * @param arg
     * @param msg
     */
    public static void nonEmpty(final String arg, final String msg) {
        Actions.doIfNot(Actions.throwArgument(msg), x -> !StringPredicates.isNullOrEmpty(x), arg);
    }

    public static void isPositive(final long arg) {
        isPositive(arg, "");
    }

    public static void isPositive(final long arg, final String msg) {
        Actions.doIfNot(Actions.throwArgument(msg), NumberPredicates::isPositive, arg);
    }

    public static void nonNegative(final long arg) {
        nonNegative(arg, "");
    }

    public static void nonNegative(final long arg, final String msg) {
        Actions.doIfNot(Actions.throwArgument(msg), NumberPredicates::isNonNegative, arg);
    }

    public static void nonEmpty(final Set<?> arg, final String msg) {
        ensure(() -> arg != null && !arg.isEmpty(), msg);
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
}
