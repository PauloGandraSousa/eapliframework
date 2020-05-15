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
public class PreconditionsTest {

    private static final String MESSAGE = "message";

    @Test
    public void testAreEqualLong() {
        Preconditions.areEqual(42, 42);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAreEqualLongFail() {
        Preconditions.areEqual(42, 24);
    }

    @Test
    public void testAreEqualObject() {
        Preconditions.areEqual("abc", "abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAreEqualObjectFail() {
        Preconditions.areEqual("abc", "xpto", MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAreEqualObjectFailOn1Null() {
        Preconditions.areEqual(null, "xpto", MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAreEqualObjectFailOn2Null() {
        Preconditions.areEqual("abc", null, MESSAGE);
    }

    @Test
    public void testMatches() {
        Preconditions.matches(Pattern.compile("A"), "bcdA234", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMatchesFail() {
        Preconditions.matches(Pattern.compile("x"), "bcdA234", MESSAGE);
    }

    @Test
    public void testEnsureBoolean() {
        Preconditions.ensure(true);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnsureBooleanFail() {
        Preconditions.ensure(false);
    }

    @Test
    public void testEnsureBooleanString() {
        Preconditions.ensure(true, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnsureBooleanStringFail() {
        Preconditions.ensure(false, MESSAGE);
    }

    @Test
    public void testNonNullObject() {
        Preconditions.nonNull("abc");
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonNullObjectFail() {
        final String x = null;
        Preconditions.nonNull(x);
    }

    @Test
    public void testNonNullObjectString() {
        Preconditions.nonNull("abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonNullObjectStringFail() {
        final String x = null;
        Preconditions.nonNull(x, MESSAGE);
    }

    @Test
    public void testNonNullObjectArray() {
        Preconditions.noneNull("abc", Integer.valueOf(42), BigDecimal.ONE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonNullObjectArrayFail() {
        Preconditions.noneNull("abc", Integer.valueOf(42), null, BigDecimal.ONE);
    }

    @Test
    public void testNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Preconditions.nonEmpty(items);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyCollectionFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Preconditions.nonEmpty(items);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyCollectionFailOnNull() {
        final Collection<String> items = null;
        Preconditions.nonEmpty(items);
    }

    @Test
    public void testNonEmptyCollectionString() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Preconditions.nonEmpty(items, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyCollectionStringFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Preconditions.nonEmpty(items, MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyCollectionStringFailOnNull() {
        final Collection<String> items = null;
        Preconditions.nonEmpty(items, MESSAGE);
    }

    @Test
    public void testNonEmptyString() {
        Preconditions.nonEmpty("abc");
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringFailOnEmpty() {
        Preconditions.nonEmpty("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringFailOnEmpty2() {
        Preconditions.nonEmpty("   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringFailOnNull() {
        final String x = null;
        Preconditions.nonEmpty(x);
    }

    @Test
    public void testNonEmptyStringString() {
        Preconditions.nonEmpty("abc", MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringStringFailOnEmpty() {
        Preconditions.nonEmpty("", MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringStringFailOnEmpty2() {
        Preconditions.nonEmpty("   ", MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEmptyStringStringFailOnNull() {
        final String x = null;
        Preconditions.nonEmpty(x, MESSAGE);
    }

    @Test
    public void testIsPositiveLong() {
        Preconditions.isPositive(42);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPositiveLongFailsOnZero() {
        Preconditions.isPositive(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPositiveLongFailsOnNegative() {
        Preconditions.isPositive(-10);
    }

    @Test
    public void testIsPositiveLongString() {
        Preconditions.isPositive(42, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPositiveLongStringFailsOnZero() {
        Preconditions.isPositive(0, MESSAGE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsPositiveLongStringFailsOnNegative() {
        Preconditions.isPositive(-10, MESSAGE);
    }

    @Test
    public void testNonNegativeLong() {
        Preconditions.nonNegative(42);
        assertTrue(true);
    }

    @Test
    public void testNonNegativeLong0() {
        Preconditions.nonNegative(0);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonNegativeLongFail() {
        Preconditions.nonNegative(-10);
    }

    @Test
    public void testNonNegativeLongString() {
        Preconditions.nonNegative(42, MESSAGE);
        assertTrue(true);
    }

    @Test
    public void testNonNegativeLongString0() {
        Preconditions.nonNegative(0, MESSAGE);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonNegativeLongStringFail() {
        Preconditions.nonNegative(-10, MESSAGE);
    }
}
