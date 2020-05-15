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
package eapli.framework.validations;

import eapli.framework.util.Utility;

/**
 * Predicates handling Comparables. Predicates are functions that test a condition and return a
 * boolean value.
 *
 * @author Paulo Gandra Sousa
 */
@Utility
public final class ComparablePredicates {

    private ComparablePredicates() {
        // to make sure this is an utility class
    }

    /**
     * Checks if a value is between (inclusive) a range.
     *
     * @param e
     * @param begin
     * @param end
     * @return true if the value {@code e} is between (inclusive) the range {@code [begin, end]}
     */
    public static boolean isBetween(final long e, final long begin, final long end) {
        return e >= begin && e <= end;
    }

    /**
     * Checks if a value is between (inclusive) a range.
     *
     * @param e
     * @param begin
     * @param end
     * @return true if the value {@code e} is between (inclusive) the range {@code [begin, end]}
     */
    public static <T extends Comparable<T>> boolean isBetween(final T e, final T begin, final T end) {
        return e.compareTo(begin) >= 0 && e.compareTo(end) <= 0;
    }

    public static <T extends Comparable<T>> boolean isGreaterThan(final T a, final T b) {
        return a.compareTo(b) > 0;
    }

    public static <T extends Comparable<T>> boolean isAtLeast(final T a, final T b) {
        return a.compareTo(b) >= 0;
    }

    public static <T extends Comparable<T>> boolean isLessThan(final T a, final T b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<T>> boolean isAtMost(final T a, final T b) {
        return a.compareTo(b) <= 0;
    }
}
