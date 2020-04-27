/**
 *
 */
package eapli.framework.general.domain.model.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eapli.framework.general.domain.model.Text;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class TextTest {

    @Test
    public void ensureNullWillBeEmpty() {
        assertEquals("", Text.valueOf(null).toString());
    }

    @Test
    public void ensureEmptyIsEmpty() {
        assertEquals("", Text.valueOf("").toString());
    }
}
