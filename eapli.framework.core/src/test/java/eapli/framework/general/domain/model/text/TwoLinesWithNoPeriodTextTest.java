/**
 *
 */
package eapli.framework.general.domain.model.text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class TwoLinesWithNoPeriodTextTest extends TextTestBase {

    public static final String TWO_LINER = "a fence.\nthe fence";

    @Override
    public String getText() {
        return TWO_LINER;
    }

    @Override
    public long getExpectedWordCount() {
        return 4;
    }

    @Override
    public long getExpectedLineCount() {
        return 2;
    }

    @Override
    public List<String> getExpectedWords() {
        final String[] words = { "a", "fence", "the", "fence" };
        return Arrays.asList(words);
    }

    @Override
    public Map<String, Integer> getExpectedFrequency() {
        final Map<String, Integer> freq = new HashMap<>();
        freq.put("a", 1);
        freq.put("fence", 2);
        freq.put("the", 1);
        return freq;
    }
}
