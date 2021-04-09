/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.infrastructure.authz.domain.model;

import eapli.framework.domain.model.Immutable;
import eapli.framework.strings.util.Strings;

/**
 * A random password. Can be used to present as a suggestion to the user upon registration.
 * <p>
 * Inspired by <a href="https://mkyong.com/java/java-password-generator-example/">MKyong</a>.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 *
 */
@Immutable
public class RandomRawPassword {

    private static final int DEFAULT_LENGTH = 12;

    private static final String LOWER_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARS = LOWER_CHARS.toUpperCase();
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL = "_-+*/=#?!.&$€";

    public static final String ALLOWED_CHARS = LOWER_CHARS + UPPER_CHARS + NUMBERS + SPECIAL;

    private final String rawPassword;

    /**
     * Constructs a random password with the default length.
     */
    public RandomRawPassword() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Constructs a random password with the desired length.
     *
     * @param length
     */
    public RandomRawPassword(final int length) {
        rawPassword = Strings.randomString(length, ALLOWED_CHARS);
    }

    @Override
    public String toString() {
        return rawPassword;
    }
}
