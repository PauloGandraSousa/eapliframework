/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;

/**
 * a set of roles.
 *
 * part of the SystemUser aggregate
 *
 * @author Paulo Gandra Sousa
 */
@Entity
class RoleSet implements Set<Role>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private Long pk;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Role> data = new HashSet<>();

    @Override
    public boolean add(final Role arg0) {
        Preconditions.nonNull(arg0);

        // TODO validations are missing, e.g., no overlap in roles with the same
        // role type
        return data.add(arg0);
    }

    @Override
    public boolean addAll(final Collection<? extends Role> arg0) {
        return data.addAll(arg0);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean contains(final Object arg0) {
        return data.contains(arg0);
    }

    @Override
    public boolean containsAll(final Collection<?> arg0) {

        return data.containsAll(arg0);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<Role> iterator() {
        return data.iterator();
    }

    @Override
    public boolean remove(final Object arg0) {
        return data.remove(arg0);
    }

    @Override
    public boolean removeAll(final Collection<?> arg0) {
        return data.removeAll(arg0);
    }

    @Override
    public boolean retainAll(final Collection<?> arg0) {
        return data.retainAll(arg0);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] arg0) {
        return data.toArray(arg0);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleSet)) {
            return false;
        }

        final RoleSet roles = (RoleSet) o;

        // we need to perform a deep equals() as we want to compare values and
        // not object instances, so we cannot do: return
        // this.data.equals(roles.data);
        for (final Role r : data) {
            boolean found = false;
            for (final Role or : roles.data) {
                if (r.equals(or)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(data).code();
    }

    public Collection<RoleType> roleTypes() {
        final List<RoleType> ret = new ArrayList<>();
        data.forEach(role -> ret.add(role.type()));
        return ret;
    }
}
