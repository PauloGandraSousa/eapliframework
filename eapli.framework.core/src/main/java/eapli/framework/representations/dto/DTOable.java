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
package eapli.framework.representations.dto;

import eapli.framework.representations.Representationable;

/**
 * A domain object that can be transformed to a DTO, e.g., {@link GeneralDTO}. This is ideal for
 * scenarios where the business objects is the one responsible for defining the structure of the DTO
 * and there is only one possible DTO representation of the business object.
 * <p>
 * <img src="dto.svg">
 * <p>
 * Alternatively, domain classes could be annotated with JAX-B or Jackson and the client code could
 * use automatic mapping thru Jackson/JAX-B.
 *
 * @param <T>
 *            the type of DTO
 * @author Paulo Gandra Sousa
 * @see Representationable
 *
 *      <!--
 * @startuml dto.svg
 *           package DomainReponsability <<cloud>> {
 *           interface DTOable<DTOX>
 *           DomainObjectX ..|> DTOable
 *           DomainObjectX ..> DTOX
 *           }
 *
 *           Client1 ...> DomainObjectX
 *           Client1 ...> DTOX
 * @enduml
 *         -->
 */
@FunctionalInterface
public interface DTOable<T> {

    /**
     * Returns a representation of the content of the object as a DTO.
     *
     * @return a representation of the content of the object as a DTO
     */
    T toDTO();
}
