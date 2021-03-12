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
package eapli.framework.strings.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra Sousa 28/05/2020
 *
 */
class StringsShuffleTest {
    private static final Logger LOGGER = LogManager.getLogger(StringsShuffleTest.class);

    @Test
    void testMantainsLength() {
        ensure1MantainsLength("ABCDEFG");
        ensure1MantainsLength("ABC");
        ensure1MantainsLength("ABCDEFG 12345");
        ensure1MantainsLength("The quick brown fox jumps over the fence");
        ensure1MantainsLength("12345");
    }

    @Test
    void testIsNotTheSame() {
        ensure1IsNotTheSame("ABCDEFG");
        ensure1IsNotTheSame("ABC fgh");
        ensure1IsNotTheSame("ABCDEFG 12345");
        ensure1IsNotTheSame("The quick brown fox jumps over the fence");
        ensure1IsNotTheSame("123456789");
    }

    private void ensure1MantainsLength(final String test) {
        final String r = Strings.shuffle(test);
        assertEquals(test.length(), r.length());
    }

    private void ensure1IsNotTheSame(final String test) {
        final String r = Strings.shuffle(test);
        LOGGER.debug("Suffled |{}| to |{}|", test, r);
        assertNotEquals(test, r);
    }
}
