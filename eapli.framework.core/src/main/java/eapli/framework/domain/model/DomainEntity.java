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
package eapli.framework.domain.model;

import eapli.framework.domain.model.identities.Identifiable;
import eapli.framework.domain.model.identities.IdentityComparable;

/**
 * An entity is a Domain-Driven Design pattern for concepts in the domain which
 * have a thread of continuity which needs to be tracked.
 *
 * These concepts matter by their identity and we need to track them
 * continuously. Instance equality must be done thru the identity of the objects
 * and we cannot loose track or allow duplication of an entity. By definition a
 * domain entity always has a business identity, so it should be impossible to
 * create new instances without identity.
 *
 * Typical examples are:
 * <ol>
 * <li>Product
 * <li>Person
 * <li>Account
 * </ol>
 *
 * @param <I>
 *            the type of the primary <b>business</b> id of the entity
 * @author Paulo Gandra Sousa
 */
public interface DomainEntity<I /* extends ValueObject */>
        extends Identifiable<I>, IdentityComparable<I> {

    /**
     * Entities are compared by identity only. no need to compare all fields of
     * the object. you can use the domainEntityHelper.areEqual() method as
     * default implementation
     *
     * see sameAs().
     *
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other);

    /**
     * hash code of this object according to java rules. i.e., the same fields
     * used in equals() should be used in hashCode().
     *
     * see <a href=
     * "http://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method">
     * stack overflow</a> for a nice discussion about hashCode() and equals()
     *
     * @return
     */
    @Override
    int hashCode();

    /**
     * Entities are compared by identity only, so equals() must only compares
     * identities. in some situations however we might want to compare the
     * content of the object by value.
     *
     * @param other
     * @return
     */
    boolean sameAs(Object other);
}
