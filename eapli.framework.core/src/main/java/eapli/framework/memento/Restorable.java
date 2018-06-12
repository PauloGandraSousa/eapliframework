/**
 *
 */
package eapli.framework.memento;

/**
 * An object that can be restored to a previous state.
 *
 * @author Paulo Gandra Sousa
 *
 */
public interface Restorable {
    /**
     * generates a memento capturing the current state of the object
     *
     * @return
     */
    Memento snapshot();

    /**
     * returns the object to a previous valid state
     *
     * @param previousState
     */
    void restoreTo(Memento previousState);
}
