/*
 * Copyright (c) 2013-2019 the original author or authors.
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
 *
 * @author Paulo Gandra Sousa 13/05/2020
 *
 */
public class PrettyJsonString extends BaseFormattedString {

    public PrettyJsonString(final String input) {
        super(input);
    }

    /**
     * Returns a pretty formatted JSON object.
     *
     * <p>
     * From
     * <a href= "https://www.mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/">mykong</a>
     *
     * @param input
     *            a JSON string
     * @return a pretty formatted JSON object
     */
    @Override
    protected String formatted() {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Object json = mapper.readValue(input(), Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException("Formatting to JSON:" + input(), e);
        }
    }
}
