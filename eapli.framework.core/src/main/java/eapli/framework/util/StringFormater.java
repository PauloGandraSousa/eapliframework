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
package eapli.framework.util;

import eapli.framework.strings.PrettyJsonString;
import eapli.framework.strings.PrettyXmlString;

/**
 * Utility class for string formating.
 *
 * @deprecated use the {@link eapli.framework.strings.FormattedString FormattedString} classes instead
 * @see eapli.framework.strings.FormattedString
 * @author Paulo Gandra Sousa
 *
 */
@Deprecated
@Utility
public final class StringFormater {

    private StringFormater() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Returns a pretty formatted JSON object.
     *
     * @param input
     *            a JSON string
     * @return a pretty formatted JSON object
     */
    public static String prettyformatJson(final String input) {
        return new PrettyJsonString(input).toString();
    }

    /**
     * Returns a pretty formated XML string
     *
     * @param input
     *            an XML string
     * @return a pretty formated XML string
     */
    public static String prettyFormatXml(final String input) {
        return new PrettyXmlString(input).toString();
    }

    /**
     * Returns a formated XML output.
     *
     * @param input
     * @param indent
     * @return a formated XML
     */
    public static String prettyFormatXml(final String input, final int indent) {
        return new PrettyXmlString(input, indent).toString();
    }
}
