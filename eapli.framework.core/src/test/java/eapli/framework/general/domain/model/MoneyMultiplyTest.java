package eapli.framework.general.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eapli.framework.general.domain.model.Money;

public class MoneyMultiplyTest {

    @Test
    public void ensureMultiplyBy0() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(0);
        assertEquals(expected, subject.multiply(0));
    }

    @Test
    public void ensureMultiplyBy1() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(10);
        assertEquals(expected, subject.multiply(1));
    }

    @Test
    public void ensureMultiplyByHalf() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(5);
        assertEquals(expected, subject.multiply(0.5));
    }

    @Test
    public void ensureMultiplyBy12() {
        final Money subject = Money.euros(10);
        final Money expected = Money.euros(120);
        assertEquals(expected, subject.multiply(12));
    }
}
