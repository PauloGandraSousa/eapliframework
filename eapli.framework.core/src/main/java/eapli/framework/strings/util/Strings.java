/*
 * Copyright (c) 2013-2019 the original author or authors.
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
     * Returns the first n chars of a string.
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
     * Returns the last n chars of a string.
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
     * Constructs a string with the same character repeated n times.
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
}
