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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;

/**
 * An immutable set of immutable values. A set by definition is an unordered
 * sequence of values and thus does not allow for repeated values.
 * <code>null</code> values are not allowed.
 *
 * @author Paulo Gandra de Sousa 2023.04.11
 *
 * @param <T>
 */
@EqualsAndHashCode
public class HashValueSet<T> implements ValueSet<T> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final Set<T> data;

	/**
	 * Constructs an empty set.
	 */
	public HashValueSet() {
		data = new HashSet<>();
	}

	/**
	 * Factory method.
	 *
	 * @param <T>
	 * @param args
	 * @return
	 */
	@SafeVarargs
	public static <T> ValueSet<T> of(T... args) {
		final var s = new HashSet<T>();
		Collections.addAll(s, args);
		return new HashValueSet<>(s);
	}

	/**
	 * helper "copy" constructor
	 *
	 * @param values
	 */
	private HashValueSet(Stream<T> values) {
		this(values.collect(Collectors.toSet()));
	}

	/**
	 * helper "copy" constructor
	 *
	 * @param values
	 */
	private HashValueSet(Set<T> values) {
		data = new HashSet<>(values);
	}

	/**
	 * helper "copy" constructor
	 *
	 * @param values
	 */
	private HashValueSet(Set<T> values, T newElement) {
		this(values);
		data.add(newElement);
	}

	/**
	 * helper "copy" constructor
	 *
	 * @param values
	 */
	private HashValueSet(Set<T> values, ValueSet<T> other) {
		this(values);
		other.stream().forEach(data::add);
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean contains(T element) {
		return data.contains(element);
	}

	@Override
	public HashValueSet<T> add(T other) {
		return new HashValueSet<>(data, other);
	}

	@Override
	public ValueSet<T> union(ValueSet<T> other) {
		return new HashValueSet<>(data, other);
	}

	@Override
	public ValueSet<T> intersection(ValueSet<T> other) {
		return filter(other::contains);
	}

	@Override
	public ValueSet<T> filter(Predicate<T> filter) {
		return new HashValueSet<>(stream().filter(filter));
	}

	@Override
	public <U> ValueSet<U> map(Function<T, U> mapper) {
		return new HashValueSet<>(stream().map(mapper));
	}

	@Override
	public <R> R fold(R initial, BiFunction<R, T, R> folder) {
		R result = initial;
		for (final var e : data) {
			result = folder.apply(result, e);
		}
		return result;
	}

	@Override
	public Stream<T> stream() {
		return data.stream();
	}

	@Override
	public ValueSet<T> complement(ValueSet<T> other) {
		final var c1 = this.filter(e -> !other.contains(e));
		final var c2 = other.filter(e -> !this.contains(e));

		return c1.union(c2);
	}
}