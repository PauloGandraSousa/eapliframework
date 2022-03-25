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
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa
 */
class InvariantsTest {

    private static final String MESSAGE = "message";

    @Test
    void testAreEqualLong() {
        Invariants.areEqual(42, 42);
        assertTrue(true);
    }

    @Test
    void testAreEqualLongFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.areEqual(42, -1));
    }

    @Test
    void testAreEqualObject() {
        Invariants.areEqual("abc", "abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void testAreEqualObjectFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.areEqual("abc", "xpto", MESSAGE));
    }

    @Test
    void testAreEqualObjectFailOn1Null() {
        assertThrows(IllegalStateException.class, () -> Invariants.areEqual(null, "xpto", MESSAGE));
    }

    @Test
    void testAreEqualObjectFailOn2Null() {
        assertThrows(IllegalStateException.class, () -> Invariants.areEqual("abc", null, MESSAGE));
    }

    @Test
    void testMatches() {
        Invariants.matches(Pattern.compile("A"), "bcdA234", MESSAGE);
        assertTrue(true);
    }

    @Test
    void testMatchesFail() {
        final Pattern pat = Pattern.compile("x");
        assertThrows(IllegalStateException.class, () -> Invariants.matches(pat, "bcdA234", MESSAGE));
    }

    @Test
    void testEnsureBoolean() {
        Invariants.ensure(true);
        assertTrue(true);
    }

    @Test
    void testEnsureBooleanFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.ensure(false));
    }

    @Test
    void testEnsureBooleanString() {
        Invariants.ensure(true, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testEnsureBooleanStringFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.ensure(false, MESSAGE));
    }

    @Test
    void testNonNullObject() {
        Invariants.nonNull("abc");
        assertTrue(true);
    }

    @Test
    void testNonNullObjectFail() {
        final String x = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonNull(x));
    }

    @Test
    void testNonNullObjectString() {
        Invariants.nonNull("abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonNullObjectStringFail() {
        final String x = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonNull(x, MESSAGE));
    }

    @Test
    void testNonNullObjectArray() {
        Invariants.noneNull("abc", Integer.valueOf(42), BigDecimal.ONE);
        assertTrue(true);
    }

    @Test
    void testNonNullObjectArrayFail() {
        final Integer fortytwo = Integer.valueOf(42);
        assertThrows(IllegalStateException.class, () -> Invariants.noneNull("abc", fortytwo, null, BigDecimal.ONE));
    }

    @Test
    void testNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items);
        assertTrue(true);
    }

    @Test
    void testNonEmptyCollectionFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(items));
    }

    @Test
    void testNonEmptyCollectionFailOnNull() {
        final Collection<String> items = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(items));
    }

    @Test
    void testNonEmptyCollectionString() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonEmptyCollectionStringFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(items, MESSAGE));
    }

    @Test
    void testNonEmptyCollectionStringFailOnNull() {
        final Collection<String> items = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(items, MESSAGE));
    }

    @Test
    void testNonEmptyString() {
        Invariants.nonEmpty("abc");
        assertTrue(true);
    }

    @Test
    void testNonEmptyStringFailOnEmpty() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(""));
    }

    @Test
    void testNonEmptyStringFailOnEmpty2() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty("   "));
    }

    @Test
    void testNonEmptyStringFailOnNull() {
        final String x = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(x));
    }

    @Test
    void testNonEmptyStringString() {
        Invariants.nonEmpty("abc", MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonEmptyStringStringFailOnEmpty() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty("", MESSAGE));
    }

    @Test
    void testNonEmptyStringStringFailOnEmpty2() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty("   ", MESSAGE));
    }

    @Test
    void testNonEmptyStringStringFailOnNull() {
        final String x = null;
        assertThrows(IllegalStateException.class, () -> Invariants.nonEmpty(x, MESSAGE));
    }

    @Test
    void testIsPositiveLong() {
        Invariants.isPositive(42);
        assertTrue(true);
    }

    @Test
    void testIsPositiveLongFailsOnZero() {
        assertThrows(IllegalStateException.class, () -> Invariants.isPositive(0));
    }

    @Test
    void testIsPositiveLongFailsOnNegative() {
        assertThrows(IllegalStateException.class, () -> Invariants.isPositive(-10));
    }

    @Test
    void testIsPositiveLongString() {
        Invariants.isPositive(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testIsPositiveLongStringFailsOnZero() {
        assertThrows(IllegalStateException.class, () -> Invariants.isPositive(0, MESSAGE));
    }

    @Test
    void testIsPositiveLongStringFailsOnNegative() {
        assertThrows(IllegalStateException.class, () -> Invariants.isPositive(-10, MESSAGE));
    }

    @Test
    void testNonNegativeLong() {
        Invariants.nonNegative(42);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLong0() {
        Invariants.nonNegative(0);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLongFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonNegative(-10));
    }

    @Test
    void testNonNegativeLongString() {
        Invariants.nonNegative(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLongString0() {
        Invariants.nonNegative(0, MESSAGE);
        assertTrue(true);
    }

    @Test
    void testNonNegativeLongStringFail() {
        assertThrows(IllegalStateException.class, () -> Invariants.nonNegative(-10, MESSAGE));
    }
}
