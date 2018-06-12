package eapli.framework.infrastructure.authz.persistence.impl;

import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;
import eapli.framework.infrastructure.authz.persistence.UserRepository;
import eapli.framework.persistence.repositories.impl.inmemory.InMemoryDomainEntityRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryUserRepository extends InMemoryDomainEntityRepository<SystemUser, Username>
        implements UserRepository {

}
