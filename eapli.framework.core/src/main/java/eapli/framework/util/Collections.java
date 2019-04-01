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
package eapli.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Gandra Sousa
 *
 */
public final class Collections implements Utilitarian {

    private Collections() {
        // to make sure this is an utility class
    }

    /**
     * returns the number of elements in an iterable. performance-wise this method
     * is of complexity O(n) as it needs to traverse the full iterator to determine
     * its size
     *
     * @param col
     * @return
     */
    public static int sizeOf(final Iterable<?> col) {
        int i = 0;
        for (@SuppressWarnings({ "unused", "squid:S1481" })
        final Object o : col) {
            i++;
        }
        return i;
    }

    /**
     * creates a List with all the elements of an enum
     *
     * @param e
     * @return
     */
    public static <T extends Enum<T>> List<T> listFromEnum(final Class<T> e) {
        final List<T> res = new ArrayList<>();
        for (final T type : e.getEnumConstants()) {
            res.add(type);
        }
        return res;
    }

    /**
     * checks if a value exists in an array. performance-wise the complexity of this
     * method is O(n)
     *
     * @param elements
     * @param x
     * @return
     */
    public static <T> boolean contains(final T[] elements, final T x) {
        for (final T e : elements) {
            if (e.equals(x)) {
                return true;
            }
        }
        return false;
    }
}
