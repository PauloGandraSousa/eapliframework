/**
 *
 */
package eapli.framework.domain.model.general;

import java.io.Serializable;

import javax.persistence.Embeddable;

import eapli.framework.util.Preconditions;
import eapli.framework.domain.model.ValueObject;
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
    private final String theDescription;

    /**
     * protected constructor. to construct a new Designation instance use the
     * valueOf() method
     *
     * @param name
     */
    protected Description(final String name) {
        Preconditions.nonEmpty(name, "Description should neither be null nor empty");

        theDescription = name;
    }

    protected Description() {
        // for ORM
        theDescription = null;
    }

    @Override
    public int length() {
        return theDescription.length();
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
        return theDescription.equals(other.theDescription);
    }

    @Override
    public String toString() {
        return theDescription;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(theDescription).code();
    }
}
