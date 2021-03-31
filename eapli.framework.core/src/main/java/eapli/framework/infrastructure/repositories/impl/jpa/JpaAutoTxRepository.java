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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.repositories.impl.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.IterableRepository;
import eapli.framework.infrastructure.repositories.LockableRepository;
import eapli.framework.validations.Preconditions;

/**
 * A JPA repository implementation to handle transactions, for use with applications <em>not
 * running</em> inside a container. This class can either support working within a
 * {@link TransactionalContext} or a single immediate transaction mode. If running in single
 * immediate mode a transaction is initiated, committed and the connection closed in the scope of
 * each repository method call.
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
public class JpaAutoTxRepository<T, K, I> implements IterableRepository<T, K>, LockableRepository<T, K> {

    protected final JpaBaseRepository<T, K, I> repo;
    private final TransactionalContext autoTx;

    /**
     * Constructor for single transaction mode.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    @SuppressWarnings({ "rawtypes", "java:S3740" })
    public JpaAutoTxRepository(final String persistenceUnitName, final String identityFieldName) {
        this(persistenceUnitName, new HashMap(), identityFieldName);
    }

    /**
     * Constructor for single transaction mode.
     *
     * @param persistenceUnitName
     *            the name of the persistence unit
     * @param properties
     *            the extended properties to pass to JPA
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    public JpaAutoTxRepository(final String persistenceUnitName,
            @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties,
            final String identityFieldName) {

        final ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();

        @SuppressWarnings("unchecked")
        final Class<T> entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

        repo = new JpaTransactionalRepository<>(persistenceUnitName, properties, entityClass, identityFieldName);
        autoTx = null;
    }

    /**
     * Constructor for transactional context mode.
     *
     * @param tx
     *            the transactional context to enroll
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    public JpaAutoTxRepository(final TransactionalContext tx, final String identityFieldName) {
        Preconditions.nonNull(tx);

        final ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();

        @SuppressWarnings("unchecked")
        final Class<T> entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

        repo = new JpaWithTransactionalContextRepository<>(tx, entityClass, identityFieldName);
        autoTx = tx;
    }

    /**
     * Factory method to create a new JPA transaction.
     *
     * @param persistenceUnitName
     * @return a new transaction context
     */
    public static TransactionalContext buildTransactionalContext(final String persistenceUnitName) {
        return new JpaTransactionalContext(persistenceUnitName);
    }

    /**
     * Factory method to create a new JPA transaction.
     *
     * @param persistenceUnitName
     * @param properties
     * @return a new transaction context
     */
    public static TransactionalContext buildTransactionalContext(final String persistenceUnitName,
            @SuppressWarnings({ "rawtypes", "java:S3740" }) final Map properties) {
        return new JpaTransactionalContext(persistenceUnitName, properties);
    }

    /**
     * Returns the transactional context this repository is enrolled.
     *
     * @return the transactional context this repository is enrolled or {@code null} if not enrolled
     */
    public TransactionalContext context() {
        // TODO this method should return an Optional
        return autoTx;
    }

    /**
     * Returns if the repository is running in single transaction mode or within
     * a TransactionalContext.
     *
     * @return {@code true} if the repository is running in single transaction mode
     *         {@code false} if running within a Transactional Context
     * @deprecated the name of this method is misleading. Use
     *             {@link #isPartOfTransactionalContext()} instead.
     */
    @Deprecated(forRemoval = true)
    public boolean isInTransaction() {
        return context() == null;
    }

    /**
     * Checks if the repository is running as part of a transaction context.
     *
     * @return {@code true} if the repository is running within a Transactional Context
     */
    public boolean isPartOfTransactionalContext() {
        return context() == null;
    }

    @Override
    public void delete(final T entity) {
        repo.delete(entity);
    }

    @Override
    public void deleteById(final K id) {
        repo.deleteById(id);
    }

    /**
     * Removes the entity with the specified identity from the repository.
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    public void deleteOfIdentity(final I entityId) {
        repo.deleteOfIdentity(entityId);
    }

    @Override
    public <S extends T> S save(final S entity) {
        return repo.save(entity);
    }

    @Override
    public Iterable<T> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<T> findById(final K id) {
        return repo.findById(id);
    }

    /**
     * Gets the entity with the specified business identity.
     *
     * @param id
     *            the business identity to look for
     * @return the entity with the specified identity.
     */
    public Optional<T> ofIdentity(final I id) {
        return repo.ofIdentity(id);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public Iterator<T> iterator() {
        return repo.iterator();
    }

    /**
     * Searches for objects that match the given criteria. Be careful if the content
     * of the where parameter is coming from external input, e.g., user entered
     * data, to avoid SQL injection exploits.
     *
     * @param where
     *            the where clause should use "e" as the query object
     *
     * @return
     */
    protected List<T> match(final String where) {
        return repo.match(where);
    }

    /**
     * Searches for objects that match the given criteria. Be careful if the content
     * of the where parameter is coming from external input, e.g., user entered
     * data, to avoid SQL injection exploits.
     *
     * @param whereWithParameters
     * @param params
     * @return
     */
    protected List<T> match(final LockModeType locking, final String whereWithParameters,
            final Map<String, Object> params) {
        return repo.match(locking, whereWithParameters, params);
    }

    protected List<T> match(final String whereWithParameters, final Map<String, Object> params) {
        return repo.match(whereWithParameters, params);
    }

    /**
     * Searches for objects that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. Each odd parameter
     *            is assumed to be the name of the parameter; each even parameter
     *            is assumed to be the value of the preceding parameter.
     * @return
     */
    protected List<T> match(final String where, final Object... args) {
        return repo.match(where, args);
    }

    /**
     * Searches for one object that matches the given criteria. Be careful if the
     * content of the where parameter is coming from external input, e.g., user
     * entered data, to avoid SQL injection exploits.
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @return
     */
    protected Optional<T> matchOne(final String where) {
        return repo.matchOne(where);
    }

    /**
     * Searches for one object that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param whereWithParameters
     *            the where clause should use "e" as the query object
     * @param params
     *            a map of the parameter name and its value to use in the query
     * @return
     */
    protected Optional<T> matchOne(final LockModeType locking, final String whereWithParameters,
            final Map<String, Object> params) {
        return repo.matchOne(locking, whereWithParameters, params);
    }

    protected Optional<T> matchOne(final String whereWithParameters, final Map<String, Object> params) {
        return repo.matchOne(whereWithParameters, params);
    }

    /**
     * Searches for one object that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. Each odd parameter
     *            is assumed to be the name of the parameter; each even parameter
     *            is assumed to be the value of the preceding parameter.
     * @return
     */
    protected Optional<T> matchOne(final LockModeType locking, final String where, final Object... args) {
        return repo.matchOne(locking, where, args);
    }

    /**
     * Allows to create a typed query (SELECT, INSERT, UPDATE, DELETE) in case the {@code match*()}
     * family of methods is not enough.
     *
     * @param <R>
     * @param jpql
     * @param classz
     * @return
     */
    protected <U> TypedQuery<U> createQuery(final String queryString, final Class<U> class1) {
        return repo.createQuery(queryString, class1);
    }

    @Override
    public Iterator<T> iterator(final int pagesize) {
        return repo.iterator(pagesize);
    }

    @Override
    public Optional<T> first() {
        return repo.first();
    }

    @Override
    public Iterable<T> first(final int n) {
        return repo.first(n);
    }

    @Override
    public T lockItUp(final T entity) {
        return repo.lockItUp(entity);
    }

    @Override
    public Optional<T> lockById(final K id) {
        return repo.lockById(id);
    }

    public Optional<T> lockOfIdentity(final I id) {
        return repo.lockOfIdentity(id);
    }
}
