package eapli.framework.infrastructure.authz.persistence.impl;

import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;
import eapli.framework.infrastructure.authz.persistence.UserRepository;
import eapli.framework.persistence.repositories.impl.springdata.SpringDataBaseRepository;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public interface SpringDataUserRepository extends UserRepository, SpringDataBaseRepository<SystemUser, Username> {

}
