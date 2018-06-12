/**
 *
 */
package eapli.framework.infrastructure.authz.application.exceptions;

import eapli.framework.infrastructure.authz.domain.ActionRight;
import eapli.framework.infrastructure.authz.domain.SystemUser;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class UnauthorizedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5601684795034834561L;
    private final SystemUser user;
    private final ActionRight[] actions;

    /**
     * @param message
     */
    public UnauthorizedException(final String message, final SystemUser user, final ActionRight... actions) {
        super(message);
        this.actions = actions;
        this.user = user;
    }

    public SystemUser user() {
        return user;
    }

    public ActionRight[] intendedActions() {
        return actions;
    }
}
