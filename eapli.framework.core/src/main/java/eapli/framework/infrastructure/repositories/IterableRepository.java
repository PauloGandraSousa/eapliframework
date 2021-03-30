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
package eapli.framework.infrastructure.repositories;

import java.util.Iterator;
import java.util.Optional;

/**
 * A generic interface for iterable repositories.
 *
 * @param <T>
 *            the class we want to manage in the repository (a table in the
 *            database)
 * @param <K>
 *            the class denoting the primary key of the table
 *
 * @author Paulo Gandra Sousa
 */
public interface IterableRepository<T, K> extends Repository<T, K>, Iterable<T> {

    /**
     * Returns an iterator over the repository with an hint of pagesize results
     * for each fetch from the persistence storage.
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
     * @return the first n entities according to its "natural" order.
     */
    Iterable<T> first(int n);
}
