/*
 * Copyright (c) 2013-2024 the original author or authors.
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
package eapli.framework.collections.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import eapli.framework.math.util.NumberGenerator;

/**
 * Test of ValueSet for the case of a set with a single element.
 *
 * @author Paulo Gandra de Sousa 2023.04.13
 */
class HashValueSet1ElementSetTest {

    private final int theValue = NumberGenerator.anInt();
    private final HashValueSet<Integer> subject = (HashValueSet<Integer>) HashValueSet.of(theValue);

    @Test
    void ensureSizeIs1() {
        assertEquals(1, subject.size());
    }

    @Test
    void ensureIsNotEmpty() {
        assertFalse(subject.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 4, 5, 6, 7, 8, 9, 10 })
    void ensureDoesNotContain(final int v) {
        assertFalse(subject.contains(v));
    }

    @Test
    void ensureContains() {
        assertTrue(subject.contains(theValue));
    }

    @Test
    void ensureUnionWithAnotherSetWithDisjointElements() {
        final var expected = HashValueSet.of(theValue, 2, 3);

        final var s = HashValueSet.of(2, 3);
        final var result = subject.union(s);

        assertEquals(expected, result);
    }

    @Test
    void ensureUnionWithEmptySetIsThisSet() {
        final var s = new HashValueSet<Integer>();
        final var result = subject.union(s);
        assertEquals(subject, result);
    }

    @Test
    void ensureIntersectionWithDisjointSetIsTheEmptySet() {
        final var expected = new HashValueSet<Integer>();

        final var s = HashValueSet.of(2, 3);
        final var result = subject.intersection(s);

        assertEquals(expected, result);
    }

    @Test
    void ensureIntersection() {
        final var expected = HashValueSet.of(theValue);

        final var s = HashValueSet.of(theValue, theValue + 2, theValue + 3);
        final var result = subject.intersection(s);

        assertEquals(expected, result);
    }

    @Test
    void ensureFilteringAll() {
        final var result = subject.filter(i -> true);
        assertEquals(subject, result);
    }

    @Test
    void ensureFilteringTheElement() {
        final var expected = new HashValueSet<Integer>();

        final var result = subject.filter(i -> i != theValue);
        assertEquals(expected, result);
    }

    @Test
    void ensureMapping() {
        final var expected = HashValueSet.of(2 * theValue);

        final var result = subject.map(i -> 2 * i);

        assertEquals(expected, result);
    }

    @Test
    void ensureFolding() {
        final int expected = 1 + theValue;

        final int initial = 1;
        final int result = subject.fold(initial, (a, b) -> a + b);

        assertEquals(expected, result);
    }

    @Test
    void ensureAddingAnExistingElement() {
        final var expected = HashValueSet.of(theValue);

        final var result = subject.add(theValue);

        assertEquals(expected, result);
    }

    @Test
    void ensureAddingANonExistingElement() {
        final var expected = HashValueSet.of(theValue, theValue + 999);

        final var result = subject.add(theValue + 999);

        assertEquals(expected, result);
    }

    @Test
    void ensureComplementOfEmptyIsThisSet() {
        final var expected = HashValueSet.of(theValue);

        final var s = new HashValueSet<Integer>();
        final var result = subject.complement(s);

        assertEquals(expected, result);
    }

    @Test
    void ensureComplementOfDisjointIsUnion() {
        final var expected = HashValueSet.of(theValue, theValue + 900, theValue + 901);

        final var s = HashValueSet.of(theValue + 900, theValue + 901);
        final var result = subject.complement(s);

        assertEquals(expected, result);
    }

    @Test
    void ensureComplement() {
        final var expected = HashValueSet.of(theValue + 900, theValue + 901);

        final var s = HashValueSet.of(theValue, theValue + 900, theValue + 901);
        final var result = subject.complement(s);

        assertEquals(expected, result);
    }

    @AfterEach
    void assertDoesNotChangeThisSet() {
        assertEquals(HashValueSet.of(theValue), subject);
    }
}
