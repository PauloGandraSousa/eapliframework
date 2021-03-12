/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
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
package eapli.framework.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa
 *
 */
class MathIsPrimeTest {

    /**
     * Test method for
     * {@link eapli.framework.functional.MathFunctions#isPrime(int)}.
     */
    @Test
    void ensure1IsPrime() {
        assertTrue(MathFunctions.isPrime(1));
    }

    @Test
    void ensure2IsPrime() {
        assertTrue(MathFunctions.isPrime(2));
    }

    @Test
    void ensure3IsPrime() {
        assertTrue(MathFunctions.isPrime(3));
    }

    @Test
    void ensure4IsNotPrime() {
        assertFalse(MathFunctions.isPrime(4));
    }

    @Test
    void ensure5IsPrime() {
        assertTrue(MathFunctions.isPrime(5));
    }

    @Test
    void ensure7IsPrime() {
        assertTrue(MathFunctions.isPrime(7));
    }

    @Test
    void ensure9IsNotPrime() {
        assertFalse(MathFunctions.isPrime(9));
    }

    @Test
    void ensure15IsNotPrime() {
        assertFalse(MathFunctions.isPrime(15));
    }

    @Test
    void ensure21IsNotPrime() {
        assertFalse(MathFunctions.isPrime(21));
    }

    /**
     * http://www.primos.mat.br/indexen.html
     */
    private final Integer[] first168Primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163,
            167, 173, 179, 181,
            191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277,
            281, 283, 293, 307,
            311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
            419, 421, 431, 433,
            439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547,
            557, 563, 569, 571,
            577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673,
            677, 683, 691, 701,
            709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823,
            827, 829, 839, 853,
            857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971,
            977, 983, 991, 997 };

    @Test
    void ensureFirst168Primes() {

        assertTrue(Arrays.stream(first168Primes).allMatch(MathFunctions::isPrime));
        /*
         * the previous code is similar to
         *
         * for (final int e : first168Primes) { assertTrue(instance.isPrime(e));
         * }
         */
    }

    @Test
    void ensureNoPrimeUpTo998() {
        final IntStream notPrimes = IntStream.range(2, 998)
                .filter(i -> Arrays.binarySearch(first168Primes, Integer.valueOf(i)) < 0);
        assertTrue(notPrimes.noneMatch(MathFunctions::isPrime));
    }

    @Test
    void ensureThereAre168PrimesUpTo998() {
        assertEquals(first168Primes.length, MathFunctions.countPrimes(998));
    }
}
