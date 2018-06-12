/**
 *
 */
package eapli.framework.domain.money;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author sou03408
 *
 */
public class MoneyCollectorTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void ensureSeed() {
        final MoneySumCollector instance = new MoneySumCollector(Money.euros(15));
        final Money expected = Money.euros(15);
        assertEquals(expected, instance.result());
    }

    @Test
    public void ensureAdding10() {
        final MoneySumCollector instance = new MoneySumCollector(Money.euros(0));
        instance.add(Money.euros(10));
        final Money expected = Money.euros(10);
        assertEquals(expected, instance.result());
    }

    @Test
    public void ensureAdding10And5() {
        final MoneySumCollector instance = new MoneySumCollector(Money.euros(0));
        instance.add(Money.euros(10));
        instance.add(Money.euros(5));
        final Money expected = Money.euros(15);
        assertEquals(expected, instance.result());
    }

    @Test
    public void ensureAggregate() {
        final MoneySumCollector instance1 = new MoneySumCollector(Money.euros(25));
        final MoneySumCollector instance2 = new MoneySumCollector(Money.euros(5));
        final Money expected = Money.euros(30);
        assertEquals(expected, instance1.aggregate(instance2).result());
    }
}
