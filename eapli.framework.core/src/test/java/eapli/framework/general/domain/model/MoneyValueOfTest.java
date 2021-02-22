/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test cases for the valueOf() method of the Money class
 *
 * @author Paulo Gandra Sousa
 *
 */
public class MoneyValueOfTest {

    @Test
    public void properStringWithCents() {
        final String test = "125.50 EUR";
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(125.5);
        assertEquals(expected, instance);
    }

    @Test
    public void properStringWith3DigitCents() {
        final String test = "125.527 EUR";
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(125.53);
        assertEquals(expected, instance);
    }

    @Test
    public void properStringWithJustCents() {
        final String test = "0.09 EUR";
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(0.09);
        assertEquals(expected, instance);
    }

    @Test
    public void properStringWithoutCents() {
        final String test = "9 EUR";
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(9);
        assertEquals(expected, instance);
    }

    @Test
    public void properStringWithBigAmount() {
        final String test = "999999999999.50 EUR";
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(999999999999.50);
        assertEquals(expected, instance);
    }

    @Test
    public void ensureStringWithInvalidAmountIsInvalid() {
        final String test = "2X9 EUR";
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }

    @Test
    public void ensureStringWithInvalidCurrencyCodeIsInvalid() {
        final String test = "29 AAA";
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }

    @Test
    public void ensureStringWithoutSpacesIsInvalid() {
        final String test = "9EUR";
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }

    @Test
    public void ensureStringWithJustDigitsIsInvalid() {
        final String test = "9.4";
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }

    @Test
    public void ensureStringWithJustCurrencyIsInvalid() {
        final String test = "USD";
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }
}
