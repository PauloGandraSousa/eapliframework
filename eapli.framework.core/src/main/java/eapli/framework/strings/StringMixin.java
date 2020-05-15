/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
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
package eapli.framework.strings;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Mixin for adding common functionality to objects that can be thought of as
 * Strings, e.g., the name concept Designation.
 *
 *
 * @author Paulo Gandra de Sousa
 *
 */
public interface StringMixin {

    /**
     * @return the length of this string
     * @see java.lang.String#length()
     */
    default int length() {
        return toString().length();
    }

    /**
     * @return true if this string has {@link #length()} 0
     * @see java.lang.String#isEmpty()
     */
    @JsonIgnore
    default boolean isEmpty() {
        return toString().isEmpty();
    }

    /**
     * Checks if this string matches the regular expression {@code regex}
     *
     * <p>
     * Note that regular expression might cause security issues. See <a href=
     * "https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS4784&rule_key=squid%3AS4784">squid:S4784</a>
     *
     * @see java.lang.String#matches(String)
     *
     * @param regex
     * @return {@code true} if this string matches the regular expression {@code regex}
     */
    @SuppressWarnings("squid:S4784")
    default boolean matches(final String regex) {
        return toString().matches(regex);
    }
}
