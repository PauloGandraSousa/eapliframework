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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

/**
 * A utility class for implementing simple agnostic JPA repositories, that is, not tied to any
 * particular managed entity. Can be useful if you want to have a Reporting repository that returns
 * different types of DTOs. If you want a repository for a JPA Entity use the typed
 * {@link JpaBaseRepository}
 *
 * <p>
 * Based on <a href=
 * "http://stackoverflow.com/questions/3888575/single-dao-generic-crud-methods-jpa-hibernate-spring"
 * > stackoverflow</a> and on <a href="https://burtbeckwith.com/blog/?p=40">burtbeckwith</a>.
 *
 * <p>
 * Also have a look at <a href="http://blog.xebia.com/tag/jpa-implementation-patterns/">JPA
 * implementation patterns</a>
 *
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class JpaAbstractRepository {

    @PersistenceUnit
    private EntityManagerFactory emFactory;
    private EntityManager entityManager;

    /**
     * Constructor.
     */
    protected JpaAbstractRepository() {
        super();
    }

    /**
     * Provides access to the underlying entity manager factory. In principle, derived classes
     * should not need to use this method. neither override it.
     *
     * @return
     */
    protected EntityManagerFactory entityManagerFactory() {
        return emFactory;
    }

    /**
     * Provides access to the underlying entity manager. In principle, derived classes
     * should not need to use this method. neither override it.
     *
     * @return
     */
    protected EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory().createEntityManager();
        }
        return entityManager;
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
    protected <R> TypedQuery<R> createQuery(final String jpql, final Class<R> classz) {
        return entityManager().createQuery(jpql, classz);
    }
}