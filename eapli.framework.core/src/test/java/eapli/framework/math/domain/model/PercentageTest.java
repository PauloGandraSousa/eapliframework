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
package eapli.framework.math.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Paulo Gandra de Sousa 06/09/2022
 *
 */
class PercentageTest {

	@ParameterizedTest
	@CsvSource({ "1,1,2", "1,0,1", "99,1,100", "1,0.5,1.5" })
	void ensureIncreasing(final double a, final double b, final double r) {
		final var pa = new Percentage(a);
		final var pb = new Percentage(b);
		final var expected = new Percentage(r);
		final var result = pa.increase(pb);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "1,1,0", "1,0,1", "99,1,98", "1,0.5,0.5" })
	void ensureDecreasing(final double a, final double b, final double r) {
		final var pa = new Percentage(a);
		final var pb = new Percentage(b);
		final var expected = new Percentage(r);
		final var result = pa.decrease(pb);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "1,1,1.01", "1,0,1", "99,1,99.01", "1,0.5,1.005" })
	void ensureIncreaseBasePoints(final double a, final double b, final double r) {
		final var pa = new Percentage(a);
		final var expected = new Percentage(r);
		final var result = pa.increase(b);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "1,1,0.99", "1,0,1", "99,1,98.99", "1,0.5,0.995" })
	void ensureDecreaseBasisPoints(final double a, final double b, final double r) {
		final var pa = new Percentage(a);
		final var expected = new Percentage(r);
		final var result = pa.decrease(b);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "0,100,0", "1,100,1", "10,100,10", "100,100,100", "0.5,100,0.5", "150,100,150", "0,1000,0",
			"1,1000,10", "10,1000,100", "100,1000,1000", "0.5,1000,5", "150,1000,1500", "0,50,0", "1,50,0.5",
			"10,5,0.5", "100,50,50", "0.5,50,0.25", "150,50,75", })
	void ensureOutOf(final double perc, final double value, final double r) {
		final Percentage tax = new Percentage(perc);
		final BigDecimal baseValue = BigDecimal.valueOf(value);
		final BigDecimal expected = BigDecimal.valueOf(r).stripTrailingZeros();
		final BigDecimal result = tax.outOf(baseValue).stripTrailingZeros();
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "0,100,0", "1,2,50", "1,4,25", "2,1,200", "5,5,100", "1,3,33.33333333333333" })
	void ensureOfRatio(final long a, final long t, final double r) {
		final var expected = Percentage.valueOf(BigDecimal.valueOf(r));
		final var result = Percentage.ofRatio(a, t);
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({ "0,0", "0.01,1", "1,1", "10,1", "-0.01,-1", "-1,-1", "-10,-1" })
	void ensureSignum(double in, int expected) {
		final var subject = new Percentage(in);

		assertEquals(expected, subject.signum());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10 })
	void ensureIsPositive(double in) {
		final var subject = new Percentage(in);

		assertTrue(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { -0.01, -1, -10 })
	void ensureIsNegative(double in) {
		final var subject = new Percentage(in);

		assertTrue(subject.isNegative());
	}

	@Test
	void ensureIsZero() {
		final var subject = new Percentage(0);

		assertTrue(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10, -0.01, -1, -10 })
	void ensureNotZero(double in) {
		final var subject = new Percentage(in);

		assertFalse(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, -0.01, -1, -10 })
	void ensureNotPositive(double in) {
		final var subject = new Percentage(in);

		assertFalse(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, 0.01, 1, 10 })
	void ensureNotNegative(double in) {
		final var subject = new Percentage(in);

		assertFalse(subject.isNegative());
	}
}
