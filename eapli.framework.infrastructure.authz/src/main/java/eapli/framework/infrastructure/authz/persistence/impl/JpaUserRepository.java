package eapli.framework.infrastructure.authz.persistence.impl;

import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;
import eapli.framework.infrastructure.authz.persistence.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaBaseRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
@Component
class JpaUserRepository extends JpaBaseRepository<SystemUser, Username> implements UserRepository {

}
