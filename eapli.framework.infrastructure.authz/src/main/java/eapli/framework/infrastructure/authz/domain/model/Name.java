package eapli.framework.infrastructure.authz.domain.model;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;

/**
 * A Person's name
 *
 */
@Embeddable
public class Name implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;
    @SuppressWarnings("squid:S4784")
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[A-Z]+[a-zA-Z ]+$", Pattern.CASE_INSENSITIVE);
    private final String firstName;
    private final String lastName;

    protected Name(final String firstName, final String lastName) {
        Preconditions.nonEmpty(firstName);
        Preconditions.nonEmpty(lastName, "First name and last name should neither be null nor empty");
        Preconditions.matches(VALID_NAME_REGEX, firstName, "Invalid First Name: " + firstName);
        Preconditions.matches(VALID_NAME_REGEX, lastName, "Invalid Last Name: " + lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Name valueOf(final String firstName, final String lastName) {
        return new Name(firstName, lastName);
    }

    protected Name() {
        // ORM only
        firstName = lastName = "";
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Name)) {
            return false;
        }

        final Name name = (Name) o;

        if (!firstName.equals(name.firstName)) {
            return false;
        }
        return lastName.equals(name.lastName);
    }

    @Override
    public int hashCode() {
        final HashCoder coder = new HashCoder().of(firstName).of(lastName);
        return coder.code();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
