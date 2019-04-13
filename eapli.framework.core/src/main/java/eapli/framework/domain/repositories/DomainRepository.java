/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.domain.repositories;

import java.util.Optional;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.ValueObject;

/**
 * A repository is a domain-driven design pattern to abstract the details of
 * persisting domain objects. it exposes a pure domain based interface without
 * leaking details of the implementation of the actual persistence mechanism.
 * there should be one repository per aggregate root
 *
 * @param <T>
 *            the type of the entity we want to manage in the repository.
 * @param <I>
 *            the type of the business identity of the entity. not the primary
 *            key
 *
 * @author Paulo Gandra Sousa
 */
public interface DomainRepository<I extends ValueObject, T extends AggregateRoot<I>> {

    /**
     * removes the specified entity from the repository.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void delete(T entity);

    /**
     * Removes the entity with the specified identity from the repository.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void deleteWithIdentity(I entityId);

    /**
     * removes the specified entity from the repository. Alias to delete() in
     * order to better fit the "List"-like nature of a repository
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void remove(final T entity) {
        delete(entity);
    }

    /**
     * Removes the entity with the specified identity from the repository. Alias
     * to deleteById() in order to better fit the "List"-like nature of a
     * repository
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void removeWithIdentity(final I entityId) {
        deleteWithIdentity(entityId);
    }

    /**
     * Saves an entity either by creating it or updating it in the persistence
     * store.
     *
     * @param entity
     * @return
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    <S extends T> S save(S entity);

    /**
     * gets all entities from the repository.
     *
     * @return
     */
    Iterable<T> findAll();

    /**
     * gets the entity with the specified identity
     *
     * @param id
     * @return
     */
    Optional<T> get(I id);

    /**
     * Gets the entity with the specified primary key. Alias to findById() in
     * order to better fit the "List"-like nature of a repository
     *
     * @param id
     * @return
     */
    default boolean contains(final I id) {
        return get(id).isPresent();
    }

    /**
     * returns the number of entities in the repository.
     *
     * @return
     */
    long count();

    /**
     * Returns the number of entities in the repository. Alias to count() in
     * order to better fit the "List"-like nature of a repository
     *
     * @return
     */
    default long size() {
        return count();
    }
}
