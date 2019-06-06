/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
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

/**
 * A factory of domain objects. When creation of an entire, internally
 * consistent aggregate, or a large value object, becomes complicated or reveals
 * too much of the internal structure, factories provide encapsulation.
 *
 * @author Paulo Gandra Sousa
 * @param <T>
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
     * @see org.apache.commons.lang3.builder.Builder
     * @return a newly (properly) created domain entity
     */
    T build();
}
