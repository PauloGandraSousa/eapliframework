/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.general.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra Sousa
 *
 */
class EmailAddressTest {

    @Test
    void testEmailAddress() {
        Assertions.assertNotNull(new EmailAddress("geral@acme.com"));
    }

    @Test
    void ensureEmailAddressHasAt() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress("geralATacme.com"));
    }

    @Test
    void ensureEmailAddressHasHostAndDomain() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress("geral@acme"));
    }

    @Test
    void ensureEmailAddressHasName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress("@acme.com"));
    }

    @Test
    void ensureEmailAddressIsNotNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress(null));
    }

    @Test
    void ensureEmailAddressIsNotEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EmailAddress(""));
    }
}
