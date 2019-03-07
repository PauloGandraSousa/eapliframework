package eapli.framework.infrastructure.authz.repositories.impl;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.repositories.impl.springdata.SpringDataBaseRepository;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public interface SpringDataUserRepository extends UserRepository, SpringDataBaseRepository<SystemUser, Username> {

}
