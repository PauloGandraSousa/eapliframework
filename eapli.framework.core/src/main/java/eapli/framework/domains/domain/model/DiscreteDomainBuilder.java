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
package eapli.framework.domains.domain.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import eapli.framework.util.Factory;
import eapli.framework.validations.Preconditions;

/**
 * A {@link DiscreteDomain} builder.
 *
 * @author Paulo Gandra de Sousa
 *
 * @param <T>
 */
public class DiscreteDomainBuilder<T extends Serializable> implements Factory<DiscreteDomain<T>> {

    private final Set<T> from = new HashSet<>();

    /**
     * Adds an element to the domain
     *
     * @param e
     * @return this bulder
     */
    public DiscreteDomainBuilder<T> with(final T e) {
        Preconditions.nonNull(e);

        from.add(e);
        return this;
    }

    /**
     * Adds some elements to the domain
     *
     * @param elements
     * @return this builder
     */
    public DiscreteDomainBuilder<T> with(@SuppressWarnings("unchecked") final T... elements) {
        from.addAll(Arrays.asList(elements));
        return this;
    }

    /**
     * Adds elements from a stream
     *
     * @param stream
     * @return this builder
     */
    public DiscreteDomainBuilder<T> with(final Stream<T> stream) {
        Preconditions.nonNull(stream);
        stream.forEach(from::add);
        return this;
    }

    /**
     * Removes an element previously added to the domain
     *
     * @param e
     * @return this builder
     */
    public DiscreteDomainBuilder<T> without(final T e) {
        Preconditions.nonNull(e);

        from.remove(e);
        return this;
    }

    @Override
    public DiscreteDomain<T> build() {
        return new DiscreteDomain<>(from);
    }
}
