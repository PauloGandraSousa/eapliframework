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
package eapli.framework.strings.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eapli.framework.util.Utility;

/**
 * String predicates. Predicates are functions that test a condition and return a boolean value; in
 * this case the test is done over a String argument.
 *
 * <p>
 * For most of the functions, the signature is:
 *
 * <pre>
 * String -> Boolean
 * </pre>
 *
 * See also <a href=
 * "https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/StringUtils.html">org.apache.commons.lang3.StringUtils</a>
 *
 * @author Paulo Gandra Sousa
 *
 */
@Utility
public final class StringPredicates {

    @SuppressWarnings("squid:S4784")
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String TRAILING_WHITESPACE_REGEX = ".*[ \t]+$";
    private static final String HEADING_WHITESPACE_REGEX = "^[ \t]+.*";

    private StringPredicates() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Checks if a string is a valid email address.
     *
     * <p>
     * See also (and favor the use of) <a href=
     * "http://commons.apache.org/proper/commons-validator/apidocs/org/apache/commons/validator/EmailValidator.html">org.apache.commons.validator.routines.EmailValidator</a>
     *
     * @param text
     * @return wether a string is a valid email address or not
     */
    public static boolean isEmail(final String text) {
        final Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
        return matcher.find();
    }

    /**
     * Checks if a string is a "phrase". Phrases are not empty and do not have heading or trailing
     * spaces.
     *
     * @param text
     * @return wether a string is a "phrase" or not
     */
    @SuppressWarnings("squid:S4784")
    public static boolean isPhrase(final String text) {
        return !isNullOrEmpty(text) && !text.matches(HEADING_WHITESPACE_REGEX)
                && !text.matches(TRAILING_WHITESPACE_REGEX);
    }

    /**
     * Checks if a string is composed of a single "word", i.e., does not contain separating spaces.
     *
     * @param text
     * @return true if a string is composed of a single "word"
     */
    public static boolean isSingleWord(final String text) {
        return !isNullOrEmpty(text) && text.indexOf(' ') == -1;
    }

    /**
     * Checks whether a String is empty (zero length) or null.
     *
     * @param text
     * @return whether a String is empty (zero length) or null, or not
     */
    public static boolean isNullOrEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    /**
     * Checks whether a String is empty (zero length or all spaces) or null.
     *
     * @param text
     * @return whether a String is empty (zero length or all spaces) or null
     */
    public static boolean isNullOrWhiteSpace(final String text) {
        return text == null || text.isBlank();
    }

    /**
     * Checks if a string contains at least one digit character.
     *
     * @param text
     * @return true if a string contains at least one digit character
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsDigit(final String text) {
        return text.matches(".*\\d.*");
    }

    /**
     * Checks if a string contains at least one alphabetic character (regular Latin alphabet).
     *
     * @param text
     * @return true if a string contains at least one alphabetic character
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsAlpha(final String text) {
        return text.matches(".*[a-zA-Z].*");
    }

    /**
     * Checks whether a string contains at least one capital letter (regular Latin alphabet).
     *
     * @param text
     * @return whether a string contains at least one capital letter
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsCapital(final String text) {
        return text.matches(".*[A-Z].*");
    }

    /**
     * Checks whether a string contains any of the characters of the second string, without using
     * regular expressions to avoid potential security threats at the expense of performance. The
     * complexity of this method O(n^2).
     *
     * @param subject
     * @param chars
     * @return whether a string contains any of the characters of the second
     */
    public static boolean containsAny(final String subject, final String chars) {
        if (StringPredicates.isNullOrEmpty(chars) || StringPredicates.isNullOrEmpty(subject)) {
            return false;
        }
        return subject.chars().anyMatch(c -> chars.indexOf(c) != -1);
    }
}
