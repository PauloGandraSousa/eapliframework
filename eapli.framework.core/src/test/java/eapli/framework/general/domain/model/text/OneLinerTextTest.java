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
public class OneLinerTextTest extends TextTestBase {

    public static final String ONE_LINER = "a little red fox jumps over a fence.";

    @Override
    public String getText() {
        return ONE_LINER;
    }

    @Override
    public long getExpectedWordCount() {
        return 8;
    }

    @Override
    public long getExpectedLineCount() {
        return 1;
    }

    @Override
    public List<String> getExpectedWords() {
        final String[] words = { "a", "little", "red", "fox", "jumps", "over", "a", "fence" };
        return Arrays.asList(words);
    }

    @Override
    public Map<String, Integer> getExpectedFrequency() {
        final Map<String, Integer> freq = new HashMap<>();
        freq.put("a", 2);
        freq.put("little", 1);
        freq.put("red", 1);
        freq.put("fox", 1);
        freq.put("jumps", 1);
        freq.put("over", 1);
        freq.put("fence", 1);
        return freq;
    }
}
