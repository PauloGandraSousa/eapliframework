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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import eapli.framework.actions.Actions;

/**
 * @author Paulo Gandra de Sousa
 */
class ValidationsEnsureTest {

    @Test
    void testEnsureAreEqualLongDoesNothingIfTrue() {
        Validations.ensureAreEqual(42, 42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureAreEqualLongFail() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureAreEqual(42, 24, Actions.THROW_STATE));
    }

    @Test
    void testEnsureAreEqualObjectDoesNothingIfTrue() {
        Validations.ensureAreEqual("abc", "abc", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureAreEqualObjectFail() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureAreEqual("abc", "xpto", Actions.THROW_STATE));
    }

    @Test
    void testEnsureAreEqualObjectFailOn1Null() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureAreEqual(null, "xpto", Actions.THROW_STATE));
    }

    @Test
    void testEnsureAreEqualObjectFailOn2Null() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureAreEqual("abc", null, Actions.THROW_STATE));
    }

    @Test
    void testEnsureMatchesDoNothingIfTrue() {
        Validations.ensureMatches(Pattern.compile("A"), "bcdA234", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureMatchesDoesSomethingIfFalse() {
        final Pattern compile = Pattern.compile("x");
        assertThrows(IllegalStateException.class,
                () -> Validations.ensureMatches(compile, "bcdA234", Actions.THROW_STATE));
    }

    @Test
    void testEnsureEnsureDoesNothingIfTrue() {
        Validations.ensure(Boolean.TRUE, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureDoesSomethingIfFalse() {
        assertThrows(IllegalStateException.class, () -> Validations.ensure(Boolean.FALSE, Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonNullDoesNothingIfTrue() {
        Validations.ensureNoneNull(Actions.THROW_STATE, "abc");
        assertTrue(true);
    }

    @Test
    void testEnsureNonNullDoesNothingIfTrueForMoreThanOneObject() {
        Validations.ensureNoneNull(Actions.THROW_STATE, new String(), Double.valueOf(1), "abc");
        assertTrue(true);
    }

    @Test
    void testEnsureNonNullDoesSomethingIfFalse() {
        final Object x = null;
        assertThrows(IllegalStateException.class, () -> Validations.ensureNoneNull(Actions.THROW_STATE, x));
    }

    @Test
    void testEnsureNonNullDoesSomethingIfFalseForAtLeastOneObject() {
        final Object x = null;
        final var y = Integer.valueOf(1);
        assertThrows(IllegalStateException.class,
                () -> Validations.ensureNoneNull(Actions.THROW_STATE, "abc", x, y));
    }

    @Test
    void testEnsureNonEmptyCollectionDoesNothingIfTrue() {
        final Collection<String> col = new ArrayList<>();
        col.add("abc");
        Validations.ensureNonEmpty(col, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureNonEmptyCollectionDoesSomethingIfNull() {
        final Collection<String> col = null;
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonEmpty(col, Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonEmptyCollectionDoesSomethingIfEmpty() {
        final Collection<String> col = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonEmpty(col, Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonEmptyStringDoesNothingIfTrue() {
        Validations.ensureNonEmpty("abc", Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureNonEmptyStringDoesSomethingIfNull() {
        final String x = null;
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonEmpty(x, Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonEmptyStringDoesSomethingIfEmpty() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonEmpty("", Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonEmptyStringDoesSomethingIfEmpty2() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonEmpty("  ", Actions.THROW_STATE));
    }

    @Test
    void testEnsureIsPositiveDoesNothingIfTrue() {
        Validations.ensureIsPositive(42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureIsPositiveDoesSomethingIfZero() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureIsPositive(0, Actions.THROW_STATE));
    }

    @Test
    void testEnsureIsPositiveDoesSomethingIfNegative() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureIsPositive(-10, Actions.THROW_STATE));
    }

    @Test
    void testEnsureNonNegativeDoesNothingIfPositive() {
        Validations.ensureNonNegative(42, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureNonNegativeDoesNothingIfZero() {
        Validations.ensureNonNegative(0, Actions.THROW_STATE);
        assertTrue(true);
    }

    @Test
    void testEnsureNonNegativeDoesSomethingIfNegative() {
        assertThrows(IllegalStateException.class, () -> Validations.ensureNonNegative(-10, Actions.THROW_STATE));
    }
}
