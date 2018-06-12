/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.actions;

/**
 * An Action (the GoF Command pattern)
 *
 * @author Paulo Gandra Sousa
 */
@FunctionalInterface
public interface Action {
    /**
     *
     * @return true if this "scope" should end or to signal OK; false otherwise,
     *         e.g., signal an error
     */
    boolean execute();
}
