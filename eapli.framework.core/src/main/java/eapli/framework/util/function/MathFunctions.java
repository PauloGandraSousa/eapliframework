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
package eapli.framework.util.function;

import java.util.stream.IntStream;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * just a playground to use the Java 8 Streams and Lambda features
 *
 * @author sou03408
 *
 */
public final class MathFunctions implements Utilitarian {

    private MathFunctions() {
        // ensure utility
    }

    /**
     * counts the number of primes up to a certain number
     *
     * using Java Lambdas
     * http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf
     *
     * @param upTo
     * @return
     */
    public static long countPrimes(final int upTo) {
        return IntStream.range(2, upTo).filter(MathFunctions::isPrime).count();
    }

    /**
     * checks whether a number is prime or not
     *
     * using Java Lambdas
     * http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf
     *
     * see also {@link org.apache.commons.math4.primes.Primes}
     *
     * @param number
     * @return
     */
    public static boolean isPrime(final int number) {
        Preconditions.isPositive(number);

        return IntStream.range(2, number).allMatch(x -> (number % x) != 0);
    }
}
