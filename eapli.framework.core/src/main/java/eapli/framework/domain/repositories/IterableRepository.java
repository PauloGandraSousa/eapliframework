/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.domain.repositories;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;

/**
 * A generic interface for iterable repositories.
 *
 * @param T
 *            the class we want to manage in the repository (a table in the
 *            database)
 * @param K
 *            the class denoting the primary key of the table
 * @author Paulo Gandra Sousa
 */
public interface IterableRepository<T, K extends Serializable> extends Repository<T, K>, Iterable<T> {

    /**
     * returns an iterator over the repository with an hint of pagesize results for
     * each fetch from the persistence storage
     *
     * @param pagesize
     *            hint about the result size of each fetch to the persistence
     *            storage
     * @return an iterator over the repository
     */
    Iterator<T> iterator(int pagesize);

    /**
     * returns the first entity according to its "natural" order
     *
     * @return
     */
    Optional<T> first();

    /**
     * returns the first n entities according to its "natural" order
     *
     * @param n
     * @return
     */
    Iterable<T> first(int n);
}
