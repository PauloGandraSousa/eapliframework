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
package eapli.framework.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra Sousa 04/06/2020
 */
class CsvRecordParseTest {

    private static final Logger LOGGER = LogManager.getLogger(CsvRecordParseTest.class);

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

    @Test
    void ensureParseAllSpaces() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ALL_SPACES).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ALL_SPACES, subject);

        assertEquals(1, subject.size());
        assertEquals("", subject.get(0));
    }

    @Test
    void ensureParseEmpty() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_EMPTY).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_EMPTY, subject);

        assertEquals(0, subject.size());
    }

    @Test
    void ensureParseNocontentInFieldIsStillCounted() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_MIDDLE_NO_CONTENT).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_MIDDLE_NO_CONTENT, subject);

        assertEquals(3, subject.size());
        assertEquals("ab", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("cd", subject.get(2));
    }

    @Test
    void ensureParseNocontentInFieldIsStillCounted2() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_FIRST_NO_CONTENT).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_FIRST_NO_CONTENT, subject);

        assertEquals(3, subject.size());
        assertEquals("", subject.get(0));
        assertEquals("ab", subject.get(1));
        assertEquals("cd", subject.get(2));
    }

    @Test
    void ensureParseNocontentInFieldIsStillCounted3() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ALL_NO_CONTENT).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ALL_NO_CONTENT, subject);

        assertEquals(3, subject.size());
        assertEquals("", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("", subject.get(2));
    }

    @Test
    void ensureParseNocontentInFieldIsStillCounted4() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ALL_NO_CONTENT_WITH_SPACE).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ALL_NO_CONTENT_WITH_SPACE, subject);

        assertEquals(3, subject.size());
        assertEquals("", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("", subject.get(2));
    }

    @Test
    void ensureParseStringWithNoDelimeterHas1Token() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_SINGLE_FIELD).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_SINGLE_FIELD, subject);

        assertEquals(1, subject.size());
        assertEquals(FIXTURE_SINGLE_FIELD, subject.get(0));
    }

    @Test
    void ensureParseTokensAreTrimmed() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_WITH_SPACES).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_WITH_SPACES, subject);

        assertEquals(3, subject.size());
        assertEquals("I", subject.get(0));
        assertEquals("Love", subject.get(1));
        assertEquals("Rock-n-Roll", subject.get(2));
    }

    @Test
    void ensureParseQuoted0() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_QUOTED0).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_QUOTED0, subject);

        assertEquals(1, subject.size());
        assertEquals("\"123\"", subject.get(0));
    }

    @Test
    void ensureParseQuoted() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_QUOTED1).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_QUOTED1, subject);

        assertEquals(4, subject.size());
        assertEquals("123", subject.get(0));
        assertEquals("abc", subject.get(1));
        assertEquals("\"xyz\"", subject.get(2));
        assertEquals("321", subject.get(3));
    }

    @Test
    void ensureParseQuoted2() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_QUOTED2).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_QUOTED2, subject);

        assertEquals(4, subject.size());
        assertEquals("\"123\"", subject.get(0));
        assertEquals("abc", subject.get(1));
        assertEquals("\"xyz\"", subject.get(2));
        assertEquals("321", subject.get(3));
    }

    @Test
    void ensureParseQuoted3() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_QUOTED3).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_QUOTED3, subject);

        assertEquals(4, subject.size());
        assertEquals("\"123\"", subject.get(0));
        assertEquals("\"abc\"", subject.get(1));
        assertEquals("\"xyz\"", subject.get(2));
        assertEquals("\"321\"", subject.get(3));
    }

    @Test
    void ensureParseCommaInQuotes() throws ParseException {
        final String FIXTURE_COMMA_IN_QUOTES = "123, \"I think, therefore I am\", 321";
        final List<String> subject = CsvRecord.parse(FIXTURE_COMMA_IN_QUOTES).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_COMMA_IN_QUOTES, subject);

        assertEquals(3, subject.size());
        assertEquals("123", subject.get(0));
        assertEquals("\"I think, therefore I am\"", subject.get(1));
        assertEquals("321", subject.get(2));
    }

    @Test
    void ensureParseSimple() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_SIMPLE).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_SIMPLE, subject);

        assertEquals(2, subject.size());
        assertEquals("123", subject.get(0));
        assertEquals("abc", subject.get(1));
    }

    @Test
    void ensureParseNoContent() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_NO_CONTENT_FIELDS).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS, subject);

        assertEquals(4, subject.size());
        assertEquals("", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("", subject.get(2));
        assertEquals("321", subject.get(3));
    }

    @Test
    void ensureParseNoContent2() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_NO_CONTENT_FIELDS2).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS2, subject);

        assertEquals(4, subject.size());
        assertEquals("abc", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("", subject.get(2));
        assertEquals("", subject.get(3));
    }

    @Test
    void ensureParseNoContent3() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_NO_CONTENT_FIELDS3).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_NO_CONTENT_FIELDS3, subject);

        assertEquals(4, subject.size());
        assertEquals("abc", subject.get(0));
        assertEquals("", subject.get(1));
        assertEquals("", subject.get(2));
        assertEquals("", subject.get(3));
    }

    @Test
    void ensureParseQuoteInField() throws ParseException {
        assertThrows(ParseException.class, () -> CsvRecord.parse(FIXTURE_QUOTE_IN_FIELD));
    }

    @Test
    void ensureParseEscapedQuoteInField() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ESCAPED_QUOTE_IN_FIELD).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD, subject);

        assertEquals(3, subject.size());
        assertEquals("a", subject.get(0));
        assertEquals("\"sd\"\"b\"", subject.get(1));
        assertEquals("1", subject.get(2));
    }

    @Test
    void ensureParseEscapedQuoteInField1() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ESCAPED_QUOTE_IN_FIELD1).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD1, subject);

        assertEquals(1, subject.size());
        assertEquals("\"sd\"\"b\"", subject.get(0));
    }

    @Test
    void ensureParseEscapedQuoteInField2() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ESCAPED_QUOTE_IN_FIELD2).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD2, subject);

        assertEquals(1, subject.size());
        assertEquals("\"sd\"\"\"", subject.get(0));
    }

    @Test
    void ensureParseEscapedQuoteInField3() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ESCAPED_QUOTE_IN_FIELD3).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD3, subject);

        assertEquals(1, subject.size());
        assertEquals("\"\"\"\"", subject.get(0));
    }

    @Test
    void ensureParseEscapedQuoteInField4() throws ParseException {
        final List<String> subject = CsvRecord.parse(FIXTURE_ESCAPED_QUOTE_IN_FIELD4).fields();

        LOGGER.info("PARSE: {} -> {}", FIXTURE_ESCAPED_QUOTE_IN_FIELD4, subject);

        assertEquals(1, subject.size());
        assertEquals("\"\"\"\"\"\"", subject.get(0));
    }

    @Test
    void ensureParseQuoteInField2() throws ParseException {
        assertThrows(ParseException.class,
                () -> CsvRecord.parse(FIXTURE_QUOTE_IN_FIELD2));
    }

    @Test
    void ensureParseMissingQuoteInField() throws ParseException {
        assertThrows(ParseException.class,
                () -> CsvRecord.parse(FIXTURE_MISSING_END_QUOTE_IN_FIELD));
    }

    @Test
    void ensureParseMissingQuoteInField2() throws ParseException {
        assertThrows(ParseException.class,
                () -> CsvRecord.parse(FIXTURE_MISSING_END_QUOTE_IN_FIELD2));
    }
}
