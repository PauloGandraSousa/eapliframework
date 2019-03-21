/**
 *
 */
package eapli.framework.infrastructure.authz.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;
import eapli.framework.util.predicates.StringPredicates;

/**
 * a password of a user.
 *
 * passwords must be at least 6 characters long and have at least one digit and
 * one capital letter
 *
 * TODO passwords should never be stored in plain format
 *
 * @author Paulo Gandra Sousa
 */
@Embeddable
public class Password implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "pass")
    private final String thePassword;

    protected Password() {
        // for ORM only
        thePassword = null;
    }

    protected Password(final String password) {
        Preconditions.ensure(() -> meetsMinimumRequirements(password));
        thePassword = password;
    }

    public static Password valueOf(final String password) {
        return new Password(password);
    }

    @SuppressWarnings("squid:S1126")
    private boolean meetsMinimumRequirements(final String password) {
        // sanity check
        if (StringPredicates.isNullOrEmpty(password)) {
            return false;
        }

        // at least 6 characters long
        if (password.length() < 6) {
            return false;
        }

        // at least one digit
        if (!StringPredicates.containsDigit(password)) {
            return false;
        }

        // at least one capital letter
        if (!StringPredicates.containsCapital(password)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }

        final Password password1 = (Password) o;

        return thePassword.equals(password1.thePassword);

    }

    @Override
    public int hashCode() {
        return new HashCoder().of(thePassword).code();
    }

    /**
     * Check how strong a password is
     *
     * @return how strong a password is
     */
    public PasswordStrength strength() {
        PasswordStrength passwordStrength = PasswordStrength.WEAK;
        if (3 > thePassword.length()) {
            passwordStrength = PasswordStrength.WEAK;
        }
        // TODO implement the rest of the method
        return passwordStrength;
    }

    @Override
    public String toString() {
        return thePassword;
    }

    public enum PasswordStrength {
        WEAK, GOOD, EXCELENT,
    }
}
