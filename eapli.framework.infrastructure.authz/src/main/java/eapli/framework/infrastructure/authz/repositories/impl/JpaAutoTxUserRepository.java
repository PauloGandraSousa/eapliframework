package eapli.framework.infrastructure.authz.repositories.impl;

import java.util.Map;
import java.util.Optional;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 * the JPA implementation for scenarios where the application is not running in
 * a container
 *
 * @author Paulo Gandra de Sousa.
 */
public class JpaAutoTxUserRepository extends JpaAutoTxRepository<SystemUser, Username, Username>
        implements UserRepository {

    public JpaAutoTxUserRepository(TransactionalContext autoTx) {
        super(autoTx, "username");
    }

    public JpaAutoTxUserRepository(String puname, @SuppressWarnings("rawtypes") Map properties) {
        super(puname, properties, "username");
    }

    @Override
    public Iterable<SystemUser> findByActive(boolean active) {
        return match(UserRepositoryConstants.FIND_BY_ACTIVE, "active", active);
    }

    @Override
    public Optional<SystemUser> ofIdentity(final Username id) {
        return matchOne("e.username = :id", "id", id);
    }
}
