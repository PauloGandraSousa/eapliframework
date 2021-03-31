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

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 * A repository is an abstraction to the persistence mechanism. If you are using
 * the DDD interfaces from the framework use
 * {@link eapli.framework.domain.repositories.DomainRepository} instead.
 *
 * @param <T>
 *            the class we want to manage in the repository
 * @param <K>
 *            the class denoting the primary key of the entity in the database
 *
 * @author Paulo Gandra Sousa
 */
public interface Repository<T, K> {

    /**
     * Saves an entity either by creating it or updating it in the persistence
     * store.Client code must assume the passed {@code entity} reference is no
     * longer valid and use the returned object instead. e.g.,
     *
     * <pre>
     * <code>
     * X myEntity = ...
     * ...
     * myEntity = repo.save(myEntity);
     * ...
     * </code>
     * </pre>
     *
     * @param entity
     * @return the object reference to the persisted object.
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    <S extends T> S save(S entity);

    /**
     * Gets all entities from the repository.
     *
     * @return all entities from the repository
     */
    Iterable<T> findAll();

    /**
     * Gets the entity with the specified primary key.
     *
     * @param id
     * @return the entity with the specified primary key
     */
    Optional<T> findById(K id);

    /**
     * Removes the specified entity from the repository.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void delete(T entity);

    /**
     * Removes the entity with the specified primary key from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void deleteById(K entityId);

    /**
     * Returns the number of entities in the repository.
     *
     * @return the number of entities in the repository
     */
    long count();
}
