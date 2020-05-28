/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra de Sousa 28/05/2020
 *
 */
public class ArraysTest {

    private final String[] originalStrings = { "aa", "bb", "cc", "dd" };
    private final String[] testStrings = new String[originalStrings.length];
    private final double[] testDoubles = { 1, 2, 3, 4, 5, 6 };

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < originalStrings.length; i++) {
            testStrings[i] = originalStrings[i];
        }
    }

    @Test
    public void ensureIndexOfAAIs0() {
        assertEquals(0, Arrays.indexOf(testStrings, "aa"));
    }

    @Test
    public void ensureIndexOfXXIsMinus1() {
        assertEquals(-1, Arrays.indexOf(testStrings, "xx"));
    }

    @Test
    public void ensureMapStringToInt() {
        final Integer[] subject = Arrays.map(testStrings, Integer[]::new, x -> x.length());

        assertEquals(testStrings.length, subject.length);
        for (int i = 0; i < subject.length; i++) {
            assertEquals(testStrings[i].length(), subject[i].intValue());
        }
    }

    @Test
    public void ensureMapStringToIntKeepsOriginal() {
        Arrays.map(testStrings, Integer[]::new, x -> x.length());

        assertArrayEquals(originalStrings, testStrings);
    }

    @Test
    public void ensureMapPrimitiveDouble() {
        final double[] subject = Arrays.map(testDoubles, x -> x * 2);

        assertEquals(testDoubles.length, subject.length);
        for (int i = 0; i < subject.length; i++) {
            assertEquals(testDoubles[i] * 2, subject[i], 0.001);
        }
    }

    @Test
    public void ensureMapPrimitiveDoubleKeepsOriginal() {
        Arrays.map(testDoubles, x -> x * 2);

        for (int i = 0; i < testDoubles.length; i++) {
            assertEquals(i + 1.0, testDoubles[i], 0.001);
        }
    }

    @Test
    public void ensureCountOfUnexistingElementIsZero() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertEquals(0, Arrays.countOf(test, 42));
    }

    @Test
    public void ensureCountOfExistingSingleElementIsOne() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertEquals(1, Arrays.countOf(test, 2));
    }

    @Test
    public void ensureCountOfExistingTripleElementIsThree() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertEquals(3, Arrays.countOf(test, 2));
    }
}
