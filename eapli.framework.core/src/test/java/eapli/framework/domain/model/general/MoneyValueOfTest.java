package eapli.framework.domain.model.general;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.general.Money;

/**
 * test cases for the valueOf() method of the Money class
 *
 * @author Paulo Gandra Sousa
 *
 */
public class MoneyValueOfTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

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

    @Test(expected = IllegalArgumentException.class)
    public void ensureStringWithInvalidAmountIsInvalid() {
        final String test = "2X9 EUR";
        Money.valueOf(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStringWithInvalidCurrencyCodeIsInvalid() {
        final String test = "29 AAA";
        Money.valueOf(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStringWithoutSpacesIsInvalid() {
        final String test = "9EUR";
        Money.valueOf(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStringWithJustDigitsIsInvalid() {
        final String test = "9.4";
        Money.valueOf(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStringWithJustCurrencyIsInvalid() {
        final String test = "USD";
        Money.valueOf(test);
    }
}
