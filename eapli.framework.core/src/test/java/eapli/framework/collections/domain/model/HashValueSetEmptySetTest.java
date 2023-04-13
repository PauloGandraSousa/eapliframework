/*
 * Copyright (c) 2013-2023 the original author or authors.
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

/**
 * Test of ValueSet for the case of an empty set.
 *
 * @author Paulo Gandra de Sousa 2023.04.12
 *
 */
class HashValueSetEmptySetTest {

	/*
	 * one element - size=1 - is empty = false - contains true/false - add - does
	 * not change size
	 */

	private final HashValueSet<Integer> subject = new HashValueSet<>();

	@Test
	void ensureSizeIs0() {
		assertEquals(0, subject.size());
	}

	@Test
	void ensureIsEmpty() {
		assertDoesNotChangeThisSet();
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
	void ensureDoesNotContain(int v) {
		assertFalse(subject.contains(v));
	}

	@Test
	void ensureUnionWithAnotherSetIsTheOtherSet() {
		final var s = HashValueSet.of(1, 2, 3);
		final var result = subject.union(s);
		assertEquals(s, result);
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureIntersectionWithAnotherSetIsTheEmptySet() {
		final var expected = new HashValueSet<Integer>();

		final var s = HashValueSet.of(1, 2, 3);
		final var result = subject.intersection(s);

		assertEquals(expected, result);
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureFilteringIsAnEmptySet() {
		final var result = subject.filter(i -> true);
		assertTrue(result.isEmpty());
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureMappingIsAnEmptySet() {
		final var result = subject.map(i -> 2 * i);
		assertTrue(result.isEmpty());
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureFoldingReturnsTheInitialValue() {
		final int initial = 1;
		final int result = subject.fold(initial, (a, b) -> a + b);
		assertEquals(initial, result);
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureAddingAnElementReturnsANewSetWithJustThatElement() {
		final var expected = HashValueSet.of(1);

		final var result = subject.add(1);

		assertEquals(expected, result);
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureComplementOfEmptyIsThisSet() {
		final var expected = new HashValueSet<Integer>();

		final var s = new HashValueSet<Integer>();
		final var result = subject.complement(s);

		assertEquals(expected, result);
		assertDoesNotChangeThisSet();
	}

	@Test
	void ensureComplementOfDisjointIsUnion() {
		final var expected = HashValueSet.of(900, 901);

		final var s = HashValueSet.of(900, 901);
		final var result = subject.complement(s);

		assertEquals(expected, result);
		assertDoesNotChangeThisSet();
	}

	@AfterEach
	void assertDoesNotChangeThisSet() {
		assertTrue(subject.isEmpty());
	}
}
