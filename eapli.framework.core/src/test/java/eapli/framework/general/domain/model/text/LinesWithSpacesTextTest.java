/**
 *
 */
package eapli.framework.general.domain.model.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class LinesWithSpacesTextTest extends TextTestBase {

    public static final String LINES_WITH_SPACES = "  \n  \n  \n  ";

    @Override
    public String getText() {
        return LINES_WITH_SPACES;
    }

    @Override
    public long getExpectedWordCount() {
        return 0;
    }

    @Override
    public long getExpectedLineCount() {
        return 4;
    }

    @Override
    public List<String> getExpectedWords() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> getExpectedFrequency() {
        return new HashMap<>();
    }
}
