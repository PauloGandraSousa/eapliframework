package eapli.framework.domain.identities.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PrefixedNumberSequenceGeneratorTest {

    private static final String PREFIX = "ABC";
    private static final byte LENGTH = 8;
    private PrefixedNumberSequenceGenerator instance;

    @Before
    public void setUp() throws Exception {
        instance = new PrefixedNumberSequenceGenerator(PREFIX, LENGTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePrefixIsNotNull() {
        instance = new PrefixedNumberSequenceGenerator(null, LENGTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePrefixIsNotEmpty() {
        instance = new PrefixedNumberSequenceGenerator("", LENGTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureLengthIsBiggerThanZero() {
        instance = new PrefixedNumberSequenceGenerator(PREFIX, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureLengthIsNotNegative() {
        instance = new PrefixedNumberSequenceGenerator(PREFIX, -1);
    }

    @Test
    public void ensureGeneratedIdLength() {
        final String result = instance.newId();
        final int expected = PREFIX.length() + LENGTH;
        assertEquals(expected, result.length());
    }

    @Test
    public void ensureGeneratedIdPrefix() {
        final String result = instance.newId();
        assertEquals(PREFIX, result.substring(0, PREFIX.length()));
    }

    @Test
    public void ensureGeneratedIdHasPaddingZeros() {
        final String result = instance.newId();
        assertEquals(PREFIX + "00000001", result);
    }
}
