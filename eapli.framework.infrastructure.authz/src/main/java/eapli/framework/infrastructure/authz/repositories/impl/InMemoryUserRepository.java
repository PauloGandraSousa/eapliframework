package eapli.framework.infrastructure.authz.repositories.impl;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainEntityRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryUserRepository extends InMemoryDomainEntityRepository<SystemUser, Username>
        implements UserRepository {

}
