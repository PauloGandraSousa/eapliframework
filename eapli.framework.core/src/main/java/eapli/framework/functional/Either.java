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

import eapli.framework.functional.Either.LeftProjection;
import eapli.framework.functional.Either.RightProjection;
import eapli.framework.validations.Preconditions;

/**
 * A simple approach to the functional Monad Either. Consider using a well-tested and proven java
 * functional library instead, e.g., <a href="https://www.vavr.io/">vavr</a>
 *
 * <p>
 * Objects of this class can represent one (1) value of either {@code L} or
 * {@code R}. Useful for error handling where, by convention, the left type is
 * the error response and the right type is the success response.
 * The mnemonic often used in Haskell to remember/explain this "bias" is that an {@code Either}
 * (usually used for error-checking), gives you <em>either</em> the <em>right</em> answer, or else
 * whatever's <em>left</em>.
 * </p>
 * <p>
 * Imagine the following typical code extract
 *
 * <pre>
 *  User login(String username, String pass) throws UnathenticatedException { ... }
 * </pre>
 *
 * Client code would be something along the lines:
 *
 * <pre>
 * try {
 *    User u = authz.login(username, pass);
 *    // the user is authenticated so let's do our job
 *    // e.g. present main menu
 *    ...
 * } catch (UnauthenticatedException e) {
 *    // the user is not authenticated so let's treat it
 *    // e.g., ask for the username/password again
 *    ...
 * }
 * </pre>
 *
 * In this scenario, the exception is in fact used to control the flow of the program execution. Or
 * worse, if {@code UnauthenticatedException } is a {@code RuntimeException} might even not be
 * caught, or maybe silenced. Furthermore, catching Exceptions is an "expensive" operation for the
 * JVM.
 * <p>
 * Using an {@code Either} allows to explicitly model the expected behaviour of a method and gives
 * the client the proper option on how to handle it.
 *
 * <pre>
 * Either&lt;ErrorReason, User&gt; login(String username, String pass) { ... }
 * </pre>
 *
 * Now, the method signature explicitly tells you that calling this method will <em>either</em>
 * return a {@code User} or an {@code ErrorReason}. The client code would look like:
 * <p>
 * a) project to each side and use it, e.g., consume
 *
 * <pre>
 * Either&lt;ErrorReason, User&gt; result = authz.login(username, pass);
 * result.left().consume(() -&gt; {
 *    // the user is not authenticated so let's treat it
 *    // e.g., ask for the username/password again
 *    ...
 * });
 *
 * result.right().consume(() -&gt; {
 *    // the user is authenticated so let's do our job
 *    // e.g. present main menu
 *    ...
 * });
 * </pre>
 *
 * b) consume a/each side and chain use
 *
 * <pre>
 * authz.login(username, pass)
 *      .onLeft(() -&gt; {
 *          // the user is not authenticated so let's treat it
 *          // e.g., ask for the username/password again
 *          ...
 *      })
 *      .onRight(() -&gt; {
 *          // the user is authenticated so let's do our job
 *          // e.g. present main menu
 *          ...
 *      });
 * </pre>
 *
 * or c) use each side accordingly, e.g., consume left <em>or</em> right
 *
 * <pre>
 * Either<ErrorReason, User> result = authz.login(username, pass);
 * result.consume(() -&gt; {
 *    // the user is not authenticated so let's treat it
 *    // e.g., ask for the username/password again
 *    ...
 * }, () &gt; {
 *    // the user is authenticated so let's do our job
 *    // e.g. present main menu
 *    ...
 * });
 * </pre>
 *
 * @author Paulo Gandra de Sousa
 * @param <L>
 *            "left" type; by convention, the error result
 * @param <R>
 *            "right" type; by convention, the success result
 */
public interface Either<L, R> {

    /**
     *
     * @author Paulo Gandra de Sousa 03/03/2021
     *
     * @param <L>
     */
    interface LeftProjection<L> {
        /**
         *
         * @param mapper
         * @return a new either resulting from the projection
         */
        <T> LeftProjection<T> map(final Function<? super L, ? extends T> mapper);

        /**
         * Creates a new {@code Either} by filtering the current value. If the current value
         * satisfies
         * the predicate, the returned {@code Either} will be the "same", otherwise, an "empty"
         * {@code either} will be returned.
         *
         * Since this method
         * returns a new {@code Either} it is suitable for composition.
         *
         * @param predicate
         * @return a new either resulting from the projection
         */
        LeftProjection<L> filter(final Predicate<? super L> predicate);

        /**
         * Folds the current value using {@code def} as the
         * initial folding value.
         *
         * @param accum
         * @param def
         *            default/initial value
         * @return the folded value
         */
        <T> T fold(final BiFunction<T, ? super L, ? extends T> accum, final T def);

        /**
         *
         * @param consumer
         * @param rightConsumer
         */
        void consume(final Consumer<? super L> consumer);

        /**
         * Returns the value hold on the left of the either or throws a
         * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
         * value on the left side.
         *
         * @param exceptionBuilder
         *
         * @return the value hold on the left of the either if there is a left value
         */
        L orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder);

        /**
         * Returns the value hold on the left of the either or the default value, if
         * there is no value on the left side.
         *
         * @param def
         *
         * @return the value hold on the left of the either if there is a left value
         */
        L orElse(final L def);
    }

    /**
     *
     * @author Paulo Gandra de Sousa 03/03/2021
     *
     * @param <R>
     */
    interface RightProjection<R> {

        /**
         *
         * @param <T>
         * @param mapper
         * @return a new projection based on the application of the mapping function
         */
        <T> RightProjection<T> map(final Function<? super R, ? extends T> mapper);

        /**
         * Creates a new {@code Either} by filtering the current value. If the current value
         * satisfies
         * the predicate, the returned {@code Either} will be the "same", otherwise, an "empty"
         * {@code either} will be returned.
         *
         * Since this method
         * returns a new {@code Either} it is suitable for composition.
         *
         * @param predicate
         * @return a new either resulting from the projection
         */
        RightProjection<R> filter(final Predicate<? super R> predicate);

        /**
         * Folds the current value using {@code def} as the
         * initial folding value.
         *
         * @param accum
         * @param def
         *            default/initial value
         * @return the folded value
         */
        <T> T fold(final BiFunction<T, ? super R, ? extends T> accum, final T def);

        /**
         *
         * @param consumer
         */
        void consume(final Consumer<? super R> consumer);

        /**
         * Returns the value hold on the right of the either or throws a
         * {@code RuntimeException}, e.g., IllegalArgumentException, if there is no
         * value on the right side.
         *
         * @param exceptionBuilder
         *
         * @return the value hold on the left of the either if there is a left value
         */
        R orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder);

        /**
         * Returns the value hold on the right of the either or the default value, if
         * there is no value on the right side.
         *
         * @param def
         *
         * @return the value hold on the left of the either if there is a left value
         */
        R orElse(final R def);
    }

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
     * Left projects this Either.
     *
     * @return a left projection of this Either.
     */
    LeftProjection<L> left();

    /**
     * Right projects this Either.
     *
     * @return a right projection of this either
     */
    RightProjection<R> right();

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
     * Acts on the value of this Either instance if it is a left. Chainable.
     *
     * @param consumer
     * @return this Either
     */
    Either<L, R> onLeft(final Consumer<? super L> consumer);

    /**
     * Acts on the value of this Either instance if it is a right. Chainable.
     *
     * @param consumer
     * @return this Either
     */
    Either<L, R> onRight(final Consumer<? super R> consumer);

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
final class LeftEither<L, R> implements Either<L, R>, LeftProjection<L> {

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

    @Override
    public LeftProjection<L> left() {
        return this;
    }

    @Override
    public RightProjection<R> right() {
        return new EmptyRight<>();
    }

    @Override
    public <T> LeftProjection<T> map(final Function<? super L, ? extends T> mapper) {
        return new LeftEither<>(mapper.apply(value));
    }

    @Override
    public LeftProjection<L> filter(final Predicate<? super L> predicate) {
        if (predicate.test(value)) {
            return this;
        }
        return new EmptyLeft<>();
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> accum, final T def) {
        return accum.apply(def, value);
    }

    @Override
    public void consume(final Consumer<? super L> consumer) {
        consumer.accept(value);
    }

    @Override
    public L orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        return value;
    }

    @Override
    public L orElse(final L def) {
        return value;
    }

    @Override
    public Either<L, R> onLeft(final Consumer<? super L> consumer) {
        consumer.accept(value);
        return this;
    }

    @Override
    public Either<L, R> onRight(final Consumer<? super R> consumer) {
        // do not consume value and allow chaining
        return null;
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
final class RightEither<L, R> implements Either<L, R>, RightProjection<R> {

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

    @Override
    public LeftProjection<L> left() {
        return new EmptyLeft<>();
    }

    @Override
    public RightProjection<R> right() {
        return this;
    }

    @Override
    public <T> RightProjection<T> map(final Function<? super R, ? extends T> mapper) {
        return new RightEither<>(mapper.apply(value));
    }

    @Override
    public RightProjection<R> filter(final Predicate<? super R> predicate) {
        if (predicate.test(value)) {
            return this;
        }
        return new EmptyRight<>();
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super R, ? extends T> accum, final T def) {
        return accum.apply(def, value);
    }

    @Override
    public void consume(final Consumer<? super R> consumer) {
        consumer.accept(value);
    }

    @Override
    public R orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        return value;
    }

    @Override
    public R orElse(final R def) {
        return value;
    }

    @Override
    public Either<L, R> onLeft(final Consumer<? super L> consumer) {
        // do not consume value and allow chaining
        return null;
    }

    @Override
    public Either<L, R> onRight(final Consumer<? super R> consumer) {
        consumer.accept(value);
        return null;
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

    @Override
    public LeftProjection<L> left() {
        return new EmptyLeft<>();
    }

    @Override
    public RightProjection<R> right() {
        return new EmptyRight<>();
    }

    @Override
    public Either<L, R> onLeft(final Consumer<? super L> consumer) {
        // do not consume value and allow chaining
        return null;
    }

    @Override
    public Either<L, R> onRight(final Consumer<? super R> consumer) {
        // do not consume value and allow chaining
        return null;
    }
}

/**
 * Special case
 *
 * @author Paulo Gandra de Sousa 03/03/2021
 *
 * @param <L>
 */
final class EmptyLeft<L> implements LeftProjection<L> {

    @Override
    public <T> LeftProjection<T> map(final Function<? super L, ? extends T> mapper) {
        return new EmptyLeft<>();
    }

    @Override
    public LeftProjection<L> filter(final Predicate<? super L> predicate) {
        return this;
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super L, ? extends T> accum, final T def) {
        return def;
    }

    @Override
    public void consume(final Consumer<? super L> consumer) {
        // nothing to do
    }

    @Override
    public L orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public L orElse(final L def) {
        return def;
    }
}

/**
 * Special case
 *
 * @author Paulo Gandra de Sousa 03/03/2021
 *
 * @param <R>
 */
final class EmptyRight<R> implements RightProjection<R> {

    @Override
    public <T> RightProjection<T> map(final Function<? super R, ? extends T> mapper) {
        return new EmptyRight<>();
    }

    @Override
    public RightProjection<R> filter(final Predicate<? super R> predicate) {
        return this;
    }

    @Override
    public <T> T fold(final BiFunction<T, ? super R, ? extends T> accum, final T def) {
        return def;
    }

    @Override
    public void consume(final Consumer<? super R> consumer) {
        // nothing to do
    }

    @Override
    public R orElseThrow(final Supplier<? extends RuntimeException> exceptionBuilder) {
        throw exceptionBuilder.get();
    }

    @Override
    public R orElse(final R def) {
        return def;
    }
}