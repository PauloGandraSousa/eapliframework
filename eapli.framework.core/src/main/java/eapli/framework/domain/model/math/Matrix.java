/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.domain.model.math;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

/**
 * An immutable matrix.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class Matrix implements ValueObject {

    private static final String MATRIX_MUST_BE_SQUARE = "matrix must be square";

    private static final long serialVersionUID = 3907439424531338640L;

    private transient int iDF = 0; // determinant factor

    private final int rows;
    private final int cols;
    private final double[][] data;

    public Matrix(final double[][] elems) {
        rows = elems.length + 1;
        cols = elems[0].length + 1;
        data = new double[rows][cols];
        copyData(elems, data);
    }

    public Matrix(final Matrix other) {
        rows = other.data.length + 1;
        cols = other.data[0].length + 1;
        data = new double[rows][cols];
        copyData(other.data, data);
    }

    private Matrix(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    /**
     * Creates a square identity matrix.
     *
     * @param n
     * @return a new identity matrix
     */
    public static Matrix identity(final int n) {
        final Matrix id = zero(n, n);
        for (int i = 0; i < n; i++) {
            id.putAt(i, i, 1.0);
        }
        return id;
    }

    /**
     * Creates a square zero matrix.
     *
     * @param n
     * @return a square zero matrix
     */
    public static Matrix zero(final int n) {
        return zero(n, n);
    }

    /**
     * Creates a zero matrix with the specified number of rows and columns.
     *
     * @param r
     *            the desired number of rows
     * @param c
     *            the desired number of columns
     * @return a zero matrix with the specified number of rows and columns
     */
    public static Matrix zero(final int r, final int c) {
        return new Matrix(r, c);
    }

    /**
     * Creates a "zero" matrix with all cells of the matrix having the same
     * given value.
     *
     * @param r
     *            the desired number of rows
     * @param c
     *            the desired number of columns
     * @param zero
     *            the value to set to all cells of the matrix
     * @return a "zero" matrix
     */
    public static Matrix zero(final int r, final int c, final double zero) {
        final Matrix z = new Matrix(r, c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                z.putAt(i, j, zero);
            }
        }
        return z;
    }

    // indexes are 1-based
    public double elementAt(final int i, final int j) {
        return getAt(i - 1, j - 1);
    }

    public int size() {
        return rows * cols;
    }

    public Vector row(final int index) {
        if (index < 0 || index >= rows) {
            throw new IndexOutOfBoundsException();
        }
        final double[] vec = new double[cols];
        for (int i = 0; i < cols; i++) {
            vec[i] = getAt(index, i);
        }
        return new Vector(vec, Vector.VectorType.ROW);
    }

    public Vector column(final int index) {
        if (index < 0 || index >= cols) {
            throw new IndexOutOfBoundsException();
        }
        final double[] vec = new double[rows];
        for (int i = 0; i < rows; i++) {
            vec[i] = getAt(i, index);
        }
        return new Vector(vec, Vector.VectorType.COLUMN);
    }

    /**
     * return a new matrix obtained by multiplying a matrix by a scalar
     *
     * @param k
     * @return
     */
    public Matrix scale(final double k) {
        final Matrix c = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                c.putAt(i, j, k * getAt(i, j));
            }
        }
        return c;
    }

    /**
     * add two matrices giving a third matrix
     *
     * @param other
     * @return
     */
    public Matrix add(final Matrix other) {
        Preconditions.ensure(rows == other.rows && cols == other.cols, "matrices must be same dimension");

        final Matrix dest = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dest.putAt(i, j, getAt(i, j) + other.getAt(i, j));
            }
        }
        return dest;
    }

    /**
     * subtract two matrices giving a third matrix
     *
     * @param other
     * @return
     */
    public Matrix subtract(final Matrix other) {
        Preconditions.ensure(rows == other.rows && cols == other.cols, "matrices must be same dimension");

        final Matrix dest = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dest.putAt(i, j, getAt(i, j) - other.getAt(i, j));
            }
        }
        return dest;
    }

    /**
     * multiply two matrices giving a third one
     *
     * @param other
     * @return
     */
    public Matrix multiply(final Matrix other) {
        Preconditions.ensure(cols == other.rows, "matrix 1 cols must = matrix 2 rows");

        final Matrix dest = new Matrix(rows, other.cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                double cellval = 0.0;
                for (int k = 0; k < cols; k++) {
                    cellval += getAt(i, k) * other.getAt(k, j);
                }
                dest.putAt(i, j, cellval);
            }
        }
        return dest;
    }

    /**
     * The trace of a square matrix is the sum of its diagonal elements
     *
     * @return
     */
    public double trace() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        double trace = 0;
        for (int i = 0; i < rows; i++) {
            trace += getAt(i, i);
        }
        return trace;
    }

    // indexes are 1-based
    public double cofactor(final int i, final int j) {
        // (-1)^(i+j)*Mij
        return java.lang.Math.pow(-1, (double) i + j) * minor(i, j);
    }

    // indexes are 1-based
    public double minor(final int i, final int j) {
        return this.submatrix(i, j).determinant1();
    }

    @SuppressWarnings("squid:S2234")
    public Matrix transpose() {
        final Matrix dest = new Matrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dest.putAt(j, i, getAt(i, j));
            }
        }
        return dest;
    }

    /**
     * @return the diagonal matrix with the same elements in this matrix's
     *         diagonal
     */
    public Matrix diagonal() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        final Matrix diag = zero(rows, cols);
        for (int i = 0; i < rows; i++) {
            diag.putAt(i, i, getAt(i, i));
        }
        return diag;
    }

    /**
     *
     * @return a row vector (a matrix with just one row) with the elements in
     *         this matrix's diagonal
     */
    public Matrix diagonalElements() {
        if (!isSquare()) {
            throw new IllegalStateException("matrix should be square");
        }
        final int k = java.lang.Math.min(rows, cols);

        final Matrix diag = Matrix.zero(1, k + 1);
        for (int i = 0; i <= k; i++) {
            diag.putAt(0, i, getAt(i, i));
        }
        return diag;
    }

    /**
     * removes a row or column from the matrix.
     *
     * indexes are 1-based
     *
     * @param index
     * @param typ
     * @return
     */
    public Matrix submatrix(final int index, final Vector.VectorType typ) {
        if (typ == Vector.VectorType.ROW) {
            return withoutRow(index);
        } else if (typ == Vector.VectorType.COLUMN) {
            return withoutColumn(index);
        }

        throw new IllegalStateException("should not happen - unknown VectorType");
    }

    // indexes are 1-based
    public Matrix submatrix(final int rowToRemove, final int colToRemove) {
        if (rowToRemove <= 0 || rowToRemove > rows) {
            throw new IndexOutOfBoundsException("rowToRemove");
        }
        if (colToRemove <= 0 || colToRemove > cols) {
            throw new IndexOutOfBoundsException("colToRemove");
        }
        if (rows - 1 <= 0 || cols - 1 <= 0) {
            throw new IllegalArgumentException("cannot remove only row/column");
        }

        final Matrix sub = new Matrix(rows - 1, cols - 1);
        int desti = 0;
        for (int i = 0; i < rows; i++) {
            if (i != rowToRemove - 1) {
                copyRowToSubmatrix(sub, colToRemove, desti, i);
                desti++;
            }
        }
        return sub;
    }

    private void copyRowToSubmatrix(final Matrix sub, final int colToRemove, final int desti, final int i) {
        int destj = 0;
        for (int j = 0; j < cols; j++) {
            if (j != colToRemove - 1) {
                sub.putAt(desti, destj, getAt(i, j));
                destj++;
            }
        }
    }

    /**
     * The vector formed by concatenating all the columns of the matrix
     *
     * @return
     */
    public Matrix vectorized() {
        final Matrix vec = new Matrix(rows * cols, 1);
        int k = 0;
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                vec.putAt(k, 0, getAt(i, j));
                k++;
            }
        }
        return vec;
    }

    public boolean isUpperDiagonal() {
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < i; j++) {
                if (getAt(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLowerDiagonal() {
        for (int i = 0; i < rows - 1; i++) {
            for (int j = i + 1; j < cols; j++) {
                if (getAt(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * X*X' = X'*X = I
     *
     * @return
     */
    public boolean isInversible() {
        if (!isSquare()) {
            return false;
        }

        final Matrix inv = inverse1();
        final Matrix t1 = multiply(inv);
        return t1.equals(inv.multiply(this)) && t1.isIdentity();
    }

    public Matrix inverse2() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        // Formula used to Calculate Inverse:
        // inv(A) = 1/det(A) * adj(A)
        final double det = determinant2();
        if (det == 0) {
            throw new IllegalStateException("Determinant Equals 0, Not Invertible.");
        }

        return adjoint().scale(1 / det);
    }

    public Matrix adjoint() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        final Matrix adj = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                setAdjointCell(adj, rows, i, j);
            }
        }

        return adj.transpose();
    }

    private void setAdjointCell(final Matrix adj, final int tms, final int i, final int j) {
        int ia = 0;
        int ja = 0;

        final Matrix ap = new Matrix(tms - 1, tms - 1);
        for (int ii = 0; ii < tms; ii++) {
            int jj;
            for (jj = 0; jj < tms; jj++) {
                if ((ii != i) && (jj != j)) {
                    ap.putAt(ia, ja, getAt(ii, jj));
                    ja++;
                }
            }
            if ((ii != i) && (jj != j)) {
                ia++;
            }
            ja = 0;
        }

        final double det = ap.determinant2();
        adj.putAt(i, j, java.lang.Math.pow(-1, (double) i + j) * det);
    }

    public Matrix upperTriangle() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        final int tms = rows; // get This Matrix Size

        iDF = 1;

        final Matrix m = new Matrix(this);

        for (int col = 0; col < tms - 1; col++) {
            for (int row = col + 1; row < tms; row++) {
                switchZeroInDiagonal(tms, m, col);

                if (m.getAt(col, col) != 0) {
                    final double f1 = (-1.0) * m.getAt(row, col) / m.getAt(col, col);
                    for (int i = col; i < tms; i++) {
                        m.putAt(row, i, f1 * m.getAt(col, i) + m.getAt(row, i));
                    }
                }
            }
        }

        return m;
    }

    private void switchZeroInDiagonal(final int tms, final Matrix m, final int col) {
        int v = 1;
        // check if 0 in diagonal
        while (m.getAt(col, col) == 0) {
            // if so switch until not
            // check if switched all rows
            if (col + v >= tms) {
                iDF = 0;
            } else {
                for (int c = 0; c < tms; c++) {
                    // switch rows
                    final double temp = m.getAt(col, c);
                    m.putAt(col, c, m.getAt(col + v, c));
                    m.putAt(col + v, c, temp);
                }
                // count row switches
                v++;
                // each switch changes determinant factor
                iDF *= -1;
            }
        }
    }

    public double determinant2() {
        final Matrix triang = upperTriangle();

        double det = 1;
        for (int i = 0; i < rows; i++) {
            det *= triang.getAt(i, i);
        } // multiply down diagonal

        det *= iDF; // adjust w/ determinant factor

        return det;
    }

    public int columnCount() {
        return cols;
    }

    public int rowCount() {
        return rows;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public boolean isSingular() {
        return determinant1() == 0.0;
    }

    public boolean isDiagonal() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) != 0.0 && i != j) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDiagonallyDominant() {
        Invariants.ensure(isSquare());

        boolean flagIneq = false;
        for (int i = 0; i < rows; i++) {
            double accum = 0.0;
            for (int j = 0; j < cols; j++) {
                if (i != j) {
                    accum += java.lang.Math.abs(getAt(i, j));
                }
            }
            if (java.lang.Math.abs(data[i][i]) < accum) {
                return false;
            }
            if (java.lang.Math.abs(data[i][i]) > accum) {
                flagIneq = true;
            }
        }
        return flagIneq;
    }

    /**
     * checks if this matrix is symmetric or not.
     *
     * equivalent to this.Equals(this.Transpose())
     *
     * @return
     */
    @SuppressWarnings("squid:S2234")
    public boolean isSymmetric() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) != getAt(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * equivalent to this.Equals(this.Transpose().Scale(-1))
     *
     * @return
     */
    @SuppressWarnings("squid:S2234")
    public boolean isSkewSymmetric() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) + getAt(j, i) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIdentity() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) != 0.0 && i != j) {
                    return false;
                }
                if (i == j && getAt(i, j) != 1.0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) != 0.0) {
                    return false;
                }
            }
        }
        return true;
    }

    public double determinant1() {
        Invariants.ensure(isSquare(), MATRIX_MUST_BE_SQUARE);

        double d = 0;
        for (int i = 0; i < cols; i++) {
            double p1 = 1.0;
            double p2 = 1.0;
            double p3 = getAt(i, 0);
            int k = i;
            for (int j = 1; j < cols; j++) {
                k = (k + 1) % cols;
                p1 *= getAt(j, k);
                p2 *= getAt(cols - j, k);
            }
            p3 *= (p1 - p2);
            d += p3;
        }
        return d;
    }

    /* ??? */
    public Matrix inverse1() {
        Invariants.ensure(isSingular(), "matrix is singular");

        final Matrix dest = new Matrix(rows, cols);
        final double[][] d = new double[rows][cols + 1];

        for (int i = 0; i < rows; i++) {
            int nrow = i - 1;
            int ncol = i - 1;
            for (int j = 0; j < rows; j++) {
                nrow = (nrow + 1) % rows;
                if (j == 0) {
                    d[j * (cols + 1)][cols] = 1;
                } else {
                    d[j * (cols + 1)][cols] = 0;
                }
                for (int k = 0; k < cols; k++) {
                    ncol = (ncol + 1) % cols;
                    d[j * (cols + 1)][k] = data[nrow * cols][ncol];
                }
            }
            nSolve(rows, d);
            nrow = i - 1;
            for (int j = 0; j < rows; j++) {
                nrow = (nrow + 1) % rows;
                dest.putAt(nrow * cols, i, d[j * (cols + 1)][cols]);
            }
        }
        return dest;
    }

    /**
     * removes a row or column from the matrix.
     *
     * indexes are 1-based
     *
     * @param index
     * @return
     */
    public Matrix withoutRow(final int index) {
        if (index <= 0 || index > rows) {
            throw new IllegalArgumentException("index");
        }
        if (rows - 1 <= 0) {
            throw new IllegalArgumentException("cannot remove single row");
        }

        final Matrix sub = new Matrix(rows - 1, cols);
        int desti = 0;
        for (int i = 0; i < rows; i++) {
            if (i != index - 1) {
                for (int j = 0; j < cols; j++) {
                    sub.putAt(desti, j, getAt(i, j));
                }
                desti++;
            }
        }
        return sub;
    }

    /**
     * removes a column from the matrix.
     *
     * indexes are 1-based
     *
     * @param index
     * @return
     */
    public Matrix withoutColumn(final int index) {
        if (index <= 0 || index > cols) {
            throw new IllegalArgumentException("index");
        }
        if (cols - 1 <= 0) {
            throw new IllegalArgumentException("cannot remove only column");
        }
        final Matrix sub = new Matrix(rows, cols - 1);
        for (int i = 0; i < rows; i++) {
            copyRowToSubmatrix(sub, index, i, i);
        }
        return sub;
    }

    // indexes are 1-based
    public Matrix submatrix(final int startRow, final int endRow, final int startCol, final int endCol) {
        if (startRow <= 0 || startRow > rows) {
            throw new IndexOutOfBoundsException("startRow");
        }
        if (endRow <= startRow || endRow > rows) {
            throw new IndexOutOfBoundsException("endRow");
        }
        if (startCol <= 0 || startCol > cols) {
            throw new IndexOutOfBoundsException("startCol");
        }
        if (endCol <= startCol || endCol > cols) {
            throw new IndexOutOfBoundsException("endCol");
        }

        final Matrix sub = new Matrix(endRow - startRow, endCol - startCol);
        for (int i = startRow - 1; i < endRow; i++) {
            for (int j = startCol - 1; j < endCol; j++) {
                sub.putAt(i - startRow + 1, j - startCol + 1, getAt(i, j));
            }
        }
        return sub;
    }

    /* ??? */
    private void nSolve(final int rows, final double[][] data) {
        final int columns = rows + 1;
        for (int i = 0; i < rows; i++) {
            nSolveStep1(rows, data, columns, i);
        }
        for (int i = rows - 2; i >= 0; i--) {
            for (int j = columns - 2; j > i; j--) {
                data[i][columns - 1] -= data[i][j] * data[j][columns - 1];
                data[i][j] = 0;
            }
        }
    }

    private void nSolveStep1(final int rows, final double[][] data, final int columns, final int i) {
        int j;
        for (j = i; j < rows && data[j][j] == 0.0; j++) {
            // do nothing
        }
        if (data[j][j] == 0.0) {
            throw new IllegalStateException("singular matrix");
        }
        if (j != i) {
            for (int k = 0; k < columns; k++) {
                final double dtemp = data[i][k];
                data[i][k] = data[j][k];
                data[j][k] = dtemp;
            }
        }
        for (j = columns - 1; j >= 0; j--) {
            data[i][j] /= data[i][i];
        }
        for (j = i + 1; j < rows; j++) {
            for (int k = columns - 1; k >= i; k--) {
                data[j][k] -= data[j][i] * data[i][k];
            }
        }
    }

    // indexes are 0-based
    private void putAt(final int i, final int j, final double v) {
        data[i][j] = v;
    }

    // indexes are 0-based
    private double getAt(final int i, final int j) {
        return data[i][j];
    }

    private void copyData(final double[][] source, final double[][] dest) {
        for (int i = 0; i <= data.length; i++) {
            for (int j = 0; j <= data[0].length; j++) {
                dest[i][j] = source[i][j];
            }
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != Matrix.class) {
            return false;
        }

        final Matrix other = (Matrix) obj;
        if (rows != other.rows || cols != other.cols) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (getAt(i, j) != other.getAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final HashCoder coder = new HashCoder().with(rows).with(cols).with(data);
        return coder.code();
    }
}
