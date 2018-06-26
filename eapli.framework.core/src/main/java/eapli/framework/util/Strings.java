/**
 *
 */
package eapli.framework.util;

/**
 * utility class for string manipulation.
 *
 * see also {@link org.apache.commons.lang3.StringUtils StringUtils}
 *
 * @author Paulo Gandra Sousa
 *
 */
public final class Strings implements Utilitarian {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Strings() {
        // to make sure this is an utility class
    }

    /**
     * returns a random string with a specified length consisting only of letters
     * (capitals and lower) and digits
     *
     * @param len
     * @return
     */
    public static String randomString(final int len) {
        return randomString(len, CHARSET);
    }

    /**
     * returns a random string with a specified length
     *
     * @param len
     * @param charSet
     * @return
     */
    public static String randomString(final int len, final String charSet) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            final int c = NumberGenerator.anInt(charSet.length());
            sb.append(charSet.charAt(c));
        }
        return sb.toString();
    }

    public static String truncate(final String org, final int len) {
        if (len < org.length()) {
            return org.substring(0, len);
        } else {
            return org;
        }
    }

    public static String left(final String org, final int len) {
        return org.substring(0, len);
    }

    public static String right(final String org, final int len) {
        return org.substring(org.length() - len);
    }

    /**
     * constructs a string with the same character repeated n times.
     *
     * @param symbol
     *            the character to repeat
     * @param size
     *            the desired length of the resulting string. if this value is
     *            negative an empty string will be returned
     * @return a string with all characters being the symbol and length size
     */
    public static String repeat(final char symbol, int size) {
        final StringBuilder b = new StringBuilder();
        while (size > 0) {
            b.append(symbol);
            size--;
        }
        return b.toString();
    }
}
