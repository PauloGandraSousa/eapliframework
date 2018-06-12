/**
 *
 */
package eapli.framework.infrastructure.authz.application;

import java.util.UUID;

import eapli.framework.domain.ValueObject;
import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.util.Preconditions;

/**
 * @FIXME javadoc
 * @author Paulo Gandra Sousa
 *
 */
public class UserSession implements ValueObject {
    private static final long serialVersionUID = 1L;

    private final SystemUser user;
    private final UUID token;

    public UserSession(final SystemUser user) {
        Preconditions.nonNull(user, "user must not be null");

        this.user = user;
        token = UUID.randomUUID();
    }

    public SystemUser authenticatedUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.id() + "@" + token;
    }
}
