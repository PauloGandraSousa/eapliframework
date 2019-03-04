/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.infrastructure.repositories.impl.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.domain.repositories.Repository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.util.Preconditions;

/**
 * A JPA repository implementation for use with applications not running inside
 * a container. this class can either support working within a
 * TransactionalContext or a single immediate transaction mode. if running in
 * single immediate mode a transaction is initiated, committed and the
 * connection closed in the scope of each repository method call.
 *
 * @author Paulo Gandra Sousa
 */
public class JpaAutoTxRepository<T, K extends Serializable> implements Repository<T, K> {

    protected final JpaBaseRepository<T, K> repo;
    private final TransactionalContext autoTx;

    @SuppressWarnings("rawtypes")
    public JpaAutoTxRepository(final String persistenceUnitName) {
        this(persistenceUnitName, new HashMap());
    }

    public JpaAutoTxRepository(final String persistenceUnitName, @SuppressWarnings("rawtypes") final Map properties) {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        final Class<T> entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

        this.repo = new JpaTransactionalRepository<>(persistenceUnitName, properties, entityClass);
        this.autoTx = null;
    }

    public JpaAutoTxRepository(final TransactionalContext autoTx) {
        Preconditions.nonNull(autoTx);

        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();

        @SuppressWarnings("unchecked")
        final Class<T> entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];

        this.repo = new JpaWithTransactionalContextRepository<>(autoTx, entityClass);
        this.autoTx = autoTx;
    }

    public static TransactionalContext buildTransactionalContext(final String persistenceUnitName) {
        return new JpaTransactionalContext(persistenceUnitName);
    }

    public static TransactionalContext buildTransactionalContext(final String persistenceUnitName,
            @SuppressWarnings("rawtypes") final Map properties) {
        return new JpaTransactionalContext(persistenceUnitName, properties);
    }

    public TransactionalContext context() {
        return this.autoTx;
    }

    /**
     * returns if the repository is running in single transaction mode or within a
     * TransactionalContext
     *
     * @return true if the repository is running in single transaction mode false if
     *         running within a Transactional Context
     */
    public boolean isInTransaction() {
        return context() == null;
    }

    @Override
    public void delete(final T entity) throws IntegrityViolationException {
        this.repo.delete(entity);
    }

    @Override
    public void deleteById(final K id) throws IntegrityViolationException {
        this.repo.deleteById(id);
    }

    @Override
    public <S extends T> S save(final S entity) throws ConcurrencyException, IntegrityViolationException {
        return this.repo.save(entity);
    }

    @Override
    public Iterable<T> findAll() {
        return this.repo.findAll();
    }

    @Override
    public Optional<T> findById(final K id) {
        return this.repo.findById(id);
    }

    @Override
    public long count() {
        return this.repo.count();
    }

    public Iterator<T> iterator() {
        return this.repo.iterator();
    }

    protected List<T> match(final String where) {
        return repo.match(where);
    }

    protected List<T> match(final String whereWithParameters, final Map<String, Object> params) {
        return repo.match(whereWithParameters, params);
    }

    protected Optional<T> matchOne(final String where) {
        return repo.matchOne(where);
    }

    protected Optional<T> matchOne(final String whereWithParameters, final Map<String, Object> params) {
        return repo.matchOne(whereWithParameters, params);
    }
}
