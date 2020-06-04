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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import eapli.framework.math.util.NumberGenerator;
import eapli.framework.util.Utility;

/**
 * Utility class for string manipulation.
 * <p>
 * See also <a href=
 * "https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html">org.apache.commons.lang3.StringUtils</a>
 *
 * @author Paulo Gandra Sousa
 *
 */
@Utility
public final class Strings {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Strings() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Returns a random string with a specified length consisting only of
     * letters (capitals and lower) and digits.
     *
     * @param len
     *            the desired length of the string to generate
     * @return a random string with a specified length consisting only of
     *         letters (capitals and lower) and digits
     */
    public static String randomString(final int len) {
        return randomString(len, CHARSET);
    }

    /**
     * Returns a random string with a specified length based on characters in a
     * specified char set.
     *
     * @param len
     *            the desired length
     * @param charSet
     *            the set of allowable characters to include in the random
     *            string
     * @return a random string with a specified length based on characters in a
     *         specified char set
     */
    public static String randomString(final int len, final String charSet) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final int c = NumberGenerator.anInt(charSet.length());
            sb.append(charSet.charAt(c));
        }
        return sb.toString();
    }

    /**
     * Returns an hexadecimal representation of a byte buffer.
     *
     * @param buffer
     * @return an hexadecimal representation of the byte buffer
     */
    public static String asHexadecimal(final byte[] buffer) {
        final StringBuilder sb = new StringBuilder();
        for (final byte each : buffer) {
            sb.append(String.format("%02x", each));
        }
        return sb.toString();
    }

    /**
     * Shuffles the characters in a string.
     * <p>
     * Inspired by <a href="https://mkyong.com/java/java-password-generator-example/">MKyong</a>.
     *
     * @return the same characters as in the original string but shuffled
     */
    public static String shuffle(final String src) {
        final List<String> letters = Arrays.asList(src.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }

    /**
     * Truncates a string to a specified length.
     *
     * @param org
     *            the string to truncate
     * @param len
     *            the desired length
     * @return the original string if the desired length is less than the actual
     *         length of the original string. otherwise a string composed of the
     *         first {@code len} characters from the original string
     */
    public static String truncate(final String org, final int len) {
        return len < org.length() ? org.substring(0, len) : org;
    }

    /**
     * Returns the first <i>n</i> chars of a string.
     *
     * @param org
     *            the string to extract the characters from
     * @param len
     *            the desired number of characters to extract
     * @return the first n chars of a string
     */
    public static String left(final String org, final int len) {
        if (len <= 0) {
            return "";
        } else if (len >= org.length()) {
            return org;
        } else {
            return org.substring(0, len);
        }
    }

    /**
     * Returns the last <i>n</i> chars of a string.
     *
     * @param org
     *            the string to extract characters from
     * @param len
     *            the desired number of characters to extract
     * @return the last n chars of a string
     */
    public static String right(final String org, final int len) {
        if (len < 0) {
            return "";
        }
        final int newLen = org.length() - len;
        return newLen < 0 ? org : org.substring(newLen);
    }

    /**
     * Constructs a string with the same character repeated <i>n</i> times.
     *
     * @param symbol
     *            the character to repeat
     * @param size
     *            the desired length of the resulting string. if this value is
     *            negative an empty string will be returned
     * @return a string with all characters being the symbol and length size
     */
    public static String repeat(final char symbol, final int size) {
        int n = size;
        final StringBuilder b = new StringBuilder();
        while (n > 0) {
            b.append(symbol);
            n--;
        }
        return b.toString();
    }

    /**
     * Constructs a left padded string from another string.
     *
     * @param src
     *            the string to pad
     * @param length
     *            the expected size of the return string. If {@code length} is less than the length
     *            of the original
     *            string, the original string is returned.
     * @param padding
     *            the character to use as padding
     * @return left padded string
     */
    public static String leftPadded(final String src, final int length, final char padding) {
        final String fmt = "%" + length + "s";
        return String.format(fmt, src).replace(' ', padding);
    }

    /**
     * Constructs a right padded string from another string.
     *
     * @param src
     *            the string to pad
     * @param length
     *            the expected size of the return string. If {@code length} is less than the length
     *            of the original
     *            string, the original string is returned.
     * @param padding
     *            the character to use as padding
     * @return right padded string
     */
    public static String rightPadded(final String src, final int length, final char padding) {
        final String fmt = "%-" + length + "s";
        return String.format(fmt, src).replace(' ', padding);
    }

    /**
     *
     * @param input
     * @param regexPattern
     * @return
     */
    public static List<String> matchResults(final String input, final String regexPattern) {
        final Pattern pattern = Pattern.compile(regexPattern);
        final List<String> list = new ArrayList<>();
        final Matcher m = pattern.matcher(input);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
    }

    /**
     * Very simple tokenizer - finds the matching tokens. Trims tokens.
     *
     * @param input
     * @param regexPattern
     *            the delimiting regular expression
     * @return
     */
    public static String[] tokenize(final String input, final String regexPattern) {
        return matchResults(input, regexPattern).stream().map(String::trim).toArray(i -> new String[i]);
    }

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
     * <a href=
     * "https://softwareengineering.stackexchange.com/questions/166454/can-the-csv-format-be-defined-by-a-regex"
     * >stackexchange</a>
     */

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

    /**
     * Simple tokenizer based on regular expressions to parse a CSV line. This does not capture
     * malformed CSV like missing end quote.
     *
     */
    public static String[] tokenizeCSV(final String input) {

        final UnaryOperator<String> removeLeadComma = s -> s.startsWith(",") ? s.substring(1) : s;
        final List<String> matches = matchResults(input, CSV_REGEX_TESTER);
        if (!matches.isEmpty() && matches.get(0).startsWith(",")) {
            matches.add(0, "");
        }
        return matches.stream().map(removeLeadComma).map(String::trim)
                .toArray(i -> new String[i]);
    }

    /**
     * Very simple tokenizer - finds the tokens around the delimiter. Trims tokens. If the input is
     * empty or just spaces it still returns one token.
     *
     * <pre>
     * String[] tokens = Strings.split("", ",");
     * assert (tokens.length == 1);
     * </pre>
     *
     * <p>
     * However if the input consists only of
     * delimiters the return is an empty array.
     *
     * <pre>
     * String[] tokens = Strings.split(",,,", ",");
     * assert (tokens.length == 0);
     * </pre>
     *
     * <p>
     * Ending delimiters are ignored
     *
     * <pre>
     * String[] tokens = Strings.split("abc,,,", ",");
     * assert (tokens.length == 1);
     * </pre>
     *
     * @param src
     * @param delimiter
     *            the delimiting regular expression
     * @return
     */
    public static String[] split(final String src, final String delimiter) {
        return Arrays.stream(src.split(delimiter)).map(String::trim).toArray(i -> new String[i]);
    }

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    /**
     * Parse a CSV line with comma separator and double quotes.
     *
     * @param cvsLine
     * @return
     */
    public static List<String> parseCsvLine(final String cvsLine) {
        return parseCsvLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    /**
     * Parse a CSV line with double quotes and the specified separator, e.g., comma or semicolon.
     *
     * @param cvsLine
     * @param separators
     * @return
     */
    public static List<String> parseCsvLine(final String cvsLine, final char separators) {
        return parseCsvLine(cvsLine, separators, DEFAULT_QUOTE);
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
    public static List<String> parseCsvLine(final String cvsLine, final char separators, final char customQuote) {

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
            final boolean startCollectChar,
            final char[] chars) {
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
