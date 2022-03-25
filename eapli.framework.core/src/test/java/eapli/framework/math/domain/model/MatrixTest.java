/*
 * Copyright (c) 2013-2022 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 *
 */
public class MatrixTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotContructWith0Rows() {
        final double t[][] = new double[0][10];
        new Matrix(t);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotContructWith0Cols() {
        final double t[][] = new double[10][0];
        new Matrix(t);
    }

    @Test
    public void ensureContructsWithRightNumberOfRows() {
        final double t[][] = { { 1, 2, 3 }, { 1, 2, 3 } };
        final Matrix subject = new Matrix(t);
        assertEquals(2, subject.rowCount());
    }

    @Test
    public void ensureContructsWithRightNumberOfColumns() {
        final double t[][] = { { 1, 2, 3 }, { 1, 2, 3 } };
        final Matrix subject = new Matrix(t);
        assertEquals(3, subject.columnCount());
    }

    @Test
    public void testIsDiagonal1() {
        final double t[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
        final Matrix subject = new Matrix(t);
        assertTrue(subject.isDiagonal());
    }

    @Test
    public void testIsDiagonal2() {
        final double t[][] = { { 22, 0, 0 }, { 0, 1, 0 }, { 0, 0, -56 } };
        final Matrix subject = new Matrix(t);
        assertTrue(subject.isDiagonal());
    }

    @Test
    public void testNotSquareIsNotDiagonal() {
        final double t[][] = { { 0, 1, 0 }, { 0, 0, -56 } };
        final Matrix subject = new Matrix(t);
        assertFalse(subject.isDiagonal());
    }

    @Test
    public void testIsNotDiagonal1() {
        final double t[][] = { { 22, 3, 0 }, { 0, 1, 0 }, { 0, 0, -56 } };
        final Matrix subject = new Matrix(t);
        assertFalse(subject.isDiagonal());
    }

    @Test
    public void testIsSquare() {
        final double t[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
        final Matrix subject = new Matrix(t);
        assertTrue(subject.isSquare());
    }

    @Test
    public void testIsNotSquare() {
        final double t[][] = { { 1, 2, 3 }, { 1, 2, 3 } };
        final Matrix subject = new Matrix(t);
        assertFalse(subject.isSquare());
    }
}
