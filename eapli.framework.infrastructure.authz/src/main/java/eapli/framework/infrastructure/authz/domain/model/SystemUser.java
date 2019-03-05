package eapli.framework.infrastructure.authz.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.EmailAddress;
import eapli.framework.dto.DTOable;
import eapli.framework.dto.GenericDTO;
import eapli.framework.util.DateTime;
import eapli.framework.util.Preconditions;
import eapli.framework.visitor.Visitable;
import eapli.framework.visitor.Visitor;

/**
 * An application user.
 *
 * This class represents application users. It follows a DDD approach where User
 * is the root entity of the User Aggregate and all of its properties are
 * instances of value objects.
 *
 * This approach may seem a little more complex than just having String or
 * native type attributes but provides for real semantic of the domain and
 * follows the Single Responsibility Pattern
 *
 * @author Paulo Gandra Sousa
 *
 */
@Entity
public class SystemUser
        implements AggregateRoot<Username>, DTOable, Visitable<GenericDTO>, Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    private Username username;
    private Password password;
    private Name name;
    private EmailAddress email;
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private RoleSet roles;
    @Temporal(TemporalType.DATE)
    private Calendar createdOn;
    private boolean active;
    @Temporal(TemporalType.DATE)
    private Calendar deactivatedOn;

    /**
     * Convenience constructor for today's date of creation
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     */
    public SystemUser(final Username username, final Password password, final Name name,
            final EmailAddress email, final Set<Role> roles) {
        this(username, password, name, email, roles, DateTime.now());
    }

    public SystemUser(final Username username, final Password password, final Name name,
            final EmailAddress email, final Set<Role> roles, final Calendar createdOn) {
        Preconditions.nonEmpty(roles, "roles cannot be null");

        final RoleSet roleset = new RoleSet();
        roleset.addAll(roles.stream().map(rt -> new RoleAssignment(rt, this.createdOn))
                .collect(Collectors.toList()));
        init(username, password, name, email, roleset, createdOn);
    }

    public SystemUser(final Username username, final Password password, final Name name,
            final EmailAddress email, final RoleSet roles) {
        this(username, password, name, email, roles, DateTime.now());
    }

    public SystemUser(final Username username, final Password password, final Name name,
            final EmailAddress email, final RoleSet roles, final Calendar createdOn) {
        init(username, password, name, email, roles, createdOn);
    }

    private void init(final Username username, final Password password, final Name name,
            final EmailAddress email, final RoleSet roles, final Calendar createdOn) {
        Preconditions.nonNull(roles, username, password, name, email, createdOn);

        this.createdOn = createdOn;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roles = roles;

        active = true;
    }

    protected SystemUser() {
        // for ORM
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof SystemUser)) {
            return false;
        }

        final SystemUser that = (SystemUser) other;
        if (this == that) {
            return true;
        }
        if (!username.equals(that.username) || !password.equals(that.password)
                || !name.equals(that.name) || !email.equals(that.email)) {
            return false;
        }
        return roles.equals(that.roles);
    }

    @Override
    public Username id() {
        return username;
    }

    public EmailAddress email() {
        return email;
    }

    /**
     * Add role to user.
     *
     * @param role
     *            Role to assign to SystemUser.
     */
    public void addRole(final RoleAssignment role) {
        roles.add(role);
    }

    public Collection<Role> roleTypes() {
        return roles.roleTypes();
    }

    @Override
    public GenericDTO toDTO() {
        final GenericDTO ret = new GenericDTO("user");
        ret.put("username", username.toString());
        ret.put("password", password.toString());
        ret.put("name", name.toString());
        ret.put("email", email.toString());
        ret.put("roles", roles.roleTypes().toString());
        // TODO: ASK Isn't it easy to forget mapping an element to DTO when
        // manipulating members?

        return ret;
    }

    /**
     * Remove role from user.
     *
     * @param role
     *            Role to remove from SystemUser.
     */
    public void removeRole(final RoleAssignment role) {
        // TODO should the role be removed or marked as "expired"?
        roles.remove(role);
    }

    public boolean passwordMatches(final Password password) {
        return this.password.equals(password);
    }

    @Override
    public void accept(final Visitor<GenericDTO> visitor) {
        visitor.visit(toDTO());
    }

    public Username username() {
        return username;
    }

    public Name name() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate(final Calendar deactivatedOn) {
        // cannot deactivate a user before it was registered in the system
        if (deactivatedOn == null || deactivatedOn.before(createdOn)) {
            throw new IllegalArgumentException();
        }
        // cannot deactivate an inactive user
        if (!active) {
            // we could simply do nothing instead of taking a harsh approach of
            // throwing an
            // exception
            throw new IllegalStateException();
        }
        active = false;
        this.deactivatedOn = deactivatedOn;
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    public boolean changePassword(final Password oldPassword, final Password newPassword) {
        if (password.equals(oldPassword)) {
            password = newPassword;
            return true;
        }
        return false;
    }

    public String resetPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean resetPassword(final String token) {
        Preconditions.nonEmpty(token);

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * checks if this user has any of a set of roles
     *
     * @param roles
     * @return
     */
    public boolean hasAny(final Role[] roles) {
        for (final Role r : roles) {
            if (this.roles.hasAssignment(r)) {
                return true;
            }
        }

        return false;
    }

    /**
     * checks if this user has all of a set of roles
     *
     * @param roles
     * @return
     */
    public boolean hasAll(final Role[] roles) {
        for (final Role r : roles) {
            if (!(this.roles.hasAssignment(r))) {
                return false;
            }
        }

        return true;
    }
}
