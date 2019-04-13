package eapli.framework.infrastructure.authz.repositories.impl;

import java.util.Map;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * the JPA implementation for scenarios where the application is not running in
 * a container
 *
 * @author sou03408.
 */
public class JpaAutoTxUserRepository extends JpaAutoTxRepository<SystemUser, Username, Username>
        implements UserRepository {

    public JpaAutoTxUserRepository(TransactionalContext autoTx) {
        super(autoTx);
    }

    public JpaAutoTxUserRepository(String puname, @SuppressWarnings("rawtypes") Map properties) {
        super(puname, properties);
    }

    @Override
    public Iterable<SystemUser> findByActive(boolean active) {
        return match(UserRepositoryConstants.FIND_BY_ACTIVE, "active", active);
    }
}
