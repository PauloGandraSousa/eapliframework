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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 2023.03.27
 */
class CheckTest {

    @Test
    void ensureAreEqualLong() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual(42, 42, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualLongFail() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual(42, -1, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObject() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual("abc", "abc", () -> false)
                .elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFail() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual("abc", "xpto", () -> false)
                .elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFailOn1Null() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual(null, "abc", () -> false)
                .elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureAreEqualObjectFailOn2Null() {
        final var result = new Check<Boolean, Boolean>().failIf().notEqual("abc", null, () -> false)
                .elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureMatches() {
        final var result = new Check<Boolean, Boolean>().failIf()
                .notMatches(Pattern.compile("A"), "bcdA234", () -> false)
                .elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureMatchesFail() {
        final var result = new Check<Boolean, Boolean>().failIf()
                .notMatches(Pattern.compile("X"), "bcdA234", () -> false)
                .elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIfNot() {
        final boolean test = true;
        final var result = new Check<Boolean, Boolean>().failIf().not(test, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIfNotFail() {
        final boolean test = false;
        final var result = new Check<Boolean, Boolean>().failIf().not(test, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIfNotSupplier() {
        final BooleanSupplier test = () -> true;
        final var result = new Check<Boolean, Boolean>().failIf().not(test, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIfNotSupplierFail() {
        final BooleanSupplier test = () -> false;
        final var result = new Check<Boolean, Boolean>().failIf().not(test, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureSomeNullObject() {
        final var result = new Check<Boolean, Boolean>().failIf().someNull(() -> false, "abc").elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureSomeNullObjectFail() {
        final String x = null;
        final var result = new Check<Boolean, Boolean>().failIf().someNull(() -> false, x).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureSomeNullObjectArray() {
        final var result = new Check<Boolean, Boolean>().failIf().someNull(() -> false, "abc", Integer.valueOf(42),
                BigDecimal.ONE).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureSomeNullObjectArrayFail() {
        final var result = new Check<Boolean, Boolean>().failIf().someNull(() -> false, "abc", Integer.valueOf(42),
                null, BigDecimal.ONE).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty(items, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollectionFailOnEmpty() {
        final var items = new ArrayList<>();
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty(items, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyCollectionFailOnNull() {
        final List<?> items = null;
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty(items, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyString() {
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty("abc", () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty() {
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty("", () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnEmpty2() {
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty("   ", () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonEmptyStringFailOnNull() {
        final String x = null;
        final var result = new Check<Boolean, Boolean>().failIf().isEmpty(x, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLong() {
        final var result = new Check<Boolean, Boolean>().failIf().notPositive(42, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLongFailsOnZero() {
        final var result = new Check<Boolean, Boolean>().failIf().notPositive(0, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureIsPositiveLongFailsOnNegative() {
        final var result = new Check<Boolean, Boolean>().failIf().notPositive(-10, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLong() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(42, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLong0() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(0, () -> false).elseSucceed(() -> true);
        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureNonNegativeLongFail() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(-10, () -> false).elseSucceed(() -> true);
        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureTryWithoutFailThrows() {
        final Supplier<Boolean> succ = () -> true;
        final Check<Boolean, Boolean> subject = new Check<>();
        assertThrows(IllegalStateException.class, () -> subject.elseSucceed(succ));
    }

    @Test
    void ensureCompositionFail1() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(-10, () -> false)
                .notPositive(10, () -> false).isEmpty("abc", () -> false).elseSucceed(() -> true);

        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureCompositionFail2() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(10, () -> false)
                .notPositive(-10, () -> false).isEmpty("abc", () -> false).elseSucceed(() -> true);

        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureCompositionFail3() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(10, () -> false)
                .notPositive(10, () -> false).isEmpty(" ", () -> false).elseSucceed(() -> true);

        assertFalse(result.left().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureCompositionSucceeds() {
        final var result = new Check<Boolean, Boolean>().failIf().isNegative(10, () -> false)
                .notPositive(10, () -> false).isEmpty("abc", () -> false).elseSucceed(() -> true);

        assertTrue(result.right().orElseThrow(IllegalStateException::new));
    }
}
