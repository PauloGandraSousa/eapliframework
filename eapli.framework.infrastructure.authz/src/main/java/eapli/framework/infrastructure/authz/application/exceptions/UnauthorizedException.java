/**
 *
 */
package eapli.framework.infrastructure.authz.application.exceptions;

import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -5601684795034834561L;

    private final SystemUser user;
    private final Role[] roles;

    /**
     * @param message
     */
    public UnauthorizedException(final String message, final SystemUser user, final Role... roles) {
        super(buildMessage(message, user, roles));
        this.roles = roles;
        this.user = user;
    }

    public UnauthorizedException(final SystemUser user, final Role... roles) {
        super(buildMessage("", user, roles));
        this.roles = roles;
        this.user = user;
    }

    private static String buildMessage(final String original, final SystemUser user, final Role... roles) {
        return "User " + user.username() + " is not authorized to perform one of these actions: " + roles + "\n"
                + original;
    }

    public SystemUser user() {
        return user;
    }

    public Role[] intendedRoles() {
        return roles;
    }
}
