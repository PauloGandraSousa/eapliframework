/*
 * Copyright (c) 2013-2022 the original author or authors.
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
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.time.util.CurrentTimeCalendars;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class SystemUserTest {
    private static final String PASSWORD1 = "Password1";
    private SystemUser instance;
    private Calendar dateOfCreation;

    @BeforeEach
    void setUp() throws Exception {
        dateOfCreation = CurrentTimeCalendars.now();
        instance = new SystemUser(Username.valueOf("abc"), TestDataSubjects.dummyPass(PASSWORD1),
                Name.valueOf("Ant", "Boocam"), EmailAddress.valueOf("a@b.com"), new HashSet<>(), dateOfCreation);
    }

    @Test
    void ensureMustHaveUsername() {
        final Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf("R"));

        assertThrows(IllegalArgumentException.class,
                () -> new SystemUser(null, TestDataSubjects.dummyPass(PASSWORD1), Name.valueOf("Ant", "Silva"),
                        EmailAddress.valueOf("a@b.co"), roles));
    }

    @Test
    void ensureMustHavePassword() {
        final Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf("R"));

        assertThrows(IllegalArgumentException.class,
                () -> new SystemUser(Username.valueOf("username"), null, Name.valueOf("Ant", "Silva"),
                        EmailAddress.valueOf("a@b.co"),
                        roles));
    }

    @Test
    void ensureMustHaveName() {
        final Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf("R"));

        assertThrows(IllegalArgumentException.class,
                () -> new SystemUser(Username.valueOf("username"), TestDataSubjects.dummyPass(PASSWORD1), null,
                        EmailAddress.valueOf("a@b.co"), roles));
    }

    @Test
    void ensureMustHaveEmail() {
        final Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf("R"));

        assertThrows(IllegalArgumentException.class,
                () -> new SystemUser(Username.valueOf("username"), TestDataSubjects.dummyPass(PASSWORD1),
                        Name.valueOf("Ant", "Silva"), null, roles));
    }

    @Test
    void ensureMustHaveDateOfCreation() {
        final Set<Role> roles = new HashSet<>();
        roles.add(Role.valueOf("R"));

        assertThrows(IllegalArgumentException.class,
                () -> new SystemUser(Username.valueOf("username"), TestDataSubjects.dummyPass(PASSWORD1),
                        Name.valueOf("Ant", "Silva"), EmailAddress.valueOf("a@b.co"), roles, null));
    }

    @Test
    void ensureDateOfCreationIsSameAsCreatedOn() {
        assertEquals(dateOfCreation, instance.createdOn());
    }

    @Test
    void ensureHasCreatedOn() {
        assertNotNull(instance.createdOn());
    }

    @Test
    void ensurePasswordMatches() {
        assertTrue(instance.passwordMatches(PASSWORD1, new PlainTextEncoder()));
    }

    @Test
    void ensurePasswordDoesntMatch() {
        assertFalse(instance.passwordMatches(PASSWORD1 + "xpto", new PlainTextEncoder()));
    }
}
