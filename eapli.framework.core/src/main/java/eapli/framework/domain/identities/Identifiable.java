/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.domain.identities;

/**
 * A generic interface identities of objects
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of the entity's identity. e.g., if an object Person is
 *            identified by an IdCardNumber, the class Person should implement
 *            interface Identifiable<IdCardNumber>
 */
public interface Identifiable<T> {

    /**
     * returns the primary <b>business</b> id of the entity
     *
     * @return the primary <b>business</b> id of the entity
     */
    T id();
}
