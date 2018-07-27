/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eapli.framework.domain.ValueObject;
import eapli.framework.util.DateTime;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;

/**
 * @author Paulo Gandra Sousa
 */
@Entity
public class RoleAssignment implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private Long pk;

    private final Role type;
    @Temporal(TemporalType.DATE)
    private final Calendar assignedOn;

    public RoleAssignment(final Role type) {
        this(type, DateTime.now());
    }

    public RoleAssignment(final Role type, final Calendar assignedOn) {
        Preconditions.nonNull(type, assignedOn);

        this.type = type;
        this.assignedOn = assignedOn;
    }

    protected RoleAssignment() {
        // for ORM
        type = null;
        assignedOn = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleAssignment)) {
            return false;
        }

        final RoleAssignment other = (RoleAssignment) o;
        return type == other.type && assignedOn.equals(other.assignedOn);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(type).code();
    }

    @Override

    public String toString() {
        return type + "@" + assignedOn;
    }

    public Role type() {
        return type;
    }

    public boolean isOf(final Role r) {
        return type.equals(r);
    }
}
