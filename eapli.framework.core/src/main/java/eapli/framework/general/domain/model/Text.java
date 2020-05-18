/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.general.domain.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import eapli.framework.util.Factory;
import eapli.framework.util.HashCoder;

/**
 * A Text composed of lines. lines are separated by the \n character; words are
 * separated by any white space character. end of sentence punctuation is the
 * period, the exclamation and the question mark. A Text object will always have
 * content, even if it is an empty string
 *
 * <p>
 * if the last character of the text is a new line, the line count will take
 * that into consideration. example, the text "the red fox" has one line while
 * the text "the red fox\n" has two lines
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Embeddable
public class Text implements ValueObject, StringMixin {

    private static final long serialVersionUID = 1L;

    @Column(name = "text")
    @JsonProperty("text")
    private final String value;

    /**
     * Text builder.
     *
     */
    public static class TextBuilder implements Factory<Text> {
        private final StringBuilder builder = new StringBuilder("");

        /**
         * Initiates the builder.
         *
         * @param text
         */
        public TextBuilder(final String text) {
            append(text);
        }

        /**
         * Appends the text followed by a new line.
         *
         * @param text
         * @return the builder
         */
        public TextBuilder appendLine(final String text) {
            return append(text).append("\n");
        }

        /**
         * Appends the text.
         *
         * @param text
         * @return the builder
         */
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
     * Fluent builder interface starting point.
     *
     * @param text
     * @return a builder
     */
    public static TextBuilder with(final String text) {
        return new TextBuilder(text);
    }

    /**
     * Factory method.
     *
     * @param fromText
     * @return a Text object
     */
    public static Text valueOf(final String fromText) {
        return new TextBuilder(fromText).build();
    }

    private Text(final String text) {
        value = text;
    }

    /**
     * helper Collector
     *
     * @author Paulo Gandra de Sousa
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

    /**
     * Returns the frequency, that is, the number of occurrences, of each word
     * in the text.
     *
     * @return the frequency of each word in the text
     */
    public Map<String, Integer> frequency() {
        return words().collect(WordCounterCollector::new, WordCounterCollector::merge,
                WordCounterCollector::mergeAll).freq;
    }

    /**
     * Returns the number of lines in the text. Lines are counted based on the
     * presence of the newline character "\n".
     *
     * @return the number of lines in the text
     */
    public long lineCount() {
        return value.split("\\n", -1).length;
    }

    /**
     * Returns the number of words.
     *
     * @return the number of words
     */
    public long wordCount() {
        return words().count();
    }

    /**
     * Returns a stream with the words of the text.
     *
     * @return the words of the text
     */
    public Stream<String> words() {
        final String[] w = wordArray();
        return Arrays.stream(w).filter(x -> !x.isEmpty());
    }

    private String[] wordArray() {
        return value.split("[\\s\\.,:;!\\?]+");
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(value).code();
    }

    /**
     * Sentence capitalize, i.e., first word of each sentence is capital letter.
     * a sentence is defined by the punctuation signals ".", "!" and "?"
     *
     * @return a new object which content is this' capitalized
     */
    public Text capitalized() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Creates a new Text with the same content as this one but with all text as
     * capital letters.
     *
     * @return a new object
     */
    public Text allCaps() {
        return valueOf(value.toUpperCase());
    }
}
