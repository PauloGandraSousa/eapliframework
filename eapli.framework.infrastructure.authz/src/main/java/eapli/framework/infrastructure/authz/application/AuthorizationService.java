package eapli.framework.infrastructure.authz.application;

import java.util.Optional;

import eapli.framework.infrastructure.authz.application.exceptions.UnauthorizedException;
import eapli.framework.infrastructure.authz.application.exceptions.UserSessionNotInitiatedException;
import eapli.framework.infrastructure.authz.domain.Role;
import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * helper "singleton" session holder
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class AuthorizationService implements Utilitarian {
    private static UserSession theSession = null;

    private AuthorizationService() {
        // ensure utility
    }

    // in a real life situation this method should not exist! anyone could
    // circunvent the authentication
    public static void setSession(final UserSession session) {
        Preconditions.nonNull(session);

        theSession = session;
    }

    // in a real life situation this method should not exist! anyone could
    // circunvent the authentication
    public static void setSession(final Optional<UserSession> session) {
        Preconditions.nonNull(session);

        if (session.isPresent()) {
            theSession = session.get();
        } else {
            clearSession();
        }
    }

    public static void clearSession() {
        theSession = null;
    }

    public static boolean hasSession() {
        return theSession != null;
    }

    public static UserSession session() {
        return theSession;
    }

    /**
     * helper method to check the permission of the currently logged in user
     */
    public static boolean isAuthorizedTo(final SystemUser user, final Role... actions) {
        return !user.hasAny(actions);
    }

    /**
     * helper method to check the permission of the currently logged in user
     */
    public static void ensurePermissionOfLoggedInUser(final Role... actions) {
        if (session() == null) {
            throw new UserSessionNotInitiatedException();
        }
        if (!isAuthorizedTo(session().authenticatedUser(), actions)) {
            throw new UnauthorizedException("User is not authorized to perform one of these actions",
                    session().authenticatedUser(), actions);
        }
    }
}
