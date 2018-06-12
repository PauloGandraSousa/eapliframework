/**
 *
 */
package eapli.framework.domain;

import java.io.Serializable;

/**
 * a simple base class for domain entities providing for equality test thru
 * business identity
 *
 * @author pgsou_000
 *
 */
public abstract class DomainEntityBase<I> implements DomainEntity<I>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }
}
