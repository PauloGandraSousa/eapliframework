/**
 *
 */
package eapli.framework.domain.general.text;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.general.Text;

/**
 * @author pgsou_000
 *
 */
public class TextTest {

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
    public void ensureNullWillBeEmpty() {
        assertEquals("", Text.valueOf(null).toString());
    }

    @Test
    public void ensureEmptyIsEmpty() {
        assertEquals("", Text.valueOf("").toString());
    }
}
