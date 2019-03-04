package eapli.framework.infrastructure.authz.persistence;

import eapli.framework.domain.repositories.Repository;
import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;

/**
 * Created by nuno on 21/03/16.
 */
public interface UserRepository extends Repository<SystemUser, Username> {

}
