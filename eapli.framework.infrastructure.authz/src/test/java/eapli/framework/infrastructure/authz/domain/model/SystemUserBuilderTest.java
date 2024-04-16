/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.time.util.CurrentTimeCalendars;

/**
 * @author Paulo Gandra de Sousa
 */
class SystemUserBuilderTest {

	private static final NilPasswordPolicy POLICY = new NilPasswordPolicy();
	private static final PlainTextEncoder ENCODER = new PlainTextEncoder();

	private static final String SILVA_STR = "Silva";
	private static final String ANTONIO_STR = "Antonio";
	private static final String ANTONIO_SILVA_COM_STR = "antonio@silva.com";
	private static final String USERNAME_STR = "username";
	private static final String PASSWORD1_STR = "Password1";

	private static final Username USERNAME = Username.valueOf(USERNAME_STR);
	private static final EmailAddress ANTONIO_AT_SILVA_DOT_COM = EmailAddress.valueOf(ANTONIO_SILVA_COM_STR);
	private static final Name ANTONIO_SILVA = Name.valueOf(ANTONIO_STR, SILVA_STR);
	private static final Password PASSWORD = Password.encodedAndValid(PASSWORD1_STR, POLICY, ENCODER)
			.orElseThrow(IllegalStateException::new);
	private static final Role ROLE_1 = Role.valueOf("R1");
	private static final Role ROLE_2 = Role.valueOf("R2");
	private static final Role ROLE_3 = Role.valueOf("R3");
	private Calendar dateOfCreation;

	private SystemUserBuilder subject;

	@BeforeEach
	void setUp() {
		subject = new SystemUserBuilder(POLICY, ENCODER);
		dateOfCreation = CurrentTimeCalendars.now();
	}

	@Test
	void ensureSystemBuilderMustHavePasswordPolicy() {
		assertThrows(IllegalArgumentException.class, () -> new SystemUserBuilder(null, ENCODER));
	}

	@Test
	void ensureSystemBuilderMustHavePasswordEncoder() {
		assertThrows(IllegalArgumentException.class, () -> new SystemUserBuilder(POLICY, null));
	}

	@Test
	void testBuildWithoutCreationDate() {
		// ARRANGE
		subject.with(USERNAME_STR, PASSWORD1_STR, ANTONIO_STR, SILVA_STR, ANTONIO_SILVA_COM_STR).build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertInvariants(user);
		assertNotNull(user.createdOn());
		assertTrue(user.roleTypes().isEmpty());
	}

	private void assertInvariants(final SystemUser user) {
		assertNotNull(user);
		assertEquals(USERNAME, user.username());
		assertTrue(user.passwordMatches(PASSWORD1_STR, ENCODER));
		assertEquals(ANTONIO_SILVA, user.name());
		assertEquals(ANTONIO_AT_SILVA_DOT_COM, user.email());
		assertTrue(user.isActive());
	}

	void testBuildWithCreationDate() {
		// ARRANGE
		subject.with(USERNAME_STR, PASSWORD1_STR, ANTONIO_STR, SILVA_STR, ANTONIO_SILVA_COM_STR)
				.createdOn(dateOfCreation).build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertInvariants(user);
		assertEquals(dateOfCreation, user.createdOn());
		assertTrue(user.roleTypes().isEmpty());
	}

	void testBuildWithOneRole() {
		// ARRANGE
		subject.with(USERNAME_STR, PASSWORD1_STR, ANTONIO_STR, SILVA_STR, ANTONIO_SILVA_COM_STR).withRoles(ROLE_1)
				.build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertInvariants(user);
		assertNotNull(user.createdOn());
		assertFalse(user.roleTypes().isEmpty());
		assertTrue(user.hasAny(ROLE_1));
		assertFalse(user.hasAny(ROLE_2));
		assertFalse(user.hasAll(ROLE_1, ROLE_2));
	}

	void testBuildWithThreeRoles() {
		// ARRANGE
		subject.with(USERNAME_STR, PASSWORD1_STR, ANTONIO_STR, SILVA_STR, ANTONIO_SILVA_COM_STR)
				.withRoles(ROLE_1, ROLE_2, ROLE_3).build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertInvariants(user);
		assertNotNull(user.createdOn());
		assertFalse(user.roleTypes().isEmpty());
		assertTrue(user.hasAny(ROLE_1));
		assertTrue(user.hasAny(ROLE_2));
		assertTrue(user.hasAny(ROLE_3));
		assertTrue(user.hasAny(ROLE_1, ROLE_2, ROLE_3));
		assertTrue(user.hasAll(ROLE_1, ROLE_2, ROLE_3));
	}

	@Test
	void testBuildWithEmailAsUsername() {
		// ARRANGE
		subject.with(ANTONIO_SILVA_COM_STR, PASSWORD1_STR, ANTONIO_STR, SILVA_STR, ANTONIO_SILVA_COM_STR).build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertNotNull(user);
		assertEquals(Username.valueOf(ANTONIO_SILVA_COM_STR), user.username());
	}

	@Test
	void testBuildWithEmailAsUsername2() {
		// ARRANGE
		subject.withEmailAsUsername(ANTONIO_SILVA_COM_STR).withPassword(PASSWORD1_STR).withName(ANTONIO_STR, SILVA_STR)
				.build();
		// ACT
		final var user = subject.build();
		// ASSERT
		assertNotNull(user);
		assertEquals(Username.valueOf(ANTONIO_SILVA_COM_STR), user.username());
	}
}
