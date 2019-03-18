package eapli.framework.infrastructure.authz.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eapli.framework.infrastructure.authz.application.exceptions.UnauthorizedException;
import eapli.framework.infrastructure.authz.application.exceptions.UserSessionNotInitiatedException;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Component
public final class AuthorizationService {

    @Autowired
    private AuthenticationService authz;

    /**
     * default constructor for Spring dependency injection
     */
    protected AuthorizationService() {
        // empty
    }

    /**
     * in scenarios where there is no dependency injector, this constructor allows
     * the controller to be be built and manually inject the dependency
     *
     * @param authenticationService
     */
    public AuthorizationService(final AuthenticationService authenticationService) {
        this.authz = authenticationService;
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
    public boolean isLoggedInUserAuthorizedTo(final Role... actions) {
        return authz.session().authenticatedUser().hasAny(actions);
    }

    /**
     * helper method to check the permission of the currently logged in user
     *
     * @todo consider other ways to perform this check without throwing exceptions,
     *       as these scenarios are not really exceptions, they are accounted for
     */
    public void ensurePermissionOfLoggedInUser(final Role... actions) {
        if (!authz.hasSession()) {
            throw new UserSessionNotInitiatedException();
        }
        if (!isLoggedInUserAuthorizedTo(actions)) {
            throw new UnauthorizedException(authz.session().authenticatedUser(), actions);
        }
    }
}
