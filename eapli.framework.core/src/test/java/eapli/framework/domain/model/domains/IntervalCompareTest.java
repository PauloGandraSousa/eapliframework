package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class IntervalCompareTest {

    // []
    private static Interval<Integer> E;
    // ]-I, +I[
    private static Interval<Integer> U;
    // ]-I, x[
    private static Interval<Integer> IOX;
    // ]-I, x]
    private static Interval<Integer> ICX;
    // [x, y]
    private static Interval<Integer> CXCY;
    // [x, y[
    private static Interval<Integer> CXOY;
    // ]x, y]
    private static Interval<Integer> OXCY;
    // ]x, y[
    private static Interval<Integer> OXOY;
    // [x, +I[
    private static Interval<Integer> CXI;
    // ]x, +I[
    private static Interval<Integer> OXI;

    @BeforeClass
    public static void setUpClass() {
        // []
        E = Interval.empty();

        // ]-I, +I[
        U = Interval.universal();

        // ]-I, x[
        Interval.IntervalBuilder<Integer> b = Interval.fromInfinity();
        IOX = b.openTo(10);

        // ]-I, x]
        b = Interval.fromInfinity();
        ICX = b.closedTo(10);

        // [x, y]
        b = Interval.closedFrom(5);
        CXCY = b.closedTo(10);

        // [x, y[
        b = Interval.closedFrom(5);
        CXOY = b.openTo(10);

        // ]x, y]
        b = Interval.openFrom(5);
        OXCY = b.closedTo(10);

        // ]x, y[
        b = Interval.openFrom(5);
        OXOY = b.openTo(10);

        // [x, +I[
        b = Interval.closedFrom(5);
        CXI = b.toInfinity();

        // ]x, +I[
        b = Interval.openFrom(5);
        OXI = b.toInfinity();
    }

    // EMPTY
    @Test
    public void ensureEmptyWithItselfIs0() {
        assertEquals(0, E.compareTo(E));
    }

    @Test
    public void ensureEmptyIsBeforeIOX() {
        assertEquals(-1, E.compareTo(IOX));
    }

    @Test
    public void ensureEmptyIsBeforeICX() {
        assertEquals(-1, E.compareTo(ICX));
    }

    @Test
    public void ensureEmptyIsBeforeOXCY() {
        assertEquals(-1, E.compareTo(OXCY));
    }

    @Test
    public void ensureEmptyIsBeforeOXOY() {
        assertEquals(-1, E.compareTo(OXOY));
    }

    @Test
    public void ensureEmptyIsBeforeCXCY() {
        assertEquals(-1, E.compareTo(CXCY));
    }

    @Test
    public void ensureEmptyIsBeforeCXOY() {
        assertEquals(-1, E.compareTo(CXOY));
    }

    @Test
    public void ensureEmptyIsBeforeOXI() {
        assertEquals(-1, E.compareTo(OXI));
    }

    @Test
    public void ensureEmptyIsBeforeCXI() {
        assertEquals(-1, E.compareTo(CXI));
    }

    // UNIVERSAL

    @Test
    public void ensureUniversalWithItselfIs0() {
        assertEquals(0, U.compareTo(U));
    }

    @Test
    public void ensureUniversalIsAfterEmpty() {
        assertEquals(1, U.compareTo(E));
    }

    @Test
    public void ensureUniversalIsBeforeCXI() {
        assertEquals(-1, U.compareTo(CXI));
    }

    @Test
    public void ensureUniversalIsBeforeOXI() {
        assertEquals(-1, U.compareTo(OXI));
    }

    @Test
    public void ensureUniversalIsAfterIOX() {
        assertEquals(1, U.compareTo(IOX));
    }

    @Test
    public void ensureUniversalIsAfterICX() {
        assertEquals(1, U.compareTo(ICX));
    }

    // TODO other comparison scenarios
    // ]-I, x[

    @Test
    public void ensureIOXIsAfterEmpty() {
        assertEquals(1, IOX.compareTo(E));
    }

    @Test
    public void ensureIOXIsBeforeUniversal() {
        assertEquals(-1, IOX.compareTo(U));
    }

    @Test
    public void ensureIOXIsSame() {
        assertEquals(0, IOX.compareTo(IOX));
    }

    @Test
    public void ensureIOXIsBeforeICX() {
        assertEquals(-1, IOX.compareTo(ICX));
    }

    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // ]-I, x]
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // [x, y]
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // [x, y[
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // ]x, y]
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // ]x, y[
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // [x, +I[
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;

    // TODO other comparison scenarios
    // ]x, +I[
    // E
    // U
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;
}
