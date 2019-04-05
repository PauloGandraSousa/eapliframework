/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

/**
 * utility class for file manipulation.
 *
 * Favour the use of {@link org.apache.commons.io commons-io}
 *
 * @author Paulo Gandra Sousa
 */
public final class Files implements Utilitarian {

    private Files() {
        // to make sure this is a utility class
    }

    /**
     * returns the current working directory of the application. be carefull no
     * to expose this information to the outside as it may expose security risks
     * - see
     * https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS4797&rule_key=squid%3AS4797
     *
     * @return the current working directory of the application
     */
    @SuppressWarnings("squid:S4797")
    public static String currentDirectory() {
        return Paths.get(".").toAbsolutePath().toString();
    }

    /**
     *
     * @param filename
     * @param extension
     * @return
     */
    public static String ensureExtension(final String filename, final String extension) {
        if (!filename.endsWith(extension)) {
            if (extension.startsWith(".")) {
                return filename + extension;
            } else {
                return filename + "." + extension;
            }
        } else {
            return filename;
        }
    }

    /**
     * gets the full content of an input string as a single String. the input
     * stream is still active and open after calling this method
     *
     * @param is
     *            the input stream
     * @return the correspondent UTF-8 String
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static String contentOf(final InputStream is, final String encoding) throws IOException {
        final StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    public static String contentOf(final InputStream is) throws IOException {
        return contentOf(is, "UTF-8");
    }
}
