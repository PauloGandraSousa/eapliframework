/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.persistence.repositories;

import java.io.Serializable;
import java.util.Optional;

/**
 * A generic interface for repositories. A repository is a domain-driven design
 * pattern to abstract the details of persisting domain objects. it exposes a
 * pure domain based interface without leaking details of the implementation of
 * the actual persistence mechanism. there should be one repository per
 * aggregate root
 *
 * @param T
 *            the class we want to manage in the repository. Eventough it is not
 *            enforced to make this interface more reusable, T should extend
 *            AggregateRoot<?>
 * @param K
 *            the class denoting the primary key of the entity in the database
 * @author Paulo Gandra Sousa
 */
public interface DataRepository<T, K extends Serializable> {

    /**
     * removes the specified entity from the repository.
     *
     * @param entity
     * @throws DataIntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void delete(T entity) throws DataIntegrityViolationException;

    /**
     * Removes the entity with the specified primary key from the repository.
     *
     * @param entity
     * @throws DataIntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void deleteById(K entityId) throws DataIntegrityViolationException;

    /**
     * removes the specified entity from the repository. Alias to delete() in
     * order to better fit the "List"-like nature of a repository
     *
     * @param entity
     * @throws DataIntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void remove(final T entity) throws DataIntegrityViolationException {
        delete(entity);
    }

    /**
     * Removes the entity with the specified primary key from the repository.
     * Alias to deleteById() in order to better fit the "List"-like nature of a
     * repository
     *
     * @param entity
     * @throws DataIntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void remove(final K entityId) throws DataIntegrityViolationException {
        deleteById(entityId);
    }

    /**
     * Saves an entity either by creating it or updating it in the persistence
     * store.
     *
     * @param entity
     * @return
     * @throws DataConcurrencyException
     * @throws DataIntegrityViolationException
     */
    <S extends T> S save(S entity) throws DataConcurrencyException, DataIntegrityViolationException;

    /**
     * gets all entities from the repository.
     *
     * @return
     */
    Iterable<T> findAll();

    /**
     * gets the entity with the specified primary key
     *
     * @param id
     * @return
     */
    Optional<T> findById(K id);

    /**
     * Gets the entity with the specified primary key. Alias to findById() in
     * order to better fit the "List"-like nature of a repository
     *
     * @param id
     * @return
     */
    default boolean contains(final K id) {
        return findById(id).isPresent();
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
