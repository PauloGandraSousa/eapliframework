package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmptyIntervalCompareTest extends IntervalCompareTestBase {

    //
    // EMPTY interval, i.e., []
    //

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
}
