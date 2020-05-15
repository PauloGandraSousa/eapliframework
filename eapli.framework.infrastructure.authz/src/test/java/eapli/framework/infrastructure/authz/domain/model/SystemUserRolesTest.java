/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.time.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class SystemUserRolesTest {
    private static final Role ROLE_R1 = Role.valueOf("R1");
    private static final Role ROLE_R2 = Role.valueOf("R2");
    private static final String PASSWORD1 = "Password1";

    private SystemUser subject;

    @Before
    public void setUp() throws Exception {
        subject = TestSystemUserFactory.dummyUser("abc", ROLE_R1);
        System.out.println("setup " + subject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveRoles() {
        final Role roles = null;
        TestSystemUserFactory.dummyUser("abc", roles);
    }

    @Test
    public void ensureCanHaveNoRole() {
        final Set<Role> roles = new HashSet<>();
        final SystemUser newUser = new SystemUser(Username.valueOf("username"),
                Password.encodedAndValid(PASSWORD1, new NilPasswordPolicy(), new PlainTextEncoder())
                        .orElseThrow(IllegalStateException::new),
                Name.valueOf("Ant", "Silva"), EmailAddress.valueOf("a@b.co"), roles, Calendars.now());
        assertNotNull(newUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotAssignNullRole() {
        final Role x = null;
        subject.assignToRole(x);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotAssignNullRoleAssignemt() {
        final RoleAssignment x = null;
        subject.assignToRole(x);
    }

    @Test
    public void ensureAssignRoleAddsRole() {
        final int nRoles = subject.roleTypes().size();
        subject.assignToRole(ROLE_R2);
        assertEquals(nRoles + 1, subject.roleTypes().size());
    }

    @Test
    public void ensureAssignRole() {
        subject.assignToRole(ROLE_R2);
        assertTrue(subject.hasAll(ROLE_R2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotUnassignNullRole() {
        final Role x = null;
        subject.unassignRole(x);
    }

    @Test
    public void ensureUnassignUnexistingRoleInformsFalse() {
        assertFalse(subject.unassignRole(Role.valueOf("R3")));
    }

    @Test
    public void ensureUnassignRole() {
        subject.assignToRole(ROLE_R2);
        subject.unassignRole(ROLE_R2);
        assertFalse(subject.hasAll(ROLE_R2));
    }

    @Test
    public void ensureHasRole() {
        subject.assignToRole(ROLE_R2);
        assertTrue(subject.hasAll(ROLE_R2));
    }

    @Test
    public void ensureHasRoleFailsForUnexistingRole() {
        assertFalse(subject.hasAll(ROLE_R2));
    }

    @Test
    public void ensureUnassignExistingRoleInformsTrue() {
        subject.assignToRole(ROLE_R2);
        assertTrue(subject.unassignRole(ROLE_R2));
    }
}
