package eapli.framework.domain.model.identities.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.identities.impl.NumberSequenceGenerator;

public class NumberSequenceGeneratorTest {

    private static final long SEED = 20L;
    private NumberSequenceGenerator instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        instance = new NumberSequenceGenerator();
    }

    @Test
    public void ensureIdAfterSeed() {
        instance = new NumberSequenceGenerator(SEED);
        assertTrue(instance.newId() >= SEED);
    }

    @Test
    public void ensure100IdsAreDifferentAndSequential() {
        final long[] ids = new long[100];

        for (int i = 0; i < ids.length; i++) {
            ids[i] = instance.newId();
        }

        for (int j = 1; j < ids.length; j++) {
            assertTrue(ids[j] > ids[j - 1]);
        }
    }
}
