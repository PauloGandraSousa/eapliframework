/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;
import eapli.framework.util.predicates.StringPredicates;

/**
 * a username.
 *
 * it must not be empty
 *
 * @author Paulo Gandra Sousa
 */
@Embeddable
public class Username implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;
    private final String username;

    protected Username() {
        // for ORM
        username = null;
    }

    protected Username(final String username) {
        Preconditions.ensure(() -> StringPredicates.isSingleWord(username), "username should neither be null nor empty");
        this.username = username;
    }

    public static Username valueOf(final String username) {
        return new Username(username);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Username)) {
            return false;
        }

        final Username other = (Username) o;

        return username.equals(other.username);

    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(username).code();
    }
}
