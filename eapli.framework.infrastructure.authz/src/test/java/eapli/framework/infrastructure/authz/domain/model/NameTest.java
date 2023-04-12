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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Paulo Gandra de Sousa 11/05/2022
 *
 */
class NameTest {

	@Test
	void ensureFirstNameIsNotNull() {
		assertThrows(IllegalArgumentException.class, () -> new Name(null, "Smith"));
	}

	@Test
	void ensureFirstNameIsNotEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new Name("", "Smith"));
	}

	@Test
	void ensureLastNameIsNotNull() {
		assertThrows(IllegalArgumentException.class, () -> new Name("Mary", null));
	}

	@Test
	void ensureLastNameIsNotEmpty() {
		assertThrows(IllegalArgumentException.class, () -> new Name("Mary", ""));
	}

	@Test
	void ensureFirstNameDoesNotContainIllegalCharacters() {
		assertThrows(IllegalArgumentException.class, () -> new Name("Ma$ry", "Smith"));
	}

	@Test
	void ensureLastNameDoesNotContainIllegalCharacters() {
		assertThrows(IllegalArgumentException.class, () -> new Name("Mary", "Smit_"));
	}

	@ParameterizedTest
	@CsvSource({ "Alicia, Keys", "António, Mc'Rib", "Eça, de Queróis", "Camilo, Castelo-Branco" })
	void ensureRightNames(String first, String last) {
		final var result = new Name(first, last);
		assertNotNull(result);
	}
}
