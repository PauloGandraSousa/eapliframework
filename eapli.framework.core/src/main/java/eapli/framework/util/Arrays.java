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

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

import eapli.framework.validations.Preconditions;

/**
 * Utility class to handle Arrays
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Arrays implements Utilitarian {

    private Arrays() {
        // ensure utility
    }

    /**
     * zips two double arrays in to one
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public static double[] zip(final double[] a, final double[] b, final DoubleBinaryOperator op) {
        Preconditions.nonNull(a, b, op);
        final int size = java.lang.Math.min(a.length, b.length);

        final double[] res = new double[size];

        for (int i = 0; i < size; i++) {
            res[i] = op.applyAsDouble(a[i], b[i]);
        }
        return res;
    }

    /**
     * creates a new double array picking up the elements of the source array
     * and applying a mapping function to each element
     *
     * @param src
     * @param map
     * @return
     */
    public static double[] map(final double[] src, final DoubleUnaryOperator map) {
        final double[] dest = new double[src.length];
        for (int i = 0; i < src.length; i++) {
            dest[i] = map.applyAsDouble(src[i]);
        }
        return dest;
    }

    /**
     * creates a new array picking up the elements of the source array and
     * applying a mapping function to each element
     *
     * @param <T>
     *            the type of the source elements
     * @param <U>
     *            the type of the mapped elements
     * @param src
     *            the source elements
     * @param creator
     *            a constructor to create the destination array with the
     *            expected number of positions
     * @param map
     *            the mapping function from source to destination, T -> U
     * @return
     */
    public static <T, U> U[] map(final T[] src, final IntFunction<U[]> creator, final Function<T, U> map) {
        final U[] dest = creator.apply(src.length);
        for (int i = 0; i < src.length; i++) {
            dest[i] = map.apply(src[i]);
        }
        return dest;
    }

    /**
     * applies a transformation to each element of an array, mutating the
     * original array
     *
     * @param src
     * @param map
     * @return
     */
    public static void transform(final double[] dest, final DoubleUnaryOperator map) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = map.applyAsDouble(dest[i]);
        }
    }

    /**
     * applies a transformation to each element of an array, mutating the
     * original array
     *
     * @param src
     * @param map
     * @return
     */
    public static <T> void transform(final T[] dest, final UnaryOperator<T> map) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = map.apply(dest[i]);
        }
    }

    /**
     * checks whether an element is present in the array or not. this methods
     * has O(n) complexity
     *
     * @param src
     * @param elem
     * @return
     */
    public static <T> boolean contains(final T[] src, final T elem) {
        return indexOf(src, elem) != -1;
    }

    /**
     * returns the index of an element is present in the array or -1 otherwise.
     * this methods has O(n) complexity
     *
     * @param src
     * @param elem
     * @return
     */
    public static <T> int indexOf(final T[] src, final T elem) {
        for (int i = 0; i < src.length; i++) {
            if (src[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }
}
