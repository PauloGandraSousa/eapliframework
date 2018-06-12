/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.util;

/**
 *
 * @author Paulo Gandra Sousa
 */
public final class Comparables implements Utilitarian {

    private Comparables() {
        // to make sure this is an utility class
    }

    /**
     * checks if a value is between (inclusive) a range
     *
     * @param e
     * @param begin
     * @param end
     * @return
     */
    public static boolean isBetween(long e, long begin, long end) {
        return e >= begin && e <= end;
    }

    /**
     * checks if a value is between (inclusive) a range
     *
     * @param e
     * @param begin
     * @param end
     * @return
     */
    public static <T extends Comparable<T>> boolean isBetween(T e, T begin, T end) {
        return e.compareTo(begin) >= 0 && e.compareTo(end) <= 0;
    }
}
