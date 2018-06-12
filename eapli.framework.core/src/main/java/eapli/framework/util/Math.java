/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Paulo Gandra Sousa
 */
public final class Math implements Utilitarian {

    private Math() {
        // to make sure this is an utility class
    }

    /**
     * Permite efectuar a conversão de um valor para outra escala.
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
    @SuppressWarnings("squid:S1488")
    public static float simpleLinearConversion(final float oldMin, final float oldMax, final float newMin,
            final float newMax, final float oldValue) {
        final float result = ((oldValue - oldMin) / (oldMax - oldMin)) * (newMax - newMin) + newMin;
        return result;
    }

    /**
     * Permite efectuar a conversão de um valor para outra escala.
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
    @SuppressWarnings("squid:S1488")
    public static BigDecimal simpleLinearConversion(final BigDecimal oldMin, final BigDecimal oldMax,
            final BigDecimal newMin, final BigDecimal newMax, final BigDecimal oldValue) {
        final BigDecimal a = oldValue.subtract(oldMin);
        final BigDecimal b = oldMax.subtract(oldMin);
        final BigDecimal c = a.divide(b, 1, RoundingMode.HALF_UP);
        final BigDecimal d = newMax.subtract(newMin);
        final BigDecimal result = c.multiply(d).add(newMin);
        return result;
    }

    /**
     * calculates base to the power of exp as a long value instead of double from
     * the standard Java Library
     *
     * @param base
     * @param exp
     * @return
     */
    public static long pow(final int base, final int exp) {
        long s = 1;
        for (int i = 0; i < exp; i++) {
            s *= base;
        }
        return s;
    }
}
