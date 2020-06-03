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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa 03/06/2020
 */
public class StringsTokenizeTest {

    @Test
    public void ensureEmptyStringHas0Tokens() {
        final String[] subject = Strings.tokenize("", ",");
        assertEquals(0, subject.length);
    }

    @Test
    public void ensureStringWithNoDelimeterHas1Token() {
        final String[] subject = Strings.tokenize("abcd", ",");
        assertEquals(1, subject.length);
        assertEquals("abcd", subject[0]);
    }

    @Test
    public void ensureTokensAreTrimmed() {
        final String[] subject = Strings.tokenize("   I,  Love ,Rock-n-Roll  ", ",");
        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }

    @Test
    public void ensureEmptyWith3DelimetersHas3Tokens() {
        final String[] subject = Strings.tokenize("I Love Rock-n-Roll", " ");
        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }
}
