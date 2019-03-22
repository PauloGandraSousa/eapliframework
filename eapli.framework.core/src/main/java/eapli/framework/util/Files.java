/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
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
     * @deprecated use {@link java.io.File} or
     *             {@link org.apache.commons.io.FilenameUtils}
     */
    @Deprecated
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
