/**
 *
 */
package eapli.framework.persistence.repositories.impl.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.persistence.repositories.TransactionalContext;
import eapli.framework.util.Preconditions;
import eapli.framework.util.Strings;

/**
 * An utility class for providing transactional context to JPA repositories not
 * running in containers. as such, this class creates its own
 * EntityManagerFactory instead of using an injected EMF by the container.
 *
 * @author Paulo Gandra Sousa
 */
/* package */ class JpaTransactionalContext implements TransactionalContext {
    private static final Logger LOGGER = LogManager.getLogger(JpaTransactionalContext.class);

    private final String persistenceUnitName;
    private static volatile EntityManagerFactory singletonEMF;
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
    /* package */ final EntityManagerFactory entityManagerFactory() {
        if (singletonEMF == null) {
            assert !Strings.isNullOrEmpty(persistenceUnitName) : "the persistence unit name must be provided";
            LOGGER.info("Not runing in container mode.");
            singletonEMF = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
        }
        return singletonEMF;
    }

    /* package */ EntityManager entityManager() {
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
