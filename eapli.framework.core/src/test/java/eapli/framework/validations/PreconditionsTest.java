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
package eapli.framework.validations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa
 */
class PreconditionsTest {

    private static final String MESSAGE = "message";

    @Test
    void testAreEqualLong() {
        Preconditions.areEqual(42, 42);
        assertTrue(true);
    }

    @Test
    void ensureAreEqualLongFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.areEqual(42, -1));
    }

    @Test
    void testAreEqualObject() {
        Preconditions.areEqual("abc", "abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureAreEqualObjectFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.areEqual("abc", "xpto", MESSAGE));
    }

    @Test
    void ensureAreEqualObjectFailOn1Null() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.areEqual(null, "xpto", MESSAGE));
    }

    @Test
    void ensureAreEqualObjectFailOn2Null() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.areEqual("abc", null, MESSAGE));
    }

    @Test
    void testMatches() {
        Preconditions.matches(Pattern.compile("A"), "bcdA234", MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureMatchesFail() {
        final Pattern pat = Pattern.compile("x");
        assertThrows(IllegalArgumentException.class, () -> Preconditions.matches(pat, "bcdA234", MESSAGE));
    }

    @Test
    void testEnsureBoolean() {
        Preconditions.ensure(true);
        assertTrue(true);
    }

    @Test
    void ensureEnsureBooleanFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.ensure(false));
    }

    @Test
    void testEnsureBooleanString() {
        Preconditions.ensure(true, MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureEnsureBooleanStringFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.ensure(false, MESSAGE));
    }

    @Test
    void testNonNullObject() {
        Preconditions.nonNull("abc");
        assertTrue(true);
    }

    @Test
    void ensureNonNullObjectFail() {
        final String x = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonNull(x));
    }

    @Test
    void testNonNullObjectString() {
        Preconditions.nonNull("abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureNonNullObjectStringFail() {
        final String x = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonNull(x, MESSAGE));
    }

    @Test
    void testNonNullObjectArray() {
        Preconditions.noneNull("abc", Integer.valueOf(42), BigDecimal.ONE);
        assertTrue(true);
    }

    @Test
    void ensureNonNullObjectArrayFail() {
        final Integer fortytwo = Integer.valueOf(42);
        assertThrows(IllegalArgumentException.class,
                () -> Preconditions.noneNull("abc", fortytwo, null, BigDecimal.ONE));
    }

    @Test
    void testNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Preconditions.nonEmpty(items);
        assertTrue(true);
    }

    @Test
    void ensureNonEmptyCollectionFailOnEmpty() {
        final var items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(items));
    }

    @Test
    void ensureNonEmptyCollectionFailOnNull() {
        final List<?> items = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(items));
    }

    @Test
    void testNonEmptyCollectionString() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Preconditions.nonEmpty(items, MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureNonEmptyCollectionStringFailOnEmpty() {
        final var items = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(items, MESSAGE));
    }

    @Test
    void ensureNonEmptyCollectionStringFailOnNull() {
        final List<?> items = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(items, MESSAGE));
    }

    @Test
    void testNonEmptyString() {
        Preconditions.nonEmpty("abc");
        assertTrue(true);
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(""));
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty2() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty("   "));
    }

    @Test
    void ensureNonEmptyStringFailOnNull() {
        final String x = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(x));
    }

    @Test
    void testNonEmptyStringString() {
        Preconditions.nonEmpty("abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureNonEmptyStringStringFailOnEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty("", MESSAGE));
    }

    @Test
    void ensureNonEmptyStringStringFailOnEmpty2() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty("   ", MESSAGE));
    }

    @Test
    void ensureNonEmptyStringStringFailOnNull() {
        final String x = null;
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonEmpty(x, MESSAGE));
    }

    @Test
    void testIsPositiveLong() {
        Preconditions.isPositive(42);
        assertTrue(true);
    }

    @Test
    void ensureIsPositiveLongFailsOnZero() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.isPositive(0));
    }

    @Test
    void ensureIsPositiveLongFailsOnNegative() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.isPositive(-10));
    }

    @Test
    void testIsPositiveLongString() {
        Preconditions.isPositive(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureIsPositiveLongStringFailsOnZero() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.isPositive(0, MESSAGE));
    }

    @Test
    void ensureIsPositiveLongStringFailsOnNegative() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.isPositive(-10, MESSAGE));
    }

    @Test
    void testNonNegativeLong() {
        Preconditions.nonNegative(42);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLong0() {
        Preconditions.nonNegative(0);
        assertTrue(true);
    }

    @Test
    void ensureNonNegativeLongFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonNegative(-10));
    }

    @Test
    void testNonNegativeLongString() {
        Preconditions.nonNegative(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLongString0() {
        Preconditions.nonNegative(0, MESSAGE);
        assertTrue(true);
    }

    @Test
    void ensureNonNegativeLongStringFail() {
        assertThrows(IllegalArgumentException.class, () -> Preconditions.nonNegative(-10, MESSAGE));
    }
}
