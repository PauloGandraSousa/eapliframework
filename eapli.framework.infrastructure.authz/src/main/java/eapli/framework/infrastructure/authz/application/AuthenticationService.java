/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.application.exceptions.UserSessionNotInitiatedException;
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

    @Autowired
    private AuthorizationService authz;

    /**
     * default constructor for Spring dependency injection
     */
    protected AuthenticationService() {
        // empty
    }

    /**
     * in scenarios where there is no dependency injector, this constructor
     * allows the object to be be built and manually inject the dependency.
     *
     * @param authenticationService
     */
    public AuthenticationService(final UserRepository repo, final AuthorizationService authz) {
        Preconditions.nonNull(repo, authz);
        this.repo = repo;
        this.authz = authz;
    }

    /**
     * for helper AuthzHolder class
     *
     * @param session
     */
    /* package */ void setRepository(final UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Checks if a user can be authenticated with the username/password pair and
     * if so creates a user session for it.
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
                newSession = user.map(this::createSessionForUser);
            } else {
                newSession = Optional.empty();
            }
        } else {
            newSession = Optional.empty();
        }

        authz.setSession(newSession);
        return newSession;
    }

    /**
     * allows a user to perform login and creates the session.
     *
     * @param userName
     * @param password
     * @throws IllegalArgumentException
     *             if the username or password cannot be constructed from the
     *             passed string arguments
     */
    public boolean login(final String userName, final String password, final Role... onlyWithThis) {
        final Optional<UserSession> newSession = authenticate(Username.valueOf(userName), Password.valueOf(password),
                onlyWithThis);
        return newSession.isPresent();
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

    /**
     * change the password of a certain user
     * 
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public boolean changePassword(final SystemUser user, final Password oldPassword, final Password newPassword) {
        final boolean sucess = user.changePassword(oldPassword, newPassword);
        if (sucess) {
            repo.save(user);
        }
        return sucess;
    }

    /**
     * change the password of the currently logged in user
     * 
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public boolean changePassword(final String oldPassword, final String newPassword) {
        final Optional<UserSession> s = authz.session();
        return s.map(u -> changePassword(u.authenticatedUser(), Password.valueOf(oldPassword),
                Password.valueOf(newPassword))).orElseThrow(UserSessionNotInitiatedException::new);
    }

    public String resetPassword(final SystemUser user) {
        final String token = user.resetPassword();
        repo.save(user);
        return token;
    }

    public boolean resetPassword(final SystemUser user, final String token) {
        if (user.resetPassword(token)) {
            repo.save(user);
            return true;
        }
        return false;
    }
}
