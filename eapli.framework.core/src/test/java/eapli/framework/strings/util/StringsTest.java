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
package eapli.framework.strings.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Paulo Gandra Sousa 2024.05.01
 *
 */
class StringsTest {

	@ParameterizedTest
	@CsvSource({ "test,\"test\"", // no quotes -> add quotes
			"\"test,\"\"test\"", // one quote at beginning -> treat as unquoted
			"test\",\"test\"\"", // one quote at end -> treat as unquoted
			"\"test\",\"test\"", // quoted -> leave it as it is
	})
	void ensureQuote(final String input, final String expected) {
		final var actual = Strings.quote(input);
		assertEquals(actual, expected);
	}

	@ParameterizedTest
	@CsvSource({ "test,test", // no quotes -> leave it as it is
			"\"test,\"test", // one quote at beginning -> treat as unquoted
			"test\",test\"", // one quote at end -> treat as unquoted
			"\"test\",test", // quoted -> remove quotes
	})
	void ensureUnquote(final String input, final String expected) {
		final var actual = Strings.unquote(input);
		assertEquals(actual, expected);
	}
}
