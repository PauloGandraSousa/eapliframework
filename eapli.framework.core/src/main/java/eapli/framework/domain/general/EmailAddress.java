/**
 *
 */
package eapli.framework.domain.general;

import java.io.Serializable;

import javax.persistence.Embeddable;

import eapli.framework.domain.ValueObject;
import eapli.framework.util.Preconditions;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.util.Strings;

/**
 * @author Paulo Gandra Sousa
 */
@Embeddable
public class EmailAddress implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String email;

    protected EmailAddress(final String address) {
        Preconditions.nonEmpty(address, "email address  should neither be null nor empty");
        Preconditions.ensure(() -> Strings.isEmail(address), "Invalid E-mail format");

        email = address;
    }

    protected EmailAddress() {
        // for ORM
        email = "";
    }

    public static EmailAddress valueOf(final String address) {
        return new EmailAddress(address);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailAddress)) {
            return false;
        }

        final EmailAddress that = (EmailAddress) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(email).code();
    }

    @Override
    public String toString() {
        return email;
    }
}
