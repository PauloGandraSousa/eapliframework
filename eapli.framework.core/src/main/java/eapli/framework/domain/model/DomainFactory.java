/*
 * Copyright (c) 2013-2021 the original author or authors.
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
 * A factory of domain objects.
 * <p>
 * Quoting
 * <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Eric
 * Evans</a>:
 * <blockquote>
 * When creation of an entire, internally
 * consistent aggregate, or a large value object, becomes complicated or reveals
 * too much of the internal structure, factories provide encapsulation.
 * <br>
 * Shift the responsibility for creating instances of complex objects and aggregates to a
 * separate object, which may itself have no responsibility in the domain model but is still
 * part of the domain design. Provide an interface that encapsulates all complex assembly and
 * that does not require the client to reference the concrete classes of the objects being
 * instantiated. Create an entire aggregate as a piece, enforcing its invariants. Create a
 * complex value object as a piece, possibly after assembling the elements with a builder.
 * </blockquote>
 *
 * <p>
 * This interface can also be used on a
 * <a href="https://refactoring.guru/design-patterns/builder">Builder</a> as the last step of the
 * building process.
 *
 * @param <T>
 *            the type of object this factory builds.
 *
 * @author Paulo Gandra Sousa
 *
 */
@FunctionalInterface
public interface DomainFactory<T extends DomainEntity<?>> {

    /**
     * Constructs a new instance of the domain entity. Most likely this method
     * is the last step in the building process (following the Builder GoF
     * pattern) where a series of steps to provide the necessary information to
     * the object constructor is previously loaded into the builder.
     *
     * <p>
     * <a href=
     * "http://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/builder/Builder.html">org.apache.commons.lang3.builder.Builder</a>
     *
     * @return a newly (properly) created domain entity
     */
    T build();
}
