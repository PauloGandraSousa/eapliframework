/*
 * Copyright (c) 2013-2021 the original author or authors.
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
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.stream.Stream;

import eapli.framework.util.Utility;
import eapli.framework.validations.Preconditions;

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

    /**
     * Calculates the <i>n</i>th element of the
     * <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci sequence</a>.
     *
     * <p>
     * Scrapped from <a href="https://mkyong.com/java/java-fibonacci-examples/">MKyong</a>
     *
     * @param n
     *            the index of the desired element. The classic non-extended Fibonacci sequence
     *            starts at 0.
     * @return the <i>n</i>th element of the Fibonacci sequence
     */
    public static BigInteger fibonacci(final int n) {
        return fibonacciSequence(n).reduce((a, b) -> b).orElse(BigInteger.ZERO);
    }

    /**
     * Calculates the <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci
     * sequence</a> up to a certain index.
     * <p>
     * Scrapped from <a href="https://mkyong.com/java/java-fibonacci-examples/">MKyong</a>
     * <p>
     * If you need to get a {@code List} instead of a {@code Stream} just use one of the standard
     * collectors, e.g.:
     *
     * <pre>
     * <code>
     * List<BigInteger> l = Math.fibionacciSerires(10).collect(Collectors.toList());
     * </code>
     * </pre>
     *
     * @param upperIndex
     *            the upper limit of the sequence. The classic non-extended Fibonacci sequence
     *            starts at 1.
     * @return a stream with the Fibonacci sequence
     */
    public static Stream<BigInteger> fibonacciSequence(final int upperIndex) {
        Preconditions.nonNegative(upperIndex, "The Fibonacci (non-extended) sequence only works for natural numbers");

        return Stream
                .iterate(new BigInteger[] { BigInteger.ZERO, BigInteger.ONE },
                        t -> new BigInteger[] { t[1], t[0].add(t[1]) })
                .limit(upperIndex + 1L)
                .map(i -> i[0]);
    }

    /**
     * Fifty/fifty chance.
     *
     * @return {@code true} or {@code false} in what tends to be a 50/50 distribution
     */
    public static boolean heads() {
        return NumberGenerator.anInt() % 2 == 0;
    }

}
