/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.domain.activerecord;

import java.util.List;

/**
 * A Finder provides methods to load (find) ActiveRecord objects in the
 * persistence store and loads then to memory
 *
 * @author Paulo Gandra Sousa
 */
public interface Finder<T extends ActiveRecord<T>, K> {

    T findById(K id);

    List<T> findAll();
}
