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
package eapli.framework.util;

import java.util.Collection;
import java.util.List;

/**
 * Utility functions to handle Collections.
 * <p>
 * See also <a href=
 * "https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/IterableUtils.html">Apache
 * Common Iterable Utils</a>.
 *
 * @author Paulo Gandra Sousa
 *
 */
@Utility
public final class Collections {

    private Collections() {
        // ensure no instantiation as this is a utility class
    }

    /**
     * Returns the number of elements in an iterable. Performance-wise this method is of complexity
     * O(n)
     * as it needs to traverse the full iterator to determine its size.
     *
     * @param itens
     * @return the number of elements in an iterable.
     */
    @SuppressWarnings({ "unused", "squid:S1481" })
    public static int sizeOf(final Iterable<?> itens) {
        int numberOfElements = 0;
        for (final Object o : itens) {
            numberOfElements++;
        }
        return numberOfElements;
    }

    /**
     * Returns the number of elements in an iterable. Since most of the times an Iterable will be an
     * objecs that extends
     * Collection, this more specialized overload of {@link #sizeOf(Iterable)} will provide better
     * performance.
     *
     * @param items
     * @return the number of elements in an iterable.
     */
    public static int sizeOf(final Collection<?> items) {
        return items.size();
    }

    /**
     * Creates a List with all the elements of an enum.
     *
     * @param e
     * @return a List with all the elements of an enum
     */
    public static <T extends Enum<T>> List<T> listFromEnum(final Class<T> e) {
        return java.util.Arrays.asList(e.getEnumConstants());
    }
}
