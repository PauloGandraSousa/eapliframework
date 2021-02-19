/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import eapli.framework.util.Utility;

/**
 * A pretty formatted XML string.
 *
 * @author Paulo Gandra Sousa 13/05/2020
 *
 */
@Utility
public class PrettyXmlString {
    private PrettyXmlString() {
    }

    /**
     * Constructs a pretty formatted XML string from a string with unformatted XML content.
     *
     * @param input
     *            XML content
     * @return a pretty formatted XML string
     */
    public static FormattedString fromString(final String input) {
        return fromString(input, 2);
    }

    /**
     * Constructs a pretty formatted string from a string with unformatted XML content.
     *
     * @param input
     *            XML content
     * @param indent
     *            indentation size
     * @return a pretty formatted XML string
     */
    public static FormattedString fromString(final String input, final int indent) {
        return new FormattedString() {
            /**
             * Returns a formated XML output.
             * <p>
             * Based in code from
             * <a href=
             * "http://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java"> stack
             * overflow</a>
             *
             * @return a formated XML
             */
            @Override
            public String toString() {
                try {
                    final TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                    transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
                    transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                    transformerFactory.setAttribute("indent-number", indent);
                    final Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                    final Source xmlInput = new StreamSource(new StringReader(input));
                    final StreamResult xmlOutput = new StreamResult(new StringWriter());
                    transformer.transform(xmlInput, xmlOutput);
                    return xmlOutput.getWriter().toString();
                } catch (final TransformerException e) {
                    throw new FormatingOrTransformationException(e);
                }
            }

        };
    }

    /**
     * Constructs a pretty formatted XML string from an object with JAX-B annotations.
     *
     * @param <T>
     * @param input
     *            a java object to marshal as an XML string
     * @param clazz
     *            the type of object to map
     * @return a pretty formatted XML string
     */
    public static <T> FormattedString fromObject(final T input, final Class<T> clazz) {

        return new FormattedString() {
            @Override
            public final String toString() {
                final StringWriter writer = new StringWriter();
                try {
                    final JAXBContext context = JAXBContext.newInstance(clazz);
                    final Marshaller mar = context.createMarshaller();
                    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    mar.marshal(input, writer);
                } catch (final JAXBException e) {
                    throw new FormatingOrTransformationException(e);
                }
                return writer.toString();
            }
        };
    }
}
