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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import eapli.framework.infrastructure.authz.application.PasswordPolicy;

/**
 * @author Paulo Gandra de Sousa 24/05/2019
 *
 */
class PasswordTest {

    @Test
    void ensurePasswordCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Password(null));
    }

    @Test
    void givenPasswordSatisfiesPolicyThenPasswordIsEncoded() {
        final PasswordPolicy policy = mock(PasswordPolicy.class);
        final PasswordEncoder encoder = mock(PasswordEncoder.class);

        when(policy.isSatisfiedBy("abc123")).thenReturn(true);
        when(encoder.encode("abc123")).thenReturn("encoded");

        final var result = Password.encodedAndValid("abc123", policy, encoder);

        assertTrue(result.isPresent());
        assertEquals("encoded", result.get().value());
    }

    @Test
    void givenPasswordThatDoesNotSatisfiesPolicyThenReturnEmptyOptional() {
        final PasswordPolicy policy = mock(PasswordPolicy.class);
        final PasswordEncoder encoder = mock(PasswordEncoder.class);

        when(policy.isSatisfiedBy("abc123")).thenReturn(false);

        final var result = Password.encodedAndValid("abc123", policy, encoder);

        assertFalse(result.isPresent());
    }

}
