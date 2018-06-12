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

/**
 * Generic description concept.it must have some content but no special rules
 * about the content exist.
 *
 * @author sou03408
 */
@Embeddable
public class Description implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;
    private final String description;

    /**
     * protected constructor. to construct a new Designation instance use the
     * valueOf() method
     *
     * @param name
     */
    protected Description(final String name) {
        Preconditions.nonEmpty(name, "Description should neither be null nor empty");

        description = name;
    }

    protected Description() {
        // for ORM
        description = null;
    }

    @Override
    public int length() {
        return description.length();
    }

    /**
     * factory method for obtaining Designation value objects.
     *
     *
     * @param name
     * @return
     */
    public static Description valueOf(final String name) {
        return new Description(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Description)) {
            return false;
        }

        final Description other = (Description) o;
        return description.equals(other.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(description).code();
    }
}
