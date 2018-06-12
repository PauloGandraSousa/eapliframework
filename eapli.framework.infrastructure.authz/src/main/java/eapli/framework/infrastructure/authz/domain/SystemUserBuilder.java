package eapli.framework.infrastructure.authz.domain;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import eapli.framework.domain.DomainFactory;
import eapli.framework.domain.general.EmailAddress;
import eapli.framework.util.predicates.StringPredicates;

/**
 * A factory for User entities.
 *
 * This class demonstrates the use of the factory (DDD) pattern using a fluent
 * interface. it acts as a Builder (GoF).
 */
public class SystemUserBuilder implements DomainFactory<SystemUser> {

    private Username username;
    private Password password;
    private String firstName;
    private String lastName;
    private Name name;
    private EmailAddress email;
    private final RoleSet roles;
    private Calendar createdOn;

    public SystemUserBuilder() {
        roles = new RoleSet();
    }

    public SystemUserBuilder(final String userName2, final String password2, final String firstName,
            final String lastName, final String email2, final Set<RoleType> roles2) {
        withUsername(userName2);
        withPassword(password2);
        withFirstName(firstName);
        withLastName(lastName);
        withEmail(email2);
        roles = new RoleSet();
        withRoles(roles2);
    }

    public SystemUserBuilder withUsername(final String username) {
        this.username = Username.valueOf(username);
        return this;
    }

    public SystemUserBuilder withUsername(final Username username) {
        this.username = username;
        return this;
    }

    public SystemUserBuilder withPassword(final String password) {
        this.password = Password.valueOf(password);
        return this;
    }

    public SystemUserBuilder withPassword(final Password password) {
        this.password = password;
        return this;
    }

    public SystemUserBuilder withFirstName(final String firstName) {
        this.firstName = firstName;
        if (!StringPredicates.isNullOrEmpty(lastName)) {
            name = new Name(this.firstName, lastName);
        }
        return this;
    }

    public SystemUserBuilder withLastName(final String lastName) {
        this.lastName = lastName;
        if (!StringPredicates.isNullOrEmpty(firstName)) {
            name = new Name(firstName, this.lastName);
        }
        return this;
    }

    public SystemUserBuilder withName(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        name = new Name(firstName, this.lastName);
        return this;
    }

    public SystemUserBuilder withName(final Name name) {
        this.name = name;
        return this;
    }

    public SystemUserBuilder withEmail(final String email) {
        this.email = EmailAddress.valueOf(email);
        return this;
    }

    public SystemUserBuilder withEmail(final EmailAddress email) {
        this.email = email;
        return this;
    }

    public SystemUserBuilder withRole(final RoleType role) {
        roles.add(new Role(role));
        return this;
    }

    public SystemUserBuilder withRole(final Role role) {
        roles.add(role);
        return this;
    }

    public SystemUserBuilder withCreatedOn(final Calendar createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public SystemUserBuilder withRoles(final Set<RoleType> someRoles) {
        List<Role> theRoles;
        if (createdOn == null) {
            theRoles = someRoles.stream().map(rt -> new Role(rt)).collect(Collectors.toList());
        } else {
            theRoles = someRoles.stream().map(rt -> new Role(rt, createdOn)).collect(Collectors.toList());
        }
        roles.addAll(theRoles);
        return this;
    }

    public SystemUserBuilder withRoles(final RoleSet roles) {
        this.roles.addAll(roles);
        return this;
    }

    @Override
    public SystemUser build() {
        // since the factory knows that all the parts are needed it could throw
        // an exception. however, we will leave that to the constructor
        if (createdOn != null) {
            return new SystemUser(username, password, name, email, roles, createdOn);
        } else {
            return new SystemUser(username, password, name, email, roles);
        }
    }
}
