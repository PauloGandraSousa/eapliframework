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
 * Test of ValueSet for the case of a set with several elements.
 *
 * @author Paulo Gandra de Sousa 2023.04.13
 *
 */
class HashValueSetNElementSetTest {

	private final HashValueSet<Integer> subject = (HashValueSet<Integer>) HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9,
			10);

	@Test
	void ensureSizeIs10() {
		assertEquals(10, subject.size());
	}

	@Test
	void ensureIsNotEmpty() {
		assertFalse(subject.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(ints = { 900, 901, 902, 903, 904, 905, 906, 907, 908, 909 })
	void ensureDoesNotContain(int v) {
		assertFalse(subject.contains(v));
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
	void ensureContains(int v) {
		assertTrue(subject.contains(v));
	}

	@Test
	void ensureUnionWithAnotherSetWithDisjointElements() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 902, 903);

		final var s = HashValueSet.of(902, 903);
		final var result = subject.union(s);

		assertEquals(expected, result);
	}

	@Test
	void ensureUnionWithAnotherSetWithSomeCommonElements() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 902, 903);

		final var s = HashValueSet.of(6, 7, 8, 9, 10, 902, 903);
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

		final var s = HashValueSet.of(902, 903);
		final var result = subject.intersection(s);

		assertEquals(expected, result);
	}

	@Test
	void ensureIntersection() {
		final var expected = HashValueSet.of(1, 2, 3);

		final var s = HashValueSet.of(1, 2, 3, 902, 903);
		final var result = subject.intersection(s);

		assertEquals(expected, result);
	}

	@Test
	void ensureFilteringAll() {
		final var result = subject.filter(i -> true);
		assertEquals(subject, result);
	}

	@Test
	void ensureFilteringOdd() {
		final var expected = HashValueSet.of(1, 3, 5, 7, 9);

		final var result = subject.filter(i -> i % 2 != 0);
		assertEquals(expected, result);
	}

	@Test
	void ensureFilteringEven() {
		final var expected = HashValueSet.of(2, 4, 6, 8, 10);

		final var result = subject.filter(i -> i % 2 == 0);
		assertEquals(expected, result);
	}

	@Test
	void ensureMapping() {
		final var expected = HashValueSet.of(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);

		final var result = subject.map(i -> 2 * i);

		assertEquals(expected, result);
	}

	@Test
	void ensureFolding() {
		final int expected = 56;

		final int initial = 1;
		final int result = subject.fold(initial, (a, b) -> a + b);

		assertEquals(expected, result);
	}

	@Test
	void ensureAddingAnExistingElement() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		final var result = subject.add(1);

		assertEquals(expected, result);
	}

	@Test
	void ensureAddingANonExistingElement() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 999);

		final var result = subject.add(999);

		assertEquals(expected, result);
	}

	@Test
	void ensureComplementOfEmptyIsThisSet() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		final var s = new HashValueSet<Integer>();
		final var result = subject.complement(s);

		assertEquals(expected, result);
	}

	@Test
	void ensureComplementOfDisjointIsUnion() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 900, 901);

		final var s = HashValueSet.of(900, 901);
		final var result = subject.complement(s);

		assertEquals(expected, result);
	}

	@Test
	void ensureComplement() {
		final var expected = HashValueSet.of(1, 2, 3, 4, 5, 6, 10, 900, 901);

		final var s = HashValueSet.of(7, 8, 9, 900, 901);
		final var result = subject.complement(s);

		assertEquals(expected, result);
	}

	@AfterEach
	void assertDoesNotChangeThisSet() {
		assertEquals(HashValueSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), subject);
	}
}
