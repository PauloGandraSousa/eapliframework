package eapli.framework.memento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SampleAdderTest {

    private final SampleAdder instance = new SampleAdder();

    @Test
    public void testSnapshot() {
        assertNotNull(instance.snapshot());
    }

    @Test
    public void testAdd10() {
        final int expected = 10;
        instance.add(10);
        assertEquals(expected, instance.current());
    }

    @Test
    public void testRestoreTo() {
        final int expected = instance.current();
        final Memento m = instance.snapshot();
        instance.add(10);
        instance.restoreTo(m);
        assertEquals(expected, instance.current());
    }
}
