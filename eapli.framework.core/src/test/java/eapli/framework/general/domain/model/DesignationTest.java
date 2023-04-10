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
package eapli.framework.general.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Paulo Gandra de Sousa
 */
class DesignationTest {

    @Test
    void ensureNotNull() {
        assertThrows(IllegalArgumentException.class, () -> Designation.valueOf(null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "    ", "  ABC  ", "ABC   ", "  ABC", "" })
    void ensureThrowsOnInvalidInput(final String input) {
        assertThrows(IllegalArgumentException.class, () -> Designation.valueOf(input));
    }

    @Test
    void ensureStartsWithSomething() {
        assertNotNull(Designation.valueOf("ABC"));
    }

    // TRY methods

    @Test
    void ensureTryNotNull() {
        final var result = Designation.tryValueOf(null).left().orElseThrow(IllegalStateException::new);
        assertEquals(String.class, result.getClass());
    }

    @ParameterizedTest
    @ValueSource(strings = { "    ", "  ABC  ", "ABC   ", "  ABC", "" })
    void ensureTryFailsOnInvalidInput(final String input) {
        final var result = Designation.tryValueOf(input).left().orElseThrow(IllegalStateException::new);
        assertEquals(String.class, result.getClass());
    }

    @Test
    void ensureTryStartsWithSomething() {
        final var result = Designation.tryValueOf("ABC").right().orElseThrow(IllegalStateException::new);
        assertEquals(Designation.class, result.getClass());
    }
}
