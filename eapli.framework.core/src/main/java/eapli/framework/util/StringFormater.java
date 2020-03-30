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
package eapli.framework.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * utility class for string formating
 *
 * @author Paulo Gandra Sousa
 *
 */
@Utility
public final class StringFormater {

    private StringFormater() {
        // ensure utility
    }

    /**
     * Returns a pretty formatted JSON object.
     *
     * <p>
     * From
     * <a href= "https://www.mkyong.com/java/how-to-enable-pretty-print-json-output-jackson/">mykong</a>
     *
     * @param input
     *              a JSON string
     * @return a pretty formatted JSON object
     */
    public static String prettyformatJson(final String input) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Object json = mapper.readValue(input, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Formatting to JSON:" + input, e);
        }
    }

    /**
     * returns a pretty formated XML string
     *
     * @param input
     *              an XML string
     * @return a pretty formated XML string
     */
    public static String prettyFormatXml(final String input) {
        return prettyFormatXml(input, 2);
    }

    /**
     * returns a formated XML output.
     * <p>
     * Based in code from
     * <a href= "http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java"> stack
     * overflow</a>
     *
     * @param input
     * @param indent
     * @return a formated XML
     */
    public static String prettyFormatXml(final String input, final int indent) {
        final Source xmlInput = new StreamSource(new StringReader(input));
        final StringWriter stringWriter = new StringWriter();
        final StreamResult xmlOutput = new StreamResult(stringWriter);
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            transformerFactory.setAttribute("indent-number", indent);
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
        } catch (final TransformerException e) {
            throw new IllegalArgumentException(e);
        }
        return xmlOutput.getWriter().toString();
    }
}
