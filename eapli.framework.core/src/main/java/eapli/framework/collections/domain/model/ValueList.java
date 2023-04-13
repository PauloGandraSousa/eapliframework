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

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An immutable list of immutable values. A List by definition is an ordered
 * sequence of values. Repeated values are allowed. <code>null</code> values are
 * not allowed.
 *
 *
 * @author Paulo Gandra de Sousa 2023.04.10
 *
 * @param <T>
 */
public interface ValueList<T /* extends ValueObject */> {

	/**
	 * Checks if this list is an empty list.
	 *
	 * @return if this list is an empty list
	 */
	boolean isEmpty();

	/**
	 * Returns the length (number of elements) of this list.
	 *
	 * @return the length (number of elements) of this list
	 */
	int size();

	/**
	 * Checks if the element exists in the list.
	 *
	 * @param element value to check
	 * @return if the element exists in the list
	 */
	boolean contains(T element);

	/**
	 * Returns the first element of this list. If the list is empty, an empty
	 * optional is returned.
	 *
	 * @return the first element of this list
	 */
	Optional<T> head();

	/**
	 * The list consisting of all elements of this list without the head.
	 *
	 * *** WIP *** If the list is empty, an exception is thrown.
	 *
	 * @return
	 */
	ValueList<T> tail();

	/**
	 *
	 * *** WIP *** If the index is out of bounds, an exception is thrown.
	 *
	 * @param index
	 * @return the nth element of the list
	 */
	T nth(int index);

	/**
	 * Returns a new list with all the elements of this list appended with a new
	 * element at the end.
	 *
	 * @param other
	 * @return
	 */
	ValueList<T> append(T other);

	/**
	 * Returns a new list with all the elements of this list appended with all the
	 * elements of the other list at the end.
	 *
	 * @param other
	 * @return
	 */
	ValueList<T> append(ValueList<T> other);

	/**
	 * Returns a new list with all the elements of this list prepended with a new
	 * element at the beginning.
	 *
	 * @param other
	 * @return
	 */
	ValueList<T> prepend(T other);

	/**
	 * Returns a new list with all the elements of this list prepended with all the
	 * elements of the other list at the beginning.
	 *
	 * @param other
	 * @return
	 */
	ValueList<T> prepend(ValueList<T> other);

	/**
	 * Returns a new list with the elements of this list that meet the specified
	 * criteria.
	 *
	 * @param filter
	 * @return
	 */
	ValueList<T> filter(Predicate<T> filter);

	/**
	 * Returns a new list with all the elements of this list mapped.
	 *
	 * @param <U>
	 * @param mapper
	 * @return
	 */
	<U /* extends ValueObject */> ValueList<U> map(Function<T, U> mapper);

	/**
	 * (Left) Folds the contents of the Monad using the provided initial value.
	 *
	 * @param initial
	 * @param folder
	 * @return the value
	 */
	<R> R fold(R initial, BiFunction<R, T, R> folder);

	/**
	 * Right Folds the contents of the Monad using the provided initial value.
	 *
	 * @param initial
	 * @param folder
	 * @return the value
	 */
	<R> R foldr(R initial, BiFunction<R, T, R> folder);

	/**
	 * Returns all the values of this list as a stream. Order is not guaranteed.
	 *
	 * @return
	 */
	Stream<T> stream();
}
