/**
 *
 */
package eapli.framework.general.domain.model.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.general.domain.model.Text;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class TextTestBase {
    protected Text instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        instance = Text.valueOf(getText());
    }

    public abstract String getText();

    public abstract long getExpectedWordCount();

    public abstract long getExpectedLineCount();

    public abstract List<String> getExpectedWords();

    public abstract Map<String, Integer> getExpectedFrequency();

    @Test
    public void ensureWordCount() {
        assertEquals(getExpectedWordCount(), instance.wordCount());
    }

    @Test
    public void ensureLineCount() {
        assertEquals(getExpectedLineCount(), instance.lineCount());
    }

    @Test
    public void ensureFrequency() {
        assertEquals(getExpectedFrequency(), instance.frequency());
    }

    @Test
    public void ensureWords() {
        final List<String> expected = getExpectedWords();
        assertEquals(expected.size(), instance.words().count());
        assertTrue(instance.words().allMatch(e -> expected.contains(e)));
    }
}
