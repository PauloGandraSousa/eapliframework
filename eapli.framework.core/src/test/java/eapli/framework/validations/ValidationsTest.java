/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.validations;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;

import eapli.framework.actions.Actions;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class ValidationsTest {

    @Test
    public void testAreEqualLongDoesNothingIfTrue() {
        Validations.areEqual(42, 42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualLongFail() {
        Validations.areEqual(42, 24, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    public void testAreEqualObjectDoesNothingIfTrue() {
        Validations.areEqual("abc", "abc", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFail() {
        Validations.areEqual("abc", "xpto", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn1Null() {
        Validations.areEqual(null, "xpto", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn2Null() {
        Validations.areEqual("abc", null, Actions.THROW_STATE);
    }

    @Test
    public void testMatchesDoNothingIfTrue() {
        Validations.matches(Pattern.compile("A"), "bcdA234", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testMatchesDoesSomethingIfFalse() {
        Validations.matches(Pattern.compile("x"), "bcdA234", Actions.THROW_STATE);
    }

    @Test
    public void testEnsureDoesNothingIfTrue() {
        Validations.ensure(true, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureDoesSomethingIfFalse() {
        Validations.ensure(false, Actions.THROW_STATE);
    }

    @Test
    public void testNonNullDoesNothingIfTrue() {
        Validations.nonNull(Actions.THROW_STATE, "abc");
        assertTrue(true);
    }

    @Test
    public void testNonNullDoesNothingIfTrueForMoreThanOneObject() {
        Validations.nonNull(Actions.THROW_STATE, new String(), Double.valueOf(1), "abc");
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullDoesSomethingIfFalse() {
        final Object x = null;
        Validations.nonNull(Actions.THROW_STATE, x);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullDoesSomethingIfFalseForAtLeastOneObject() {
        final Object x = null;
        Validations.nonNull(Actions.THROW_STATE, "abc", x, Integer.valueOf(1));
    }

    @Test
    public void testNonEmptyCollectionDoesNothingIfTrue() {
        final Collection<String> col = new ArrayList<>();
        col.add("abc");
        Validations.nonEmpty(col, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionDoesSomethingIfNull() {
        final Collection<String> col = null;
        Validations.nonEmpty(col, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionDoesSomethingIfEmpty() {
        final Collection<String> col = new ArrayList<>();
        Validations.nonEmpty(col, Actions.THROW_STATE);
    }

    @Test
    public void testNonEmptyStringDoesNothingIfTrue() {
        Validations.nonEmpty("abc", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfNull() {
        final String x = null;
        Validations.nonEmpty(x, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfEmpty() {
        Validations.nonEmpty("", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfEmpty2() {
        Validations.nonEmpty("  ", Actions.THROW_STATE);
    }

    @Test
    public void testIsPositiveDoesNothingIfTrue() {
        Validations.isPositive(42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveDoesSomethingIfZero() {
        Validations.isPositive(0, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveDoesSomethingIfNegative() {
        Validations.isPositive(-10, Actions.THROW_STATE);
    }

    @Test
    public void testNonNegativeDoesNothingIfPositive() {
        Validations.nonNegative(42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    public void testNonNegativeDoesNothingIfZero() {
        Validations.nonNegative(0, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNegativeDoesSomethingIfNegative() {
        Validations.nonNegative(-10, Actions.THROW_STATE);
    }
}
