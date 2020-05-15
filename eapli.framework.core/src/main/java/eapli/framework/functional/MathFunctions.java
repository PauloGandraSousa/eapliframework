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
package eapli.framework.functional;

import java.util.stream.IntStream;

import eapli.framework.util.Utility;
import eapli.framework.validations.Preconditions;

/**
 * Just a playground to use the Java 8 Streams and Lambda features.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
public final class MathFunctions {

    private MathFunctions() {
        // ensure utility
    }

    /**
     * Counts the number of primes up to a certain number.
     *
     * <p>
     * Scraped from <a href=
     * "http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf">Using
     * Java Lambdas</a>
     *
     * @param upTo
     * @return the number of primes up to a certain number.
     */
    public static long countPrimes(final int upTo) {
        return IntStream.range(2, upTo).filter(MathFunctions::isPrime).count();
    }

    /**
     * Checks whether a number is prime or not using a functional descriptive style. This might not be the most
     * optimized way to calculate a prime number but it is the descriptive way (the mathematical definition) of what a
     * prime number is.
     *
     * <p>
     * Scraped from <a href=
     * "http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf">Using
     * Java Lambdas</a>
     *
     * <p>
     * See also <a href=
     * "https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math4/primes/Primes.html">org.apache.commons.math4.primes.Primes</a>
     *
     * @param number
     * @return whether a number is prime or not.
     */
    public static boolean isPrime(final int number) {
        Preconditions.isPositive(number);

        return IntStream.range(2, number).allMatch(x -> (number % x) != 0);
    }
}
