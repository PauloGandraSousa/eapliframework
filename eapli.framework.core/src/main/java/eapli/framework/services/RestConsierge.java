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
package eapli.framework.services;

import java.util.Optional;

import eapli.framework.util.Utility;

/**
 * Helper class to handle some common REST API situations, e.g., returning 404
 * on empty Optionals.
 *
 * @author Paulo Gandra de Sousa 23/05/2019
 *
 */
@Utility
public final class RestConsierge {

    private RestConsierge() {
        // ensure utility
    }

    /**
     * Treats a null value as a 404 by throwing
     * {@link ResourceNotFoundException}.
     *
     * @param resource
     * @return the value if it is not null.
     * @throws ResourceNotFoundException
     *             if the value is null
     */
    public static <T> T getOrElse404(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }

    /**
     * Treats a null value as a 404 by throwing
     * {@link ResourceNotFoundException}.
     *
     * @param resource
     * @param message
     *            the error message to send to the client
     * @return the value if it is not null.
     * @throws ResourceNotFoundException
     *             if the value is null
     */
    public static <T> T getOrElse404(final T resource, final String message) {
        if (resource == null) {
            throw new ResourceNotFoundException(message);
        }
        return resource;
    }

    /**
     * Properly handle Optional results from an API to either returns the value
     * of the optional or generate a 404 response.
     *
     * @param resource
     * @return the value of the optional if the optional is not empty
     * @throws ResourceNotFoundException
     *             if the optional is empty. to generate a 404 response
     */
    public static <T> T getOrElse404(final Optional<T> resource) {
        return resource.orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Properly handle Optional results from an API to either returns the value
     * of the optional or generate a 404 response.
     *
     * @param resource
     * @param message
     *            the error message to send to the client
     * @return the value of the optional if the optional is not empty
     * @throws ResourceNotFoundException
     *             if the optional is empty. to generate a 404 response
     */
    public static <T> T getOrElse404(final Optional<T> resource, final String message) {
        return resource.orElseThrow(() -> new ResourceNotFoundException(message));
    }
}
