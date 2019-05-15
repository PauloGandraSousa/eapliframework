package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UniversalIntervalCompareTest extends IntervalCompareTestBase {

    //
    // UNIVERSAL interval, i.e., ]-oo, +oo[
    //

    @Test
    public void ensureUniversalIsAfterEmpty() {
        assertEquals(1, U.compareTo(E));
    }

    @Test
    public void ensureUniversalWithItselfIs0() {
        assertEquals(0, U.compareTo(U));
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
}
