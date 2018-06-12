/**
 *
 */
package eapli.framework.util.function;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author SOU03408
 *
 */
public class FunctionsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for
     * {@link eapli.framework.util.function.Functions#zip(java.util.stream.Stream, java.util.stream.Stream, java.util.function.BiFunction)}.
     */
    @Test
    public void testZipIntArrays() {
        final Integer[] a = new Integer[] { 2, 4, 6 };
        final Integer[] b = new Integer[] { 2, 4, 6 };

        final Integer[] expected = new Integer[] { 4, 8, 12 };
        final Integer[] result = Functions.zip(Arrays.stream(a), Arrays.stream(b), (x, y) -> x + y)
                .toArray(Integer[]::new);
        assertArrayEquals(expected, result);
    }
}
