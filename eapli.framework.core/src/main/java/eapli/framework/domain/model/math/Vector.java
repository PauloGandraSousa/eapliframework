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

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.EqualsBuilder;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.ArrayOfDoubleCollector;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.NumberPredicates;
import eapli.framework.validations.Preconditions;

/**
 * An immutable vector.
 *
 * Vector indexes are 1-based, so a Vector with length 4 will have indexes 1, 2,
 * 3 and 4
 *
 * @author Paulo Gandra Sousa
 *
 */
public class Vector implements ValueObject {

    private static final long serialVersionUID = -3010865637192089056L;

    private static final String VECTOR_TYPE_MUST_BE_THE_SAME = "vector type must be the same";
    private static final double EPSILON = 0.0000001;

    public enum VectorType {
        ROW, COLUMN
    }

    private final int dimensions;
    private final double[] data;
    private final VectorType type;

    public Vector(final double[] src, final VectorType type) {
        dimensions = src.length;
        this.type = type;
        data = Arrays.copyOf(src, dimensions);
    }

    public Vector(final Vector other) {
        dimensions = other.dimensions;
        type = other.type;
        data = Arrays.copyOf(other.data, other.dimensions);
    }

    public Vector(final VectorType type, final int maxSize, final Stream<Double> src) {
        dimensions = maxSize;
        this.type = type;
        data = new double[dimensions];
        src.limit(maxSize).collect(ArrayOfDoubleCollector.collector(data));
    }

    public Vector(final VectorType type, final int maxSize, final DoubleStream src) {
        dimensions = maxSize;
        this.type = type;
        data = src.limit(maxSize).toArray();
    }

    private Vector(final VectorType type, final int maxSize, final DoubleSupplier src) {
        dimensions = maxSize;
        this.type = type;
        data = new double[dimensions];
        for (int i = 0; i < maxSize; i++) {
            data[i] = src.getAsDouble();
        }
    }

    private Vector(final Vector a, final Vector b, final DoubleBinaryOperator op) {
        Preconditions.areEqual(a.dimensions, b.dimensions);
        Preconditions.areEqual(a.type, b.type, VECTOR_TYPE_MUST_BE_THE_SAME);

        dimensions = a.dimensions;
        type = a.type;
        data = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            data[i] = op.applyAsDouble(a.data[i], b.data[i]);
        }
    }

    /**
     * creates a vector with all elements with the value 0.0
     * 
     * @param n
     * @param type
     * @return
     */
    public static Vector zero(final int n, final VectorType type) {
        return zero(n, type, 0.0);
    }

    /**
     * creates a "zero" vector with the same given value
     * 
     * @param n
     * @param type
     * @param zero
     * @return
     */
    public static Vector zero(final int n, final VectorType type, final double zero) {
        return new Vector(type, n, () -> zero);
    }

    /**
     * adds support for Java 8 Streams
     *
     * @return the elements of the Vector as a stream
     */
    public DoubleStream stream() {
        return Arrays.stream(data);
    }

    /**
     * indexes are 1-based
     *
     * @param i
     * @return
     */
    public double elementAt(final int i) {
        return data[i - 1];
    }

    /**
     * returns the corresponding unit vector of this vector
     *
     * @return
     */
    public Vector normalized() {
        final double magnitude = magnitude();
        return new Vector(type, dimensions, Arrays.stream(data).map(e -> e / magnitude));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Vector)) {
            return false;
        }
        final Vector other = (Vector) obj;
        final boolean partial = new EqualsBuilder().append(dimensions, other.dimensions).append(type, other.type)
                .isEquals();
        if (!partial) {
            return false;
        }

        // compare values with epsilon margin
        for (int i = 0; i < dimensions; i++) {
            if (!NumberPredicates.isWithinMargin(data[i], other.data[i], EPSILON)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final HashCoder coder = new HashCoder().of(type).of(data);
        return coder.code();
    }

    public Vector add(final Vector b) {
        return new Vector(this, b, (x, y) -> x + y);
    }

    public Vector subtract(final Vector b) {
        return new Vector(this, b, (x, y) -> x - y);
    }

    public Vector crossProduct(final Vector b) {
        return new Vector(this, b, (x, y) -> x * y);
    }

    /**
     * calculates the dot product of two vectors
     *
     * @param other
     * @return the dot product of two vectors
     */
    public double dotProduct(final Vector other) {
        Preconditions.areEqual(dimensions, other.dimensions);
        Preconditions.areEqual(type, other.type, VECTOR_TYPE_MUST_BE_THE_SAME);

        double accum = 0;
        for (int i = 0; i < dimensions; i++) {
            accum += (data[i] * other.data[i]);
        }
        return accum;
    }

    /**
     * return a new vector obtained by multiplying a vector by a scalar
     *
     * @param k
     * @return
     */
    public Vector scale(final double k) {
        return new Vector(type, dimensions, Arrays.stream(data).map(e -> k * e));
    }

    /**
     * returns the magnitude (length) of the vector
     *
     * @return
     */
    public double magnitude() {
        double accum = 0;
        for (final double x : data) {
            accum += (x * x);
        }
        return Math.sqrt(accum);
    }

    public double length() {
        return magnitude();
    }

    public boolean isUnit() {
        final double mag = magnitude();
        return NumberPredicates.isWithinMargin(mag, 1.0, EPSILON);
    }

    public int dimensions() {
        return dimensions;
    }

    /**
     * checks if all elements of the vector are zero
     *
     * @return
     */
    public boolean isZero() {
        return stream().allMatch(e -> e == 0);
    }

    /**
     * returns the data elements of the vector
     *
     * @return
     */
    public double[] elements() {
        return Arrays.copyOf(data, 1);
    }
}
