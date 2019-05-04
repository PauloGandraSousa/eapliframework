package eapli.framework.representations.builders;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class JsonRepresentationBuilderChildObjectsTest {
    private JsonRepresentationBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new JsonRepresentationBuilder();
    }

    @Test
    public void ensureEmptyChildObject() {
        final String expected = "{\"name\":{}}";
        final String actual = subject.startObject("Test").startObject("name").endObject().build();

        System.out.println("ensureEmptyChildObject");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void ensureCannotBuildIfThereAreUnfinishedChildObjects() {
        subject.startObject("Test").startObject("name").build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureCannotEndChildObjectWithoutStartingBuilder() {
        subject.endObject().build();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureCannotEndChildMoreThanOnce() {
        subject.startObject("name").withProperty("i", 1L).endObject().endObject().build();
    }

    @Test
    public void ensureOneChildProperty() {
        final String expected = "{\"name\":{\"i\":1}}";
        final String actual = subject.startObject("Test").startObject("name").withProperty("i", 1L).endObject().build();

        System.out.println("ensureOneChildProperty");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    public void ensureOnePropertyAndOneChildProperty() {
        final String expected = "{\"one\":100,\"name\":{\"i\":1}}";
        final String actual = subject.startObject("Test").withProperty("one", BigDecimal.valueOf(100))
                .startObject("name").withProperty("i", 1L).endObject().build();

        System.out.println("ensureOnePropertyAndOneChildProperty");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    public void ensureOneChildPropertyAndOneProperty() {
        final String expected = "{\"name\":{\"i\":1},\"one\":100}";
        final String actual = subject.startObject("Test").startObject("name").withProperty("i", 1L).endObject()
                .withProperty("one", BigDecimal.valueOf(100)).build();

        System.out.println("ensureOneChildPropertyAndOneProperty");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    public void ensureChildOfAChild() {
        final String expected = "{\"one\":{\"i\":1,\"two\":{\"j\":2}},\"bd\":100}";
        final String actual = subject.startObject("Test").startObject("one").withProperty("i", 1L).startObject("two")
                .withProperty("j", 2).endObject().endObject().withProperty("bd", BigDecimal.valueOf(100)).build();

        System.out.println("ensureChildOfAChild");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }
}
