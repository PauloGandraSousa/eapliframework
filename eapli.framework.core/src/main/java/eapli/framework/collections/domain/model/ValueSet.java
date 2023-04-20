/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.collections.domain.model;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import eapli.framework.domain.model.ValueObject;

/**
 * An immutable set of immutable values. A set by definition is an unordered
 * sequence of values and thus does not allow for repeated values. However, implementations may
 * support ordering (sorting) of elements when iterating thru the contents of the set.
 * <code>null</code> values are not allowed.
 * <p>
 * A set allows for iteration of its values by implementing the java standard {@link Iterable}
 * interface. However, to keep with the immutability of a ValueSet, implementations
 * <strong>must</strong> return iterators that do not support {@link java.util.Iterator.remove}.
 *
 * @author Paulo Gandra de Sousa 2023.04.11
 *
 * @param <T>
 */
public interface ValueSet<T /* extends ValueObject */> extends ValueObject, Iterable<T> {

    /**
     * Checks if this set is empty.
     *
     * @return if this set is empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements of this set.
     *
     * @return the number of elements of this set
     */
    int size();

    /**
     * Checks if the element exists in the set.
     *
     * @param element
     *            value to check
     *
     * @return if the element exists in the set
     */
    boolean contains(T element);

    /**
     * Returns a new set with all the elements of this set plus a new element. <code>null</code>
     * values are not allowed. If the Set is sorted, <code>add</code> ensures the stream and
     * iterator will satisfy that ordering.
     *
     * @param other
     *
     * @return
     */
    ValueSet<T> add(T other);

    /**
     * Returns a new set with all the elements of this set plus all the elements of
     * the other set.
     * <p>
     *
     * <pre>
     * A = [1 2 3]
     * B = [4 5 6]
     *
     * C = A union B = [1 2 3 4 5 6]
     * </pre>
     *
     * @param other
     *
     * @return
     */
    ValueSet<T> union(ValueSet<T> other);

    /**
     * Returns a new set with just the elements that belong to both sets.
     * <p>
     *
     * <pre>
     * A = [1 2 3]
     * B = [3 4 5]
     *
     * C = A intersection B = [3]
     * </pre>
     *
     * @param other
     *
     * @return
     */
    ValueSet<T> intersection(ValueSet<T> other);

    /**
     * Returns a new set with the elements that do not belong to both sets.
     * <p>
     *
     * <pre>
     * A = [1 2 3]
     * B = [3 4 5]
     *
     * C = A complement B = [1 2 4 5]
     * </pre>
     *
     * @param other
     *
     * @return
     */
    ValueSet<T> complement(ValueSet<T> other);

    /**
     * Returns a new set with the elements of this set that meet the specified
     * criteria.
     *
     * @param criteria
     *
     * @return
     */
    ValueSet<T> filter(Predicate<T> criteria);

    /**
     * Returns a new set with all the elements of this set mapped.
     *
     * @param <U>
     * @param mapper
     *
     * @return
     */
    <U /* extends ValueObject */> ValueSet<U> map(Function<T, U> mapper);

    /**
     * Folds (reduces) the contents of the Monad using the provided initial value.
     *
     * @param initial
     * @param reducer
     *
     * @return the value
     */
    <R> R fold(R initial, BiFunction<R, T, R> reducer);

    /**
     * Returns all the values of this set as a stream.
     *
     * @return
     */
    Stream<T> stream();
}