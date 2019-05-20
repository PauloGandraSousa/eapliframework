/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.util;

import java.util.Random;

/**
 * very simple pseudo random generator support.
 *
 * <p/>
 * make sure you check
 * https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS2245&rule_key=squid%3AS2245
 * in order to understand the security issues that rise from using pseudorandon number generators
 */
public final class NumberGenerator implements Utilitarian {
    @SuppressWarnings("squid:S2245")
    private static final Random RANDOM_GEN = new Random(Calendars.now().getTimeInMillis());

    private NumberGenerator() {
        // ensure utility
    }

    /**
     *
     * @return a pseudo random integer
     */
    public static int anInt() {
        return RANDOM_GEN.nextInt();
    }

    /**
     *
     * @param bound
     * @return a pseudo random integer between 0 (inclusive) and {@code bound} (exclusive)
     */
    public static int anInt(final int bound) {
        return RANDOM_GEN.nextInt(bound);
    }

    /**
     *
     * @return a pseudo random float between 0 and 1
     */
    public static float aFloat() {
        return RANDOM_GEN.nextFloat();
    }

    /**
     * fifty/fifty chance.
     *
     * @return true or false in what tends to be a 50/50 distribution
     */
    public static boolean heads() {
        return anInt() % 2 == 0;
    }
}
