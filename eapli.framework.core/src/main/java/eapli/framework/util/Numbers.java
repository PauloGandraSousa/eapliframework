/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package eapli.framework.util;

import java.math.BigDecimal;

/**
 *
 * @author Paulo Gandra Sousa
 */
public final class Numbers implements Utilitarian {

    private Numbers() {
        // to make sure this is an utility class
    }

    public static String format(final BigDecimal amount) {
        // TODO use number formatter
        return amount.toString();
    }
}
