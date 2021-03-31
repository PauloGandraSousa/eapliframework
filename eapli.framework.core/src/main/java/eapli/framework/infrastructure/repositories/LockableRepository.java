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

import java.util.Optional;

import org.springframework.data.repository.query.Param;

/**
 * Support for pessimistic locking.
 *
 * @param <T>
 *            the class we want to manage in the repository
 * @param <K>
 *            the class denoting the primary key of the entity in the database
 *
 * @author Paulo Gandra Sousa 2021.03.31
 * @see Repository
 */
public interface LockableRepository<T, K> {

    /**
     * Acquires an exclusive write lock to a managed entity previously fetched from the persistence
     * storage.
     * <p>
     *
     * <pre>
     * products = repo.findAll();
     * ...
     * Product selected = products.get(selectedIdx);
     * Product locked = repo.lockItUp(selected);
     * ...
     * locked.changeDescription(...);
     * ...
     * Product saved = repo.save(locked);
     * </pre>
     *
     * @param entity
     * @return the managed entity
     */
    T lockItUp(@Param("entity") T entity);

    /**
     * Loads and acquires an exclusive write lock to a managed entity.
     * <p>
     * Each specific repository MUST implement this method and define the exact JPQL query
     *
     * @param id
     * @return the specified entity
     */
    Optional<T> lockById(@Param("id") K id);
}
