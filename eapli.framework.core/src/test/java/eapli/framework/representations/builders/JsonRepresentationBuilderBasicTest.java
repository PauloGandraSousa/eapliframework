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
import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import eapli.framework.general.domain.model.Money;
import eapli.framework.time.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class JsonRepresentationBuilderBasicTest {
	private JsonRepresentationBuilder subject;

	@BeforeEach
	void setUp() throws Exception {
		subject = new JsonRepresentationBuilder();
	}

	@Test
	void ensureCannotAddPropertyBeforeStarting() {
		assertThrows(IllegalStateException.class, () -> subject.withProperty("name", 1));
	}

	@Test
	void ensureCannotBuildWithoutStarting() {
		assertThrows(IllegalStateException.class, () -> subject.build());
	}

	@Test
	void ensureEmpty() {
		final String actual = subject.startObject("Test").endObject().build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureEmptyAutoFinish() {
		final String actual = subject.startObject("Test").build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureStringProperty() {
		final String actual = subject.startObject("Test").withProperty("name", "value").build();
		assertEquals("{\"name\":\"value\"}", actual);
	}

	@Test
	void ensureStringAndMoneyProperty() {
		final String actual = subject.startObject("Test").withProperty("name", "value")
				.withProperty("p", Money.euros(15.0)).build();
		assertEquals("{\"name\":\"value\",\"p\":{\"amount\":15.0,\"currency\":\"EUR\"}}", actual);
	}

	@Test
	void ensureNullStringProperty() {
		final String value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullLongProperty() {
		final Long value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullIntegerProperty() {
		final Integer value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullBigDecimalProperty() {
		final BigDecimal value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullBigIntegrProperty() {
		final BigInteger value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullDoubleProperty() {
		final Double value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullFloatProperty() {
		final Float value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensureNullMoneyProperty() {
		final Money value = null;
		final String actual = subject.startObject("Test").withProperty("name", value).build();
		assertEquals("{}", actual);
	}

	@Test
	void ensurePrimitiveLongProperty() {
		final String actual = subject.startObject("Test").withProperty("name", 1L).build();
		assertEquals("{\"name\":1}", actual);
	}

	@Test
	void ensurePrimitiveIntProperty() {
		final String actual = subject.startObject("Test").withProperty("name", 1).build();
		assertEquals("{\"name\":1}", actual);
	}

	@Test
	void ensureLongProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Long.valueOf(1L)).build();
		assertEquals("{\"name\":1}", actual);
	}

	@Test
	void ensureIntegerProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Integer.valueOf(1)).build();
		assertEquals("{\"name\":1}", actual);
	}

	@Test
	void ensurePrimitiveDoubleProperty() {
		final String actual = subject.startObject("Test").withProperty("name", 1.0D).build();
		assertEquals("{\"name\":1.0}", actual);
	}

	@Test
	void ensurePrimitiveFloatProperty() {
		final String actual = subject.startObject("Test").withProperty("name", 1.0F).build();
		assertEquals("{\"name\":1.0}", actual);
	}

	@Test
	void ensureDoubleProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Double.valueOf(1.0D)).build();
		assertEquals("{\"name\":1.0}", actual);
	}

	@Test
	void ensureFloatProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Float.valueOf(1.0F)).build();
		assertEquals("{\"name\":1.0}", actual);
	}

	@Test
	void ensurePrimitiveBooleanProperty() {
		final String actual = subject.startObject("Test").withProperty("name", true).build();
		assertEquals("{\"name\":true}", actual);
	}

	@Test
	void ensureBooleanProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Boolean.TRUE).build();
		assertEquals("{\"name\":true}", actual);
	}

	@Test
	void ensureMoneyProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Money.euros(10)).build();
		final String expected = "{\"name\":{\"amount\":10.0,\"currency\":\"EUR\"}}";

		System.out.println("ensureMoneyProperty");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	@Disabled("due to differences in time zones with bitbucket pipeline")
	void ensureDateProperty() {
		final String actual = subject.startObject("Test").withProperty("name", Calendars.of(2019, 4, 23)).build();
		final String expected = "{\"name\":\"2019-04-22T23:00Z\"}";

		System.out.println("ensureDateProperty");
		System.out.println("Expected: " + expected);
		System.out.println("Actual:   " + actual);

		assertEquals(expected, actual);
	}

	@Test
	void ensureTwoProperties() {
		final String actual = subject.startObject("Test").withProperty("one", "value").withProperty("two", 2).build();
		assertEquals("{\"one\":\"value\",\"two\":2}", actual);
	}

	@Test
	void ensureTwoPropertiesButOneIsNull() {
		final String value = null;
		final String actual = subject.startObject("Test").withProperty("one", "value").withProperty("name", value)
				.build();
		assertEquals("{\"one\":\"value\"}", actual);
	}
}
