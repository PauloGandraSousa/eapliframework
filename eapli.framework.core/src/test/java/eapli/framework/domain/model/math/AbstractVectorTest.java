/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.domain.model.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.math.Vector.VectorType;

/**
 *
 * @author sou03408
 *
 */
public abstract class AbstractVectorTest {

    private static final Vector ONE = new Vector(new double[] { 1, 1, 1 }, VectorType.ROW);
    private static final Vector ZERO = new Vector(new double[] { 0, 0, 0 }, VectorType.ROW);

    protected Vector instance;

    @Before
    public void setUp() throws Exception {
        instance = new Vector(expectedElements(), VectorType.ROW);
    }

    @Test
    public void ensureMagnitude() {
        assertEquals(expectedMagnitude(), instance.magnitude(), 0.01);
    }

    @Test
    public void ensureNormalized() {
        assertEquals(expectedNormalized(), instance.normalized());
    }

    protected abstract Vector expectedNormalized();

    protected abstract double expectedMagnitude();

    @Test
    public void ensureElements() {
        final double[] expected = expectedElements();
        checkEqual(expected);
    }

    public void checkEqual(final double[] expected) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], instance.elementAt(i + 1), 0.01);
        }
    }

    protected abstract double[] expectedElements();

    @Test
    public void ensureDimensions() {
        final double[] expected = expectedElements();
        assertEquals(expected.length, instance.dimensions());
    }

    @Test
    public void ensureScale2() {
        final double[] expected = expectedScale2();
        instance = instance.scale(2);
        checkEqual(expected);
    }

    protected abstract double[] expectedScale2();

    @Test
    public void ensureSubtractOne() {
        final double[] expected = expectedSubtract1();
        instance = instance.subtract(ONE);
        checkEqual(expected);
    }

    protected abstract double[] expectedSubtract1();

    @Test
    public void ensureAddOne() {
        final double[] expected = expectedAdd1();
        instance = instance.add(ONE);
        checkEqual(expected);
    }

    protected abstract double[] expectedAdd1();

    @Test
    public void ensureDotProduct1() {
        final double expected = expectedDotProduct1();
        assertEquals(expected, instance.dotProduct(ONE), 0.01);
    }

    protected abstract double expectedDotProduct1();

    @Test
    public void ensureAdd0() {
        assertEquals(instance, instance.add(ZERO));
    }

    @Test
    public void ensureMultiply1() {
        assertEquals(instance, instance.crossProduct(ONE));
    }

    @Test
    public void ensureMultiply0() {
        assertEquals(ZERO, instance.crossProduct(ZERO));
    }

    @Test
    public void ensureDotProduct0() {
        assertEquals(0.0, instance.dotProduct(ZERO), 0.01);
    }
}
