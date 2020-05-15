/*
 * Copyright (c) 2013-2020 the original author or authors.
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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class InvariantsTest {

    private static final String MESSAGE = "message";

    @Test
    public void testAreEqualLong() {
        Invariants.areEqual(42, 42);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualLongFail() {
        Invariants.areEqual(42, 24);
    }

    @Test
    public void testAreEqualObject() {
        Invariants.areEqual("abc", "abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFail() {
        Invariants.areEqual("abc", "xpto", MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn1Null() {
        Invariants.areEqual(null, "xpto", MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn2Null() {
        Invariants.areEqual("abc", null, MESSAGE);
    }

    @Test
    public void testMatches() {
        Invariants.matches(Pattern.compile("A"), "bcdA234", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testMatchesFail() {
        Invariants.matches(Pattern.compile("x"), "bcdA234", MESSAGE);
    }

    @Test
    public void testEnsureBoolean() {
        Invariants.ensure(true);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanFail() {
        Invariants.ensure(false);
    }

    @Test
    public void testEnsureBooleanString() {
        Invariants.ensure(true, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanStringFail() {
        Invariants.ensure(false, MESSAGE);
    }

    @Test
    public void testNonNullObject() {
        Invariants.nonNull("abc");
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectFail() {
        final String x = null;
        Invariants.nonNull(x);
    }

    @Test
    public void testNonNullObjectString() {
        Invariants.nonNull("abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectStringFail() {
        final String x = null;
        Invariants.nonNull(x, MESSAGE);
    }

    @Test
    public void testNonNullObjectArray() {
        Invariants.noneNull("abc", Integer.valueOf(42), BigDecimal.ONE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectArrayFail() {
        Invariants.noneNull("abc", Integer.valueOf(42), null, BigDecimal.ONE);
    }

    @Test
    public void testNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Invariants.nonEmpty(items);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionFailOnNull() {
        final Collection<String> items = null;
        Invariants.nonEmpty(items);
    }

    @Test
    public void testNonEmptyCollectionString() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionStringFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Invariants.nonEmpty(items, MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionStringFailOnNull() {
        final Collection<String> items = null;
        Invariants.nonEmpty(items, MESSAGE);
    }

    @Test
    public void testNonEmptyString() {
        Invariants.nonEmpty("abc");
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringFailOnEmpty() {
        Invariants.nonEmpty("");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringFailOnEmpty2() {
        Invariants.nonEmpty("   ");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringFailOnNull() {
        final String x = null;
        Invariants.nonEmpty(x);
    }

    @Test
    public void testNonEmptyStringString() {
        Invariants.nonEmpty("abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringStringFailOnEmpty() {
        Invariants.nonEmpty("", MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringStringFailOnEmpty2() {
        Invariants.nonEmpty("   ", MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringStringFailOnNull() {
        final String x = null;
        Invariants.nonEmpty(x, MESSAGE);
    }

    @Test
    public void testIsPositiveLong() {
        Invariants.isPositive(42);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveLongFailsOnZero() {
        Invariants.isPositive(0);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveLongFailsOnNegative() {
        Invariants.isPositive(-10);
    }

    @Test
    public void testIsPositiveLongString() {
        Invariants.isPositive(42, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveLongStringFailsOnZero() {
        Invariants.isPositive(0, MESSAGE);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveLongStringFailsOnNegative() {
        Invariants.isPositive(-10, MESSAGE);
    }

    @Test
    public void testNonNegativeLong() {
        Invariants.nonNegative(42);
        assertTrue(true);
    }

    @Test
    public void testNonNegativeLong0() {
        Invariants.nonNegative(0);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNegativeLongFail() {
        Invariants.nonNegative(-10);
    }

    @Test
    public void testNonNegativeLongString() {
        Invariants.nonNegative(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    public void testNonNegativeLongString0() {
        Invariants.nonNegative(0, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNegativeLongStringFail() {
        Invariants.nonNegative(-10, MESSAGE);
    }
}
