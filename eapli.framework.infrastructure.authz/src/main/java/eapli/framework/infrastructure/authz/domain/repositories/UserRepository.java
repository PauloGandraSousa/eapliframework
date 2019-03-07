package eapli.framework.infrastructure.authz.domain.repositories;

import eapli.framework.domain.repositories.Repository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * Created by nuno on 21/03/16.
 */
public interface UserRepository extends Repository<SystemUser, Username> {

}
