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
class StringsRepeatTest {

    @Test
    void ensureRepeat0Isempty() {
        final String result = Strings.repeat('-', 0);
        assertEquals(0, result.length());
    }

    @Test
    void ensureRepeatNegativeIsempty() {
        final String result = Strings.repeat('-', -1);
        assertEquals(0, result.length());
    }

    @Test
    void ensureRepeatNLengthIsN() {
        final int n = 10;
        final String result = Strings.repeat('-', n);
        assertEquals(n, result.length());
    }

    @Test
    void ensureSymbolsIsRepeated() {
        final char symbol = '-';
        final String result = Strings.repeat(symbol, 10);
        for (int i = 0; i < result.length(); i++) {
            assertEquals(symbol, result.charAt(i));
        }
    }
}
