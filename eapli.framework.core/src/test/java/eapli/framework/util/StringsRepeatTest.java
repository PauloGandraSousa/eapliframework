/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class StringsRepeatTest {

    @Test
    public void ensureRepeat0Isempty() {
        final String result = Strings.repeat('-', 0);
        assertEquals(0, result.length());
    }

    @Test
    public void ensureRepeatNegativeIsempty() {
        final String result = Strings.repeat('-', -1);
        assertEquals(0, result.length());
    }

    @Test
    public void ensureRepeatNLengthIsN() {
        final int n = 10;
        final String result = Strings.repeat('-', n);
        assertEquals(n, result.length());
    }

    @Test
    public void ensureSymbolsIsRepeated() {
        final char symbol = '-';
        final String result = Strings.repeat(symbol, 10);
        for (int i = 0; i < result.length(); i++) {
            assertEquals(symbol, result.charAt(i));
        }
    }
}
