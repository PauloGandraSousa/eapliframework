/**
 *
 */
package eapli.framework.util.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eapli.framework.util.Utilitarian;

/**
 * String predicates. Predicates are functions that test a condition and return
 * a boolean value. in this case the test is done over a String argument.
 *
 * the function signature is
 *
 * <pre>
 * String -> Boolean
 *
 * <pre>
 *
 *
 * @author Paulo Gandra Sousa
 *
 */
public final class StringPredicates implements Utilitarian {

    @SuppressWarnings("squid:S4784")
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String TRAILING_WHITESPACE_REGEX = ".*[ \t]+$";
    private static final String HEADING_WHITESPACE_REGEX = "^[ \t]+.*";

    private StringPredicates() {
        // to make sure this is an utility class
    }

    /**
     * checks if a string is a valid email address.
     *
     * see also (and favor the use of)
     * {@link org.apache.commons.validator.routines.EmailValidator}
     *
     * @param text
     * @return
     */
    public static boolean isEmail(final String text) {
        final Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
        return matcher.find();
    }

    /**
     * checks if a string is a "phrase". phrases are not empty and do not have
     * heading or trailing spaces
     *
     * @param text
     * @return
     */
    @SuppressWarnings("squid:S4784")
    public static boolean isPhrase(final String text) {
        return !isNullOrEmpty(text) && !text.matches(HEADING_WHITESPACE_REGEX)
                && !text.matches(TRAILING_WHITESPACE_REGEX);
    }

    /**
     * checks if a string is composed of a single "word", i.e., does not contain
     * separating spaces
     *
     * @param text
     * @return
     */
    public static boolean isSingleWord(final String text) {
        return !isNullOrEmpty(text) && text.indexOf(' ') == -1;
    }

    /**
     * checks whether a String is empty (zero length or all spaces) or null
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    /**
     * checks whether a String is empty (zero length or all spaces) or null
     *
     * @param text
     * @return
     */
    public static boolean isNullOrWhiteSpace(final String text) {
        return text == null || text.trim().isEmpty();
    }

    /**
     * checks if a string contains at least one digit character
     *
     * @param text
     * @return
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsDigit(final String text) {
        return text.matches(".*\\d.*");
    }

    /**
     * checks if a string contains at least one alphabetic character (regular
     * Latin alphabet)
     *
     * @param text
     * @return
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsAlpha(final String text) {
        return text.matches(".*[a-zA-Z].*");
    }

    /**
     * checks whether a string contains at least one capital letter (regular
     * Latin alphabet)
     *
     * @param text
     * @return
     */
    @SuppressWarnings("squid:S4784")
    public static boolean containsCapital(final String text) {
        return text.matches(".*[A-Z].*");
    }
}
