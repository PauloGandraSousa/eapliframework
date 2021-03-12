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
package eapli.framework.strings.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
class StringsLeftTest {

    private static final String TEST_STRING = "1234567890";

    @Test
    void ensureLeft0IsEmpty() {
        final String result = Strings.left(TEST_STRING, 0);
        assertEquals("", result);
    }

    @Test
    void ensureLeftNegativeIsEmpty() {
        final String result = Strings.left(TEST_STRING, -1);
        assertEquals("", result);
    }

    @Test
    void ensureLeftNLengthIsN() {
        final int n = 3;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(n, result.length());
    }

    @Test
    void ensureLeft3Is123() {
        final String expected = "123";
        final int n = 3;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(expected, result);
    }

    @Test
    void ensureLeftLenBiggerThanLengthIsOriginalString() {
        final String expected = TEST_STRING;
        final int n = TEST_STRING.length() + 15;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(expected, result);
    }
}
