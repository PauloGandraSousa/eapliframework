package eapli.framework.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SampleAdderTest {

    private final SampleAdder instance = new SampleAdder();

    @Test
    public void testAdd10() {
        final int expected = 10;
        instance.add(10);
        assertEquals(expected, instance.current());
    }

    @Test
    public void testSubtract10() {
        final int expected = -10;
        instance.subtract(10);
        assertEquals(expected, instance.current());
    }
}
