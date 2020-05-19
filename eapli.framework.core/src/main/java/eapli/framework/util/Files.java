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

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for file manipulation.
 * <p>
 * Favour the use of
 * <a href="https://commons.apache.org/proper/commons-io/">org.apache.commons.io</>.
 *
 * @author Paulo Gandra Sousa
 * @deprecated use {@link eapli.framework.io.util.Files} instead
 * @see eapli.framework.io.util.Files
 */
@Deprecated
@Utility
public final class Files {

    private Files() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Returns the current working directory of the application.
     *
     * <p>
     * Be careful not to expose this information to the outside as it may expose
     * security risks - see <a href=
     * "squid:S4797">https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS4797&rule_key=squid%3AS4797</a>
     *
     * @return the current working directory of the application
     */
    @SuppressWarnings("squid:S4797")
    public static String currentDirectory() {
        return eapli.framework.io.util.Files.currentDirectory();
    }

    /**
     * Creates a filename based on {@code filename} and adding the extension
     * {@code extension} if not present.
     *
     * @param filename
     * @param extension
     * @return the filename with extension
     */
    public static String ensureExtension(final String filename, final String extension) {
        return eapli.framework.io.util.Files.ensureExtension(filename, extension);
    }

    /**
     * Gets the full content of an input stream as a single String. The input stream
     * is still active and open after calling this method.
     *
     * @param is
     *            the input stream
     * @return the correspondent UTF-8 String
     * @throws IOException
     */
    public static String contentOf(final InputStream is, final String encoding) throws IOException {
        return eapli.framework.io.util.Files.textFrom(is, encoding);
    }

    /**
     * Gets the full content of an input stream as a single String encoded as UTF-8. The input
     * stream is still active and open after calling this method.
     *
     * @param is
     *            the input stream
     * @return the correspondent UTF-8 String
     * @throws IOException
     */
    public static String contentOf(final InputStream is) throws IOException {
        return eapli.framework.io.util.Files.textFrom(is);
    }
}
