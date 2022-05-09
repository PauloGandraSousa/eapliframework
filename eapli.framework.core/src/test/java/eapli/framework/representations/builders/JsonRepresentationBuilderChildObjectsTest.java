/*
 * Copyright (c) 2013-2022 the original author or authors.
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
package eapli.framework.representations.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class JsonRepresentationBuilderChildObjectsTest {
	private JsonRepresentationBuilder subject;

	@BeforeEach
	void setUp() throws Exception {
		subject = new JsonRepresentationBuilder();
	}

	@Test
	void ensureEmptyChildObject() {
		final String expected = "{\"name\":{}}";
		final String actual = subject.startObject("Test").startObject("name").endObject().build();

		System.out.println("ensureEmptyChildObject");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	void ensureCannotBuildIfThereAreUnfinishedChildObjects() {
		final var builder = subject.startObject("Test").startObject("name");
		assertThrows(IllegalStateException.class, () -> builder.build());
	}

	@Test
	void ensureCannotEndChildObjectWithoutStartingBuilder() {
		assertThrows(IllegalStateException.class, () -> subject.endObject());
	}

	@Test
	void ensureCannotEndChildMoreThanOnce() {
		final var builder = subject.startObject("name").withProperty("i", 1L).endObject();
		assertThrows(IllegalStateException.class, () -> builder.endObject());
	}

	@Test
	void ensureOneChildProperty() {
		final String expected = "{\"name\":{\"i\":1}}";
		final String actual = subject.startObject("Test").startObject("name").withProperty("i", 1L).endObject().build();

		System.out.println("ensureOneChildProperty");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	void ensureOnePropertyAndOneChildProperty() {
		final String expected = "{\"one\":100,\"name\":{\"i\":1}}";
		final String actual = subject.startObject("Test").withProperty("one", BigDecimal.valueOf(100))
				.startObject("name").withProperty("i", 1L).endObject().build();

		System.out.println("ensureOnePropertyAndOneChildProperty");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	void ensureOneChildPropertyAndOneProperty() {
		final String expected = "{\"name\":{\"i\":1},\"one\":100}";
		final String actual = subject.startObject("Test").startObject("name").withProperty("i", 1L).endObject()
				.withProperty("one", BigDecimal.valueOf(100)).build();

		System.out.println("ensureOneChildPropertyAndOneProperty");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	void ensureChildOfAChild() {
		final String expected = "{\"one\":{\"i\":1,\"two\":{\"j\":2}},\"bd\":100}";
		final String actual = subject.startObject("Test").startObject("one").withProperty("i", 1L).startObject("two")
				.withProperty("j", 2).endObject().endObject().withProperty("bd", BigDecimal.valueOf(100)).build();

		System.out.println("ensureChildOfAChild");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}
}
