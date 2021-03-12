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
package eapli.framework.identities.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class PrefixedNumberSequenceGeneratorTest {

    private static final String PREFIX = "ABC";
    private static final byte LENGTH = 8;
    private PrefixedNumberSequenceGenerator instance;

    @BeforeEach
    void setUp() throws Exception {
        instance = new PrefixedNumberSequenceGenerator(PREFIX, LENGTH);
    }

    @Test
    void ensurePrefixIsNotNull() {
        assertThrows(IllegalArgumentException.class,
                () -> instance = new PrefixedNumberSequenceGenerator(null, LENGTH));
    }

    @Test
    void ensurePrefixIsNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> instance = new PrefixedNumberSequenceGenerator("", LENGTH));
    }

    @Test
    void ensureLengthIsBiggerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> instance = new PrefixedNumberSequenceGenerator(PREFIX, 0));
    }

    @Test
    void ensureLengthIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () -> instance = new PrefixedNumberSequenceGenerator(PREFIX, -1));
    }

    @Test
    void ensureGeneratedIdLength() {
        final String result = instance.newId();
        final int expected = PREFIX.length() + LENGTH;
        assertEquals(expected, result.length());
    }

    @Test
    void ensureGeneratedIdPrefix() {
        final String result = instance.newId();
        assertEquals(PREFIX, result.substring(0, PREFIX.length()));
    }

    @Test
    void ensureGeneratedIdHasPaddingZeros() {
        final String result = instance.newId();
        assertEquals(PREFIX + "00000001", result);
    }
}
