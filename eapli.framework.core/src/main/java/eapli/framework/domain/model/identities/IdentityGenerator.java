/**
 *
 */
package eapli.framework.domain.model.identities;

/**
 * @author sou03408
 *
 */
@FunctionalInterface
public interface IdentityGenerator<T> {
    T newId();
}
