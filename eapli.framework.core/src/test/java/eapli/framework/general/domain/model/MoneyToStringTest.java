/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.general.domain.model;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for the toString() method of the class Money.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class MoneyToStringTest {

    private static Locale oldDefault;

    @BeforeClass
    public static void setUpClass() {
        oldDefault = Locale.getDefault();
        Locale.setDefault(new Locale("pt-PT"));
    }

    @AfterClass
    public static void tearDownClass() {
        Locale.setDefault(oldDefault);
    }

    @Test
    public void oneDecimalPlace() {
        final Money instance = Money.euros(125.5);
        final String expected = "€\u00A0125.50";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void threeDecimalPlace() {
        final Money instance = Money.euros(125.527);
        final String expected = "€\u00A0125.53";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void onlyCents() {
        final String expected = "€\u00A00.09";
        final Money instance = Money.euros(0.09);
        assertEquals(expected, instance.toString());
    }

    @Test
    public void noDecimalPlaces() {
        final String expected = "€\u00A09.00";
        final Money instance = Money.euros(9);
        assertEquals(expected, instance.toString());
    }

    @Test
    public void bigAmount() {
        final String expected = "€\u00A0999,999,999,999.53";
        final Money instance = Money.euros(999999999999.53);
        assertEquals(expected, instance.toString());
    }
}
