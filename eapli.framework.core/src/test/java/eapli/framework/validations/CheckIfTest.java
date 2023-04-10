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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 2023.03.24
 */
class CheckIfTest {

    @Test
    void ensureAreEqualLong() {
        final var result = CheckIf.areEqual(42, 42, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualLongFail() {
        final var result = CheckIf.areEqual(42, -1, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObject() {
        final var result = CheckIf.areEqual("abc", "abc", () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFail() {
        final var result = CheckIf.areEqual("abc", "xpto", () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFailOn1Null() {
        final var result = CheckIf.areEqual(null, "xpto", () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFailOn2Null() {
        final var result = CheckIf.areEqual("abc", null, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureMatches() {
        final var result = CheckIf.matches(Pattern.compile("A"), "bcdA234", () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureMatchesFail() {
        final Pattern pat = Pattern.compile("x");
        final var result = CheckIf.matches(pat, "bcdA234", () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureEnsureBoolean() {
        final var result = CheckIf.succeeds(true, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureEnsureBooleanFail() {
        final var result = CheckIf.succeeds(false, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNoneNullObject() {
        final var result = CheckIf.noneNull(() -> true, () -> false, "abc");
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNoneNullObjectFail() {
        final String x = null;
        final var result = CheckIf.noneNull(() -> true, () -> false, x);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNoneNullObjectArray() {
        final var result = CheckIf.noneNull(() -> true, () -> false, "abc", Integer.valueOf(42),
                BigDecimal.ONE);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNoneNullObjectArrayFail() {
        final Integer fortytwo = Integer.valueOf(42);
        final var result = CheckIf.noneNull(() -> true, () -> false, "abc", fortytwo, null, BigDecimal.ONE);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        final var result = CheckIf.nonEmpty(items, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollectionFailOnEmpty() {
        final var items = new ArrayList<>();
        final var result = CheckIf.nonEmpty(items, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollectionFailOnNull() {
        final List<?> items = null;
        final var result = CheckIf.nonEmpty(items, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyString() {
        final var result = CheckIf.nonEmpty("abc", () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty() {
        final var result = CheckIf.nonEmpty("", () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty2() {
        final var result = CheckIf.nonEmpty("   ", () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnNull() {
        final String x = null;
        final var result = CheckIf.nonEmpty(x, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLong() {
        final var result = CheckIf.isPositive(42, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLongFailsOnZero() {
        final var result = CheckIf.isPositive(0, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLongFailsOnNegative() {
        final var result = CheckIf.isPositive(-10, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLong() {
        final var result = CheckIf.nonNegative(42, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLong0() {
        final var result = CheckIf.nonNegative(0, () -> true, () -> false);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLongFail() {
        final var result = CheckIf.nonNegative(-10, () -> true, () -> false);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }
}
