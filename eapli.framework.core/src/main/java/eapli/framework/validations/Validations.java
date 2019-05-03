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
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

import eapli.framework.actions.Action;
import eapli.framework.actions.Actions;
import eapli.framework.util.Utilitarian;

/**
 * Utility class for validations
 *
 * @author Paulo Gandra de Sousa
 *
 */
/* package */ class Validations implements Utilitarian {
    protected Validations() {
        // ensure utility
    }

    public static void areEqual(final long a, final long b, final Action consequence) {
        ensure(() -> a == b, consequence);
    }

    public static void areEqual(final Object a, final Object b, final Action consequence) {
        ensure(() -> Objects.equals(a, b), consequence);
    }

    public static void matches(final Pattern regex, final String arg, final Action consequence) {
        ensure(() -> regex.matcher(arg).find(), consequence);
    }

    /**
     * asserts true
     *
     * @param b
     */
    public static void ensure(final BooleanSupplier test, final Action consequence) {
        Actions.doIfNot(consequence, test);
    }

    /**
     * checks if all object references are not null
     *
     * @param consequence
     * @param objects
     */
    public static void nonNull(final Action consequence, final Object... objects) {
        for (final Object each : objects) {
            Actions.doIfNot(consequence, Objects::nonNull, each);
        }
    }

    /**
     * checks if a Collection is non null and contains at least an element
     *
     * @param items
     */
    public static void nonEmpty(final Collection<?> arg, final Action consequence) {
        ensure(() -> arg != null && !arg.isEmpty(), consequence);
    }

    /**
     * checks if a string is neither null nor empty nor just white space
     *
     * @param arg
     */
    public static void nonEmpty(final String arg, final Action consequence) {
        Actions.doIfNot(consequence, x -> !StringPredicates.isNullOrWhiteSpace(x), arg);
    }

    public static void isPositive(final long arg, final Action consequence) {
        Actions.doIfNot(consequence, NumberPredicates::isPositive, arg);
    }

    public static void nonNegative(final long arg, final Action consequence) {
        Actions.doIfNot(consequence, NumberPredicates::isNonNegative, arg);
    }
}
