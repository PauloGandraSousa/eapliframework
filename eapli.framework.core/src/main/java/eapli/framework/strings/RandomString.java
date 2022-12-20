/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.strings;

import eapli.framework.domain.model.Immutable;
import eapli.framework.math.util.NumberGenerator;
import lombok.EqualsAndHashCode;

/**
 * @author Paulo Gandra de Sousa 11/05/2022
 */
@Immutable
@EqualsAndHashCode
public final class RandomString {
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final String value;

    /**
     * Returns a random string with a specified length consisting only of
     * letters (capitals and lower) and digits.
     *
     * @param len
     *            the desired length of the string to generate
     */
    public static RandomString of(final int len) {
        return of(len, CHARSET);
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
     */
    public static RandomString of(final int len, final String charSet) {
        final var sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final int c = NumberGenerator.anInt(charSet.length());
            sb.append(charSet.charAt(c));
        }
        return new RandomString(sb.toString());
    }

    /**
     * constructor.
     *
     * @param value
     */
    private RandomString(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
