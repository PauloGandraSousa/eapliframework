package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IOXIntervalCompareTest extends IntervalCompareTestBase {

    //
    // ]-oo, x[
    //
    private final Interval<Integer> subject = IOX;

    @Test
    public void ensureIsAfterEmpty() {
        assertEquals(1, subject.compareTo(E));
    }

    @Test
    public void ensureIsBeforeUniversal() {
        assertEquals(-1, subject.compareTo(U));
    }

    @Test
    public void ensureIsSame() {
        assertEquals(0, subject.compareTo(subject));
    }

    @Test
    public void ensureIOXIsBeforeICX() {
        assertEquals(-1, IOX.compareTo(ICX));
    }

    @Test
    public void ensureIOXIsBeforeCXCY() {
        assertEquals(-1, IOX.compareTo(CXCY));
    }

    @Test
    public void ensureIOXIsBeforeCXOY() {
        assertEquals(-1, IOX.compareTo(CXOY));
    }

    @Test
    public void ensureIOXIsBeforeOXCY() {
        assertEquals(-1, IOX.compareTo(OXCY));
    }

    @Test
    public void ensureIOXIsBeforeOXOY() {
        assertEquals(-1, IOX.compareTo(OXOY));
    }

    @Test
    public void ensureIOXIsBeforeCXI() {
        assertEquals(-1, IOX.compareTo(CXI));
    }

    @Test
    public void ensureIOXIsBeforeOXI() {
        assertEquals(-1, IOX.compareTo(OXI));
    }
}
