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
        final var sb = new StringBuilder();
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
        final var sb = new StringBuilder();
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
        final var b = new StringBuilder();
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
        final var fmt = "%" + length + "s";
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
        final var fmt = "%-" + length + "s";
        return String.format(fmt, src).replace(' ', padding);
    }

    /**
     * Get all matches of a regular expression.
     *
     * @param input
     * @param regexPattern
     * @return all matches of the regular expression
     */
    public static List<String> matchResults(final String input, final String regexPattern) {
        final var pattern = Pattern.compile(regexPattern);
        final List<String> list = new ArrayList<>();
        final var m = pattern.matcher(input);
        while (m.find()) {
            list.add(m.group());
        }
        return list;
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
     * <p>
     * Otherwise, it will split the input along the separator token
     *
     * <pre>
     * String[] tokens = Strings.split("abc, def, ghi", ",");
     * assert (tokens.length == 3);
     * assert (tokens[0].equals("abc"));
     * assert (tokens[1].equals("def"));
     * assert (tokens[2].equals("ghi"));
     * </pre>
     *
     * @param src
     * @param delimiter
     *            the delimiting regular expression
     * @return the array of strings computed from splitting the source string and removing empty
     *         elements
     */
    public static String[] split(final String src, final String delimiter) {
        return Arrays.stream(src.split(delimiter)).map(String::trim).toArray(i -> new String[i]);
    }
}
