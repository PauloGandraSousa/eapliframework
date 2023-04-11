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
package eapli.framework.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.UnaryOperator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author Paulo Gandra de Sousa 2023.03.30
 */
class FunctionsTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 910 })
	void ensureConstantInt(final int x) {
		final var subject = Functions.constant(3);
		assertEquals(3, subject.apply(x));
	}

	@ParameterizedTest
	@ValueSource(strings = { "1", "2", "3", "4", "5", "6", "7", "8", "910" })
	void ensureConstantString(final String x) {
		final var subject = Functions.constant("A");
		assertEquals("A", subject.apply(x));
	}

	@ParameterizedTest
	@CsvSource({ "1, 3", "2, 4", "3, 5", "4, 6", "5, 7", "6, 8", "7, 9", "8, 10", "910, 912" })
	void ensureFFX(final long x, final long expected) {
		final var subject = Functions.ffx(eapli.framework.math.util.Math::add1);
		assertEquals(expected, subject.apply(x));
	}

	@ParameterizedTest
	@CsvSource({ "1, 3", "2, 5", "3, 7", "4, 9", "5, 11", "6, 13", "7, 15", "8, 17", "10, 21" })
	void ensureComposition(final long x, final long expected) {
		final UnaryOperator<Long> add1 = eapli.framework.math.util.Math::add1;
		final UnaryOperator<Long> twice = eapli.framework.math.util.Math::twice;
		final var subject = Functions.composition(add1, twice);
		assertEquals(expected, subject.apply(x));
	}
}
