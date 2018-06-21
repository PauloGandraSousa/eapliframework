/**
 *
 */
package eapli.framework.infrastructure.authz.application.exceptions;

import eapli.framework.infrastructure.authz.domain.Role;
import eapli.framework.infrastructure.authz.domain.SystemUser;

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
        super(message);
        this.roles = roles;
        this.user = user;
    }

    public SystemUser user() {
        return user;
    }

    public Role[] intendedRoles() {
        return roles;
    }
}
