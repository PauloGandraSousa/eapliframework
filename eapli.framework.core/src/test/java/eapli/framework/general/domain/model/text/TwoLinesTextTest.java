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
public class TwoLinesTextTest extends TextTestBase {

    public static final String TWO_LINER = "a little red fox jumps over a fence.\nthe fence is too high, so the fox cries.";

    @Override
    public String getText() {
        return TWO_LINER;
    }

    @Override
    public long getExpectedWordCount() {
        return 17;
    }

    @Override
    public long getExpectedLineCount() {
        return 2;
    }

    @Override
    public List<String> getExpectedWords() {
        final String[] words = { "a", "little", "red", "fox", "jumps", "over", "a", "fence", "the", "fence", "is",
                "too", "high", "so", "the", "fox", "cries" };
        return Arrays.asList(words);
    }

    @Override
    public Map<String, Integer> getExpectedFrequency() {
        final Map<String, Integer> freq = new HashMap<>();
        freq.put("a", 2);
        freq.put("little", 1);
        freq.put("red", 1);
        freq.put("fox", 2);
        freq.put("jumps", 1);
        freq.put("over", 1);
        freq.put("fence", 2);
        freq.put("the", 2);
        freq.put("is", 1);
        freq.put("too", 1);
        freq.put("high", 1);
        freq.put("so", 1);
        freq.put("cries", 1);
        return freq;
    }
}
