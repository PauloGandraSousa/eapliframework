/**
 *
 */
package eapli.framework.domain.identities;

/**
 * @author sou03408
 *
 */
@FunctionalInterface
public interface IdentityGenerator<T> {
    T newId();
}
