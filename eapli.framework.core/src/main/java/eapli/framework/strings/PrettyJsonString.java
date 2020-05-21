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
package eapli.framework.strings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A string with JSON content pretty formatted for output.
 *
 * @author Paulo Gandra Sousa 13/05/2020
 *
 */
public class PrettyJsonString implements FormattedString {

    private final Object input;

    /**
     * Factory method to construct a pretty formatted string from a string with unformatted JSON
     * content.
     *
     * @param input
     *            a json-encoded content
     * @return a PrettyJsonString
     */
    public static FormattedString fromString(final String input) {
        return new PrettyJsonString(input);
    }

    /**
     * Factory method to construct a pretty formatted string from a java object.
     *
     * @param input
     *            the java object to map to JSON
     * @return a PrettyJsonString
     */
    public static FormattedString fromObject(final Object input) {
        return new PrettyJsonString(input);
    }

    /**
     * Constructs a pretty formatted string from a string with unformatted JSON content.
     *
     * @param input
     *            a json-encoded string
     */
    public PrettyJsonString(final String input) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            this.input = mapper.readValue(input, Object.class);
        } catch (final JsonProcessingException e) {
            throw new FormatingOrTransformationException("Formatting to JSON:" + input, e);
        }
    }

    /**
     * Constructs a pretty formatted string from a java object.
     *
     * @param input
     *            the java object to map to JSON
     */
    public PrettyJsonString(final Object input) {
        this.input = input;
    }

    /**
     * Returns a pretty formatted JSON string.
     *
     * <p>
     * From
     * <a href=
     * "https://www.mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/">mykong</a>
     *
     * @return a pretty formatted JSON object
     */
    @Override
    public String toString() {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input);
        } catch (final JsonProcessingException e) {
            throw new FormatingOrTransformationException("Formatting to JSON:" + input, e);
        }
    }
}
