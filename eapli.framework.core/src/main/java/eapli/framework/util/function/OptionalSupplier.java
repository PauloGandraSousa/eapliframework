/**
 *
 */
package eapli.framework.util.function;

import java.util.Optional;

/**
 * a function that can produce an Optional
 * 
 * @author sou03408
 *
 */
@FunctionalInterface
public interface OptionalSupplier<T> {

    Optional<T> get();
}
