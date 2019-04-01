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

/**
 * utility class
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
     * creates a new double array picking up the elements of the source array and
     * applying a mapping function to each element
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
     * applies a transformation to each element of an array
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
}
