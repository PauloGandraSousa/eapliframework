/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.activerecord;

import java.util.Optional;

/**
 * A Finder provides methods to load (find) ActiveRecord objects in the
 * persistence store and loads then to memory.
 *
 * @param <T>
 *            the type of business entity to find
 * @param <K>
 *            the type of the business identity
 *
 * @author Paulo Gandra Sousa
 */
public interface Finder<T extends ActiveRecord<K>, K extends Comparable<K>> {

    /**
     * Gets all entities from the repository. If the repository is empty and
     * empty collection is returned.
     *
     * @return all entities from the repository.
     */
    Iterable<T> findAll();

    /**
     * Gets the entity with the specified identity.
     *
     * @param id
     * @return an Optional which value is the entity with the specified
     *         identity. An Empty Optional if there is no entity with such
     *         identity in the repository.
     */
    Optional<T> findById(K id);
}
