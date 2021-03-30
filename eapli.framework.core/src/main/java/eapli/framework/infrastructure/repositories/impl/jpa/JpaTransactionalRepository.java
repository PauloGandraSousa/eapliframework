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
package eapli.framework.infrastructure.repositories.impl.jpa;

import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.validations.Preconditions;

/**
 * An utility class for implementing JPA repositories not running in containers and not relying on
 * external transaction managers. This class' methods initiate an explicit transaction and commit in
 * the end of the method.
 * <p>
 * Check {@link JpaBaseRepository} if you want to have transaction control outside of the base class
 * (for instance, when using a JPA container). If you are not using a container and all your
 * controllers update just one aggregate, use this class that explicitly opens and closes a
 * transaction in each method. If you need fine control over when to commit or rollback your
 * transactions or need to enroll more than one repository in the same transaction use the
 * {@link JpaAutoTxRepository} class.
 *
 * <p>
 * We are closing the entity manager at the end of each method (in the finally block) because this
 * code is running in a non-container managed way. If it was the case to be running under an
 * application server with a JPA container and managed transactions/sessions, one should not be
 * doing this.
 *
 * @param <T>
 *            the entity type that we want to build a repository for
 * @param <K>
 *            the primary key type of the table
 * @param <I>
 *            the type of the <b>business identity</b> of the entity
 *
 * @author Paulo Gandra Sousa
 */
public class JpaTransactionalRepository<T, K, I> extends JpaWithTransactionalContextRepository<T, K, I> {

    /**
     * Constructor.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    public JpaTransactionalRepository(final String persistenceUnitName, final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName), identityFieldName);
    }

    /**
     * Internal constructor.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param classz
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    /* package */ JpaTransactionalRepository(final String persistenceUnitName, final Class<T> classz,
            final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName), classz, identityFieldName);
    }

    /**
     * Constructor.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param properties
     *            the extended properties to pass to JPA
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    public JpaTransactionalRepository(final String persistenceUnitName,
            @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties,
            final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName, properties), identityFieldName);
    }

    /**
     * Internal constructor.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param properties
     *            the extended properties to pass to JPA
     * @param classz
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    /* package */ JpaTransactionalRepository(final String persistenceUnitName,
            @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties,
            final Class<T> classz,
            final String identityFieldName) {
        super(new JpaTransactionalContext(persistenceUnitName, properties), classz,
                identityFieldName);
    }

    /**
     * Removes the object from the persistence storage. The object reference is still "valid" but
     * should not be used in the code afterwards. The persisted entity is/will be deleted in the
     * database.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws RollbackException
     */
    @Override
    public void delete(final T entity) {
        try {
            context().beginTransaction();
            super.delete(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }
    }

    /**
     * Removes the entity with the specified ID from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    @Override
    public void deleteById(final K entityId) {
        try {
            context().beginTransaction();
            super.deleteById(entityId);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }
    }

    /**
     * Adds <b>and commits</b> a new entity to the persistence store.
     *
     * @param entity
     * @return the newly created persistent object
     * @throws IntegrityViolationException
     * @throws RollbackException
     */
    @Override
    public T create(final T entity) {
        try {
            context().beginTransaction();
            super.create(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }

        return entity;
    }

    /**
     * Inserts or updates an entity.
     *
     * <p>
     * Note that you should reference the return value to use the persisted entity, as the original
     * object passed as argument might be copied to a new object.
     *
     * <pre>
     * <code>
     * Person p = ...
     * p.method1(..);
     * ...
     * Person u = repo.save(p); // p should not be used afterwards
     * u.method2(..);
     * ...
     * </code>
     * </pre>
     *
     * <p>
     * check
     * <a href=
     * "http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/"
     * > JPA implementation patterns</a> for a discussion on saveOrUpdate() Behaviour and merge()
     *
     * @param entity
     * @return the persisted entity - might be a different object than the parameter
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     * @throws RollbackException
     */
    @Override
    @SuppressWarnings("squid:S1226")
    public <S extends T> S save(S entity) {
        Preconditions.nonNull(entity);

        try {
            context().beginTransaction();
            entity = super.save(entity);
            context().commit();
        } catch (final RollbackException e) {
            handleRollbackException(e);
        } finally {
            context().close();
        }

        return entity;
    }

    private RuntimeException handleRollbackException(final RollbackException e) {
        // get the cause PersistenceException
        final Throwable ex = e.getCause();
        if (ex.getCause() instanceof OptimisticLockException) {
            throw new ConcurrencyException(e);
        }
        if (ex.getCause() instanceof EntityExistsException
                || ex.getCause() instanceof ConstraintViolationException) {
            throw new IntegrityViolationException(e);
        } else {
            throw e;
        }
    }
}
