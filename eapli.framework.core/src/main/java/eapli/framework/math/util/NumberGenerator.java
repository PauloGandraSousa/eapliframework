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
package eapli.framework.math.util;

import java.security.SecureRandom;
import java.util.Random;

import eapli.framework.strings.util.Strings;
import eapli.framework.util.Utility;

/**
 * Very simple secure random generator support.
 *
 * <p>
 * Make sure you check <a href=
 * "https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS2245&rule_key=squid%3AS2245">Sonar
 * squid:S2245</a>
 * in order to understand the security issues that rise from using pseudorandom number generators.
 */
@Utility
public final class NumberGenerator {

    private static final Random RANDOM_GEN = new SecureRandom();

    private NumberGenerator() {
        // ensure no instantiation as this is a utility class
    }

    /**
     *
     * @return a random integer
     */
    public static int anInt() {
        return RANDOM_GEN.nextInt();
    }

    /**
     *
     * @param bound
     * @return a random integer between 0 (inclusive) and {@code bound}
     *         (exclusive)
     */
    public static int anInt(final int bound) {
        return RANDOM_GEN.nextInt(bound);
    }

    /**
     *
     * @return a random float between 0 and 1
     */
    public static float aFloat() {
        return RANDOM_GEN.nextFloat();
    }

    /**
     *
     * @param n
     * @return an array of random bytes
     */
    public static byte[] nBytes(final int n) {
        final byte[] buffer = new byte[n];
        RANDOM_GEN.nextBytes(buffer);
        return buffer;
    }

    /**
     *
     * @param length
     * @return a random hexadecimal string
     */
    public static String anHex(final int length) {
        if (NumberPredicates.isOdd(length)) {
            final byte[] buffer = nBytes((length + 1) / 2);
            return Strings.asHexadecimal(buffer).substring(0, length);
        } else {
            final byte[] buffer = nBytes(length / 2);
            return Strings.asHexadecimal(buffer);
        }
    }
}
