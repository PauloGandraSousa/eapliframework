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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa 03/06/2020
 */
public class StringsTokenizeTest {

    private static final Logger LOGGER = LogManager.getLogger(StringsTokenizeTest.class);

    private static final String FIXTURE_EMPTY = "";
    private static final String FIXTURE_ALL_SPACES = "    ";
    private static final String FIXTURE_NO_CONTENT_FIELDS = ", , , 321";
    private static final String FIXTURE_NO_CONTENT_FIELDS2 = "abc,,,";
    private static final String FIXTURE_NO_CONTENT_FIELDS3 = "abc, , , ";
    private static final String FIXTURE_SIMPLE = "123, abc";
    private static final String FIXTURE_QUOTED = "123, abc, \"xyz\", 321";
    private static final String FIXTURE_WITH_SPACES = "   I,  Love ,Rock-n-Roll  ";
    private static final String FIXTURE_SINGLE_FIELD = "abcd";
    private static final String FIXTURE_ALL_NO_CONTENT = ",,";
    private static final String FIXTURE_ALL_NO_CONTENT_WITH_SPACE = " , , ";
    private static final String FIXTURE_FIRST_NO_CONTENT = ",ab,cd";
    private static final String FIXTURE_MIDDLE_NO_CONTENT = "ab,,cd";

    @Test
    public void ensureWith3DelimetersHas3Tokens() {
        final String FIXTURE_SIMPLE_SPACE = "I Love Rock-n-Roll";
        final String[] subject = Strings.split(FIXTURE_SIMPLE_SPACE, " ");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_SIMPLE_SPACE, subject);

        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }

    @Test
    public void ensureSplitAllSpacesStringHas0Tokens() {
        final String[] subject = Strings.split(FIXTURE_ALL_SPACES, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_ALL_SPACES, subject);

        assertEquals(1, subject.length);
    }

    @Test
    public void ensureTokenizeAllSpaces() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_ALL_SPACES);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ALL_SPACES, subject);

        assertEquals(1, subject.length);
        assertEquals("", subject[0]);
    }

    @Test
    public void ensureSplitEmptyStringHas1Token() {
        final String[] subject = Strings.split(FIXTURE_EMPTY, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_EMPTY, subject);

        assertEquals(1, subject.length);
    }

    @Test
    public void ensureTokenizeEmpty() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_EMPTY);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_EMPTY, subject);

        assertEquals(1, subject.length);
    }

    @Test
    public void ensureSplitNocontentInFieldIsStillCounted() {
        final String[] subject = Strings.split(FIXTURE_MIDDLE_NO_CONTENT, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_MIDDLE_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("ab", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("cd", subject[2]);
    }

    @Test
    public void ensureTokenizeNocontentInFieldIsStillCounted() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_MIDDLE_NO_CONTENT);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_MIDDLE_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("ab", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("cd", subject[2]);
    }

    @Test
    public void ensureSplitNocontentInFieldIsStillCounted2() {
        final String[] subject = Strings.split(FIXTURE_FIRST_NO_CONTENT, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_FIRST_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("ab", subject[1]);
        assertEquals("cd", subject[2]);
    }

    @Test
    public void ensureTokenizeCSVNocontentInFieldIsStillCounted2() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_FIRST_NO_CONTENT);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_FIRST_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("ab", subject[1]);
        assertEquals("cd", subject[2]);
    }

    // not really helpfull...
    @Test
    public void ensureSplitNoContentHas0Tokens() {
        final String[] subject = Strings.split(FIXTURE_ALL_NO_CONTENT, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_ALL_NO_CONTENT, subject);

        assertEquals(0, subject.length);
    }

    @Test
    public void ensureTokenizeCSVNocontentInFieldIsStillCounted3() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_ALL_NO_CONTENT);

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_ALL_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
    }

    @Test
    public void ensureSplitNocontentInFieldIsStillCounted4() {
        final String[] subject = Strings.split(FIXTURE_ALL_NO_CONTENT_WITH_SPACE, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_ALL_NO_CONTENT_WITH_SPACE, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
    }

    @Test
    public void ensureTokenizeCSVNocontentInFieldIsStillCounted4() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_ALL_NO_CONTENT_WITH_SPACE);

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_ALL_NO_CONTENT_WITH_SPACE, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
    }

    @Test
    public void ensureSplitStringWithNoDelimeterHas1Token() {
        final String[] subject = Strings.split(FIXTURE_SINGLE_FIELD, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_SINGLE_FIELD, subject);

        assertEquals(1, subject.length);
        assertEquals(FIXTURE_SINGLE_FIELD, subject[0]);
    }

    @Test
    public void ensureTokenizeCSVStringWithNoDelimeterHas1Token() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_SINGLE_FIELD);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_SINGLE_FIELD, subject);

        assertEquals(1, subject.length);
        assertEquals(FIXTURE_SINGLE_FIELD, subject[0]);
    }

    @Test
    public void ensureSplitTokensAreTrimmed() {
        final String[] subject = Strings.split(FIXTURE_WITH_SPACES, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_WITH_SPACES, subject);

        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }

    @Test
    public void ensureTokenizeCSVTokensAreTrimmed() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_WITH_SPACES);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_WITH_SPACES, subject);

        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }

    @Test
    public void ensureSplitQuoted() {
        final String[] subject = Strings.split(FIXTURE_QUOTED, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_QUOTED, subject);

        assertEquals(4, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
        assertEquals("\"xyz\"", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    public void ensureTokenizeQuoted() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_QUOTED);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_QUOTED, subject);

        assertEquals(4, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
        assertEquals("\"xyz\"", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    public void ensureTokenizeCommaInQuotes() {
        final String FIXTURE_COMMA_IN_QUOTES = "123, \"I think, therefore I am\", 321";
        final String[] subject = Strings.tokenizeCSV(FIXTURE_COMMA_IN_QUOTES);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_COMMA_IN_QUOTES, subject);

        assertEquals(3, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("\"I think, therefore I am\"", subject[1]);
        assertEquals("321", subject[2]);
    }

    @Test
    public void ensureSplitSimple() {
        final String[] subject = Strings.split(FIXTURE_SIMPLE, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_SIMPLE, subject);

        assertEquals(2, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
    }

    @Test
    public void ensureTokenizeSimple() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_SIMPLE);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_SIMPLE, subject);

        assertEquals(2, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
    }

    @Test
    public void ensureSplitNoContent() {
        final String[] subject = Strings.split(FIXTURE_NO_CONTENT_FIELDS, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_NO_CONTENT_FIELDS, subject);

        assertEquals(4, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    public void ensureTokenizeNoContent() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_NO_CONTENT_FIELDS);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS, subject);

        assertEquals(4, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    public void ensureSplitNoContent2() {
        final String[] subject = Strings.split(FIXTURE_NO_CONTENT_FIELDS2, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_NO_CONTENT_FIELDS2, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }

    @Test
    public void ensureTokenizeNoContent2() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_NO_CONTENT_FIELDS2);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS2, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }

    @Test
    public void ensureSplitNoContent3() {
        final String[] subject = Strings.split(FIXTURE_NO_CONTENT_FIELDS3, ",");

        LOGGER.info("SPLIT: {} -> {}", FIXTURE_NO_CONTENT_FIELDS3, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }

    @Test
    public void ensureTokenizeNoContent3() {
        final String[] subject = Strings.tokenizeCSV(FIXTURE_NO_CONTENT_FIELDS3);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS3, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }
}
