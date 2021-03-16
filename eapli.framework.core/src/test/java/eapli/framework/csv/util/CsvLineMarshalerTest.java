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
package eapli.framework.csv.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Paulo Gandra Sousa 04/06/2020
 */
class CsvLineMarshalerTest {

    private static final Logger LOGGER = LogManager.getLogger(CsvLineMarshalerTest.class);

    private static final String FIXTURE_EMPTY = "";
    private static final String FIXTURE_ALL_SPACES = "    ";
    private static final String FIXTURE_NO_CONTENT_FIELDS = ", , , 321";
    private static final String FIXTURE_NO_CONTENT_FIELDS2 = "abc,,,";
    private static final String FIXTURE_NO_CONTENT_FIELDS3 = "abc, , , ";
    private static final String FIXTURE_SIMPLE = "123, abc";
    private static final String FIXTURE_QUOTED0 = "\"123\"";
    private static final String FIXTURE_QUOTED1 = "123, abc, \"xyz\", 321";
    private static final String FIXTURE_QUOTED2 = "\"123\", abc, \"xyz\", 321";
    private static final String FIXTURE_QUOTED3 = "\"123\", \"abc\", \"xyz\", \"321\"";
    private static final String FIXTURE_WITH_SPACES = "   I,  Love ,Rock-n-Roll  ";
    private static final String FIXTURE_SINGLE_FIELD = "abcd";
    private static final String FIXTURE_ALL_NO_CONTENT = ",,";
    private static final String FIXTURE_ALL_NO_CONTENT_WITH_SPACE = " , , ";
    private static final String FIXTURE_FIRST_NO_CONTENT = ",ab,cd";
    private static final String FIXTURE_MIDDLE_NO_CONTENT = "ab,,cd";

    private static final String FIXTURE_ESCAPED_QUOTE_IN_FIELD = "a, \"sd\"\"b\",1";
    private static final String FIXTURE_ESCAPED_QUOTE_IN_FIELD1 = "\"sd\"\"b\"";
    private static final String FIXTURE_ESCAPED_QUOTE_IN_FIELD2 = "\"sd\"\"\"";
    private static final String FIXTURE_ESCAPED_QUOTE_IN_FIELD3 = "\"\"\"\"";
    private static final String FIXTURE_ESCAPED_QUOTE_IN_FIELD4 = "\"\"\"\"\"\"";
    private static final String FIXTURE_QUOTE_IN_FIELD = "a, sd\"b,1";
    private static final String FIXTURE_QUOTE_IN_FIELD2 = "a, sd\",1";
    private static final String FIXTURE_MISSING_END_QUOTE_IN_FIELD = "a, \"sdb,1";
    private static final String FIXTURE_MISSING_END_QUOTE_IN_FIELD2 = "a, sdb,\"";

    @ParameterizedTest
    @CsvSource({
            "'', ''", // empty
            "\"\", ''", // empty
            "\"A\", A", // unquotes
            "\"Abc\", Abc", // unquotes
            "A, A", // no quotes
    })
    void ensureUnquote() {
        final String input = "";
        final String expected = "";

        final String result = CsvLineMarshaler.unquote(input);

        assertEquals(expected, result);
    }

    @Test
    void ensureTokenizeAllSpaces() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ALL_SPACES).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ALL_SPACES, subject);

        assertEquals(1, subject.length);
        assertEquals("", subject[0]);
    }

    @Test
    void ensureTokenizeEmpty() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_EMPTY).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_EMPTY, subject);

        assertEquals(1, subject.length);
    }

    @Test
    void ensureTokenizeNocontentInFieldIsStillCounted() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_MIDDLE_NO_CONTENT).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_MIDDLE_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("ab", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("cd", subject[2]);
    }

    @Test
    void ensureTokenizeNoContentInFieldIsStillCounted2() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_FIRST_NO_CONTENT).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_FIRST_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("ab", subject[1]);
        assertEquals("cd", subject[2]);
    }

    @Test
    void ensureTokenizeNoContentInFieldIsStillCounted3() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ALL_NO_CONTENT).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ALL_NO_CONTENT, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
    }

    @Test
    void ensureTokenizeNocontentInFieldIsStillCounted4() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ALL_NO_CONTENT_WITH_SPACE).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ALL_NO_CONTENT_WITH_SPACE, subject);

        assertEquals(3, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
    }

    @Test
    void ensureTokenizeStringWithNoDelimeterHas1Token() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_SINGLE_FIELD).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_SINGLE_FIELD, subject);

        assertEquals(1, subject.length);
        assertEquals(FIXTURE_SINGLE_FIELD, subject[0]);
    }

    @Test
    void ensureTokenizeTokensAreTrimmed() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_WITH_SPACES).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_WITH_SPACES, subject);

        assertEquals(3, subject.length);
        assertEquals("I", subject[0]);
        assertEquals("Love", subject[1]);
        assertEquals("Rock-n-Roll", subject[2]);
    }

    @Test
    void ensureTokenizeQuoted0() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_QUOTED0).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_QUOTED0, subject);

        assertEquals(1, subject.length);
        assertEquals("\"123\"", subject[0]);
    }

    @Test
    void ensureTokenizeQuoted() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_QUOTED1).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_QUOTED1, subject);

        assertEquals(4, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
        assertEquals("\"xyz\"", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    void ensureTokenizeQuoted2() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_QUOTED2).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_QUOTED2, subject);

        assertEquals(4, subject.length);
        assertEquals("\"123\"", subject[0]);
        assertEquals("abc", subject[1]);
        assertEquals("\"xyz\"", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    void ensureTokenizeQuoted3() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_QUOTED3).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_QUOTED3, subject);

        assertEquals(4, subject.length);
        assertEquals("\"123\"", subject[0]);
        assertEquals("\"abc\"", subject[1]);
        assertEquals("\"xyz\"", subject[2]);
        assertEquals("\"321\"", subject[3]);
    }

    @Test
    void ensureTokenizeCommaInQuotes() throws ParseException {
        final String FIXTURE_COMMA_IN_QUOTES = "123, \"I think, therefore I am\", 321";
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_COMMA_IN_QUOTES).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_COMMA_IN_QUOTES, subject);

        assertEquals(3, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("\"I think, therefore I am\"", subject[1]);
        assertEquals("321", subject[2]);
    }

    @Test
    void ensureTokenizeSimple() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_SIMPLE).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_SIMPLE, subject);

        assertEquals(2, subject.length);
        assertEquals("123", subject[0]);
        assertEquals("abc", subject[1]);
    }

    @Test
    void ensureTokenizeNoContent() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_NO_CONTENT_FIELDS).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS, subject);

        assertEquals(4, subject.length);
        assertEquals("", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("321", subject[3]);
    }

    @Test
    void ensureTokenizeNoContent2() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_NO_CONTENT_FIELDS2).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS2, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }

    @Test
    void ensureTokenizeNoContent3() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_NO_CONTENT_FIELDS3).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS3, subject);

        assertEquals(4, subject.length);
        assertEquals("abc", subject[0]);
        assertEquals("", subject[1]);
        assertEquals("", subject[2]);
        assertEquals("", subject[3]);
    }

    @Test
    void ensureTokenizeThrowsForUnescapedQuoteInField() throws ParseException {
        assertThrows(ParseException.class, () -> CsvLineMarshaler.tokenize(FIXTURE_QUOTE_IN_FIELD));
    }

    @Test
    void ensureTokeniseEscapedQuoteInField() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ESCAPED_QUOTE_IN_FIELD).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD, subject);

        assertEquals(3, subject.length);
        assertEquals("a", subject[0]);
        assertEquals("\"sd\"\"b\"", subject[1]);
        assertEquals("1", subject[2]);
    }

    @Test
    void ensureTokenizeEscapedQuoteInField1() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ESCAPED_QUOTE_IN_FIELD1).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD1, subject);

        assertEquals(1, subject.length);
        assertEquals("\"sd\"\"b\"", subject[0]);
    }

    @Test
    void ensureTokenizeEscapedQuoteInField2() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ESCAPED_QUOTE_IN_FIELD2).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD2, subject);

        assertEquals(1, subject.length);
        assertEquals("\"sd\"\"\"", subject[0]);
    }

    @Test
    void ensureTokenizeEscapedQuoteInField3() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ESCAPED_QUOTE_IN_FIELD3).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD3, subject);

        assertEquals(1, subject.length);
        assertEquals("\"\"\"\"", subject[0]);
    }

    @Test
    void ensureTokenizeEscapedQuoteInField4() throws ParseException {
        final String[] subject = CsvLineMarshaler.tokenize(FIXTURE_ESCAPED_QUOTE_IN_FIELD4).toArray(new String[0]);

        LOGGER.info("TOKENIZE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD4, subject);

        assertEquals(1, subject.length);
        assertEquals("\"\"\"\"\"\"", subject[0]);
    }

    @Test
    void ensureTokenizeThrowsForUnescapedQuoteInField2() throws ParseException {
        assertThrows(ParseException.class, () -> CsvLineMarshaler.tokenize(FIXTURE_QUOTE_IN_FIELD2));
    }

    @Test
    void ensureTokenizeMissingQuoteInField() throws ParseException {
        assertThrows(ParseException.class, () -> CsvLineMarshaler.tokenize(FIXTURE_MISSING_END_QUOTE_IN_FIELD));
    }

    @Test
    void ensureTokenizeMissingQuoteInField2() throws ParseException {
        assertThrows(ParseException.class, () -> CsvLineMarshaler.tokenize(FIXTURE_MISSING_END_QUOTE_IN_FIELD2));
    }
}
