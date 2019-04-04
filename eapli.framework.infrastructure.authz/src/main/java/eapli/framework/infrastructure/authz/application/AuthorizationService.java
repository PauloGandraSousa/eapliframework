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
import eapli.framework.util.Preconditions;

/**
 * Very simple session holder and permission checking helper. note that it does
 * not check the permissions against any type of database but relies on the
 * roles assigned to a user.
 *
 * note that this class is conceptually a singleton as it holds the Session
 * object. ensure that you set up spring correctly and if you create the
 * instances yourself, make sure you handle the singletonness nature of the
 * object
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
     * default constructor for Spring dependency injection
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
     * verifies if there is an authenticated user session
     *
     * @return
     */
    public boolean hasSession() {
        return theSession.isPresent();
    }

    /**
     * @return
     */
    public Optional<UserSession> session() {
        return theSession;
    }

    /**
     * helper method to check the permission of a user
     */
    public boolean isAuthorizedTo(final SystemUser user, final Role... actions) {
        return user.hasAny(actions);
    }

    /**
     * helper method to check the permission of the currently logged in user
     */
    public boolean isAuthenticatedUserAuthorizedTo(final Role... actions) {
        return session().map(u -> hasAny(u, actions)).orElse(false);
    }

    /**
     * helper method to check the permission of the currently logged in user
     *
     * @throws UserSessionNotInitiated
     * @throws UnauthorizedException
     *
     * @todo consider other ways to perform this check without throwing
     *       exceptions, as these scenarios are not really exceptions, they are
     *       accounted for
     */
    @SuppressWarnings("squid:S3655")
    public void ensurePermissionOfAuthenticatedUser(final Role... actions) {
        final UserSession us = session().orElseThrow(UserSessionNotInitiatedException::new);
        if (!hasAny(us, actions)) {
            throw new UnauthorizedException(session().get().authenticatedUser(), actions);
        }
    }

    /**
     * returns the user session of the logged in user if there is a session and
     * the authenticated user has the desired permissions. otherwise returns an
     * empty optional.
     *
     * @param actions
     * @return
     */
    public Optional<UserSession> loggedinUserWithPermissions(final Role... actions) {
        return session().filter(u -> hasAny(u, actions));
    }

    private boolean hasAny(final UserSession u, final Role... actions) {
        return u.authenticatedUser().hasAny(actions);
    }
}
