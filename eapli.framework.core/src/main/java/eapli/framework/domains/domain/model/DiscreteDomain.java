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
package eapli.framework.domains.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * An immutable discrete domain, that is, a set of possible known values. Allows
 * for empty domains. This is a monadic type.
 *
 * @param <T>
 *            the type of values of the domain. T should extend ValueObject,
 *            however, to make this class more general we are not adding that
 *            constraint
 *
 * @author Paulo Gandra Sousa
 *
 */
@Embeddable
public final class DiscreteDomain<T extends Serializable> implements Iterable<T>, ValueObject {

    private static final long serialVersionUID = 1626025693468901924L;

    private final Set<T> elements = new HashSet<>();

    @SuppressWarnings({ "rawtypes", "java:S3740" })
    private static final DiscreteDomain EMPTY = new DiscreteDomain<>(Stream.empty());

    /**
     * The empty domain.
     *
     * @return the empty domain
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> DiscreteDomain<T> empty() {
        return EMPTY;
    }

    /**
     * Constructs a domain.
     *
     * @param from
     */
    /* package */ DiscreteDomain(final Iterable<T> from) {
        Preconditions.nonNull(from);

        from.forEach(elements::add);
    }

    /**
     * Constructs a domain.
     *
     * @param from
     */
    /* package */ DiscreteDomain(final Stream<T> from) {
        Preconditions.nonNull(from);

        from.forEach(elements::add);
    }

    /**
     * Factory method.
     *
     * @param data
     * @return a discrete domain based on the elements of the stream
     */
    public static <R extends Serializable> DiscreteDomain<R> from(final Stream<R> data) {
        return new DiscreteDomainBuilder<R>().with(data).build();
    }

    /**
     * Factory method.
     *
     * @param elements
     * @return a discrete domain
     */
    public static <R extends Serializable> DiscreteDomain<R> of(
            @SuppressWarnings("unchecked") final R... elements) {
        return new DiscreteDomain<>(Arrays.stream(elements));
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof DiscreteDomain<?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final DiscreteDomain<T> other = (DiscreteDomain<T>) o;
        return elements.equals(other.elements);
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(elements).code();
    }

    public boolean contains(final T e) {
        return elements.contains(e);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean intersects(final DiscreteDomain<T> other) {
        return !this.intersection(other).isEmpty();
    }

    /**
     * creates a new domain consisting of the intersection of elements between
     * this domain and another domain.
     *
     * <p>
     * for instance,
     *
     * <pre>
     * <code>
     * D1 = [1, 2 , 3, 4]
     * D2 = [3, 4, 5]
     * D3 = [6, 5, 7, 8]
     * D4 = []
     *
     * R12 = D1.intersection(D2) = [3, 4]
     * R13 = D1.intersection(D3) = []
     * R14 = D1.intersection(D4) = []
     *</code>
     * </pre>
     *
     * @param other
     * @return a new domain instance corresponding to the intersection
     */
    public DiscreteDomain<T> intersection(final DiscreteDomain<T> other) {
        final var builder = new DiscreteDomainBuilder<T>();
        for (final T each : elements) {
            if (other.contains(each)) {
                builder.with(each);
            }
        }
        return builder.build();
    }

    /**
     * creates a new domain consisting of the union of elements between this
     * domain and another domain
     *
     * <p>
     * for instance,
     *
     * <pre>
     * <code>
     * D1 = [1, 2 , 3, 4]
     * D2 = [3, 4, 5]
     * D3 = [6, 5, 7, 8]
     * D4 = []
     *
     * R12 = D1.union(D2) = [1, 2, 3, 4, 5]
     * R13 = D1.union(D3) = [1, 2, 3, 4, 6, 5, 7, 8]
     * R14 = D1.union(D4) = [1, 2 , 3, 4]
     *</code>
     * </pre>
     *
     * @param other
     * @return a new domain instance corresponding to the union
     */
    public DiscreteDomain<T> union(final DiscreteDomain<T> other) {
        final var builder = new DiscreteDomainBuilder<T>();
        elements.forEach(builder::with);
        other.forEach(builder::with);
        return builder.build();
    }

    /**
     * Creates a new domain consisting of the complement of elements between
     * this domain and another domain, that is, a domain with the elements of
     * this domain if and only if those elements do not exist in the other
     * domain
     *
     * <p>
     * for instance,
     *
     * <pre>
     * <code>
     * D1 = [1, 2 , 3, 4]
     * D2 = [3, 4, 5]
     * D3 = [6, 5, 7, 8]
     * D4 = []
     *
     * R12 = D1.without(D2) = [1, 2]
     * R13 = D1.without(D3) = [1, 2, 3, 4]
     * R14 = D1.without(D4) = [1, 2, 3, 4]
     * </code>
     * </pre>
     *
     * @param other
     * @return a new domain corresponding to the complement
     */
    public DiscreteDomain<T> without(final DiscreteDomain<T> other) {
        return from(stream().filter(e -> !other.contains(e)));
    }

    /**
     * Creates a new domain without the desired element.
     *
     * @param someElement
     * @return a new domain corresponding to the complement
     */
    public DiscreteDomain<T> without(final T someElement) {
        return from(stream().filter(e -> !e.equals(someElement)));
    }

    /**
     * Returns an iterator over the domain guaranteeing its immutability
     *
     * @return an iterator over the domain
     */
    @Override
    public Iterator<T> iterator() {
        return Collections.unmodifiableSet(elements).iterator();
    }

    /**
     * Returns the elements of the domain as a stream.
     *
     * @return the elements in the domain as a stream
     */
    public Stream<T> stream() {
        return elements.stream();
    }

    /**
     * Returns the elements of the domain as a stream.
     *
     * @return the elements in the domain as a stream
     */
    public Stream<T> parallelStream() {
        return elements.parallelStream();
    }

    /**
     * Creates a new discrete domain by mapping each element of this domain.
     *
     * @param mapper
     * @return a new discrete domain
     */
    public <R extends Serializable> DiscreteDomain<R> map(final Function<T, R> mapper) {
        return new DiscreteDomainBuilder<R>().with(stream().map(mapper)).build();
    }

    /**
     * Creates a new discrete domain by mapping each element of this domain.
     *
     * @param mapper
     * @return a new discrete domain
     */
    public <R extends Serializable> DiscreteDomain<R> flatMap(
            final Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new DiscreteDomainBuilder<R>().with(stream().flatMap(mapper)).build();
    }
}