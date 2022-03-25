/*
 * Copyright (c) 2013-2022 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Paulo Gandra Sousa 28/05/2020
 *
 */
class RandomRawPasswordTest {

    @Test
    void ensureMinLength3() {
        assertThrows(IllegalArgumentException.class, () -> new RandomRawPassword(2));
    }

    @Test
    void ensureDefault() {
        final RandomRawPassword rrp = new RandomRawPassword();

        assertNotNull(rrp.toString());
        assertTrue(rrp.toString().length() > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 4, 6, 8, 10, 24 })
    void ensureLength(final int n) {
        final RandomRawPassword rrp = new RandomRawPassword(n);

        System.out.println("Random password: " + rrp.toString());

        assertNotNull(rrp.toString());
        assertEquals(rrp.toString().length(), n);
    }

    @Test
    void ensureOnlyHasAllowedCharacters() {
        final int N = 124;
        final RandomRawPassword rrp = new RandomRawPassword(N);

        for (final char c : rrp.toString().toCharArray()) {
            assertTrue(RandomRawPassword.ALLOWED_CHARS.indexOf(c) >= 0);
        }
    }
}
