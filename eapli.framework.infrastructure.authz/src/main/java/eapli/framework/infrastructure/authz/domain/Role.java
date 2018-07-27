/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import eapli.framework.domain.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;

/**
 * A generic concept for a role that can be used by application code to classify
 * users
 *
 * @author pgsou_000
 *
 */
public class Role implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final String roleName;

    private Role(final String role) {
        Preconditions.nonEmpty(role);

        roleName = role;
    }

    private Role() {
        // for ORM
        roleName = null;
    }

    @Override
    public String toString() {
        return roleName;
    }

    public static Role valueOf(final String role) {
        return new Role(role);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Role)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        final Role that = (Role) other;
        return this.roleName.equals(that.roleName);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(roleName).code();
    }
}
