/**
 *
 */
package eapli.framework.infrastructure.authz.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eapli.framework.infrastructure.authz.domain.Password;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class PasswordTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ensurePasswordHasAtLeastOneDigitOneCapitalAnd6CharactersLong() {
        new Password("abCfefgh1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsSmallerThan6CharactersAreNotAllowed() {
        new Password("ab1c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsWithoutDigitsAreNotAllowed() {
        new Password("abcefghi");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsWithoutCapitalLetterAreNotAllowed() {
        new Password("abcefghi1");
    }
}
