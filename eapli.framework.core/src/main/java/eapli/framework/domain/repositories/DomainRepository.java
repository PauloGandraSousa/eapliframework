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

/**
 * A repository is a domain-driven design pattern to abstract the details of
 * persisting domain objects. It exposes a pure domain based interface without
 * leaking details of the implementation of the actual persistence mechanism.
 * there should be one repository per aggregate root.
 *
 * if you are not using the DDD interfaces of the framework use
 * {@link eapli.framework.infrastructure.repositories.Repository} instead
 *
 * @param <T>
 *            the type of the entity we want to manage in the repository.
 * @param <I>
 *            the type of the business identity of the entity.
 *
 * @author Paulo Gandra Sousa
 */
public interface DomainRepository<I extends Comparable<I>/* & ValueObject */, T extends AggregateRoot<I>> {

    /**
     * Saves an entity either by creating it or updating it in the persistence
     * store.
     *
     * @param entity
     * @return
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    <S extends T> S save(final S entity);

    /**
     * gets all entities from the repository.
     *
     * @return
     */
    Iterable<T> findAll();

    /**
     * gets the entity with the specified identity.
     *
     * @param id
     * @return
     */
    Optional<T> ofIdentity(final I id);

    /**
     * checks for the existence of the entity identified by id
     *
     * @param id
     * @return
     */
    default boolean containsOfIdentity(final I id) {
        return ofIdentity(id).isPresent();
    }

    /**
     * checks for the existence of the entity
     *
     * @param id
     * @return
     */
    default boolean contains(final T entity) {
        return containsOfIdentity(entity.identity());
    }

    /**
     * removes the specified entity from the repository.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void delete(final T entity);

    /**
     * Removes the entity with the specified identity from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void deleteOfIdentity(final I entityId);

    /**
     * Returns the number of entities in the repository.
     *
     * @return
     */
    long count();

    // ALIAS

    /**
     * Returns the number of entities in the repository.
     *
     * @return
     */
    default long size() {
        return count();
    }

    /**
     * removes the specified entity from the repository.
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
     * Removes the entity with the specified identity from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void removeOfIdentity(final I entityId) {
        deleteOfIdentity(entityId);
    }
}
