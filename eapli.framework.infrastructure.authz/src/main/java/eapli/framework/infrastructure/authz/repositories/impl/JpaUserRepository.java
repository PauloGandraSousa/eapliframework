package eapli.framework.infrastructure.authz.repositories.impl;

import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaBaseRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
@Component
class JpaUserRepository extends JpaBaseRepository<SystemUser, Username> implements UserRepository {

}
