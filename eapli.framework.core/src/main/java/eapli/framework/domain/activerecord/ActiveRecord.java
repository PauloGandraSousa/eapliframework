/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.domain.activerecord;

import eapli.framework.domain.model.identities.Identifiable;

/**
 * An Active Record is a design pattern where an object both holds behavior
 * (business logic) and knows how to handle its own persistence.
 *
 * Active Records might have static finder methods or use a separated Finder
 * class.
 *
 * @author Paulo Gandra Sousa
 */
public interface ActiveRecord<I> extends Identifiable<I> {

    /*
     * save the current object to the persistence store either by creating it or
     * updating it
     */
    void save();
}
