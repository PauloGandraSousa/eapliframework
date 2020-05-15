/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.math.domain.model;

import eapli.framework.math.domain.model.Vector;
import eapli.framework.math.domain.model.Vector.VectorType;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class V123Test extends AbstractVectorTest {

    private final double[] elems = new double[] { 1, 2, 3 };

    @Override
    protected Vector expectedNormalized() {
        final double[] norm = { 1 / Math.sqrt(14), Math.sqrt(2 / 7), 3 / Math.sqrt(14) };
        return new Vector(norm, VectorType.ROW);
    }

    @Override
    protected double expectedMagnitude() {
        return 3.74166;
    }

    @Override
    protected double[] expectedElements() {
        return elems;
    }

    @Override
    protected double[] expectedScale2() {
        return new double[] { 2, 4, 6 };
    }

    @Override
    protected double[] expectedSubtract1() {
        return new double[] { 0, 1, 2 };
    }

    @Override
    protected double[] expectedAdd1() {
        return new double[] { 2, 3, 4 };
    }

    @Override
    protected double expectedDotProduct1() {
        return 6.0;
    }
}
