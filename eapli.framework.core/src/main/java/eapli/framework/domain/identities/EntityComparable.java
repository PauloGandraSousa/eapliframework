/**
 *
 */
package eapli.framework.domain.identities;

/**
 * A generic interface for checking if an object refers to the same object as
 * other
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of the entity's identity. e.g., if an object Person is
 *            identified by an IdCardNumber, the class Person should implement
 *            interface Identifiable<IdCardNumber>
 */
@FunctionalInterface
public interface EntityComparable<T extends Identifiable<?>> extends Identifiable<T> {

    /**
     * checks if the object refers to the same real world entity as the passed
     * parameter
     *
     * @param other
     *            the object to check the identity of
     * @return true if the object has that identity
     */
    default boolean is(T other) {
        return id().equals(other.id());
    }
}
