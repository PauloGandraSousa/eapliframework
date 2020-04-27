package eapli.framework.math.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eapli.framework.math.domain.model.Matrix;

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
