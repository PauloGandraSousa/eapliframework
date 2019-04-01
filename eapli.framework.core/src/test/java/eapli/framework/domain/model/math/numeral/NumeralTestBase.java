/**
 *
 */
package eapli.framework.domain.model.math.numeral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.domain.model.math.Numeral;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public abstract class NumeralTestBase {

    private Numeral instance;

    private static final String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    protected abstract int getBase();

    @Test
    public void ensure0is0() {
        instance = Numeral.valueOf("0", getBase());
        assertEquals(0, instance.decimalValue());
    }

    @Test
    public void ensureBaseSymbols() {
        for (int i = 0; i < getBase(); i++) {
            final String result = Numeral.valueOf(i).toBase(getBase()).toString();
            final String expected = String.valueOf(SYMBOLS.charAt(i));
            if (!result.equals(expected)) {
                fail("expected [" + expected + "] but was [" + result + "]");
            }
        }
        assertTrue(true);
    }

    @Test
    public void ensureConvertingToBaseDoesNotProduceStrangeSymbols() {
        final Stream<String> results = IntStream.range(0, 10000)
                .<String>mapToObj(x -> Numeral.valueOf(x).toBase(getBase()).toString());
        assertTrue(results.allMatch(x -> validateSymbols(x, getBase())));
    }

    private boolean validateSymbols(final String x, final int base) {
        final String pattern = "^[" + SYMBOLS.substring(0, getBase()) + "]+$";
        return x.matches(pattern);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInvalidNumeralIsNotAccepted() {
        final String subject = "12=F";
        Numeral.valueOf(subject, getBase());
    }
}
