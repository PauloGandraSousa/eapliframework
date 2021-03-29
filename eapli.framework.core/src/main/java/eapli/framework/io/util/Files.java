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
package eapli.framework.io.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.io.StreamGobbler;
import eapli.framework.util.Utility;

/**
 * Utility class for file manipulation.
 * <p>
 * Favour the use of
 * <a href="https://commons.apache.org/proper/commons-io/">org.apache.commons.io</>.
 *
 * @author Paulo Gandra Sousa 19/05/2020
 */
@Utility
public final class Files {
    private static final Logger LOGGER = LogManager.getLogger(Files.class);

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
        return Paths.get(".").toAbsolutePath().toString();
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
        if (!filename.endsWith(extension)) {
            if (extension.startsWith(".")) {
                return filename + extension;
            }
            return filename + "." + extension;
        }
        return filename;
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
    public static String textFrom(final InputStream is, final String encoding) throws IOException {
        final var result = new StringBuilder();
        try (var reader = new BufferedReader(new InputStreamReader(is, encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    /**
     * Gets the full content of an input stream as a single String encoded as UTF-8. The input
     * stream
     * is still active and open after calling this method.
     *
     * @param is
     *            the input stream
     * @return the correspondent UTF-8 String
     * @throws IOException
     */
    public static String textFrom(final InputStream is) throws IOException {
        return textFrom(is, StandardCharsets.UTF_8.name());
    }

    /**
     * Simple utility to call the default OS application to open a file.
     *
     * @param path
     * @throws IOException
     * @throws InterruptedException
     */
    public static int openInOSViewer(final String path) throws IOException, InterruptedException {
        final String osname = System.getProperty("os.name").toLowerCase();
        final boolean isWindows = osname.startsWith("windows");
        final boolean isMac = osname.indexOf("mac") >= 0;
        final boolean isNix = osname.indexOf("nix") >= 0 || osname.indexOf("nux") >= 0 || osname.indexOf("aix") >= 0;

        final var builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "start " + path);
        } else if (isMac) {
            builder.command("sh", "-c", "open " + path);
        } else if (isNix) {
            builder.command("sh", "-c", "xdg-open " + path);
        }
        builder.directory(new File(System.getProperty("user.home")));
        final Process process = builder.start();

        final var streamGobbler = new StreamGobbler(process.getInputStream(), LOGGER::info);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        return process.waitFor();
    }
}
