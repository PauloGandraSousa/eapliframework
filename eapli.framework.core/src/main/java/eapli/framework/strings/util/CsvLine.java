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

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import eapli.framework.util.Utility;

/**
 * Simple utility to parse CSV records.
 *
 * @author Paulo Gandra Sousa 04/06/2020
 *
 */
@Utility
public final class CsvLine {

    /**
     * <a href=
     * "https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch09s13.html"
     * >OReilly</a>.
     *
     * The comma is not removed from the token.
     */
    private static final String CSV_OREILLY = "(,|\\\r?\\\n|^)([^\",\\\r\\\n]+|\"(?:[^\"]|\"\")*\")?";
    /**
     * <a href="http://regexlib.com/REDetails.aspx?regexp_id=1197">RegExLib.com 1197</a>
     *
     */
    private static final String CSV_LIB_1197 = "((?:[^\",]|(?:\"(?:\\{2}|\\\"|[^\"])*?\"))*)";
    /**
     * <a href="http://regexlib.com/REDetails.aspx?regexp_id=1106">RegExLib.com 1106</a>
     */
    private static final String CSV_LIB_1106 = "(?<=,)\\s*(?=,)|^(?=,)|[^\\\"]{2,}(?=\\\")|([^,\\\"]+(?=,|$))";
    /**
     * Based on the regex from
     * <a href="https://www.regextester.com/103086">regextester</a>
     *
     * added the handling of whitespace after comma.
     * <p>
     *
     * <pre>
     * (\,<span style=
    "border-style:dotted;border-width:2px;border-color:red;background-color:#AFEEEE"> *</span>|\n|^)(?:"([^"]*(?:""[^"]*)*)"|([^"\,\n]*))
     * </pre>
     */
    private static final String CSV_REGEX_TESTER = "(\\, *|\\n|^)(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|([^\"\\,\\n]*))";

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    private CsvLine() {
        // ensure utility
    }

    /**
     * Simple tokenizer based on regular expressions to parse a CSV line. This does not capture
     * malformed CSV like missing end quote.
     *
     */
    public static String[] tokenize(final String input) {

        final UnaryOperator<String> removeLeadComma = s -> s.startsWith(",") ? s.substring(1) : s;
        final List<String> matches = Strings.matchResults(input, CSV_REGEX_TESTER);
        if (!matches.isEmpty() && matches.get(0).startsWith(",")) {
            matches.add(0, "");
        }
        return matches.stream().map(removeLeadComma).map(String::trim)
                .toArray(i -> new String[i]);
    }

    public static String escapeQuotes(final String msg) {
        return msg.replace("\"", "\"\"");
    }

    public static String unescapeQuotes(final String msg) {
        return msg.replace("\"\"", "\"");
    }

    /**
     * Parse a CSV line with comma separator and double quotes.
     *
     * @param cvsLine
     * @return
     */
    public static List<String> parse(final String cvsLine) {
        return parse(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line with double quotes and the specified separator, e.g., comma or semicolon.
     *
     * @param cvsLine
     * @param separators
     * @return
     */
    public static List<String> parse(final String cvsLine, final char separators) {
        return parse(cvsLine, separators, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line of text.
     * <p>
     * Based on <a href="https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/">MKyong</a>
     *
     * @param cvsLine
     * @param separators
     * @param customQuote
     * @return
     */
    public static List<String> parse(final String cvsLine, final char separators, final char customQuote) {

        final List<String> result = new ArrayList<>();

        // if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        final char[] chars = cvsLine.toCharArray();

        for (final char ch : chars) {
            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                    doubleQuotesInColumn = handleCharInQuotedField(curVal, doubleQuotesInColumn, ch);
                }
            } else {
                if (ch == customQuote) {
                    inQuotes = startQuotedField(customQuote, curVal, startCollectChar, chars);
                } else if (ch == separators) {
                    curVal = addToken(result, curVal);
                    startCollectChar = false;
                } else {
                    curVal.append(ch);
                }
            }
        }

        result.add(curVal.toString());

        return result;
    }

    private static StringBuilder addToken(final List<String> result, StringBuilder curVal) {
        result.add(curVal.toString());
        curVal = new StringBuilder();
        return curVal;
    }

    private static boolean startQuotedField(final char customQuote, final StringBuilder curVal,
            final boolean startCollectChar, final char[] chars) {
        boolean inQuotes;
        inQuotes = true;

        // Fixed : allow "" in empty quote enclosed
        if (chars[0] != '"' && customQuote == '\"') {
            curVal.append('"');
        }

        // double quotes in column will hit this!
        if (startCollectChar) {
            curVal.append('"');
        }
        return inQuotes;
    }

    private static boolean handleCharInQuotedField(final StringBuilder curVal, boolean doubleQuotesInColumn,
            final char ch) {
        // Fixed : allow "" in custom quote enclosed
        if (ch == '\"') {
            if (!doubleQuotesInColumn) {
                curVal.append(ch);
                doubleQuotesInColumn = true;
            }
        } else {
            curVal.append(ch);
        }
        return doubleQuotesInColumn;
    }
}