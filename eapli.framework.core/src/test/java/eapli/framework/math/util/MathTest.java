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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa 28/05/2020
 *
 */
class MathTest {

    @Test
    void ensureFibonacci0() {
        final BigInteger expected = BigInteger.valueOf(0);
        assertEquals(expected, Math.fibonacci(0));
    }

    @Test
    void ensureFibonacci1() {
        final BigInteger expected = BigInteger.valueOf(1);
        assertEquals(expected, Math.fibonacci(1));
    }

    @Test
    void ensureFibonacci2() {
        final BigInteger expected = BigInteger.valueOf(1);
        assertEquals(expected, Math.fibonacci(2));
    }

    @Test
    void ensureFibonacci3() {
        final BigInteger expected = BigInteger.valueOf(2);
        assertEquals(expected, Math.fibonacci(3));
    }

    @Test
    void ensureFibonacci9() {
        final BigInteger expected = BigInteger.valueOf(34);
        assertEquals(expected, Math.fibonacci(9));
    }

    @Test
    void ensureFibonacci10() {
        final BigInteger expected = BigInteger.valueOf(55);
        assertEquals(expected, Math.fibonacci(10));
    }

    @Test
    void ensureFibonacciNegative() {
        assertThrows(IllegalArgumentException.class, () -> Math.fibonacci(-3));
    }

    @Test
    void ensureFirst10Fibonacci() {
        final List<Integer> auxExpected = Arrays.asList(0, 1, 1, 2, 3, 5, 8, 13, 21, 34);
        final List<BigInteger> expected = auxExpected.stream().map(i -> BigInteger.valueOf(i))
                .collect(Collectors.toList());

        // note that the first 10 elements means we want to determine fac(0) .. fac(9)
        final List<BigInteger> result = Math.fibonacciSequence(9).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    void ensureFirstFibonacci() {
        final List<Integer> auxExpected = Arrays.asList(0);
        final List<BigInteger> expected = auxExpected.stream().map(i -> BigInteger.valueOf(i))
                .collect(Collectors.toList());

        final List<BigInteger> result = Math.fibonacciSequence(0).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    void ensureFirst2Fibonacci() {
        final List<Integer> auxExpected = Arrays.asList(0, 1);
        final List<BigInteger> expected = auxExpected.stream().map(i -> BigInteger.valueOf(i))
                .collect(Collectors.toList());

        // note that the first 2 elements means we want to determine fac(0) .. fac(1)
        final List<BigInteger> result = Math.fibonacciSequence(1).collect(Collectors.toList());

        assertEquals(expected, result);
    }

}
