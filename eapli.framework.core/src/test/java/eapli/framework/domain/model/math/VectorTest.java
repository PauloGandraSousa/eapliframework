package eapli.framework.domain.model.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.math.Vector.VectorType;

public class VectorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ensureIsUnit() {
        final double[] values = { 1 / Math.sqrt(2), 1 / Math.sqrt(2) };
        final Vector subject = new Vector(values, VectorType.ROW);
        assertTrue(subject.isUnit());
    }

    @Test
    public void ensureHasRightDimensions() {
        final double[] values = { 1, 2 };
        final Vector subject = new Vector(values, VectorType.ROW);
        assertEquals(values.length, subject.dimensions());
    }

    @Test
    public void testNormalized() {
        final double EPSILON = 0.001;

        final double[] values = { 3, 1, 2 };
        final Vector subject = new Vector(values, VectorType.ROW).normalized();

        final double[] expected = { 0.802, 0.267, 0.534 };
        for (int i = 1; i <= values.length; i++) {
            if (subject.elementAt(i) <= expected[i - 1] - EPSILON
                    || subject.elementAt(i) >= expected[i - 1] + EPSILON) {
                fail();
            }
        }
        assertTrue(true);
    }

    @Test
    public void ensureIsNotUnit() {
        final double[] values = { 1, 2, 3 };
        final Vector subject = new Vector(values, VectorType.ROW);
        assertFalse(subject.isUnit());
    }

    @Test
    public void ensureIsZero() {
        final Vector subject = Vector.zero(3, VectorType.ROW);
        assertTrue(subject.isZero());
    }

    @Test
    public void ensureIsNotZero() {
        final double[] values = { 1, 2, 3 };
        final Vector subject = new Vector(values, VectorType.ROW);
        assertFalse(subject.isZero());
    }
}
