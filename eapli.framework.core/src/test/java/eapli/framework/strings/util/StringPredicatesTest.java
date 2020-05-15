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
package eapli.framework.strings.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eapli.framework.strings.util.StringPredicates;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class StringPredicatesTest {

    @Test
    public void ensureStringWithContentIsNotNullNorEmpty() {
        System.out.println("string with content is not isNullOrEmpty");
        final String text = "abcdef";
        assertFalse(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    public void ensureNullIsNullOrEmpty() {
        System.out.println("null isNullOrEmpty");
        final String text = null;
        assertTrue(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    public void ensureEmptyStringIsNullOrEmpty() {
        System.out.println("empty string isNullOrEmpty");
        final String text = "";
        assertTrue(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    public void ensureWhiteSpaceStringIsNotEmpty() {
        System.out.println("empty string isNullOrEmpty");
        final String text = "   ";
        assertFalse(StringPredicates.isNullOrEmpty(text));
    }

    @Test
    public void testContainsDigitSingleChar() {
        System.out.println("ContainsDigit single char");
        final String text = "1";
        assertTrue(StringPredicates.containsDigit(text));
    }

    @Test
    public void testContainsDigitInTheMiddle() {
        System.out.println("ContainsDigit in the middle");
        final String text = "ab1cd";
        assertTrue(StringPredicates.containsDigit(text));
    }

    @Test
    public void testContainsDigitInTheBegining() {
        System.out.println("ContainsDigit in the begining");
        final String text = "1cd";
        assertTrue(StringPredicates.containsDigit(text));
    }

    @Test
    public void testContainsDigitInTheEnd() {
        System.out.println("ContainsDigit in the end");
        final String text = "ad1";
        assertTrue(StringPredicates.containsDigit(text));
    }

    @Test
    public void ensureContainsDigitDetectsNonDigit() {
        System.out.println("ContainsDigit without digits");
        final String text = "dd";
        assertFalse(StringPredicates.containsDigit(text));
    }

    private static final String TEST_STRING = "1234567890";
    private static final String CHAR_LIST = "6";

    @Test
    public void ensureContainsAny() {
        assertTrue(StringPredicates.containsAny(TEST_STRING, CHAR_LIST));
    }

    @Test
    public void ensureEmptyCharsAreNotContained() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, ""));
    }

    @Test
    public void ensureNullCharsAreNotContained() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, null));
    }

    @Test
    public void ensureDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny(TEST_STRING, "abc"));
    }

    @Test
    public void ensureEmptyStringDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny("", CHAR_LIST));
    }

    @Test
    public void ensureNullStringDoesNotContainsAny() {
        assertFalse(StringPredicates.containsAny(null, CHAR_LIST));
    }
}
