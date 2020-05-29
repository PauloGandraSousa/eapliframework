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
package eapli.framework.util;

import java.util.function.Predicate;

/**
 * Utility class for Array predicates.
 *
 * @author Paulo Gandra de Sousa 28/05/2020
 *
 */
@Utility
public final class ArrayPredicates {

    private ArrayPredicates() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Checks whether an element is present in the array or not. This method
     * has O(n) complexity.
     *
     * @param src
     * @param elem
     * @return {@code true} if the element is present in the array
     */
    public static <T> boolean contains(final T[] src, final T elem) {
        return Arrays.indexOf(src, elem) != -1;
    }

    /**
     * Checks if all elements of an array are distinct.
     *
     * @param <T>
     * @param src
     * @return {@code true} if all elements of the array are distinct.
     */
    public static <T> boolean areAllDistinct(final T[] src) {
        for (final T each : src) {
            if (Arrays.countOf(src, each) > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all elements of the array satisfy a certain condition.
     *
     * @param <T>
     * @param src
     * @param condition
     * @return {@code true} if all elements of the array satisfy a certain condition
     */
    public static <T> boolean all(final T[] src, final Predicate<T> condition) {
        for (final T each : src) {
            if (!condition.test(each)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any of the elements of the array satisfies a certain condition.
     *
     * @param <T>
     * @param src
     * @param condition
     * @return {@code true} if at least one of the elements of the array satisfies the condition
     */
    public static <T> boolean any(final T[] src, final Predicate<T> condition) {
        for (final T each : src) {
            if (condition.test(each)) {
                return true;
            }
        }
        return false;
    }
}
