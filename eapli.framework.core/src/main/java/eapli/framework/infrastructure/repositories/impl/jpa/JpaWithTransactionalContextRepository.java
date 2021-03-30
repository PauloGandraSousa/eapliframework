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

import eapli.framework.domain.repositories.TransactionalContext;

/**
 * An utility class for implementing JPA repositories not running in containers.
 * as such, this class creates its own EntityManagerFactory instead of using an
 * injected EMF by the container. This classes assumes that transaction
 * management is done outside of the class so callers of the repository must
 * first initiate a transaction and explicitly end it; typically this will be a
 * responsibility of the controller by calling the methods of the
 * TransactionalContext interface.
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
/* package */ class JpaWithTransactionalContextRepository<T, K, I> extends JpaBaseRepository<T, K, I> {

    private JpaTransactionalContext txContext;

    /**
     * Constructor.
     *
     * @param txCtx
     *            the transactional context to enroll
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     *
     */
    public JpaWithTransactionalContextRepository(final TransactionalContext txCtx, final String identityFieldName) {
        super(identityFieldName);
        setTxCtx(txCtx);
    }

    /**
     * Internal Constructor.
     *
     * @param txCtx
     *            the transactional context to enroll
     * @param classz
     * @param identityFieldName
     *            the name of the <b>business identity</b> field (not the primary key)
     */
    /* package */ JpaWithTransactionalContextRepository(final TransactionalContext txCtx, final Class<T> classz,
            final String identityFieldName) {
        super(classz, identityFieldName);
        setTxCtx(txCtx);
    }

    private void setTxCtx(final TransactionalContext txCtx) {
        if (!(txCtx instanceof JpaTransactionalContext)) {
            throw new IllegalArgumentException();
        }
        this.txContext = (JpaTransactionalContext) txCtx;
    }

    @Override
    protected EntityManagerFactory entityManagerFactory() {
        return txContext.entityManagerFactory();
    }

    @Override
    protected EntityManager entityManager() {
        return txContext.entityManager();
    }

    /**
     *
     * @return
     */
    protected TransactionalContext context() {
        return txContext;
    }
}
