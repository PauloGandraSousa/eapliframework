/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.util;

/**
 * utility class for string manipulation.
 *
 * see also {@link org.apache.commons.lang3.StringUtils StringUtils}
 *
 * @author Paulo Gandra Sousa
 *
 */
public final class Strings implements Utilitarian {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Strings() {
        // to make sure this is an utility class
    }

    /**
     * returns a random string with a specified length consisting only of
     * letters (capitals and lower) and digits
     *
     * @param len
     * @return
     */
    public static String randomString(final int len) {
        return randomString(len, CHARSET);
    }

    /**
     * returns a random string with a specified length based on characters in a
     * specified char set
     *
     * @param len
     * @param charSet
     * @return
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
     * truncates a string to a specified length
     *
     * @param org
     *            the string to truncate
     * @param len
     *            the desired length
     * @return the original string if the desired length is less than the actual
     *         length of the original string. otherwise a string composed of the
     *         first len characters from the original string
     */
    public static String truncate(final String org, final int len) {
        return len < org.length() ? org.substring(0, len) : org;
    }

    /**
     * returns the first n chars of a string
     *
     * @param org
     * @param len
     * @return
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
     * returns the last n chars of a string
     *
     * @param org
     * @param len
     * @return
     */
    public static String right(final String org, final int len) {
        if (len < 0) {
            return "";
        }
        final int newLen = org.length() - len;
        return newLen < 0 ? org : org.substring(newLen);
    }

    /**
     * constructs a string with the same character repeated n times.
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
