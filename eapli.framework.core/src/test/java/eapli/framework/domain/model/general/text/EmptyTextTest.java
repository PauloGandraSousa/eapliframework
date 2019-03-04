/**
 *
 */
package eapli.framework.domain.model.general.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pgsou_000
 *
 */
public class EmptyTextTest extends TextTestBase {

    @Override
    public String getText() {
        return "";
    }

    @Override
    public long getExpectedWordCount() {
        return 0;
    }

    @Override
    public long getExpectedLineCount() {
        return 1;
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
