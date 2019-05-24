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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.general.EmailAddress;
import eapli.framework.util.Calendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class SystemUserRolesTest {
	private static final Role ROLE_R2 = Role.valueOf("R2");
	private static final String PASSWORD1 = "Password1";
	private SystemUser instance;
	private Calendar createdOn;

	@Before
	public void setUp() throws Exception {
		createdOn = Calendars.now();
		instance = new SystemUserBuilder().withUsername("abc").withPassword(PASSWORD1).withFirstName("Ant")
				.withLastName("Boocam").withEmail("a@b.com").createdOn(createdOn).build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureMustHaveRoles() {
		final Set<Role> roles = null;
		new SystemUser(Username.valueOf("username"), Password.valueOf(PASSWORD1), Name.valueOf("Ant", "Silva"),
				EmailAddress.valueOf("a@b.co"), roles, Calendars.now());
	}

	@Test
	public void ensureCanHaveNoRole() {
		final Set<Role> roles = new HashSet<>();
		final SystemUser newUser = new SystemUser(Username.valueOf("username"), Password.valueOf(PASSWORD1),
				Name.valueOf("Ant", "Silva"), EmailAddress.valueOf("a@b.co"), roles, Calendars.now());
		assertNotNull(newUser);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureCannotAssignNullRole() {
		final Role x = null;
		instance.assignToRole(x);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureCannotAssignNullRoleAssignemt() {
		final RoleAssignment x = null;
		instance.assignToRole(x);
	}

	@Test
	public void ensureAssignRoleAddsRole() {
		final int nRoles = instance.roleTypes().size();
		instance.assignToRole(ROLE_R2);
		assertEquals(nRoles + 1, instance.roleTypes().size());
	}

	@Test
	public void ensureAssignRole() {
		instance.assignToRole(ROLE_R2);
		assertTrue(instance.hasAll(ROLE_R2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureCannotUnassignNullRole() {
		final Role x = null;
		instance.unassignRole(x);
	}

	@Test
	public void ensureUnassignUnexistingRoleInformsFalse() {
		assertFalse(instance.unassignRole(Role.valueOf("R3")));
	}

	@Test
	public void ensureUnassignRole() {
		instance.assignToRole(ROLE_R2);
		instance.unassignRole(ROLE_R2);
		assertFalse(instance.hasAll(ROLE_R2));
	}

	@Test
	public void ensureHasRole() {
		instance.assignToRole(ROLE_R2);
		assertTrue(instance.hasAll(ROLE_R2));
	}

	@Test
	public void ensureHasRoleFailsForUnexistingRole() {
		assertFalse(instance.hasAll(ROLE_R2));
	}

	@Test
	public void ensureUnassignExistingRoleInformsTrue() {
		instance.assignToRole(ROLE_R2);
		assertTrue(instance.unassignRole(ROLE_R2));
	}
}
