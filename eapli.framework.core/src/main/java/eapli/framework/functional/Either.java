/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.functional;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import eapli.framework.validations.Preconditions;

/**
 * A simple approach to the functional Monad Either.
 *
 * <p>
 * Objects of this class can represent one (1) value of either T or U. Useful for error handling
 * where, by convention, the left type is the correct response and the right type is the error
 * response.
 * <p>
 * Consider using a java functional library instead, e.g., vavr
 *
 * @author Paulo Gandra de Sousa
 * @param <T>
 * @param <U>
 */
public final class Either<T, U> {

    private enum SideType {
        LEFT, RIGHT
    }

    private T left;
    private final SideType type;
    private U right;

    /**
     * Factory method to create a left value Either.
     *
     * @param left
     * @return a new Either
     */
    public static <T, U> Either<T, U> left(final T left) {
        return new Either<>(left, true);
    }

    /**
     * Factory method to create a right value Either.
     *
     * @param right
     * @return a new Either
     */
    public static <T, U> Either<T, U> right(final U right) {
        return new Either<>(right);
    }

    /**
     * Constructs a left Either.
     *
     * @param left
     * @param marker
     *               just to make the compiler happy due to the erasure of both constructors
     */
    @SuppressWarnings("squid:S1172")
    private Either(final T left, final boolean marker) {
        Preconditions.nonNull(left);

        type = SideType.LEFT;
        this.left = left;
    }

    /**
     * Constructs a right Either.
     *
     * @param right
     */
    private Either(final U right) {
        Preconditions.nonNull(right);

        type = SideType.RIGHT;
        this.right = right;
    }

    /**
     * Applies the "normal" function to the left value if this Either instance holds a left value,
     * otherwise applies the "error" function to the right value. since this method returns a new Either
     * it is suitable for composition.
     *
     * @param normal
     * @param error
     * @return a new either resulting from the projection
     */
    public Either<T, U> map(final UnaryOperator<T> normal, final UnaryOperator<U> error) {
        if (type == SideType.LEFT) {
            return left(normal.apply(left));
        }
        return right(error.apply(right));
    }

    /**
     * Acts on the value of this Either instance, executing the "normal" function with the left value if
     * this Either instance holds a left value, otherwise executes the "error" function with the right
     * value
     *
     * @param doer
     * @param error
     */
    public void consume(final Consumer<T> doer, final Consumer<U> error) {
        if (type == SideType.LEFT) {
            doer.accept(left);
        } else {
            error.accept(right);
        }
    }

    /**
     * Returns the value hold on the left of the either or throws an IllegalArgumentException if there
     * is no value on the left side. If the right side is a String it will use that value as the message
     * of the exception.
     *
     * @return the value hold on the left of the either if there is a left value
     * @throws IllegalArgumentException
     */
    public T orElseThrow() {
        if (type == SideType.LEFT) {
            return left;
        } else {
            final String message = right instanceof String ? (String) right : "";
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns a new Either with the values (and types) swapped.
     *
     * @return a new Either with the values (and types) swapped
     */
    public Either<U, T> swap() {
        if (type == SideType.LEFT) {
            return right(left);
        } else {
            return left(right);
        }
    }
}
