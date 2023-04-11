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
package eapli.framework.quantities.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import eapli.framework.money.domain.model.Money;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class MoneySignalTest {
	@Test
	void testNegateAPositive() {
		final var subject = Money.euros(1);
		final var expected = Money.euros(-1);
		assertEquals(expected, subject.negate());
	}

	@Test
	void testNegateANegative() {
		final var subject = Money.euros(-1);
		final var expected = Money.euros(1);
		assertEquals(expected, subject.negate());
	}

	@Test
	void ensureSignumOfPositive() {
		final var subject = Money.euros(150);
		assertEquals(1, subject.signum());
	}

	@Test
	void ensureSignumOfNegative() {
		final var subject = Money.euros(-0.99);
		assertEquals(-1, subject.signum());
	}

	@Test
	void ensureSignumOfZero() {
		final var subject = Money.euros(0);
		assertEquals(0, subject.signum());
	}

	@Test
	void ensureNegativeIsNegative() {
		final var subject = Money.euros(-150);
		assertTrue(subject.isNegative());
	}

	@Test
	void ensurePositiveIsNotNegative() {
		final var subject = Money.euros(150);
		assertFalse(subject.isNegative());
	}

	@Test
	void ensureZeroIsNotNegative() {
		final var subject = Money.euros(0);
		assertFalse(subject.isNegative());
	}

	@Test
	void ensureZeroIsNotPositive() {
		final var subject = Money.euros(0);
		assertFalse(subject.isPositive());
	}

	@Test
	void ensureNegativeIsNotPositive() {
		final var subject = Money.euros(-150);
		assertFalse(subject.isPositive());
	}

	@Test
	void ensurePositiveIsPositive() {
		final var subject = Money.euros(150);
		assertTrue(subject.isPositive());
	}

	@Test
	void ensureZeroIsZero() {
		final var subject = Money.euros(0);
		assertTrue(subject.isZero());
	}

	@Test
	void ensureNegativeIsNotZero() {
		final var subject = Money.euros(-150);
		assertFalse(subject.isZero());
	}

	@Test
	void ensurePositiveIsNotZero() {
		final var subject = Money.euros(150);
		assertFalse(subject.isZero());
	}

	// ---------------
	@ParameterizedTest
	@CsvSource({ "0,0", "0.01,1", "1,1", "10,1", "-0.01,-1", "-1,-1", "-10,-1" })
	void ensureSignum(double in, int expected) {
		final var subject = Money.euros(in);

		assertEquals(expected, subject.signum());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10 })
	void ensureIsPositive(double in) {
		final var subject = Money.euros(in);

		assertTrue(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { -0.01, -1, -10 })
	void ensureIsNegative(double in) {
		final var subject = Money.euros(in);

		assertTrue(subject.isNegative());
	}

	@Test
	void ensureIsZero() {
		final var subject = Money.euros(0);

		assertTrue(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10, -0.01, -1, -10 })
	void ensureNotZero(double in) {
		final var subject = Money.euros(in);

		assertFalse(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, -0.01, -1, -10 })
	void ensureNotPositive(double in) {
		final var subject = Money.euros(in);

		assertFalse(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, 0.01, 1, 10 })
	void ensureNotNegative(double in) {
		final var subject = Money.euros(in);

		assertFalse(subject.isNegative());
	}
}
