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
package eapli.framework.functional;

import eapli.framework.util.Utility;

/**
 * A utility class with some handy functions.
 *
 * @author Paulo Gandra de Sousa 24/05/2019
 */
@Utility
public final class StandardFunctions {
    private StandardFunctions() {
        // ensure utility
    }

    /**
     * Identity function.
     *
     * <pre>
     * <code>
     * T -> T : x -> x
     * </code>
     * </pre>
     *
     * @param x
     * @return the same object passed as input parameter
     */
    public static <T> T identity(final T x) {
        return x;
    }

    /**
     * Nil Operation.
     */
    public static void nop() {
        // do noting
    }

    /**
     * Nil operation, i.e., do nothing on the parameter of the function.
     *
     * <pre>
     * <code>
     * T -> void : x -> void
     * </code>
     * </pre>
     *
     * @param x
     * @param <T>
     */
    @SuppressWarnings("squid:S1172")
    public static <T> void ignore(final T x) {
        // do noting
    }
}
