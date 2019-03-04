/**
 *
 */
package eapli.framework.infrastructure.authz.application;

import java.util.Calendar;
import java.util.UUID;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.domain.SystemUser;
import eapli.framework.util.DateTime;
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
    private final Calendar startedOn;

    public UserSession(final SystemUser user) {
        Preconditions.nonNull(user, "user must not be null");

        this.user = user;
        token = UUID.randomUUID();
        startedOn = DateTime.now();
    }

    public SystemUser authenticatedUser() {
        return user;
    }

    /**
     * @return the startedOn
     */
    public Calendar startedOn() {
        return startedOn;
    }

    @Override
    public String toString() {
        return user.id() + "@" + token;
    }
}
