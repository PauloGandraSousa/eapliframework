/*
 * Copyright (c) 2013-2022 the original author or authors.
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
 * @author Paulo Gandra de Sousa
 */
class JsonRepresentationBuilderCollectionTest {
    private JsonRepresentationBuilder subject;

    @BeforeEach
    void setUp() throws Exception {
        subject = new JsonRepresentationBuilder();
    }

    @Test
    void ensureCannotBuildUnfinishedCollection() {
        subject.startObject("Test").startCollection("name");

        assertThrows(IllegalStateException.class, () -> subject.build());
    }

    @Test
    void ensureCannotBuildUnfinishedCollection2() {
        subject.startObject("Test").startCollection("one").endCollection().startCollection("two");
        assertThrows(IllegalStateException.class, () -> subject.build());
    }

    @Test
    void ensureCannotEndCollectionWithoutStartingCollection() {
        subject.startObject("Test");
        assertThrows(IllegalStateException.class, () -> subject.endCollection());
    }

    @Test
    void ensureCannotAddElementWithoutStartingCollection() {
        subject.startObject("Test");
        assertThrows(IllegalStateException.class, () -> subject.withElement(1));
    }

    @Test
    void ensureCannotAddElementAfterEndingCollection() {
        subject.startObject("Test").startCollection("name").endCollection();

        assertThrows(IllegalStateException.class, () -> subject.withElement(1));
    }

    @Test
    void ensureCannotAddPropertyToColection() {
        subject.startObject("Test").startCollection("name");
        assertThrows(IllegalStateException.class, () -> subject.withProperty("i", 1));
    }

    @Test
    void ensureCannotEndCollectionWithoutStartingBuilder() {
        assertThrows(IllegalStateException.class, () -> subject.endCollection());
    }

    @Test
    void ensureCannotEndCollectionMoreThanOnce() {
        subject.startObject("Test").startCollection("name")
                .withElement(1).endCollection();
        assertThrows(IllegalStateException.class, () -> subject.endCollection());
    }

    @Test
    void ensureEmptyArray() {
        final String actual = subject.startObject("Test").startCollection("name").endCollection().build();
        assertEquals("{\"name\":[]}", actual);
    }

    @Test
    void ensureArrayWithNullLongElement() {
        final Long value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWithNullIntegerElement() {
        final Integer value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWithNullBooleanElement() {
        final Boolean value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWithNullFloatElement() {
        final Float value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWithNullDoubleElement() {
        final Double value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWithNullStringElement() {
        final String value = null;
        final String actual = subject.startObject("Test").startCollection("name").withElement(value).endCollection()
                .build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    void ensureArrayWith1PrimitiveIntElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(1).endCollection()
                .build();
        assertEquals("{\"name\":[1]}", actual);
    }

    @Test
    void ensureArrayWith1PrimitiveLongElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(1L).endCollection()
                .build();
        assertEquals("{\"name\":[1]}", actual);
    }

    @Test
    void ensureArrayWith1PrimitiveFloatElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(1.0F).endCollection()
                .build();
        assertEquals("{\"name\":[1.0]}", actual);
    }

    @Test
    void ensureArrayWith1PrimitiveDoubleElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(1.0D).endCollection()
                .build();
        assertEquals("{\"name\":[1.0]}", actual);
    }

    @Test
    void ensureArrayWith1PrimitiveBooleanElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(false).endCollection()
                .build();
        assertEquals("{\"name\":[false]}", actual);
    }

    @Test
    void ensureArrayWith2PrimitiveIntElements() {
        final String actual = subject.startObject("Test").startCollection("name").withElement(1).withElement(2)
                .endCollection().build();
        assertEquals("{\"name\":[1,2]}", actual);
    }

    @Test
    void ensureArrayWith1StringElement() {
        final String actual = subject.startObject("Test").startCollection("name").withElement("1").endCollection()
                .build();
        assertEquals("{\"name\":[\"1\"]}", actual);
    }

    @Test
    void ensureArrayWith2StringElements() {
        final String actual = subject.startObject("Test").startCollection("name").withElement("1").withElement("2")
                .endCollection().build();
        assertEquals("{\"name\":[\"1\",\"2\"]}", actual);
    }

    @Test
    void ensureArrayProperty() {
        final String actual = subject.startObject("Test").withProperty("one", 1).startCollection("name")
                .withElement("1").withElement("2").endCollection().withProperty("two", false).build();
        assertEquals("{\"one\":1,\"name\":[\"1\",\"2\"],\"two\":false}", actual);
    }

    @Test
    void ensureArrayWith1StringAnd1NativeElements() {
        final String actual = subject.startObject("Test").startCollection("name").withElement("1").withElement(2)
                .endCollection().build();
        assertEquals("{\"name\":[\"1\",2]}", actual);
    }

    @Test
    void ensureArrayWithLotsOfElements() {
        final String expected = "{\"name\":[1,15,true,2.0,4.0,2,4,{\"amount\":50.0,\"currency\":\"EUR\"},\"abc\"]}";
        final String actual = subject.startObject("Test").startCollection("name").withElement(BigDecimal.ONE)
                .withElement(BigInteger.valueOf(15)).withElement(true).withElement(2.0D).withElement(4.0F)
                .withElement(2).withElement(4L).withElement(Money.euros(50)).withElement("abc").endCollection().build();

        System.out.println("ensureArrayWithLotsOfElements");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    @Disabled("due to differences in time zones with bitbucket pipeline")
    void ensureArrayWithDateElement() {
        final String expected = "{\"name\":[\"2019-04-22T23:00Z\"]}";
        final String actual = subject.startObject("Test").startCollection("name").withElement(Calendars.of(2019, 4, 23))
                .endCollection().build();

        System.out.println("ensureArrayWithDateElement");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }
}
