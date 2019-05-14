package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class IntervalCompareTest {

    // []

    // ]-I, +I[
    private static Interval<Integer> I_TO_I;
    // ]-I, x[
    private static Interval<Integer> I_TO_OX;
    // ]-I, x]
    private static Interval<Integer> I_TO_CX;
    // [x, y]
    private static Interval<Integer> CX_TO_CY;
    // [x, y[
    private static Interval<Integer> CX_TO_OY;
    // ]x, y]
    private static Interval<Integer> OX_TO_CY;
    // ]x, y[
    private static Interval<Integer> OX_TO_OY;
    // [x, +I[
    private static Interval<Integer> CX_TO_I;
    // ]x, +I[
    private static Interval<Integer> OX_TO_I;

    @BeforeClass
    public static void setUpClass() {
        // ]-I, +I[
        Interval.IntervalBuilder<Integer> b = Interval.fromInfinity();
        I_TO_I = b.toInfinity();

        // ]-I, x[
        b = Interval.fromInfinity();
        I_TO_OX = b.openTo(10);

        // ]-I, x]
        b = Interval.fromInfinity();
        I_TO_CX = b.closedTo(10);

        // [x, y]
        b = Interval.closedFrom(5);
        CX_TO_CY = b.closedTo(10);

        // [x, y[
        b = Interval.closedFrom(5);
        CX_TO_OY = b.openTo(10);

        // ]x, y]
        b = Interval.openFrom(5);
        OX_TO_CY = b.closedTo(10);

        // ]x, y[
        b = Interval.openFrom(5);
        OX_TO_OY = b.openTo(10);

        // [x, +I[
        b = Interval.closedFrom(5);
        CX_TO_I = b.toInfinity();

        // ]x, +I[
        b = Interval.openFrom(5);
        OX_TO_I = b.toInfinity();
    }

    @Test
    public void ensureSpanningInfinityWithItselfIs0() {
        assertEquals(0, I_TO_I.compareTo(I_TO_I));
    }

    @Test
    public void ensureSpanningInfinityIsFirstThanFromClosedX() {
        assertEquals(-1, I_TO_I.compareTo(CX_TO_I));
    }

    @Test
    public void ensureSpanningInfinityIsFirstThanFromOpenX() {
        assertEquals(-1, I_TO_I.compareTo(OX_TO_I));
    }

    @Test
    public void ensureSpanningInfinityIsAfterThanInfinityToOpenX() {
        assertEquals(1, I_TO_I.compareTo(I_TO_OX));
    }

    @Test
    public void ensureSpanningInfinityIsAfterThanInfinityToClosedX() {
        assertEquals(1, I_TO_I.compareTo(I_TO_CX));
    }

    // TODO compare with empty interval

}
