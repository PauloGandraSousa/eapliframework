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

/**
 * An entity is a Domain-Driven Design pattern for concepts in the domain which
 * have a thread of continuity which needs to be tracked.
 *
 * <p>
 * These concepts matter by their identity and we need to track them
 * continuously. Instance equality must be done thru the identity of the objects
 * and we cannot loose track or allow duplication of an entity. By definition a
 * domain entity always has a business identity, so it should be impossible to
 * create new instances without identity.
 *
 * <p>
 * Typical examples are:
 * <ul>
 * <li>Product
 * <li>Person
 * <li>Account
 * </ul>
 *
 * @param <I>
 *            the type of the primary <b>business</b> id of the entity
 * @author Paulo Gandra Sousa
 */
public interface DomainEntity<I extends Comparable<I> /* & ValueObject */> extends Identifiable<I>, Comparable<I> {

    /**
     * Entities are compared by identity only. No need to compare all fields of
     * the object. you can use the
     * {@link DomainEntities#areEqual(DomainEntity, Object)} method as default
     * implementation
     *
     * <p>
     * be careful that if your entities are using the ORM generated primary key
     * as their business identity, you MUST properly implement equals() and
     * hashcode(). See <a href=
     * "https://thoughts-on-java.org/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/">Toughts
     * on java</a>
     *
     * <p>
     * see {@link #sameAs(Object)}.
     *
     * @param other
     * @return true if this domain entity refers to the same real-world entity
     *         as the {@code other}
     */
    @Override
    boolean equals(Object other);

    /**
     * Hash code of this object. According to java rules. i.e., the same fields
     * used in equals() should be used in hashCode(). you can use
     * {@link DomainEntities#hashCode(DomainEntity)} as default implementation
     *
     * <p>
     * see <a href=
     * "http://stackoverflow.com/questions/113511/best-implementation-for-hashcode-method">
     * stack overflow</a> for a nice discussion about hashCode() and equals()
     *
     * @return the hash code
     */
    @Override
    int hashCode();

    /**
     * Entities are compared by identity only, so equals() must only compares
     * identities. In some situations however we might want to compare the
     * content of the object by value.
     *
     * @param other
     * @return true if this domain entity have the "same content" as the
     *         {@code other}
     */
    boolean sameAs(Object other);

    /**
     * Domain entities are naturally ordered by their identity.
     */
    @Override
    default int compareTo(final I other) {
        return identity().compareTo(other);
    }
}
