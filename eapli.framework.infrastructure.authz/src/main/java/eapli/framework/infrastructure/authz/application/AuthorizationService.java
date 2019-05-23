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

import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.application.exceptions.UnauthorizedException;
import eapli.framework.infrastructure.authz.application.exceptions.UserSessionNotInitiatedException;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.validations.Preconditions;

/**
 * Very simple session holder and permission checking helper. Note that it does
 * not check the permissions against any type of database but relies on the
 * roles assigned to a user.
 *
 * <p>
 * note that this class is conceptually a singleton as it holds the Session
 * object. ensure that you set up spring correctly and if you create the
 * instances yourself, make sure you handle the singletonness nature of the
 * object.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Component
public final class AuthorizationService {

    /**
     * the current logged in user session
     */
    private Optional<UserSession> theSession = Optional.empty();

    /**
     * hiding the constructor so that this class is used either by spring or
     * thru {@link AuthzRegistry#authorizationService()}
     */
    protected AuthorizationService() {
        // empty
    }

    /**
     * internal method to set the session after authentication has been
     * performed
     *
     * @param session
     */
    /* package */ void setSession(final Optional<UserSession> session) {
        Preconditions.nonNull(session);

        if (session.isPresent()) {
            theSession = session;
        } else {
            clearSession();
        }
    }

    /**
     * clear the session
     */
    public void clearSession() {
        theSession = Optional.empty();
    }

    /**
     * checks if there is an authenticated user session
     *
     * @return {@code true} if there is an authenticated user session
     */
    public boolean hasSession() {
        return theSession.isPresent();
    }

    /**
     * Returns the current session or an empty optional if there is no session
     *
     * @return the current session or an empty optional if there is no session
     */
    public Optional<UserSession> session() {
        return theSession;
    }

    /**
     * Checks if the currently logged in user has the specified roles.
     *
     * @param actions
     * @return {@code true} if the authenticated user has at least one of the
     *         desired roles
     */
    public boolean isAuthenticatedUserAuthorizedTo(final Role... actions) {
        return session().map(us -> us.authenticatedUser().hasAny(actions)).orElse(false);
    }

    /**
     * Checks the permission of the currently logged in user and throws an
     * exception if there is no session or the currently logged in user does not
     * have the requested permissions.
     *
     * <p>
     * consider using {@link #loggedinUserWithPermissions(Role...)} to perform
     * this check without throwing exceptions, as these scenarios are not really
     * exceptions but accounted for
     *
     * @param actions
     * @throws UserSessionNotInitiatedException
     * @throws UnauthorizedException
     *
     */
    public void ensureAuthenticatedUserHasAnyOf(final Role... actions) {
        final UserSession us = session().orElseThrow(UserSessionNotInitiatedException::new);
        if (!us.authenticatedUser().hasAny(actions)) {
            throw new UnauthorizedException(us.authenticatedUser(), actions);
        }
    }

    /**
     * returns the authenticated user if there is a session and the
     * authenticated user has the desired permissions. otherwise returns an
     * empty optional.
     *
     * @param actions
     * @return the authenticated user if there is a session and the
     *         authenticated user has the desired permissions. otherwise returns
     *         an empty optional
     */
    public Optional<SystemUser> loggedinUserWithPermissions(final Role... actions) {
        return session().filter(us -> us.authenticatedUser().hasAny(actions)).map(UserSession::authenticatedUser);
    }
}
