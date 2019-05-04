/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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

/**
 *
 * @author Paulo Gandra Sousa
 */
public class StringsTest {

    private static final String TEST_STRING = "1234567890";

    @Test
    public void ensureTruncate5HasLength5() {
        final String subject = Strings.truncate(TEST_STRING, 5);
        assertEquals(5, subject.length());
    }

    @Test
    public void ensureTruncate5HasFirst5CharsOfOriginal() {
        final String subject = Strings.truncate(TEST_STRING, 5);
        for (int i = 0; i < 5; i++) {
            assertEquals(TEST_STRING.charAt(i), subject.charAt(i));
        }
    }

    @Test
    public void ensureTruncateBiggerThanLengthHasOriginalString() {
        final String subject = Strings.truncate(TEST_STRING, 2 + TEST_STRING.length());
        assertEquals(TEST_STRING, subject);
    }

    @Test
    public void ensureTruncateLengthHasOriginalString() {
        final String subject = Strings.truncate(TEST_STRING, TEST_STRING.length());
        assertEquals(TEST_STRING, subject);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void ensureTruncateNegativeHasOriginalString() {
        Strings.truncate(TEST_STRING, -1);
    }

    @Test
    public void ensureTruncateZeroHasemptyString() {
        final String subject = Strings.truncate(TEST_STRING, 0);
        assertEquals("", subject);
    }
}
