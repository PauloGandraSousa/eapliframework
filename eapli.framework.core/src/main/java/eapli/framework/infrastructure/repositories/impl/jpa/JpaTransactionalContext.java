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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.validations.Preconditions;
import eapli.framework.validations.StringPredicates;

/**
 * An utility class for providing transactional context to JPA repositories not
 * running in containers. as such, this class creates its own
 * EntityManagerFactory instead of using an injected EMF by the container.
 *
 * @author Paulo Gandra Sousa
 */
public class JpaTransactionalContext implements TransactionalContext {
    private static final Logger LOGGER = LogManager.getLogger(JpaTransactionalContext.class);

    private final String persistenceUnitName;
    private static EntityManagerFactory singletonEMF;
    private EntityManager entityManager;
    @SuppressWarnings("rawtypes")
    private final Map properties = new HashMap();

    /**
     *
     * @param persistenceUnitName
     *            the name of the persistence unit to use
     * @param properties
     *            extend properties to override the persistence.xml file
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public JpaTransactionalContext(final String persistenceUnitName, final Map properties) {
        Preconditions.nonEmpty(persistenceUnitName);

        this.persistenceUnitName = persistenceUnitName;
        this.properties.putAll(properties);
        entityManagerFactory();
    }

    @SuppressWarnings("rawtypes")
    public JpaTransactionalContext(final String persistenceUnitName) {
        this(persistenceUnitName, new HashMap());
    }

    @SuppressWarnings({ "squid:S3346", "squid:S2696" })
    public final EntityManagerFactory entityManagerFactory() {
        if (singletonEMF == null) {
            assert !StringPredicates.isNullOrEmpty(
                    persistenceUnitName) : "the persistence unit name must be provided";
            LOGGER.info("Not runing in container mode.");
            singletonEMF = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        }
        return singletonEMF;
    }

    public EntityManager entityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            LOGGER.trace("Creating a new entity manager");
            entityManager = entityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    @Override
    public void beginTransaction() {
        final EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
    }

    @Override
    public void commit() {
        entityManager().getTransaction().commit();
    }

    @Override
    public void rollback() {
        entityManager().getTransaction().rollback();
    }

    @Override
    public void close() {
        entityManager().close();
    }
}
