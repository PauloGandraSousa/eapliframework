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

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.repositories.IterableRepository;
import eapli.framework.infrastructure.repositories.LockableRepository;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Preconditions;

/**
 * An utility class for implementing JPA repositories. This class' methods don't
 * initiate an explicit transaction relying on an outside Transaction-enabled
 * container.
 *
 * <p>
 * Check {@link JpaAutoTxRepository} if you want to have transaction control
 * inside the base class.
 *
 * <p>
 * Based on <a href=
 * "http://stackoverflow.com/questions/3888575/single-dao-generic-crud-methods-jpa-hibernate-spring"
 * > stackoverflow</a> and on
 * <a href="https://burtbeckwith.com/blog/?p=40">burtbeckwith</a>.
 *
 * <p>
 * Also have a look at
 * <a href="http://blog.xebia.com/tag/jpa-implementation-patterns/">JPA
 * implementation patterns</a>
 *
 * @param <T>
 *            the entity type that we want to build a repository for
 * @param <K>
 *            the primary key type of the table
 * @param <I>
 *            the type of the <b>business identity</b> of the entity
 *
 * @author Paulo Gandra Sousa
 *
 */
public class JpaBaseRepository<T, K, I> extends JpaAbstractRepository
        implements IterableRepository<T, K>, LockableRepository<T, K> {

    private static final String SELECT_E_FROM = "SELECT e FROM ";
    private static final String PARAMS_MUST_NOT_BE_NULL_OR_EMPTY = "Params must not be null or empty";
    private static final String QUERY_MUST_NOT_BE_NULL_OR_EMPTY = "query must not be null or empty";
    private static final int DEFAULT_PAGESIZE = 20;

    private final Class<T> entityClass;
    private final String identityFieldName;

    /**
     * Constructs a repository.
     *
     * @param identityFieldName
     *            name of the field that serves as the <em>business
     *            identity</em> of the managed class
     */
    @SuppressWarnings("unchecked")
    public JpaBaseRepository(final String identityFieldName) {
        final boolean isGeneric = getClass().getGenericSuperclass() instanceof ParameterizedType;
        if (!isGeneric) {
            throw new IllegalStateException(
                    "The repository must be parametrized to a specific managed class");
        }

        final ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.identityFieldName = identityFieldName;
    }

    /**
     * Internal constructor.
     *
     * @param classz
     * @param identityFieldName
     */
    /* package */ JpaBaseRepository(final Class<T> classz, final String identityFieldName) {
        assert classz != null : "you must parametrize the class";
        entityClass = classz;
        this.identityFieldName = identityFieldName;
    }

    /**
     * Adds a new entity to the persistence store.
     *
     * @param entity
     * @return the newly created persistent object
     * @throws IntegrityViolationException
     *             in case there is an underlying persistence store exception. Usually this is
     *             caused by a constraint
     *             violation (e.g., sql state 23505) but it will wrap any persistence exception
     */
    public T create(final T entity) {
        Preconditions.nonNull(entity);

        try {
            entityManager().persist(entity);
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
        }
        return entity;
    }

    /**
     * Reads an entity given its K.
     *
     * @param id
     * @return
     */
    protected Optional<T> read(final K id, final LockModeType locking) {
        return Optional.ofNullable(this.entityManager().find(this.entityClass, id, locking));
    }

    /**
     * Reads an entity given its primary key.
     *
     * @param id
     * @return an Optional which value is the entity with the requested primary key.
     *         An empty Optional if there is no such entity.
     */
    @Override
    public Optional<T> findById(final K id) {
        Preconditions.nonNull(id);

        return read(id, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    /**
     * Returns the name of the <b>business identity</b> field.
     *
     * @return the name of the <b>business identity</b> field
     */
    protected String identityFieldName() {
        return identityFieldName;
    }

    /**
     * Gets the entity with the specified business identity.
     *
     * @param id
     *            the business identity to look for
     * @return the entity with the specified identity.
     */
    public Optional<T> ofIdentity(final I id) {
        return matchOne("e." + identityFieldName() + " = :id", "id", id);
    }

    /**
     * Removes the entity with the specified identity from the repository.
     *
     * @param entityId
     *            the business identity to look for
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    public void deleteOfIdentity(final I entityId) {
        ofIdentity(entityId).ifPresent(this::delete);
    }

    /**
     * Updates an object in the persistence store. Calling code should not use the {@code entity}
     * reference aftewards
     * and should instead use the return value of this method.
     *
     * <pre>
     * <code>
     * Person p = ...
     * p.method1(..);
     * ...
     * Person u = repo.update(p); // p should not be used afterwards
     * u.method2(..);
     * ...
     * </code>
     * </pre>
     *
     * @param entity
     * @return the managed entity reference.
     * @see JpaBaseRepository#save
     */
    public T update(final T entity) {
        return save(entity);
    }

    /**
     * Removes the object from the persistence storage. The object reference is
     * still valid but the persisted entity is marked as deleted in the entity
     * manager.
     *
     * @param entity
     * @throws IntegrityViolationException
     *             in case there is an underlying persistence store exception. Usually this is
     *             caused by a constraint
     *             violation (e.g., sql state 23505) but it will wrap any persistence exception.
     */
    @Override
    @SuppressWarnings("squid:S1226")
    public void delete(T entity) {
        Preconditions.nonNull(entity);

        try {
            entity = entityManager().merge(entity);
            entityManager().remove(entity);
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
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
        Preconditions.nonNull(entityId);

        // this is not efficient as it will fetch the data first and only
        // afterwards will delete it. a more efficient implementation might
        // issue a DELETE statement directly
        findById(entityId).ifPresent(this::delete);
    }

    /**
     * Checks for the existence of an entity with the provided key.
     *
     * @param key
     * @return {@code true} if there is an entity with the provided key.
     */
    public boolean containsKey(final K key) {
        return findById(key).isPresent();
    }

    /**
     * Inserts or updates an entity.
     * <p>
     * Note that you should reference the return value to use the persisted entity,
     * as the original object passed as argument might be copied to a new object.
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
     * </p>
     * Check <a href=
     * "http://blog.xebia.com/2009/03/23/jpa-implementation-patterns-saving-detached-entities/"
     * > JPA implementation patterns</a> for a discussion on saveOrUpdate() behavior
     * and merge().
     *
     * @param entity
     * @return the persisted entity - might be a different object than the parameter
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     * @throws PersistenceException
     */
    @Override
    public <S extends T> S save(final S entity) {
        try {
            return entityManager().merge(entity);
        } catch (final OptimisticLockException ex) {
            throw new ConcurrencyException(ex);
        } catch (final PersistenceException ex) {
            throw handlePersistenceException(ex);
        }
    }

    private RuntimeException handlePersistenceException(final PersistenceException ex) {
        if (ex.getCause() instanceof OptimisticLockException) {
            throw new ConcurrencyException(ex);
        }
        if (ex.getCause() instanceof EntityExistsException
                || ex.getCause() instanceof ConstraintViolationException) {
            throw new IntegrityViolationException(ex);
        } else {
            throw ex;
        }
    }

    @Override
    public T lockItUp(final T entity) {
        Preconditions.nonNull(entity);

        try {
            entityManager().lock(entity, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            return entity;
        } catch (final PessimisticLockException e) {
            throw new ConcurrencyException(e);
        }
    }

    @Override
    public Optional<T> lockById(final K id) {
        Preconditions.nonNull(id);

        return read(id, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<T> lockOfIdentity(final I id) {
        return matchOne(LockModeType.PESSIMISTIC_FORCE_INCREMENT, "e." + identityFieldName() + " = :id", "id", id);
    }

    /**
     * Helper method to create a typed query.
     *
     * @return
     */
    protected TypedQuery<T> queryAll() {
        final String className = this.entityClass.getSimpleName();
        return entityManager().createQuery(SELECT_E_FROM + className + " e ", this.entityClass);
    }

    /**
     * Helper method to create a typed query. Since this method concatenates the
     * where clause, be careful if this results from external input, e.g., user
     * entered data, to avoid SQL injection exploits.
     * <p>
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param where
     * @return
     */
    @SuppressWarnings({ "squid:S3346", "squid:S2077" })
    private TypedQuery<T> query(final LockModeType locking, final String where) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final String className = this.entityClass.getSimpleName();
        final var query = entityManager().createQuery(SELECT_E_FROM + className + " e WHERE " + where,
                this.entityClass);
        if (locking != null) {
            query.setLockMode(locking);
        }
        return query;
    }

    /**
     * Helper method to create a typed query with a where clause.
     *
     * @param where
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected TypedQuery<T> query(final LockModeType locking, final String where, final Map<String, Object> params) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, where);
        params.entrySet().stream().forEach(e -> q.setParameter(e.getKey(), e.getValue()));
        return q;
    }

    /**
     * Helper method to create a typed query with a where clause.
     *
     * @param where
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected TypedQuery<T> query(final LockModeType locking, final String where, final Object... args) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args != null && args.length >= 2 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args.length % 2 == 0 : "uneven number of arguments passed";

        final TypedQuery<T> q = query(locking, where);
        boolean handleAsArgName = true;
        String argName = "";
        for (final Object o : args) {
            if (handleAsArgName) {
                argName = (String) o;
            } else {
                q.setParameter(argName, o);
            }
            handleAsArgName = !handleAsArgName;
        }
        return q;
    }

    /**
     * Returns the first <em>n</em> entities according to its "natural" order.
     *
     * @param n
     * @return the first <em>n</em> entities according to its "natural" order
     */
    @Override
    public List<T> first(final int n) {
        Preconditions.isPositive(n);

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(n);
        return q.getResultList();
    }

    /**
     * Returns the first entity according to its "natural" order.
     *
     * @return the first entity according to its "natural" order.
     */
    @Override
    public Optional<T> first() {
        final List<T> r = first(1);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    /**
     * Fetch a "page" of results. This method should be used when there is a large number of objects
     * in the persistence store and making a {@link #findAll()} will simply be impractical.
     *
     * @param pageNumber
     * @param pageSize
     * @return the results
     */
    public List<T> page(final int pageNumber, final int pageSize) {
        Preconditions.isPositive(pageNumber);
        Preconditions.isPositive(pageSize);

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(pageSize);
        q.setFirstResult((pageNumber - 1) * pageSize);

        return q.getResultList();
    }

    /**
     * Returns a paged iterator.
     *
     * @return a paged iterator
     */
    @Override
    public Iterator<T> iterator(final int pagesize) {
        return new JpaPagedIterator(this, pagesize);
    }

    @Override
    public Iterator<T> iterator() {
        return new JpaPagedIterator(this, DEFAULT_PAGESIZE);
    }

    @Override
    public Iterable<T> findAll() {
        return queryAll().getResultList();
    }

    /**
     * Searches for objects that match the given criteria. Be careful if the content
     * of the where parameter is coming from external input, e.g., user entered
     * data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param where
     *            the where clause should use "e" as the query object
     *
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected List<T> match(final LockModeType locking, final String where) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, where);
        return q.getResultList();
    }

    protected List<T> match(final String where) {
        return match(null, where);
    }

    /**
     * Searches for objects that match the given criteria. Be careful if the content
     * of the where parameter is coming from external input, e.g., user entered
     * data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param whereWithParameters
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected List<T> match(final LockModeType locking, final String whereWithParameters,
            final Map<String, Object> params) {
        assert !StringPredicates
                .isNullOrEmpty(whereWithParameters) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(locking, whereWithParameters, params);
        return q.getResultList();
    }

    protected List<T> match(final String whereWithParameters,
            final Map<String, Object> params) {
        return match(null, whereWithParameters, params);
    }

    /**
     * Searches for one object that matches the given criteria. Be careful if the
     * content of the where parameter is coming from external input, e.g., user
     * entered data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param where
     *            the where clause should use "e" as the query object
     * @return
     */
    protected Optional<T> matchOne(final LockModeType locking, final String where) {
        final TypedQuery<T> q = query(locking, where);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String where) {
        return matchOne(null, where);
    }

    /**
     * Searches for one object that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param whereWithParameters
     *            the where clause should use "e" as the query object
     * @param params
     *            a map of the parameter name and its value to use in the query
     * @return
     */
    protected Optional<T> matchOne(final LockModeType locking, final String whereWithParameters,
            final Map<String, Object> params) {
        final TypedQuery<T> q = query(locking, whereWithParameters, params);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String whereWithParameters, final Map<String, Object> params) {
        return matchOne(null, whereWithParameters, params);
    }

    /**
     * Searches for one object that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. Each odd parameter
     *            is assumed to be the name of the parameter; each even parameter
     *            is assumed to be the value of the preceding parameter.
     * @return
     */
    protected Optional<T> matchOne(final LockModeType locking, final String where, final Object... args) {
        final TypedQuery<T> q = query(locking, where, args);
        return getSingleResultAsOptional(q);
    }

    protected Optional<T> matchOne(final String where, final Object... args) {
        return matchOne(null, where, args);
    }

    /**
     * Searches for objects that matches the given criteria with parameters. Be
     * careful if the content of the where parameter is coming from external input,
     * e.g., user entered data, to avoid SQL injection exploits.
     *
     * @param locking
     *            the explicitly locking mode to set. pass {@code null} if you want to use the
     *            default locking strategy.
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. Each odd parameter
     *            is assumed to be the name of the parameter; each even parameter
     *            is assumed to be the value of the preceding parameter.
     * @return
     */
    protected List<T> match(final LockModeType locking, final String where, final Object... args) {
        final TypedQuery<T> q = query(locking, where, args);
        return q.getResultList();
    }

    protected List<T> match(final String where, final Object... args) {
        return match(null, where, args);
    }

    /**
     * Avoid {@code NoResultException} and use Optional instead to signal that there are no results
     * to this query.
     *
     * @param q
     * @return
     */
    private Optional<T> getSingleResultAsOptional(final TypedQuery<T> q) {
        try {
            final T ret = q.getSingleResult();
            return Optional.of(ret);
        } catch (@SuppressWarnings("unused") final NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the number of entities in the persistence store.
     *
     * @return the number of entities in the persistence store
     */
    @Override
    public long count() {
        final TypedQuery<Long> q = createQuery(
                "SELECT COUNT(tablename) FROM " + entityClass.getSimpleName() + " tablename",
                Long.class);
        return q.getSingleResult();
    }

    /**
     * Returns the number of entities in the persistence store. A alias to {@link #count()} but
     * using the {@code List} terminology.
     *
     * @return the number of entities in the persistence store
     */
    public long size() {
        return count();
    }

    /**
     * An iterator over JPA.
     *
     * @author Paulo Gandra Sousa
     *
     */
    private class JpaPagedIterator implements Iterator<T> {

        private final JpaBaseRepository<T, K, I> repository;
        private final int pageSize;
        private int currentPageNumber;
        private Iterator<T> currentPage;

        private JpaPagedIterator(final JpaBaseRepository<T, K, I> repository, final int pagesize) {
            this.repository = repository;
            this.pageSize = pagesize;
        }

        @Override
        public boolean hasNext() {
            if (needsToLoadPage()) {
                loadNextPage();
            }
            return this.currentPage.hasNext();
        }

        @Override
        public T next() {
            if (needsToLoadPage()) {
                loadNextPage();
            }
            return this.currentPage.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void loadNextPage() {
            final List<T> page = this.repository.page(++this.currentPageNumber, this.pageSize);
            this.currentPage = page.iterator();
        }

        private boolean needsToLoadPage() {
            // either we do not have an iterator yet or we have reached the end
            // of the (current) iterator
            return this.currentPage == null || !this.currentPage.hasNext();
        }
    }
}
