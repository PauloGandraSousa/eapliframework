package eapli.framework.domain.money;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.money.Money;

/**
 * test cases for the toString() method of the class Money
 * 
 * @author Paulo Gandra Sousa
 *
 */
public class MoneyToStringTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void oneDecimalPlace() {
        final Money instance = Money.euros(125.5);
        final String expected = "125.5 EUR";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void threeDecimalPlace() {
        final Money instance = Money.euros(125.527);
        final String expected = "125.53 EUR";
        assertEquals(expected, instance.toString());
    }

    @Test
    public void onlyCents() {
        final String expected = "0.09 EUR";
        final Money instance = Money.euros(0.09);
        assertEquals(expected, instance.toString());
    }

    @Test
    public void noDecimalPlaces() {
        final String expected = "9.0 EUR";
        final Money instance = Money.euros(9);
        assertEquals(expected, instance.toString());
    }

    @Test
    public void bigAmount() {
        final String expected = "999999999999.53 EUR";
        final Money instance = Money.euros(999999999999.53);
        assertEquals(expected, instance.toString());
    }
}
