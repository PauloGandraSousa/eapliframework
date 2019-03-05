package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
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
     * the current logged in user session
     */
    private UserSession theSession = null;

    private void setSession(final Optional<UserSession> session) {
        Preconditions.nonNull(session);

        if (session.isPresent()) {
            theSession = session.get();
        } else {
            clearSession();
        }
    }

    public void clearSession() {
        theSession = null;
    }

    public boolean hasSession() {
        return theSession != null;
    }

    public UserSession session() {
        return theSession;
    }

    /**
     * Checks if a user can be authenticated with the username/password pair and if
     * so creates a user session for it.
     *
     * @param username
     * @param pass
     * @return the authenticated user session or an empty optional otherwise
     */
    public Optional<UserSession> authenticate(final Username username, final Password pass,
            final Role... onlyWithThis) {
        Preconditions.nonNull(username, "a username must be provided");

        Optional<UserSession> newSession;
        final Optional<SystemUser> user = retrieveUser(username);
        if (!user.isPresent()) {
            newSession = Optional.empty();
        } else if (user.get().passwordMatches(pass) && user.get().isActive()) {
            if (noActionRightsToValidate(onlyWithThis) || user.get().hasAny(onlyWithThis)) {
                newSession = Optional.of(createSessionForUser(user.get()));
            } else {
                newSession = Optional.empty();
            }
        } else {
            newSession = Optional.empty();
        }

        setSession(newSession);
        return newSession;
    }

    private boolean noActionRightsToValidate(final Role... onlyWithThis) {
        return onlyWithThis.length == 0 || (onlyWithThis.length == 1 && onlyWithThis[0] == null);
    }

    private UserSession createSessionForUser(final SystemUser user) {
        return new UserSession(user);
    }

    private Optional<SystemUser> retrieveUser(final Username userName) {
        return repo.findById(userName);
    }

    public boolean changePassword(final SystemUser user, final Password oldPassword, final Password newPassword)
            throws ConcurrencyException, IntegrityViolationException {
        final boolean sucess = user.changePassword(oldPassword, newPassword);
        if (sucess) {
            repo.save(user);
        }
        return sucess;
    }

    public String resetPassword(final SystemUser user)
            throws ConcurrencyException, IntegrityViolationException {
        final String token = user.resetPassword();
        repo.save(user);
        return token;
    }

    public boolean resetPassword(final SystemUser user, final String token)
            throws ConcurrencyException, IntegrityViolationException {
        if (user.resetPassword(token)) {
            repo.save(user);
            return true;
        }
        return false;
    }
}
