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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Currency;

import org.junit.jupiter.api.Test;

import eapli.framework.money.domain.model.Money;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class MoneyTest {

	@Test
	void ensureDollarsHasRightCurrency() {
		final var subject = Money.dollars(1);
		assertEquals(Currency.getInstance("USD"), subject.currency());
	}

	@Test
	void ensureEurosHasRightCurrency() {
		final var subject = Money.euros(1);
		assertEquals(Currency.getInstance("EUR"), subject.currency());
	}

	@Test
	void ensureDollarsHasRightAmount() {
		final var subject = Money.dollars(1);
		assertEquals(1, subject.amountAsDouble(), 0.01);
	}

	@Test
	void ensureEurosHasRightAmount() {
		final var subject = Money.euros(1);
		assertEquals(1, subject.amountAsDouble(), 0.01);
	}

	@Test
	void ensureEqualsObject() {
		final var subject = Money.euros(1);
		final var other = Money.euros(1);
		assertEquals(subject, other);
	}

	@Test
	void ensureNotEqualIfDifferentCurrency() {
		final var subject = Money.euros(1);
		final var other = Money.dollars(2);
		assertNotEquals(subject, other);
	}

	@Test
	void ensureNotEqualIfDifferentAmount() {
		final var subject = Money.euros(1);
		final var other = Money.euros(2);
		assertNotEquals(subject, other);
	}

	@Test
	void ensureEurosAreOfSameCurrency() {
		final var subject = Money.euros(1);
		final var other = Money.euros(2);
		assertTrue(subject.isOfSameCurrency(other));
	}

	@Test
	void ensureEurosAndDollarsAreNotOfSameCurrency() {
		final var subject = Money.euros(1);
		final var other = Money.dollars(1);
		assertFalse(subject.isOfSameCurrency(other));
	}
}
