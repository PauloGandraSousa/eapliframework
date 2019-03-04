/**
 *
 */
package eapli.framework.domain.model.identities;

/**
 * A generic interface for checking if an object has a certain business identity
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of the entity's identity. e.g., if an object Person is
 *            identified by an IdCardNumber, the class Person should implement
 *            interface Identifiable<IdCardNumber>,
 *            IdentityComparable<IdCardNumber>
 */
public interface IdentityComparable<T> extends Identifiable<T> {

    /**
     * checks if the object has the passed business identity parameter
     *
     * @param other
     *            the object to check the identity of
     * @return true if the object has that identity
     */
    default boolean hasId(T otherId) {
        return id().equals(otherId);
    }
}
