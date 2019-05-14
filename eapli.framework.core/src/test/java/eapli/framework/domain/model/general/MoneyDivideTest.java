package eapli.framework.domain.model.general;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MoneyDivideTest {

    @Test(expected = ArithmeticException.class)
    public void ensureCannotDivideBy0() {
        final Money subject = Money.euros(1);
        subject.divide(0);
    }

    @Test
    public void ensureDivide100By2() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(50), Money.euros(50) };
        final Money[] actuals = subject.divide(2);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void ensureDivide100By1() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(100) };
        final Money[] actuals = subject.divide(1);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void ensureDivide100By3() {
        final Money subject = Money.euros(100);
        final Money[] expecteds = { Money.euros(33.34), Money.euros(33.33), Money.euros(33.33) };
        final Money[] actuals = subject.divide(3);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void ensureDivide99By2() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(49.50), Money.euros(49.50) };
        final Money[] actuals = subject.divide(2);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void ensureDivide99By1() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(99) };
        final Money[] actuals = subject.divide(1);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void ensureDivide99By3() {
        final Money subject = Money.euros(99);
        final Money[] expecteds = { Money.euros(33), Money.euros(33), Money.euros(33) };
        final Money[] actuals = subject.divide(3);
        assertArrayEquals(expecteds, actuals);
    }
}
