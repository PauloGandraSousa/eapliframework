package eapli.framework.infrastructure.authz.repositories.impl;

import java.util.Map;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * The JPA implementation for scenarios where the application is not running in
 * a container.
 *
 * @author Paulo Gandra de Sousa.
 */
public class JpaAutoTxUserRepository extends JpaAutoTxRepository<SystemUser, Username, Username>
        implements UserRepository {

    public JpaAutoTxUserRepository(final TransactionalContext autoTx) {
        super(autoTx, "username");
    }

    public JpaAutoTxUserRepository(final String puname, @SuppressWarnings("rawtypes") final Map properties) {
        super(puname, properties, "username");
    }

    @Override
    public Iterable<SystemUser> findByActive(final boolean active) {
        return match(UserRepositoryConstants.FIND_BY_ACTIVE, "active", active);
    }
}
