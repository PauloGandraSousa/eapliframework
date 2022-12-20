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
package eapli.framework.quantities.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import eapli.framework.quantities.domain.model.Temperature.TemperatureUnit;

/**
 * @author Paulo Gandra de Sousa 18/11/2022
 *
 */
class TemperatureTest {

	// ----------

	@ParameterizedTest
	@CsvSource({ "0,0,0.0", "0,10,0.0", "0,-10,0.0", "0,0.5,0.0", "0,-0.5,0.0",

			"1,0,0", "1,10,10", "1,0.5,0.5",

			"100,0,0", "100,10,1000", "100,0.5,50",

			"200.5,0,0", "200.5,10,2005", "200.5,0.5,100.25" })
	void ensureScalesKelvin(final double temperature, final double scale, final double expected) {
		final var subject = Temperature.kelvin(temperature);
		final var result = subject.scale(scale);

		assertEquals(0, BigDecimal.valueOf(expected).compareTo(result.value()));
		assertEquals(TemperatureUnit.KELVIN, result.unit());
	}

	@ParameterizedTest
	@CsvSource({ "0,0,0.0", "0,10,0.0", "0,-10,0.0", "0,0.5,0.0", "0,-0.5,0.0",

			"1,0,0", "1,10,10", "1,-10,-10", "1,0.5,0.5", "1,-0.5,-0.5",

			"100,0,0", "100,10,1000", "100,-2,-200", "100,0.5,50", "100,-0.5,-50",

			"200.5,0,0", "200.5,10,2005", "10.5,-2,-21", "200.5,0.5,100.25", "200.5,-0.5,-100.25" })
	void ensureScalesCelsius(final double temperature, final double scale, final double expected) {
		final var subject = Temperature.celsius(temperature);
		final var result = subject.scale(scale);

		assertEquals(0, BigDecimal.valueOf(expected).compareTo(result.value()));
		assertEquals(TemperatureUnit.CELSIUS, result.unit());
	}

	@ParameterizedTest
	@CsvSource({ "0,0,0.0", "0,10,0.0", "0,-10,0.0", "0,0.5,0.0", "0,-0.5,0.0",

			"1,0,0", "1,10,10", "1,-10,-10", "1,0.5,0.5", "1,-0.5,-0.5",

			"100,0,0", "100,10,1000", "100,-2,-200", "100,0.5,50", "100,-0.5,-50",

			"200.5,0,0", "200.5,10,2005", "10.5,-2,-21", "200.5,0.5,100.25", "200.5,-0.5,-100.25" })
	void ensureScalesFarenheit(final double temperature, final double scale, final double expected) {
		final var subject = Temperature.farenheit(temperature);
		final var result = subject.scale(scale);

		assertEquals(0, BigDecimal.valueOf(expected).compareTo(result.value()));
		assertEquals(TemperatureUnit.FARENHEIT, result.unit());
	}

//---------------
	@ParameterizedTest
	@CsvSource({ "0,0", "0.01,1", "1,1", "10,1", "-0.01,-1", "-1,-1", "-10,-1" })
	void ensureSignum(double in, int expected) {
		final var subject = Temperature.celsius(in);

		assertEquals(expected, subject.signum());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10 })
	void ensureIsPositive(double in) {
		final var subject = Temperature.celsius(in);

		assertTrue(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { -0.01, -1, -10 })
	void ensureIsNegative(double in) {
		final var subject = Temperature.celsius(in);

		assertTrue(subject.isNegative());
	}

	@Test
	void ensureIsZero() {
		final var subject = Temperature.celsius(0);

		assertTrue(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.01, 1, 10, -0.01, -1, -10 })
	void ensureNotZero(double in) {
		final var subject = Temperature.celsius(in);

		assertFalse(subject.isZero());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, -0.01, -1, -10 })
	void ensureNotPositive(double in) {
		final var subject = Temperature.celsius(in);

		assertFalse(subject.isPositive());
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0, 0.01, 1, 10 })
	void ensureNotNegative(double in) {
		final var subject = Temperature.celsius(in);

		assertFalse(subject.isNegative());
	}
}
