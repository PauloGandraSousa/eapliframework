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
 * Generic designation concept, e.g., name, short name, nickname. A designation
 * consists of some character string without heading and trailing white spaces
 * (but spaces in between are allowed)
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 * @author sou03408
 */
@Embeddable
public class Designation implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;
    private final String name;

    /**
     * protected constructor. to construct a new Designation instance use the
     * valueOf() method
     *
     * @param name
     */
    protected Designation(final String name) {
        Preconditions.ensure(() -> Strings.isPhrase(name),
                "Name should neither be null nor empty nor have starting blank spaces");

        this.name = name;
    }

    protected Designation() {
        // for ORM
        name = null;
    }

    /**
     * factory method for obtaining Designation value objects.
     *
     *
     * @param name
     * @return
     */
    public static Designation valueOf(final String name) {
        return new Designation(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Designation)) {
            return false;
        }

        final Designation other = (Designation) o;
        return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(name).code();
    }
}
