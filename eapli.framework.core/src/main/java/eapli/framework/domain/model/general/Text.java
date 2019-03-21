/**
 *
 */
package eapli.framework.domain.model.general;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.Factory;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;

/**
 * A Text composed of lines. lines are separated by the \n character; words are
 * separated by any white space character. end of sentence punctuation is the
 * period, the exclamation and the question mark.
 *
 * a Text object will always have content, even if it is an empty string
 *
 * if the last character of the text is a new line, the line count will take
 * that into consideration. example, the text "the red fox" has one line while
 * the text "the red fox\n" has two lines
 *
 * @author pgsou_000
 *
 */
public class Text implements ValueObject, StringMixin {

    private static final long serialVersionUID = 1L;
    private final String theText;

    public static class TextBuilder implements Factory<Text> {
        private final StringBuilder builder = new StringBuilder("");

        public TextBuilder(final String text) {
            append(text);
        }

        public TextBuilder appendLine(final String text) {
            return append(text).append("\n");
        }

        public TextBuilder append(final String text) {
            if (text != null) {
                builder.append(text);
            }
            return this;
        }

        @Override
        public Text build() {
            return new Text(builder.toString());
        }
    }

    /**
     * fluent builder interface starting point
     *
     * @param text
     * @return
     */
    public static TextBuilder with(final String text) {
        return new TextBuilder(text);
    }

    /**
     * factory method
     *
     * @param fromText
     * @return
     */
    public static Text valueOf(final String fromText) {
        return new TextBuilder(fromText).build();
    }

    private Text(final String text) {
        theText = text;
    }

    /**
     * helper Collector
     *
     * @author pgsou_000
     *
     */
    private class WordCounterCollector {
        Map<String, Integer> freq = new HashMap<>();

        public void merge(final String word) {
            freq.merge(word, 1, (i, j) -> i + j);
        }

        public void mergeAll(final WordCounterCollector o) {
            for (final Entry<String, Integer> each : o.freq.entrySet()) {
                freq.merge(each.getKey(), each.getValue(), (a, b) -> a + b);
            }
        }
    }

    public Map<String, Integer> frequency() {
        return words().collect(WordCounterCollector::new, WordCounterCollector::merge,
                WordCounterCollector::mergeAll).freq;
    }

    public long lineCount() {
        return theText.split("\\n", -1).length;
    }

    public long wordCount() {
        return words().count();
    }

    public Stream<String> words() {
        final String[] w = wordArray();
        return Arrays.stream(w).filter(x -> !x.isEmpty());
    }

    private String[] wordArray() {
        return theText.split("[\\s\\.,;!\\?]+");
    }

    @Override
    public String toString() {
        return theText;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Text)) {
            return false;
        }
        if (this == other) {
            return true;
        }

        final Text that = (Text) other;
        return theText.equals(that.theText);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(theText).code();
    }

    /**
     * sentence capitalize, i.e., first word of each sentence is capital letter.
     * a sentence is defined by the punctuation signals ".", "!" and "?"
     *
     * @return
     */
    public Text capitalized() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * all text as capital letters
     *
     * @return
     */
    public Text allCaps() {
        return valueOf(theText.toUpperCase());
    }
}
