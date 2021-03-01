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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
class StringsPaddingTest {

    private static final String TEST_STRING = "1234567890";
    private static final int PADDING_LENGTH = 12;
    private static final String EXPECTED_PADDING = "--";

    //
    // LEFT PADDING
    //

    @Test
    void ensureLeftPadWithLargerLengthHasRightLength() {
        final String subject = Strings.leftPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertEquals(PADDING_LENGTH, subject.length());
    }

    @Test
    void ensureLeftPadWithLargerLengthHasOriginalString() {
        final String subject = Strings.leftPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertTrue(subject.contains(TEST_STRING));
    }

    @Test
    void ensureLeftPadWithLargerLengthHasRightPadding() {
        final String subject = Strings.leftPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertTrue(subject.contains(EXPECTED_PADDING));
        assertEquals('-', subject.charAt(0));
    }

    @Test
    void ensureLeftPadWithSmallerLengthHasOriginalLength() {
        final String subject = Strings.leftPadded(TEST_STRING, 5, '-');
        assertEquals(TEST_STRING.length(), subject.length());
    }

    @Test
    void ensureLeftPadWithSmallerLengthHasOriginalString() {
        final String subject = Strings.leftPadded(TEST_STRING, 5, '-');
        assertEquals(TEST_STRING, subject);
    }

    //
    // RIGHT PADDING
    //

    @Test
    void ensureRightPadWithLargerLengthHasRightLength() {
        final String subject = Strings.rightPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertEquals(PADDING_LENGTH, subject.length());
    }

    @Test
    void ensureRightPadWithLargerLengthHasOriginalString() {
        final String subject = Strings.rightPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertTrue(subject.contains(TEST_STRING));
    }

    @Test
    void ensureRightPadWithLargerLengthHasRightPadding() {
        final String subject = Strings.rightPadded(TEST_STRING, PADDING_LENGTH, '-');
        assertTrue(subject.contains(EXPECTED_PADDING));
        assertEquals('-', subject.charAt(subject.length() - 1));
    }

    @Test
    void ensureRightPadWithSmallerLengthHasOriginalLength() {
        final String subject = Strings.rightPadded(TEST_STRING, 5, '-');
        assertEquals(TEST_STRING.length(), subject.length());
    }

    @Test
    void ensureRightPadWithSmallerLengthHasOriginalString() {
        final String subject = Strings.rightPadded(TEST_STRING, 5, '-');
        assertEquals(TEST_STRING, subject);
    }
}
