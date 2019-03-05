/**
 *
 */
package eapli.framework.util;

import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * utility class
 *
 * @author sou03408
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
    public static double[] zip(final double[] a, final double[] b, final BinaryOperator<Double> op) {
        Preconditions.nonNull(a, b, op);
        final int size = java.lang.Math.min(a.length, b.length);

        final double[] res = new double[size];

        for (int i = 0; i < size; i++) {
            res[i] = op.apply(a[i], b[i]);
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
    public static double[] copyMapped(final double[] src, final DoubleUnaryOperator map) {
        final double[] dest = new double[src.length];
        for (int i = 0; i < src.length; i++) {
            dest[i] = map.applyAsDouble(src[i]);
        }
        return dest;
    }
}
