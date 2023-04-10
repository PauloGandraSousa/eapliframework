/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 2023.03.24
 */
class ValidationsTest {

    @Test
    void testAreEqualLong() {
        assertTrue(Validations.areEqual(42, 42));
    }

    @Test
    void testAreEqualLongFail() {
        assertFalse(Validations.areEqual(42, 24));
    }

    @Test
    void testAreEqualObject() {
        assertTrue(Validations.areEqual("abc", "abc"));
    }

    @Test
    void testAreEqualObjectFail() {
        assertFalse(Validations.areEqual("abc", "xpto"));
    }

    @Test
    void testAreEqualObjectFailOn1Null() {
        assertFalse(Validations.areEqual(null, "xpto"));
    }

    @Test
    void testAreEqualObjectFailOn2Null() {
        assertFalse(Validations.areEqual("abc", null));
    }

    @Test
    void testMatches() {
        assertTrue(Validations.matches(Pattern.compile("A"), "bcdA234"));
    }

    @Test
    void testMatchesDoesnt() {
        assertFalse(Validations.matches(Pattern.compile("x"), "bcdA234"));
    }

    @Test
    void testNonNull() {
        assertTrue(Validations.nonNull("abc"));
    }

    @Test
    void testNonNullFail() {
        assertFalse(Validations.nonNull(null));
    }

    @Test
    void testNonEmptyCollection() {
        final Collection<String> col = new ArrayList<>();
        col.add("abc");
        assertTrue(Validations.isEmpty(col));
    }

    @Test
    void testNonEmptyCollectionNull() {
        assertFalse(Validations.isEmpty(null));
    }

    @Test
    void testEnsureNonEmptyCollectionFailsIfEmpty() {
        final Collection<String> col = new ArrayList<>();
        assertFalse(Validations.isEmpty(col));
    }

    @Test
    void testNonEmptyString() {
        assertTrue(Validations.nonEmpty("abc"));
    }

    @Test
    void testNonEmptyStringFailsIfNull() {
        assertFalse(Validations.nonEmpty(null));
    }

    @Test
    void testNonEmptyStringFailsIfEmpty() {
        assertFalse(Validations.nonEmpty(""));
    }

    @Test
    void testNonEmptyStringDoesSomethingIfEmpty2() {
        assertFalse(Validations.nonEmpty("  "));
    }

    @Test
    void testIsPositive() {
        assertTrue(Validations.isPositive(42));
    }

    @Test
    void testIsPositiveFailsIfZero() {
        assertFalse(Validations.isPositive(0));
    }

    @Test
    void testIsPositiveFailsIfNegative() {
        assertFalse(Validations.isPositive(-10));
    }

    @Test
    void testIsNonNegativeFailsIfNegative() {
        assertFalse(Validations.isNonNegative(-42));
    }

    @Test
    void testIsNonNegativeZero() {
        assertTrue(Validations.isNonNegative(0));
    }

    @Test
    void testIsNonNegative() {
        assertTrue(Validations.isNonNegative(10));
    }
}
