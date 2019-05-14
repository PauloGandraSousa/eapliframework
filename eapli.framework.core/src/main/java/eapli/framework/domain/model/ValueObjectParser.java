/**
 *
 */
package eapli.framework.domain.model;

/**
 * A factory of value objects based on String representations
 * 
 * @param <T>
 *
 * @author Paulo Gandra Sousa
 *
 */
@FunctionalInterface
public interface ValueObjectParser<T extends ValueObject> {

    /**
     * Parses a string in order to create a new value object instance. Works in
     * reverse to the toString() method of a Value Object.
     *
     * @param value
     * @return a value object instance corresponding to the value represented in
     *         {@code value}
     */
    T valueOf(String value);
}
