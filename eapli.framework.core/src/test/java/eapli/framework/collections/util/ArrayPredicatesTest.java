/*
 * Copyright (c) 2013-2024 the original author or authors.
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
package eapli.framework.collections.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 28/05/2020
 */
class ArrayPredicatesTest {

    private final String[] originalStrings = { "aa", "bb", "cc", "dd" };
    private final String[] testStrings = new String[originalStrings.length];

    @BeforeEach
    public void setUp() throws Exception {
        for (int i = 0; i < originalStrings.length; i++) {
            testStrings[i] = originalStrings[i];
        }
    }

    @Test
    void ensureContainsAA() {
        assertTrue(ArrayPredicates.contains(testStrings, "aa"));
    }

    @Test
    void ensureDoesNotContainXX() {
        assertFalse(ArrayPredicates.contains(testStrings, "xx"));
    }

    @Test
    void ensureDistinctArrayElements() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        assertTrue(ArrayPredicates.areAllDistinct(test));
    }

    @Test
    void ensureNotDistintictArrayElements() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertFalse(ArrayPredicates.areAllDistinct(test));
    }

    @Test
    void ensureAll() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertTrue(ArrayPredicates.all(test, i -> i < 50));
    }

    @Test
    void ensureAllNot() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertFalse(ArrayPredicates.all(test, i -> i < 5));
    }

    @Test
    void ensureAny() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertTrue(ArrayPredicates.any(test, i -> i > 5));
    }

    @Test
    void ensureAnyNot() {
        final Integer[] test = new Integer[] { 1, 2, 3, 4, 2, 5, 6, 7, 8, 2, 9, 10 };
        assertFalse(ArrayPredicates.any(test, i -> i > 50));
    }
}
