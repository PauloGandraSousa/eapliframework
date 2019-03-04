/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.visitor;

/**
 * a visitor (GoF)
 *
 * @author Paulo Gandra Sousa
 * @todo consider adding beforeVisit() and afterVisit() default methods
 * @param <T>
 *            the specific type we want to visit
 */
@FunctionalInterface
public interface Visitor<T> {

    /**
     * visits a specific object
     *
     * @param visitee
     *            the object to visit
     */
    void visit(T visitee);
}
