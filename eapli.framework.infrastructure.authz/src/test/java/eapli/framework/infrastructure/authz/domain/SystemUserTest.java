package eapli.framework.infrastructure.authz.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eapli.framework.util.DateTime;

public class SystemUserTest {
    private static final String PASSWORD1 = "Password1";
    private SystemUser instance;

    @Before
    public void setUp() throws Exception {
        instance = new SystemUserBuilder().withUsername("abc").withPassword(PASSWORD1).withFirstName("Ant")
                .withLastName("Boocam").withEmail("a@b.com").withCreatedOn(DateTime.now()).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalStateException.class)
    public void ensureCannotDeactivateAnInactiveUser() {
        try {
            instance.deactivate(DateTime.now());
        } catch (final Exception e) {
            fail("something stange just happened. we are only interested in catching the exception on the second call to deactivate");
        }
        instance.deactivate(DateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotDeactivatePriorToCreationDate() {
        instance.deactivate(DateTime.yesterday());
    }

    @Test
    public void ensureAfterDeactivationUserIsInactive() {
        instance.deactivate(DateTime.now());
        assertFalse(instance.isActive());
    }

    @Test
    public void ensurePasswordMatches() {
        assertTrue(instance.passwordMatches(Password.valueOf(PASSWORD1)));
    }

    @Test
    public void ensurePasswordDoesntMatch() {
        assertFalse(instance.passwordMatches(Password.valueOf(PASSWORD1 + "xpto")));
    }
}
