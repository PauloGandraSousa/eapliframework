/**
 *
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
    public static int sizeOf(Iterable<?> col) {
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
    public static <T extends Enum<T>> List<T> listFromEnum(Class<T> e) {
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
    public static <T> boolean contains(T[] elements, T x) {
        for (final T e : elements) {
            if (e.equals(x)) {
                return true;
            }
        }
        return false;
    }
}
