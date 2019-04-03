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
package eapli.framework.infrastructure.repositories.impl.jpa;

import java.io.Serializable;
import java.util.Map;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.util.Preconditions;

/**
 * An utility class for implementing JPA repositories not running in containers
 * and not relying on external transaction managers. This class' methods
 * initiate an explicit transaction and commit in the end of the method. check
 * JpaBaseRepository if you want to have transaction control outside of the base
 * class (for instance, when using a JPA container). If you are not using a
 * container and all your controllers update just one aggregate, use this class
 * that explicitly opens and closes a transaction in each method.
 *
 * we are closing the entity manager at the end of each method (in the finally
 * block) because this code is running in a non-container managed way. if it was
 * the case to be running under an application server with a JPA container and
 * managed transactions/sessions, one should not be doing this
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the entity type managed by this repository (a table in the
 *            database)
 * @param <K>
 *            the primary key of the table
 */
public class JpaTransactionalRepository<T, K extends Serializable> extends JpaWithTransactionalContextRepository<T, K> {

    public JpaTransactionalRepository(final String persistenceUnitName) {
        super(new JpaTransactionalContext(persistenceUnitName));
    }

    /* package */ JpaTransactionalRepository(final String persistenceUnitName, final Class<T> classz) {
        super(new JpaTransactionalContext(persistenceUnitName), classz);
    }

    public JpaTransactionalRepository(final String persistenceUnitName,
            @SuppressWarnings("rawtypes") final Map properties) {
        super(new JpaTransactionalContext(persistenceUnitName, properties));
    }

    /* package */ JpaTransactionalRepository(final String persistenceUnitName,
            @SuppressWarnings("rawtypes") final Map properties, final Class<T> classz) {
        super(new JpaTransactionalContext(persistenceUnitName, properties), classz);
    }

    /**
     * removes the object from the persistence storage. the object reference is
     * still valid but the persisted entity is/will be deleted
     *
     * @param entity
     * @throws IntegrityViolationException
     */
    @Override
    public void delete(final T entity) {
        try {
            context().beginTransaction();
            super.delete(entity);
            context().commit();
        } finally {
            context().close();
        }
    }

    /**
     * Removes the entity with the specified ID from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsuportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    @Override
    public void deleteById(final K entityId) {
        try {
            context().beginTransaction();
            super.deleteById(entityId);
            context().commit();
        } finally {
            context().close();
        }
    }

    /**
     * adds <b>and commits</b> a new entity to the persistence store
     *
     * @param entity
     * @return the newly created persistent object
     * @throws IntegrityViolationException
     */
    @Override
    public T create(final T entity) {
        try {
            context().beginTransaction();
            super.create(entity);
            context().commit();
        } finally {
            context().close();
        }

        return entity;
    }

    /**
     * Inserts or updates an entity <b>and commits</b>.
     *
     * note that you should reference the return value to use the persisted
     * entity, as the original object passed as argument might be copied to a
     * new object
     *
     * check <a href=
     * "http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/"
     * > JPA implementation patterns</a> for a discussion on saveOrUpdate()
     * Behaviour and merge()
     *
     * @param entity
     * @return the persisted entity - might be a different object than the
     *         parameter
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    @Override
    @SuppressWarnings("squid:S1226")
    public <S extends T> S save(S entity) {
        Preconditions.nonNull(entity);

        try {
            context().beginTransaction();
            entity = super.save(entity);
            context().commit();
        } finally {
            context().close();
        }

        return entity;
    }
}
