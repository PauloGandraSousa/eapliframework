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
package eapli.framework.functional;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import eapli.framework.validations.Preconditions;

/**
 * A simple approach to the functional Monad Either.
 *
 * <p>
 * Objects of this class can represent one (1) value of either {@code L} or
 * {@code R}. Useful for error handling where, by convention, the left type is
 * the correct response and the right type is the error response.
 * </p>
 * <p>
 * Consider using a java functional library instead, e.g.,
 * <a href="https://www.vavr.io/">vavr</a>
 * </p>
 *
 * @author Paulo Gandra de Sousa
 * @param <L>
 * @param <R>
 */
public final class Either<L, R> {

    private enum SideType {
        LEFT, RIGHT,
        /**
         * marker value for empty objects
         */
        EMPTY_MARKER
    }

    @SuppressWarnings({ "rawtypes" })
    private static final Either EMPTY = new Either();

    private final SideType type;
    private L left;
    private R right;

    @SuppressWarnings({ "unchecked" })
    /* package */static <T, U> Either<T, U> empty() {
        return EMPTY;
    }

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
     *            just to make the compiler happy due to the erasure of both
     *            constructors
     */
    @SuppressWarnings("squid:S1172")
    private Either(final L left, final boolean marker) {
        Preconditions.nonNull(left);

        type = SideType.LEFT;
        this.left = left;
    }

    /**
     * Constructs a right Either.
     *
     * @param right
     */
    private Either(final R right) {
        Preconditions.nonNull(right);

        type = SideType.RIGHT;
        this.right = right;
    }

    /**
     * Constructs an empty Either.
     */
    private Either() {
        type = SideType.EMPTY_MARKER;
    }

    /* package */ boolean isEmpty() {
        return type == SideType.EMPTY_MARKER;
    }

    /**
     * Applies the "normal" function to the left value if this Either instance holds
     * a left value, otherwise applies the "error" function to the right value.
     * Since this method returns a new {@code Either} it is suitable for
     * composition.
     *
     * @param leftMapper
     * @param rightMapper
     * @return a new either resulting from the projection
     */
    public <T, U> Either<T, U> map(final Function<? super L, ? extends T> leftMapper,
            final Function<? super R, ? extends U> rightMapper) {
        if (type == SideType.LEFT) {
            return left(leftMapper.apply(left));
        } else if (type == SideType.RIGHT) {
            return right(rightMapper.apply(right));
        } else {
            return empty();
        }
    }

    /**
     * Creates a new Either by filtering the current value. Since this method
     * returns a new {@code Either} it is suitable for composition.
     *
     * @param leftPredicate
     * @param rightPredicate
     * @return a new either resulting from the projection
     */
    public Either<L, R> filter(final Predicate<? super L> leftPredicate,
            final Predicate<? super R> rightPredicate) {
        if (type == SideType.LEFT && leftPredicate.test(left)) {
            return left(left);
        } else if (type == SideType.RIGHT && rightPredicate.test(right)) {
            return right(right);
        }
        return empty();
    }

    /**
     * Folds the current value (either right or left) using {@code def} as the
     * initial folding value.
     *
     * @param leftAccum
     * @param rightAccum
     * @param def
     *            default/initial value
     * @return the folded value
     */
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> leftAccum,
            final BiFunction<T, ? super R, ? extends T> rightAccum, final T def) {
        if (type == SideType.LEFT) {
            return leftAccum.apply(def, left);
        } else if (type == SideType.RIGHT) {
            return rightAccum.apply(def, right);
        } else {
            return def;
        }
    }

    /**
     * Acts on the value of this Either instance, executing the "normal" function
     * with the left value if this Either instance holds a left value, otherwise
     * executes the "error" function with the right value
     *
     * @param leftConsumer
     * @param rightConsumer
     */
    public void consume(final Consumer<? super L> leftConsumer,
            final Consumer<? super R> rightConsumer) {
        if (type == SideType.LEFT) {
            leftConsumer.accept(left);
        } else {
            rightConsumer.accept(right);
        }
    }

    /**
     * Returns the value hold on the left of the either or throws a
     * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
     * value on the left side.
     *
     * @param exceptionBuilder
     *
     * @return the value hold on the left of the either if there is a left value
     */
    public L leftValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        if (type == SideType.LEFT) {
            return left;
        }
        throw exceptionBuilder.get();
    }

    /**
     * Returns the value hold on the left of the either or the default value, if
     * there is no value on the left side.
     *
     * @param def
     *
     * @return the value hold on the left of the either if there is a left value
     */
    public L leftValueOrElse(final L def) {
        return (type == SideType.LEFT) ? left : def;
    }

    /**
     * Returns the value hold on the right of the either or throws a
     * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
     * value on the right side.
     *
     * @param exceptionBuilder
     *
     * @return the value hold on the left of the either if there is a left value
     */
    public R rightValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        if (type == SideType.RIGHT) {
            return right;
        }
        throw exceptionBuilder.get();
    }

    /**
     * Returns the value hold on the right of the either or the default value, if
     * there is no value on the right side.
     *
     * @param def
     *
     * @return the value hold on the left of the either if there is a left value
     */
    public R rightValueOrElse(final R def) {
        return (type == SideType.RIGHT) ? right : def;
    }

    /**
     * Returns a new Either with the values (and types) swapped.
     *
     * @return a new Either with the values (and types) swapped
     */
    public Either<R, L> swap() {
        if (type == SideType.LEFT) {
            return right(left);
        } else if (type == SideType.RIGHT) {
            return left(right);
        } else {
            return empty();
        }
    }
}
