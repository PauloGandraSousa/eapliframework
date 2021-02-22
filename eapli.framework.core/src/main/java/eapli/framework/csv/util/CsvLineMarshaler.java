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
package eapli.framework.csv.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eapli.framework.util.Utility;

/**
 * Simple utility to parse CSV records.
 *
 * @author Paulo Gandra Sousa 04/06/2020
 *
 */
@Utility
public final class CsvLineMarshaler {

    /**
     * <a href=
     * "https://www.oreilly.com/library/view/regular-expressions-cookbook/9781449327453/ch09s13.html"
     * >OReilly</a>.
     *
     * The comma is not removed from the token.
     */
    // "(,|\\\r?\\\n|^)([^\",\\\r\\\n]+|\"(?:[^\"]|\"\")*\")?"

    /**
     * <a href="http://regexlib.com/REDetails.aspx?regexp_id=1197">RegExLib.com 1197</a>
     *
     */
    // "((?:[^\",]|(?:\"(?:\\{2}|\\\"|[^\"])*?\"))*)"

    /**
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

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

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
        final Pattern pattern = Pattern.compile(regexPattern);
        final List<String> list = new ArrayList<>();
        final Matcher m = pattern.matcher(input);
        int pos = 0;
        while (m.find(pos)) {
            final String match = m.group();
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
     * Prepares a quoted string to be represented as a properly escaped CSV field with double
     * quotes.
     *
     * <pre>
     * The girl said "hello"
     * </pre>
     *
     * is transformed to:
     *
     * <pre>
     * The girl said ""hello""
     * </pre>
     *
     * @param field
     * @return a properly escaped CSV field with double quotes
     */
    public static String escapeQuotes(final String field) {
        return field.replace("\"", "\"\"");
    }

    /**
     * Takes a CSV escaped quote content and removes the double quotes.
     *
     * <pre>
     * The girl said ""hello""
     * </pre>
     *
     * is transformed to:
     *
     * <pre>
     * The girl said "hello"
     * </pre>
     *
     *
     * @param field
     * @return
     */
    public static String unescapeQuotes(final String field) {
        return field.replace("\"\"", "\"");
    }

    /**
     * Adds quotes around a field content if the field is not yet quoted.
     *
     * @param field
     * @return
     */
    public static String quote(final String field) {
        if (!field.isEmpty() && field.charAt(0) == '"' && field.charAt(field.length() - 1) == '"') {
            return field;
        }
        return "\"" + field + "\"";
    }

    /**
     * Removes the quotes around a field content.
     *
     * @param field
     * @return
     */
    public static String unquote(final String field) {
        if (field.isEmpty() || (field.charAt(0) != '"' && field.charAt(field.length() - 1) != '"')) {
            return field;
        }
        return field.substring(1, field.length() - 1);
    }

    /**
     * Builds a CSV record where all fields (no matter what data type) are quoted.
     *
     * @param fields
     * @return
     * @deprecated use {@link eapli.framework.csv.CsvRecord#CsvRecord(Object[])}
     */
    @Deprecated
    public static String marshal(final Object[] fields) {
        if (fields.length == 0) {
            return "";
        }

        final StringBuilder sb = new StringBuilder();
        for (final Object f : fields) {
            if (f != null) {
                sb.append(quote(escapeQuotes(f.toString())));
            } else {
                sb.append("");
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Parse a CSV line with comma separator and double quotes.
     *
     * @param cvsLine
     * @return
     * @throws ParseException
     * @deprecated use {@link eapli.framework.csv.CsvRecord#CsvRecord(String)}
     */
    @Deprecated
    public static List<String> parse(final String cvsLine) throws ParseException {
        return parse(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line with double quotes and the specified separator, e.g., comma or semicolon.
     *
     * @param cvsLine
     * @param separators
     * @return
     * @throws ParseException
     * @deprecated use {@link eapli.framework.csv.CsvRecord#CsvRecord(String, char)}
     */
    @Deprecated
    public static List<String> parse(final String cvsLine, final char separators) throws ParseException {
        return parse(cvsLine, separators, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line of text. Empty strings produce no tokens. Empty fields count as a (empty)
     * token. Quoted fields are not unquoted. Handles double quotes (escaped quotes).
     *
     * <p>
     * Based on <a href="https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/">MKyong</a>
     *
     * @param cvsLine
     * @param separators
     * @param customQuote
     * @return
     * @throws ParseException
     * @deprecated use {@link eapli.framework.csv.CsvRecord#CsvRecord(String, char, char)}
     */
    @Deprecated
    public static List<String> parse(final String cvsLine, final char separators, final char customQuote)
            throws ParseException {

        final List<String> result = new ArrayList<>();

        // if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;
        final char[] chars = cvsLine.toCharArray();

        int i = 0;
        while (i < chars.length) {
            final char ch = chars[i];
            if (inQuotes) {
                curVal.append(ch);
                if (ch == customQuote) {
                    if (i < chars.length - 1 && chars[i + 1] == customQuote) {
                        i++;
                        curVal.append(customQuote);
                    } else {
                        inQuotes = false;
                    }
                }
            } else {
                if (ch == customQuote) {
                    inQuotes = startQuotedField(curVal, customQuote);
                } else if (ch == separators) {
                    curVal = addToken(result, curVal);
                } else {
                    curVal.append(ch);
                }
            }

            i++;
        }
        if (inQuotes) {
            throw new ParseException("Unfinished quote", -1);
        }
        result.add(curVal.toString().trim());

        return result;
    }

    private static StringBuilder addToken(final List<String> result, StringBuilder curVal) {
        result.add(curVal.toString().trim());
        curVal = new StringBuilder();
        return curVal;
    }

    private static boolean startQuotedField(final StringBuilder curVal, final char customQuote) {
        curVal.append(customQuote);
        return true;
    }
}