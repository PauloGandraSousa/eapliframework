/**
 *
 */
package eapli.framework.domain.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eapli.framework.domain.range.DiscreteDomain;
import eapli.framework.domain.range.DiscreteDomainBuilder;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class DiscreteDomainTest {

    private static DiscreteDomain<Long> EMPTY;
    private static DiscreteDomain<Long> TEN;
    private static DiscreteDomain<Long> ONE_TO_TEN;
    private static DiscreteDomain<Long> FIVE_TO_SEVEN;
    private static DiscreteDomain<Long> TEN_TO_FIFTHTEEN;
    private static DiscreteDomain<Long> FIVE_TO_SEVEN_TEN_TO_FIFHTEEN;
    private static DiscreteDomain<Long> ONE_TO_FOUR_EIGTH_TO_TEN;
    private static DiscreteDomain<Long> ONE_TO_NINE;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        EMPTY = DiscreteDomain.empty();

        TEN = build(10, 10);
        ONE_TO_TEN = build(1, 10);
        FIVE_TO_SEVEN = build(5, 7);
        TEN_TO_FIFTHTEEN = build(10, 15);
        FIVE_TO_SEVEN_TEN_TO_FIFHTEEN = builder(10, 15).add(5L).add(6L).add(7L).build();
        ONE_TO_FOUR_EIGTH_TO_TEN = builder(1, 4).add(8L, 9L, 10L).build();
        ONE_TO_NINE = build(1, 9);
    }

    private static DiscreteDomain<Long> build(long begin, long end) {
        return builder(begin, end).build();
    }

    private static DiscreteDomainBuilder<Long> builder(long begin, long end) {
        final DiscreteDomainBuilder<Long> builder = new DiscreteDomainBuilder<Long>();
        for (long i = begin; i <= end; i++) {
            builder.add(i);
        }
        return builder;
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void ensureEmptyDomainIsEmpty() {
        assertTrue("Empty domain is not empty", EMPTY.isEmpty());
    }

    @Test
    public void ensureNonEmptyDomainIsNotEmpty() {
        assertFalse("Non Empty domain is empty", ONE_TO_TEN.isEmpty());
    }

    @Test
    public void ensureTheDomainDoesNotContainNotAddedElement() {
        assertFalse("TheDomainDoesNotContainNotAddedElement", ONE_TO_TEN.contains(50L));
    }

    @Test
    public void ensureTheDomainContainsAddedElement() {
        assertTrue("TheDomainContainsAddedElement", ONE_TO_TEN.contains(5L));
    }

    @Test
    public void ensureTheDomainContainsAllAddedElements() {
        for (long e = 1; e <= 10; e++) {
            if (!ONE_TO_TEN.contains(e)) {
                fail("Element " + e + " is missing in the domain");
            }
        }
    }

    @Test
    public void ensureTheDomainDoesNotContainExtraElements() {
        for (final long e : ONE_TO_TEN) {
            if (e > 10 || e < 1) {
                fail("Element " + e + " should not be in the domain");
            }
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureImmutable() {
        final Iterator<Long> i = ONE_TO_TEN.iterator();
        i.remove();
    }

    //
    // intersection
    //

    @Test
    public void ensureIntersection1_10And5_7Is5_7() {
        final DiscreteDomain<Long> i = ONE_TO_TEN.intersection(FIVE_TO_SEVEN);
        assertEquals(FIVE_TO_SEVEN, i);
    }

    @Test
    public void ensureIntersection1_10And10_15Is10() {
        final DiscreteDomain<Long> i = ONE_TO_TEN.intersection(TEN_TO_FIFTHTEEN);
        assertEquals(TEN, i);
    }

    @Test
    public void ensureIntersection10_15And5_7IsEmpty() {
        final DiscreteDomain<Long> i = TEN_TO_FIFTHTEEN.intersection(FIVE_TO_SEVEN);
        assertEquals(EMPTY, i);
    }

    @Test
    public void ensureIntersection10_15AndEmptyIsEmpty() {
        final DiscreteDomain<Long> i = TEN_TO_FIFTHTEEN.intersection(EMPTY);
        assertEquals(EMPTY, i);
    }

    //
    // Union
    //

    @Test
    public void ensureUnion10_15And5_7Is5_7_10_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.union(FIVE_TO_SEVEN);
        assertEquals(FIVE_TO_SEVEN_TEN_TO_FIFHTEEN, u);
    }

    @Test
    public void ensureUnion1_10And5_7Is1_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.union(FIVE_TO_SEVEN);
        assertEquals(ONE_TO_TEN, u);
    }

    @Test
    public void ensureUnion1_10AndEmptyIs1_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.union(EMPTY);
        assertEquals(ONE_TO_TEN, u);
    }

    //
    // Complement
    //

    @Test
    public void ensure1_10Minus5_7Is1_4_8_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.without(FIVE_TO_SEVEN);
        assertEquals(ONE_TO_FOUR_EIGTH_TO_TEN, u);
    }

    @Test
    public void ensure1_10Minus10_to_15Is1_TO_9() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.without(TEN_TO_FIFTHTEEN);
        assertEquals(ONE_TO_NINE, u);
    }

    @Test
    public void ensure10_to_15Minus5_to7Is10_to_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.without(FIVE_TO_SEVEN);
        assertEquals(TEN_TO_FIFTHTEEN, u);
    }

    @Test
    public void ensure10_to_15MinusEmptyIs10_to_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.without(EMPTY);
        assertEquals(TEN_TO_FIFTHTEEN, u);
    }

    @Test
    public void ensureEmptyMinus10_to_15IsEmpty() {
        final DiscreteDomain<Long> u = EMPTY.without(TEN_TO_FIFTHTEEN);
        assertEquals(EMPTY, u);
    }

    @Test
    public void ensureAMinusBUnionAIntersectionBIsA() {
        final DiscreteDomain<Long> aMinusB = ONE_TO_TEN.without(TEN_TO_FIFTHTEEN);
        final DiscreteDomain<Long> aIntersectionB = ONE_TO_TEN.intersection(TEN_TO_FIFTHTEEN);
        final DiscreteDomain<Long> u = aMinusB.union(aIntersectionB);
        assertEquals(ONE_TO_TEN, u);
    }
}
