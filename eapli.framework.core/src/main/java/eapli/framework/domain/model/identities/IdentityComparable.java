/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.domain.model.identities;

/**
 * A generic interface for checking if an object has a certain business identity
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 *            the type of the entity's identity. e.g., if an object Person is
 *            identified by an IdCardNumber, the class Person should implement
 *            interface Identifiable<IdCardNumber>,
 *            IdentityComparable<IdCardNumber>
 */
public interface IdentityComparable<T> extends Identifiable<T> {

    /**
     * checks if the object has a certain business identity
     *
     * @param otherId
     *            the identity to compare
     * @return true if the object has that identity
     */
    default boolean hasIdentity(final T otherId) {
        return identity().equals(otherId);
    }
}
