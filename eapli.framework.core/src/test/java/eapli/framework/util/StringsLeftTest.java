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
public class StringsLeftTest {

    private static final String TEST_STRING = "1234567890";

    @Test
    public void ensureLeft0IsEmpty() {
        final String result = Strings.left(TEST_STRING, 0);
        assertEquals("", result);
    }

    @Test
    public void ensureLeftNegativeIsEmpty() {
        final String result = Strings.left(TEST_STRING, -1);
        assertEquals("", result);
    }

    @Test
    public void ensureLeftNLengthIsN() {
        final int n = 3;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(n, result.length());
    }

    @Test
    public void ensureLeft3Is123() {
        final String expected = "123";
        final int n = 3;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(expected, result);
    }

    @Test
    public void ensureLeftLenBiggerThanLengthIsOriginalString() {
        final String expected = TEST_STRING;
        final int n = TEST_STRING.length() + 15;
        final String result = Strings.left(TEST_STRING, n);
        assertEquals(expected, result);
    }
}
