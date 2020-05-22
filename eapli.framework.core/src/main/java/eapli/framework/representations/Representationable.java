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
package eapli.framework.representations;

import eapli.framework.representations.dto.DTOable;

/**
 * A business object that can guide the generation of a representation of itself. This is ideal for
 * scenarios where there might be the need to get different representations for different use cases.
 * In such scenarios we cannot rely on a single DTO or automatic mapping thru Jackson/JAX-B.
 * <p>
 * Generally speaking, a domain object implementing this interface will provide a way to pass
 * key/value pairs of its own internal state to an external {@link RepresentationBuilder} ignoring
 * what properties the client class needs or how it will use them and in which format, thus
 * decoupling the domain from the representation.
 * <p>
 * <img src="representationable.svg">
 * <p>
 * For instance in the previous example, {@code Representation1} might be a DTO with all the
 * properties and child objects of {@code DomainObjectX} while {@code Representation2} might be a
 * summary DTO with just the code and human-friendly name of the domain object. If other clients
 * need different representations (in content and7or format) new builders can be added without any
 * change to the underlying domain object.
 *
 * @see RepresentationBuilder
 * @see DTOable
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml representationable.svg
 *           interface Representationable
 *           interface RepresentationBuilder
 *           Representationable ..> RepresentationBuilder
 *
 *           package DomainResponsability <<cloud>> {
 *           class DomainObjectX
 *           DomainObjectX ..|> Representationable
 *           }
 *
 *           package Client1Reponsability <<cloud>> {
 *           ConcreteBuilder1 ..|> RepresentationBuilder
 *           ConcreteBuilder1 ..> Representation1
 *
 *           Client1 ..> DomainObjectX
 *           Client1 ..> ConcreteBuilder1
 *           Client1 .left.> Representation1
 *           }
 * 
 *           package Client2Reponsability <<cloud>> {
 *           ConcreteBuilder2 ..|> RepresentationBuilder
 *           ConcreteBuilder2 .up.> Representation2
 *
 *           Client2 ..> DomainObjectX
 *           Client2 ..> ConcreteBuilder2
 *           Client2 .right.> Representation2
 *           }
 * @enduml
 *         -->
 */
@FunctionalInterface
public interface Representationable {
    /**
     * Guides the construction of a representation of this business object's
     * current status.
     *
     * @param builder
     * @param <R>
     *            the type of representation that will be created, e.g., a DTO
     * @return the object constructed by the builder
     */
    <R> R buildRepresentation(RepresentationBuilder<R> builder);
}
