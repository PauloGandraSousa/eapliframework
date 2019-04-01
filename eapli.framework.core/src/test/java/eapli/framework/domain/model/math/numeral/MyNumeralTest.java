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
import eapli.framework.domain.model.math.NumeralSystem;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class MyNumeralTest {

    private Numeral instance;

    private static final String SYMBOLS = "=!\"#$%&/()";
    private static final NumeralSystem SYSTEM = () -> SYMBOLS;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void ensure0is0() {
        instance = Numeral.valueOf("=", SYSTEM);
        assertEquals(0, instance.decimalValue());
    }

    @Test
    public void ensureBaseSymbols() {
        for (int i = 0; i < 10; i++) {
            final String result = Numeral.valueOf(i).toBase(SYSTEM).toString();
            final String expected = String.valueOf(SYMBOLS.charAt(i));
            if (!result.equals(expected)) {
                fail("expected [" + expected + "] but was [" + result + "]");
            }
        }
        assertTrue(true);
    }

    @Test
    public void ensureNoStrangeSymbols() {
        final Stream<String> results = IntStream.range(0, 10000)
                .<String>mapToObj(x -> Numeral.valueOf(x).toBase(SYSTEM).toString());
        assertTrue(results.allMatch(x -> validateSymbols(x)));
    }

    private boolean validateSymbols(String x) {
        final String pattern = "^[" + SYMBOLS + "]+$";
        return x.matches(pattern);
    }

    @Test
    public void testSomeCases() {
        assertEquals(128, Numeral.valueOf("!\"(", SYSTEM).decimalValue());
        assertEquals(1024, Numeral.valueOf("!=\"$", SYSTEM).decimalValue());
        assertEquals(9999, Numeral.valueOf("))))", SYSTEM).decimalValue());
        assertEquals(100000, Numeral.valueOf("!=====", SYSTEM).decimalValue());
    }
}
