/*
 * Copyright (c) 2013-2019 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eapli.framework.strings.util.Strings;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class StringsRightTest {

    private static final String TEST_STRING = "1234567890";

    @Test
    public void ensureRight0IsEmpty() {
        final String result = Strings.right(TEST_STRING, 0);
        assertEquals("", result);
    }

    @Test
    public void ensureRightNegativeIsEmpty() {
        final String result = Strings.right(TEST_STRING, -1);
        assertEquals("", result);
    }

    @Test
    public void ensureRightNLengthIsN() {
        final int n = 3;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(n, result.length());
    }

    @Test
    public void ensureRight3Is890() {
        final String expected = "890";
        final int n = 3;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(expected, result);
    }

    @Test
    public void ensureRightLenBiggerThanLengthIsOriginalString() {
        final String expected = TEST_STRING;
        final int n = TEST_STRING.length() + 15;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(expected, result);
    }
}
