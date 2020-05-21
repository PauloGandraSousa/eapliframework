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
package eapli.framework.math.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import eapli.framework.util.Utility;

/**
 * Utility mathematical functions.
 *
 * @author Paulo Gandra Sousa
 * @author NMB
 */
@Utility
public final class Math {

    private Math() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Permite efectuar a conversão de um valor para outra escala.
     * <p>
     * Author NMB
     *
     * @param oldMin
     *            - exemplo 0
     * @param oldMax
     *            - exemplo 100
     * @param newMin
     *            - exemplo 0
     * @param newMax
     *            - exemplo 10
     * @param oldValue
     *            - exemplo 50
     * @return retorna o novo valor aplicando uma conversão linear - exemplo 5
     */
    public static float simpleLinearConversion(final float oldMin, final float oldMax,
            final float newMin,
            final float newMax, final float oldValue) {
        return ((oldValue - oldMin) / (oldMax - oldMin)) * (newMax - newMin) + newMin;
    }

    /**
     * Permite efectuar a conversão de um valor para outra escala.
     * <p>
     * Author NMB
     * 
     * @param oldMin
     *            - exemplo 0
     * @param oldMax
     *            - exemplo 100
     * @param newMin
     *            - exemplo 0
     * @param newMax
     *            - exemplo 10
     * @param oldValue
     *            - exemplo 50
     * @return retorna o novo valor aplicando uma conversão linear - exemplo 5
     */
    public static BigDecimal simpleLinearConversion(final BigDecimal oldMin,
            final BigDecimal oldMax,
            final BigDecimal newMin, final BigDecimal newMax, final BigDecimal oldValue) {
        final BigDecimal a = oldValue.subtract(oldMin);
        final BigDecimal b = oldMax.subtract(oldMin);
        final BigDecimal c = a.divide(b, 1, RoundingMode.HALF_UP);
        final BigDecimal d = newMax.subtract(newMin);
        return c.multiply(d).add(newMin);
    }

    /**
     * Calculates {@code base} to the power of {@code exp} as a long value
     * instead of double from the standard Java Library.
     *
     * @param base
     * @param exp
     * @return base to the power of exp
     */
    public static long pow(final int base, final int exp) {
        long s = 1;
        for (int i = 0; i < exp; i++) {
            s *= base;
        }
        return s;
    }
}
