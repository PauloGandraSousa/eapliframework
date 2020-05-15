/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.math.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import eapli.framework.math.domain.model.Vector;
import eapli.framework.math.domain.model.Vector.VectorType;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class VectorTest {

    @Test
    public void testIsUnit() {
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
    public void testIsNotUnit() {
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
    public void testIsNotZero() {
        final double[] values = { 1, 2, 3 };
        final Vector subject = new Vector(values, VectorType.ROW);
        assertFalse(subject.isZero());
    }
}
