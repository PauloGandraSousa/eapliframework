/**
 *
 */
package eapli.framework.util;

/**
 * a Mixin for adding common functionality to objects that can be thought of as
 * Strings, e.g., the name concept Designation.
 *
 *
 * @author sou03408
 *
 */
public interface StringMixin {

    default int length() {
        return toString().length();
    }

    default boolean isEmpty() {
        return toString().isEmpty();
    }

    default boolean matches(final String regex) {
        return toString().matches(regex);
    }
}
