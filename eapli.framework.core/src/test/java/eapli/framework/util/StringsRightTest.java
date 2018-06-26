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
public class StringsRightTest {

    private static final String TEST_STRING = "1234567890";

    @Test
    public void ensureRight0IsEmpty() {
        final String result = Strings.right(TEST_STRING, 0);
        assertEquals("", result);
    }

    @Test
    public void ensureRightNegativeIsEmpty() {
        final String result = Strings.right(TEST_STRING, -1);
        assertEquals("", result);
    }

    @Test
    public void ensureRightNLengthIsN() {
        final int n = 3;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(n, result.length());
    }

    @Test
    public void ensureRight3Is890() {
        final String expected = "890";
        final int n = 3;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(expected, result);
    }

    @Test
    public void ensureRightLenBiggerThanLengthIsOriginalString() {
        final String expected = TEST_STRING;
        final int n = TEST_STRING.length() + 15;
        final String result = Strings.right(TEST_STRING, n);
        assertEquals(expected, result);
    }
}
