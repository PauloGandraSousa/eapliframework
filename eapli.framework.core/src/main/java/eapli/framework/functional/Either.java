/*
 * Copyright (c) 2013-2021 the original author or authors.
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
 * the error response and the right type is the success response.
 * The mnemonic often used in Haskell to remember/explain this "bias" is that an Either (usually
 * used for error-checking), gives you <em>either</em> the <em>right</em> answer, or else whatever's
 * <em>left</em>.
 * </p>
 * <p>
 * Consider using a java functional library instead, e.g.,
 * <a href="https://www.vavr.io/">vavr</a>
 * </p>
 *
 * @author Paulo Gandra de Sousa
 * @param <L>
 *            "left" type; by convention, the error result
 * @param <R>
 *            "right" type; by convention, the success result
 */
public interface Either<L, R> {

    /**
     * Factory method to create a left value Either.
     *
     * @param left
     * @return a new Either
     */
    static <T, U> Either<T, U> left(final T left) {
        // this doesn't make much sense as we are creating an either to avoid exceptions in the
        // first place...
        Preconditions.nonNull(left);

        return new LeftEither<>(left);
    }

    /**
     * Factory method to create a right value Either.
     *
     * @param right
     * @return a new Either
     */
    static <T, U> Either<T, U> right(final U right) {
        Preconditions.nonNull(right);
        return new RightEither<>(right);
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
    <T, U> Either<T, U> map(final Function<? super L, ? extends T> leftMapper,
            final Function<? super R, ? extends U> rightMapper);

    /**
     * Creates a new {@code Either} by filtering the current value. If the current value satisfies
     * the predicate, the returned {@code Either} will be the "same", otherwise, an "empty"
     * {@code either} will be returned.
     *
     * Since this method
     * returns a new {@code Either} it is suitable for composition.
     *
     * @param leftPredicate
     * @param rightPredicate
     * @return a new either resulting from the projection
     */
    Either<L, R> filter(final Predicate<? super L> leftPredicate,
            final Predicate<? super R> rightPredicate);

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
    <T> T fold(final BiFunction<T, ? super L, ? extends T> leftAccum,
            final BiFunction<T, ? super R, ? extends T> rightAccum, final T def);

    /**
     * Acts on the value of this Either instance, executing the "normal" function
     * with the left value if this Either instance holds a left value, otherwise
     * executes the "error" function with the right value
     *
     * @param leftConsumer
     * @param rightConsumer
     */
    void consume(final Consumer<? super L> leftConsumer, final Consumer<? super R> rightConsumer);

    /**
     * Returns the value hold on the left of the either or throws a
     * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
     * value on the left side.
     *
     * @param exceptionBuilder
     *
     * @return the value hold on the left of the either if there is a left value
     */
    L leftValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder);

    /**
     * Returns the value hold on the left of the either or the default value, if
     * there is no value on the left side.
     *
     * @param def
     *
     * @return the value hold on the left of the either if there is a left value
     */
    L leftValueOrElse(final L def);

    /**
     * Returns the value hold on the right of the either or throws a
     * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
     * value on the right side.
     *
     * @param exceptionBuilder
     *
     * @return the value hold on the left of the either if there is a left value
     */
    R rightValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder);

    /**
     * Returns the value hold on the right of the either or the default value, if
     * there is no value on the right side.
     *
     * @param def
     *
     * @return the value hold on the left of the either if there is a left value
     */
    R rightValueOrElse(final R def);

    /**
     * Returns a new Either with the values (and types) swapped.
     *
     * @return a new Either with the values (and types) swapped
     */
    Either<R, L> swap();
}

/**
 * Left projection.
 *
 * @author Paulo Gandra de Sousa 02/03/2021
 *
 * @param <L>
 * @param <R>
 */
final class LeftEither<L, R> implements Either<L, R> {

    private final L value;

    /**
     * @param left
     */
    public LeftEither(final L left) {
        super();
        value = left;
    }

    @Override
    public <T, U> Either<T, U> map(final Function<? super L, ? extends T> leftMapper,
            final Function<? super R, ? extends U> rightMapper) {
        return Either.left(leftMapper.apply(value));
    }

    @Override
    public Either<L, R> filter(final Predicate<? super L> leftPredicate, final Predicate<? super R> rightPredicate) {
        if (leftPredicate.test(value)) {
            return this;
        }
        return new EmptyEither<>();
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> leftAccum,
            final BiFunction<T, ? super R, ? extends T> rightAccum,
            final T def) {
        return leftAccum.apply(def, value);
    }

    @Override
    public void consume(final Consumer<? super L> leftConsumer, final Consumer<? super R> rightConsumer) {
        leftConsumer.accept(value);
    }

    @Override
    public L leftValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        return value;
    }

    @Override
    public L leftValueOrElse(final L def) {
        return value;
    }

    @Override
    public R rightValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public R rightValueOrElse(final R def) {
        return def;
    }

    @Override
    public Either<R, L> swap() {
        return Either.right(value);
    }
}

/**
 * Right projection.
 *
 * @author Paulo Gandra de Sousa 02/03/2021
 *
 * @param <L>
 * @param <R>
 */
final class RightEither<L, R> implements Either<L, R> {

    private final R value;

    /**
     * @param right
     */
    public RightEither(final R right) {
        value = right;
    }

    @Override
    public <T, U> Either<T, U> map(final Function<? super L, ? extends T> leftMapper,
            final Function<? super R, ? extends U> rightMapper) {
        return Either.right(rightMapper.apply(value));
    }

    @Override
    public Either<L, R> filter(final Predicate<? super L> leftPredicate, final Predicate<? super R> rightPredicate) {
        if (rightPredicate.test(value)) {
            return this;
        }
        return new EmptyEither<>();
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> leftAccum,
            final BiFunction<T, ? super R, ? extends T> rightAccum,
            final T def) {
        return rightAccum.apply(def, value);
    }

    @Override
    public void consume(final Consumer<? super L> leftConsumer, final Consumer<? super R> rightConsumer) {
        rightConsumer.accept(value);
    }

    @Override
    public L leftValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public L leftValueOrElse(final L def) {
        return def;
    }

    @Override
    public R rightValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        return value;
    }

    @Override
    public R rightValueOrElse(final R def) {
        return value;
    }

    @Override
    public Either<R, L> swap() {
        return Either.left(value);
    }
}

/**
 * Special case due to {@link Either#filter}
 *
 * @author Paulo Gandra de Sousa 02/03/2021
 *
 * @param <L>
 * @param <R>
 */
final class EmptyEither<L, R> implements Either<L, R> {

    @Override
    public <T, U> Either<T, U> map(final Function<? super L, ? extends T> leftMapper,
            final Function<? super R, ? extends U> rightMapper) {
        return new EmptyEither<>();
    }

    @Override
    public Either<L, R> filter(final Predicate<? super L> leftPredicate, final Predicate<? super R> rightPredicate) {
        return this;
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> leftAccum,
            final BiFunction<T, ? super R, ? extends T> rightAccum,
            final T def) {
        return def;
    }

    @Override
    public void consume(final Consumer<? super L> leftConsumer, final Consumer<? super R> rightConsumer) {
        // nothing to do
    }

    @Override
    public L leftValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public L leftValueOrElse(final L def) {
        return def;
    }

    @Override
    public R rightValueOrElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public R rightValueOrElse(final R def) {
        return def;
    }

    @Override
    public Either<R, L> swap() {
        return new EmptyEither<>();
    }
}
