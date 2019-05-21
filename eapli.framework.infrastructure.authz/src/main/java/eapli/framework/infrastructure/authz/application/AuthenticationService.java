/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.validations.Preconditions;

/**
 * Authentication-related service. Handles login and password change/reset.
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
     * @param repo
     *            a user repository
     * @param authz
     *            an authentication service
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
     * @param onlyWithThis
     * @return the authenticated user session or an empty optional otherwise
     */
    public Optional<UserSession> authenticate(final Username username, final Password pass,
            final Role... onlyWithThis) {
        Preconditions.nonNull(username, "a username must be provided");
        Preconditions.nonNull(pass, "a password must be provided");

        final Optional<UserSession> newSession = retrieveUser(username)
                .filter(u -> u.passwordMatches(pass) && u.isActive()
                        && (noActionRightsToValidate(onlyWithThis) || u.hasAny(onlyWithThis)))
                .map(this::createSessionForUser);

        authz.setSession(newSession);
        return newSession;
    }

    /**
     * allows a user to perform login and creates the session.
     *
     * @param userName
     * @param password
     * @param onlyWithThis
     * @return true if the user has the right credentials and permissions
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
        return repo.ofIdentity(userName);
    }

    /**
     * Change the password of a certain user. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should use the value of the returned {@code Optional} to work with the
     * user.
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return An optional which value is the changed SystemUser object or an
     *         empty Optional if the password could not be changed
     */
    public Optional<SystemUser> changePassword(final SystemUser user, final Password oldPassword,
            final Password newPassword) {
        if (user.changePassword(oldPassword, newPassword)) {
            return Optional.of(repo.save(user));
        }
        return Optional.empty();
    }

    /**
     * Change the password of the currently logged in user.
     *
     * @param oldPassword
     * @param newPassword
     * @return true if the password could be changed. false if there is no user
     *         session or the password could not be changed
     */
    public boolean changePassword(final String oldPassword, final String newPassword) {
        final Optional<UserSession> s = authz.session();
        return s.map(u -> changePassword(u.authenticatedUser(), Password.valueOf(oldPassword),
                Password.valueOf(newPassword))).map(u -> true).orElse(false);
    }

    /**
     * Initiates a password reset request. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should NOT use it.
     *
     * @param user
     * @return the reset token to be provided back in order to reset the
     *         password
     */
    public String resetPassword(final SystemUser user) {
        final String token = user.resetPassword();
        repo.save(user);
        return token;
    }

    /**
     * Resets the password of the user. If the operation succeeds, the
     * {@code user} object reference is no longer valid and subsequent code
     * should use the value of the returned {@code Optional} to work with the
     * user.
     *
     * @param user
     * @param token
     * @param newPass
     * @return an Optional which value is the changed SystemUser or an empty
     *         optional otherwise
     */
    public Optional<SystemUser> confirmResetPassword(final SystemUser user, final String token,
            final Password newPass) {
        if (user.confirmResetPassword(token, newPass)) {
            return Optional.of(repo.save(user));
        }
        return Optional.empty();
    }
}
