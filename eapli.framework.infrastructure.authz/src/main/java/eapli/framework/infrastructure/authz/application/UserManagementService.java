/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.infrastructure.authz.application;

import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eapli.framework.domain.model.general.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 */
@Component
public class UserManagementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SystemUserBuilder userBuilder;

    public UserManagementService() {
        // default for Spring dependency injection
    }

    /**
     * For scenarios without Spring dependency injection.
     *
     * @param userRepo
     */
    public UserManagementService(final UserRepository userRepo) {
        userRepository = userRepo;
    }

    /**
     * Registers a new user in the system.
     *
     * @param username
     * @param rawPassword
     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     * @param createdOn
     * @return the new user
     */
    @Transactional
    public SystemUser registerNewUser(final String username, final String rawPassword, final String firstName,
            final String lastName, final String email, final Set<Role> roles, final Calendar createdOn) {
        userBuilder.with(username, rawPassword, firstName, lastName, email).createdOn(createdOn).withRoles(roles);
        final SystemUser newUser = userBuilder.build();
        return userRepository.save(newUser);
    }

    /**
     * Registers a new user in the system.
     *
     * @param username
     * @param rawPassword
     * @param firstName
     * @param lastName
     * @param email
     * @param roles
     * @return the new user
     */
    @Transactional
    public SystemUser registerNewUser(final String username, final String rawPassword, final String firstName,
            final String lastName, final String email, final Set<Role> roles) {
        return registerNewUser(username, rawPassword, firstName, lastName, email, roles, Calendars.now());
    }

    /**
     * Creates a user <strong>assuming</strong> the password policy and encoding
     * have already been enforced. Mostly useful for two-step
     * signup/registration process.
     *
     * @param username
     * @param password
     * @param name
     * @param email
     * @param roles
     * @return the enw user
     */
    public SystemUser registerUser(final Username username, final Password password, final Name name,
            final EmailAddress email, final Set<Role> roles) {
        userBuilder.with(username, password, name, email).withRoles(roles);
        final SystemUser newUser = userBuilder.build();
        return userRepository.save(newUser);
    }

    public Iterable<SystemUser> activeUsers() {
        return userRepository.findByActive(true);
    }

    public Iterable<SystemUser> deactivatedUsers() {
        return userRepository.findByActive(false);
    }

    public Iterable<SystemUser> allUsers() {
        return userRepository.findAll();
    }

    public Optional<SystemUser> find(final Username id) {
        return userRepository.ofIdentity(id);
    }

    @Transactional
    public SystemUser deactivateUser(final SystemUser user) {
        user.deactivate(Calendars.now());
        return userRepository.save(user);
    }
}
