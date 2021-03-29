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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import eapli.framework.util.Utility;

/**
 * Simple utility to parse CSV records based on regular expressions.
 *
 * @author Paulo Gandra Sousa 04/06/2020
 *
 */
@Utility
public final class CsvLineMarshaler {

    /*
     * <a href=
     * "https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch09s13.html"
     * >OReilly</a>.
     *
     * The comma is not removed from the token.
     */
    // "(,|\\\r?\\\n|^)([^\",\\\r\\\n]+|\"(?:[^\"]|\"\")*\")?"

    /*
     * <a href="http://regexlib.com/REDetails.aspx?regexp_id=1197">RegExLib.com 1197</a>
     *
     */
    // "((?:[^\",]|(?:\"(?:\\{2}|\\\"|[^\"])*?\"))*)"

    /*
     * <a href="http://regexlib.com/REDetails.aspx?regexp_id=1106">RegExLib.com 1106</a>
     */
    // "(?<=,)\\s*(?=,)|^(?=,)|[^\\\"]{2,}(?=\\\")|([^,\\\"]+(?=,|$))"

    /**
     * Based on the regex from
     * <a href="https://www.regextester.com/103086">regextester</a>
     *
     * added the handling of whitespace after comma.
     * <p>
     */
    private static final String CSV_REGEX_TESTER = "(\\, *|\\n|^)(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|([^\"\\,\\n]*))";

    private CsvLineMarshaler() {
        // ensure utility
    }

    /**
     * Removes lead comma and trims the token.
     *
     * @param s
     * @return
     */
    private static String cleanToken(final String s) {
        return (s.startsWith(",") ? s.substring(1) : s).trim();
    }

    private static List<String> matchTokens(final String input, final String regexPattern) throws ParseException {
        final var pattern = Pattern.compile(regexPattern);
        final List<String> list = new ArrayList<>();
        final var m = pattern.matcher(input);
        int pos = 0;
        while (m.find(pos)) {
            final var match = m.group();
            if (pos == 0 && !match.isEmpty() && match.charAt(0) == ',') {
                list.add("");
            }
            if (m.start() > pos) {
                throw new ParseException("Sintax error around position", pos);
            }
            list.add(cleanToken(match));
            pos = m.end();
        }
        if (pos < input.length()) {
            throw new ParseException("Sintax error around position", pos);
        }
        return list;
    }

    /**
     * Simple tokenizer based on regular expressions to parse a CSV line. Empty input will return
     * empty token.
     *
     * @param input
     * @return list of tokens
     *
     * @throws ParseException
     *
     */
    public static List<String> tokenize(final String input) throws ParseException {
        if (input.isEmpty()) {
            final List<String> matches = new ArrayList<>();
            matches.add(0, "");
            return matches;
        }

        return matchTokens(input, CSV_REGEX_TESTER);
    }

    /**
     * Removes the quotes around a field content.
     *
     * <pre>
     * "abc" -&gt; abc
     *
     * "abc -&gt; "abc
     *
     * abc" -&gt; abc"
     *
     * abc -&gt; abc
     * </pre>
     *
     * @param field
     * @return unquoted content
     */
    public static String unquote(final String field) {
        if (field.isEmpty() || (field.charAt(0) != '"' && field.charAt(field.length() - 1) != '"')) {
            return field;
        }
        return field.substring(1, field.length() - 1);
    }
}