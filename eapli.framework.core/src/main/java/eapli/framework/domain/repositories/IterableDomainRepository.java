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
package eapli.framework.domain.repositories;

import java.util.Iterator;
import java.util.Optional;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.ValueObject;

/**
 * An iterable repository of domain entities.
 *
 * @param <T>
 *            the type of the entity we want to manage in the repository.
 * @param <I>
 *            the type of the business identity of the entity. not the primary
 *            key
 *
 * @author Paulo Gandra Sousa
 */
public interface IterableDomainRepository<I extends Comparable<I> & ValueObject, T extends AggregateRoot<I>>
        extends DomainRepository<I, T>, Iterable<T> {

    /**
     * returns an iterator over the repository with an hint of pagesize results
     * for each fetch from the persistence storage
     *
     * @param pagesize
     *            hint about the result size of each fetch to the persistence
     *            storage
     * @return an iterator over the repository
     */
    Iterator<T> iterator(int pagesize);

    /**
     * Returns the first entity according to its "natural" order.
     *
     * @return the first entity according to its "natural" order.
     */
    Optional<T> first();

    /**
     * Returns the first n entities according to its "natural" order.
     *
     * @param n
     * @return the first n entities according to its "natural" order
     */
    Iterable<T> first(int n);
}
