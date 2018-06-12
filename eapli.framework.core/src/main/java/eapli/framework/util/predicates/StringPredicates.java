/**
 *
 */
package eapli.framework.util.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eapli.framework.util.Utilitarian;

/**
 * utility class for string manipulation.
 *
 * see also {@link org.apache.commons.lang3.StringUtils StringUtils}
 *
 * @author Paulo Gandra Sousa
 *
 */
public final class StringPredicates implements Utilitarian {

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
     * @param name
     * @return
     */
    public static boolean isPhrase(final String name) {
        return !StringPredicates.isNullOrEmpty(name) && !name.matches(HEADING_WHITESPACE_REGEX)
                && !name.matches(TRAILING_WHITESPACE_REGEX);
    }

    /**
     * checks if a string is composed of a single "word", i.e., does not contain
     * separating spaces
     *
     * @param username
     * @return
     */
    public static boolean isSingleWord(final String username) {
        return !StringPredicates.isNullOrEmpty(username) && username.indexOf(' ') == -1;
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

    public static boolean containsDigit(final String password) {
        return password.matches(".*\\d.*");
    }

    public static boolean containsAlpha(final String password) {
        return password.matches(".*[a-zA-Z].*");
    }

    public static boolean containsCapital(final String password) {
        return password.matches(".*[A-Z].*");
    }
}
