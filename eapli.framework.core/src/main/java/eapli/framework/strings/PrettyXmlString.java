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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * A pretty formatted XML string.
 *
 * @author Paulo Gandra Sousa 13/05/2020
 *
 */
public class PrettyXmlString implements FormattedString {

    /**
     * Constructs a pretty formatted string from a string with unformatted XML content.
     *
     * @param input
     */
    public static FormattedString fromString(final String input) {
        return new PrettyXmlFromString(input, 2);
    }

    /**
     * Constructs a pretty formatted string from a string with unformatted XML content.
     *
     * @param input
     * @param indent
     */
    public static FormattedString fromString(final String input, final int indent) {
        return new PrettyXmlFromString(input, indent);
    }

    public static <T> FormattedString fromObject(final T input, Class<T> clazz) {

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
                    throw new IllegalArgumentException(e);
                }
                return writer.toString();
            }
        };
    }

}
