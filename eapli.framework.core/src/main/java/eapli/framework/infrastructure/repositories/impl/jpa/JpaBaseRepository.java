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
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.domain.repositories.IterableRepository;
import eapli.framework.util.Invariants;
import eapli.framework.util.Preconditions;
import eapli.framework.util.predicates.StringPredicates;

/**
 * An utility class for implementing JPA repositories. This class' methods don't
 * initiate an explicit transaction relying on an outside Transaction-enabled
 * container.
 *
 * check JpaAutoTxRepository if you want to have transaction control inside the
 * base class.
 *
 * <p>
 * based on <a href=
 * "http://stackoverflow.com/questions/3888575/single-dao-generic-crud-methods-jpa-hibernate-spring"
 * > stackoverflow</a> and on
 * <a href="https://burtbeckwith.com/blog/?p=40">burtbeckwith</a>.
 * <p>
 * also have a look at
 * <a href="http://blog.xebia.com/tag/jpa-implementation-patterns/">JPA
 * implementation patterns</a>
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the entity type that we want to build a repository for
 * @param <K>
 *            the key type of the entity
 */
public abstract class JpaBaseRepository<T, K extends Serializable> implements IterableRepository<T, K> {

    private static final String SELECT_E_FROM = "SELECT e FROM ";
    private static final String PARAMS_MUST_NOT_BE_NULL_OR_EMPTY = "Params must not be null or empty";
    private static final String THIS_REPOSITORY_WAS_NOT_PARAMETRIZED_AT_CONSTRUCTION_TIME = "This repository was not parametrized at construction time";
    private static final String QUERY_MUST_NOT_BE_NULL_OR_EMPTY = "query must not be null or empty";
    private static final int DEFAULT_PAGESIZE = 20;

    protected final Class<T> entityClass;

    // will be injected by Spring Container
    @PersistenceUnit
    private EntityManagerFactory emFactory;
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public JpaBaseRepository() {
        final Boolean isGeneric = getClass().getGenericSuperclass() instanceof ParameterizedType;
        if (isGeneric) {
            final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        } else {
            // special case when this class is used without parametrization.
            // we are assuming this only happens if you are creating a pure
            // Reporting Repository
            entityClass = null;
        }
    }

    /* package */ JpaBaseRepository(final Class<T> classz) {
        assert classz != null : "you must parametrize the class";
        entityClass = classz;
    }

    protected EntityManagerFactory entityManagerFactory() {
        return this.emFactory;
    }

    protected EntityManager entityManager() {
        if (this.entityManager == null || !this.entityManager.isOpen()) {
            this.entityManager = entityManagerFactory().createEntityManager();
        }
        return this.entityManager;
    }

    /**
     * adds a new entity to the persistence store
     *
     * @param entity
     * @return the newly created persistent object
     * @throws IntegrityViolationException
     *             in case there is an underlying persistence store exception.
     *             usually this is caused by a constraint violation (e.g., sql
     *             state 23505) but it will wrap any persistence exception
     */
    public T create(final T entity) {
        Preconditions.nonNull(entity);

        try {
            this.entityManager().persist(entity);
        } catch (final PersistenceException ex) {
            throw new IntegrityViolationException(ex);
        }
        return entity;
    }

    /**
     * reads an entity given its K
     *
     * @param id
     * @return
     */
    protected Optional<T> read(final K id) {
        Invariants.nonNull(entityClass, THIS_REPOSITORY_WAS_NOT_PARAMETRIZED_AT_CONSTRUCTION_TIME);

        return Optional.ofNullable(this.entityManager().find(this.entityClass, id));
    }

    /**
     * reads an entity given its K
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(final K id) {
        Preconditions.nonNull(id);

        return read(id);
    }

    public T update(final T entity) {
        return save(entity);
    }

    /**
     * removes the object from the persistence storage. the object reference is
     * still valid but the persisted entity is marked as deleted in the entity
     * manager
     *
     * @param entity
     * @throws IntegrityViolationException
     *             in case there is an underlying persistence store exception.
     *             usually this is caused by a constraint violation (e.g., sql
     *             state 23505) but it will wrap any persistence exception
     */
    @Override
    @SuppressWarnings("squid:S1226")
    public void delete(T entity) {
        Preconditions.nonNull(entity);

        try {
            entity = entityManager().merge(entity);
            entityManager().remove(entity);
        } catch (final PersistenceException ex) {
            throw new IntegrityViolationException(ex);
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
        Preconditions.nonNull(entityId);

        // this is not efficient as it will fetch the data first and only
        // afterwards will delete it. a more efficient implementation might
        // issue a DELETE statement directly
        final Optional<T> entity = findById(entityId);
        if (entity.isPresent()) {
            delete(entity.get());
        }
    }

    /**
     * returns the number of entities in the persistence store
     *
     * @return the number of entities in the persistence store
     */
    @Override
    public long count() {
        Invariants.nonNull(entityClass, THIS_REPOSITORY_WAS_NOT_PARAMETRIZED_AT_CONSTRUCTION_TIME);

        final TypedQuery<Long> q = entityManager().createQuery(
                "SELECT COUNT(tablename) FROM " + this.entityClass.getSimpleName() + " tablename", Long.class);
        return q.getSingleResult();
    }

    /**
     * checks for the existence of an entity with the provided K.
     *
     * @param key
     * @return
     */
    @Override
    public boolean contains(final K key) {
        return findById(key).isPresent();
    }

    public boolean existsById(final K key) {
        return contains(key);
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
     * behavior and merge()
     *
     * @param entity
     * @return the persisted entity - might be a different object than the
     *         parameter
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    @Override
    public <S extends T> S save(final S entity) {
        try {
            return entityManager().merge(entity);
        } catch (final PersistenceException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                throw new ConcurrencyException(ex);
            }
            throw new IntegrityViolationException(ex);
        }
    }

    /**
     * helper method to create a type query
     *
     * @return
     */
    protected TypedQuery<T> queryAll() {
        Invariants.nonNull(entityClass, THIS_REPOSITORY_WAS_NOT_PARAMETRIZED_AT_CONSTRUCTION_TIME);

        final String className = this.entityClass.getSimpleName();
        return entityManager().createQuery(SELECT_E_FROM + className + " e ", this.entityClass);
    }

    /**
     * helper method to create a typed query. since this method concatenates the
     * where clause, be careful if this results from external input, e.g., user
     * entered data, to avoid SQL injection exploits
     *
     * @param where
     * @return
     */
    @SuppressWarnings({ "squid:S3346", "squid:S2077" })
    private TypedQuery<T> query(final String where) {
        Invariants.nonNull(entityClass, THIS_REPOSITORY_WAS_NOT_PARAMETRIZED_AT_CONSTRUCTION_TIME);

        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final String className = this.entityClass.getSimpleName();
        return entityManager().createQuery(SELECT_E_FROM + className + " e WHERE " + where, this.entityClass);
    }

    /**
     * helper method to create a typed query with a where clause
     *
     * @param where
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected TypedQuery<T> query(final String where, final Map<String, Object> params) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(where);
        params.entrySet().stream().forEach(e -> q.setParameter(e.getKey(), e.getValue()));
        return q;
    }

    /**
     * helper method to create a typed query with a where clause
     *
     * @param where
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected TypedQuery<T> query(final String where, final Object... args) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args != null && args.length >= 2 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;
        assert args.length % 2 == 0 : "uneven number of arguments passed";

        final TypedQuery<T> q = query(where);
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
     * returns the first n entities according to its "natural" order
     *
     * @param n
     * @return
     */
    @Override
    public List<T> first(final int n) {
        Preconditions.isPositive(n);

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(n);
        return q.getResultList();
    }

    /**
     * returns the first entity according to its "natural" order
     *
     * @return
     */
    @Override
    public Optional<T> first() {
        final List<T> r = first(1);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<T> page(final int pageNumber, final int pageSize) {
        Preconditions.isPositive(pageNumber);
        Preconditions.isPositive(pageSize);

        final TypedQuery<T> q = queryAll();
        q.setMaxResults(pageSize);
        q.setFirstResult((pageNumber - 1) * pageSize);

        return q.getResultList();
    }

    /**
     * returns a paged iterator
     *
     * @return
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
     * searches for objects that match the given criteria. be careful if the
     * content of the where parameter is coming from external input, e.g., user
     * entered data, to avoid SQL injection exploits
     *
     * @param where
     *            the where clause should use "e" as the query object
     *
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected List<T> match(final String where) {
        assert !StringPredicates.isNullOrEmpty(where) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(where);
        return q.getResultList();
    }

    /**
     * searches for objects that match the given criteria. be careful if the
     * content of the where parameter is coming from external input, e.g., user
     * entered data, to avoid SQL injection exploits
     *
     * @param whereWithParameters
     * @param params
     * @return
     */
    @SuppressWarnings("squid:S3346")
    protected List<T> match(final String whereWithParameters, final Map<String, Object> params) {
        assert !StringPredicates.isNullOrEmpty(whereWithParameters) : QUERY_MUST_NOT_BE_NULL_OR_EMPTY;
        assert params != null && params.size() > 0 : PARAMS_MUST_NOT_BE_NULL_OR_EMPTY;

        final TypedQuery<T> q = query(whereWithParameters, params);
        return q.getResultList();
    }

    /**
     * searches for one object that matches the given criteria be careful if the
     * content of the where parameter is coming from external input, e.g., user
     * entered data, to avoid SQL injection exploits
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @return
     */
    protected Optional<T> matchOne(final String where) {
        final TypedQuery<T> q = query(where);
        return getOptionalResult(q);
    }

    /**
     * searches for one object that matches the given criteria with parameters
     * be careful if the content of the where parameter is coming from external
     * input, e.g., user entered data, to avoid SQL injection exploits
     *
     * @param whereWithParameters
     *            the where clause should use "e" as the query object
     * @param params
     *            a map of the parameter name and its value to use in the query
     * @return
     */
    protected Optional<T> matchOne(final String whereWithParameters, final Map<String, Object> params) {
        final TypedQuery<T> q = query(whereWithParameters, params);
        return getOptionalResult(q);
    }

    /**
     * searches for one object that matches the given criteria with parameters
     * be careful if the content of the where parameter is coming from external
     * input, e.g., user entered data, to avoid SQL injection exploits
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. each odd
     *            parameter is assumed to be the name of the parameter; each
     *            even parameter is assumed to be the value of the preceeding
     *            parameter
     * @return
     */
    protected Optional<T> matchOne(final String where, final Object... args) {
        final TypedQuery<T> q = query(where, args);
        return getOptionalResult(q);
    }

    /**
     * searches for objects that matches the given criteria with parameters be
     * careful if the content of the where parameter is coming from external
     * input, e.g., user entered data, to avoid SQL injection exploits
     *
     * @param where
     *            the where clause should use "e" as the query object
     * @param args
     *            a list of parameters to be used in the query. each odd
     *            parameter is assumed to be the name of the parameter; each
     *            even parameter is assumed to be the value of the preceeding
     *            parameter
     * @return
     */
    protected List<T> match(final String where, final Object... args) {
        final TypedQuery<T> q = query(where, args);
        return q.getResultList();
    }

    private Optional<T> getOptionalResult(final TypedQuery<T> q) {
        try {
            final T ret = q.getSingleResult();
            return Optional.of(ret);
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * an iterator over JPA
     *
     * @author Paulo Gandra Sousa
     *
     */
    private class JpaPagedIterator implements Iterator<T> {

        private final JpaBaseRepository<T, K> repository;
        private final int pageSize;
        private int currentPageNumber;
        private Iterator<T> currentPage;

        private JpaPagedIterator(final JpaBaseRepository<T, K> repository, final int pagesize) {
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
