package eapli.framework.infrastructure.authz.persistence;

import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;
import eapli.framework.persistence.repositories.DataRepository;

/**
 * Created by nuno on 21/03/16.
 */
public interface UserRepository extends DataRepository<SystemUser, Username> {

}
