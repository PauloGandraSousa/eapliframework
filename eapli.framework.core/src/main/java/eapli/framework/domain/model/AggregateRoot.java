/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.domain.model;

/**
 * An aggregate root is a Domain-Driven Design pattern for defining scopes of
 * change and "whole-objects". some entities cluster other objects (entities and
 * value objects) which don't make sense to exist outside of that entity (e.g.,
 * OrderLine and Order).
 * <p>
 * The aggregate root is the entity serving as a root for that cluster of
 * objects. these are the only objects that client code can interact directly
 * and that is managed by Repositories. "inside" objects cannot be directly
 * manipulated by client code, instead they need to be manipulated thru the
 * aggregate root.
 * </p>
 * <p>
 * Typical examples are:
 * <ol>
 * <li>Order {OrderLine, Billing Address, Shipping Address}
 * <li>Customer {Home Address}
 * <li>Product
 * </ol>
 * </p>
 *
 * @param <I>
 *            the type of the primary <b>business</b> identity of the entity
 *
 * @author Paulo Gandra Sousa
 */
public interface AggregateRoot<I extends Comparable<I> /* & ValueObject */>
        extends DomainEntity<I> {
    // empty
}
