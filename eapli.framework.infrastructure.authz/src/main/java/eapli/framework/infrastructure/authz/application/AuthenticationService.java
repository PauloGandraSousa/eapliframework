package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.domain.Password;
import eapli.framework.infrastructure.authz.domain.Role;
import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.infrastructure.authz.domain.Username;
import eapli.framework.infrastructure.authz.persistence.UserRepository;
import eapli.framework.persistence.DataConcurrencyException;
import eapli.framework.persistence.DataIntegrityViolationException;
import eapli.framework.util.Preconditions;

/**
 *
 * @author Paulo Gandra Sousa
 *
 */
@Component
public class AuthenticationService {

    @Autowired
    private UserRepository repo;

    /**
     * Checks if a user can be authenticated with the username/password pair
     *
     * @param username
     * @param pass
     * @return the authenticated user or null otherwise
     */
    public Optional<UserSession> authenticate(final Username username, final Password pass,
            final Role... onlyWithThis) {
        Preconditions.nonNull(username, "a username must be provided");

        final Optional<SystemUser> user = retrieveUser(username);
        if (!user.isPresent()) {
            return Optional.empty();
        }
        if (user.get().passwordMatches(pass) && user.get().isActive()) {
            if (anyActionRight(onlyWithThis) || user.get().hasAny(onlyWithThis)) {
                return Optional.of(createSessionForUser(user.get()));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private boolean anyActionRight(final Role... onlyWithThis) {
        return onlyWithThis.length == 0 || (onlyWithThis.length == 1 && onlyWithThis[0] == null);
    }

    private UserSession createSessionForUser(final SystemUser user) {
        return new UserSession(user);
    }

    private Optional<SystemUser> retrieveUser(final Username userName) {
        return repo.findById(userName);
    }

    public boolean changePassword(final SystemUser user, final Password oldPassword, final Password newPassword)
            throws DataConcurrencyException, DataIntegrityViolationException {
        final boolean sucess = user.changePassword(oldPassword, newPassword);
        if (sucess) {
            repo.save(user);
        }
        return sucess;
    }

    public String resetPassword(final SystemUser user)
            throws DataConcurrencyException, DataIntegrityViolationException {
        final String token = user.resetPassword();
        repo.save(user);
        return token;
    }

    public boolean resetPassword(final SystemUser user, final String token)
            throws DataConcurrencyException, DataIntegrityViolationException {
        if (user.resetPassword(token)) {
            repo.save(user);
            return true;
        }
        return false;
    }
}
