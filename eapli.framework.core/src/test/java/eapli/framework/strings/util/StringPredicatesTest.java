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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author Paulo Gandra Sousa
 */
class StringPredicatesTest {

    @Test
    void ensureStringWithContentIsNotNullNorEmpty() {
        System.out.println("string with content is not isNullOrEmpty");
        final String text = "abcdef";
        assertFalse(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    void ensureNullIsNullOrEmpty() {
        System.out.println("null isNullOrEmpty");
        final String text = null;
        assertTrue(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    void ensureEmptyStringIsNullOrEmpty() {
        System.out.println("empty string isNullOrEmpty");
        final String text = "";
        assertTrue(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    void ensureWhiteSpaceStringIsNotEmpty() {
        System.out.println("empty string isNullOrEmpty");
        final String text = "   ";
        assertFalse(StringPredicates.isNullOrEmpty(text));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1",
            "ab1cd",
            "1cd",
            "ad1"
    })
    void testContainsDigit(final String text) {
        assertTrue(StringPredicates.containsDigit(text));
    }

    @Test
    void ensureContainsDigitDetectsNonDigit() {
        System.out.println("ContainsDigit without digits");
        final String text = "dd";
        assertFalse(StringPredicates.containsDigit(text));
    }

    private static final String TEST_STRING = "1234567890";
    private static final String CHAR_LIST = "6";

    @Test
    void ensureContainsAny() {
        assertTrue(StringPredicates.containsAny(TEST_STRING, CHAR_LIST));
    }

    @Test
    void ensureEmptyCharsAreNotContained() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, ""));
    }

    @Test
    void ensureNullCharsAreNotContained() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, null));
    }

    @Test
    void ensureDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, "abc"));
    }

    @Test
    void ensureEmptyStringDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny("", CHAR_LIST));
    }

    @Test
    void ensureNullStringDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny(null, CHAR_LIST));
    }
}
