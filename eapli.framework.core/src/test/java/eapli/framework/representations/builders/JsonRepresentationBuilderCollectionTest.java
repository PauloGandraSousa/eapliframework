package eapli.framework.representations.builders;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import eapli.framework.domain.model.general.Money;
import eapli.framework.util.DateTime;

public class JsonRepresentationBuilderCollectionTest {
    private JsonRepresentationBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new JsonRepresentationBuilder();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureStartingIsMandatoryBeforeAddingCollection() {
        subject.startCollection("name").endCollection().build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureUnfinishedCollectionIsInvalid() {
        subject.of("Test").startCollection("name").build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureEndingCollectionWithoutStartingCollectionIsInvalid() {
        subject.of("Test").endCollection().build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureAddElementWithoutStartingCollectionIsInvalid() {
        subject.of("Test").withElement(1).build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureEndingCollectionWithoutStartingBuilderIsInvalid() {
        subject.endCollection().build();
    }

    @Test
    public void ensureEmptyArray() {
        final String actual = subject.of("Test").startCollection("name").endCollection().build();
        assertEquals("{\"name\":[]}", actual);
    }

    @Test
    public void ensureArrayWithNullStringElement() {
        final String value = null;
        final String actual = subject.of("Test").startCollection("name").withElement(value).endCollection().build();
        assertEquals("{\"name\":[null]}", actual);
    }

    @Test
    public void ensureArrayWith1PrimitiveIntElement() {
        final String actual = subject.of("Test").startCollection("name").withElement(1).endCollection().build();
        assertEquals("{\"name\":[1]}", actual);
    }

    @Test
    public void ensureArrayWith2PrimitiveIntElements() {
        final String actual = subject.of("Test").startCollection("name").withElement(1).withElement(2).endCollection()
                .build();
        assertEquals("{\"name\":[1,2]}", actual);
    }

    @Test
    public void ensureArrayWith1StringElement() {
        final String actual = subject.of("Test").startCollection("name").withElement("1").endCollection().build();
        assertEquals("{\"name\":[\"1\"]}", actual);
    }

    @Test
    public void ensureArrayWith2StringElements() {
        final String actual = subject.of("Test").startCollection("name").withElement("1").withElement("2")
                .endCollection().build();
        assertEquals("{\"name\":[\"1\",\"2\"]}", actual);
    }

    @Test
    public void ensureArrayProperty() {
        final String actual = subject.of("Test").withProperty("one", 1).startCollection("name").withElement("1")
                .withElement("2").endCollection().withProperty("two", false).build();
        assertEquals("{\"one\":1,\"name\":[\"1\",\"2\"],\"two\":false}", actual);
    }

    @Test
    public void ensureArrayWith1StringAnd1NativeElements() {
        final String actual = subject.of("Test").startCollection("name").withElement("1").withElement(2).endCollection()
                .build();
        assertEquals("{\"name\":[\"1\",2]}", actual);
    }

    @Test
    public void ensureArrayWithLotsOfElements() {
        final String expected = "{\"name\":[1,15,true,2.0,4.0,2,4,{\"amount\":50.0,\"currency\":\"EUR\"},\"abc\"]}";
        final String actual = subject.of("Test").startCollection("name").withElement(BigDecimal.ONE)
                .withElement(BigInteger.valueOf(15)).withElement(true).withElement(2.0D).withElement(4.0F)
                .withElement(2).withElement(4L).withElement(Money.euros(50)).withElement("abc").endCollection().build();

        System.out.println("ensureArrayWithLotsOfElements");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    @Ignore // due to differences in time zones with bitbucket pipeline
    public void ensureArrayWithDateElement() {
        final String expected = "{\"name\":[\"2019-04-22T23:00Z\"]}";
        final String actual = subject.of("Test").startCollection("name").withElement(DateTime.newCalendar(2019, 4, 23))
                .endCollection().build();

        System.out.println("ensureArrayWithDateElement");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }
}
