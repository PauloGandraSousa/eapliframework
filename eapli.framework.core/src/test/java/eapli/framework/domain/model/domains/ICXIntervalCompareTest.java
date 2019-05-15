package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ICXIntervalCompareTest extends IntervalCompareTestBase {

    //
    // ]-oo, x]
    //
    private final Interval<Integer> subject = ICX;

    @Test
    public void ensureIsAfterEmpty() {
        assertEquals(1, subject.compareTo(E));
    }

    @Test
    public void ensureIsBeforeUniversal() {
        assertEquals(-1, ICX.compareTo(U));
    }

    @Test
    public void ensureIsSame() {
        assertEquals(0, subject.compareTo(subject));
    }

    @Test
    public void ensureICXIsAfterIOX() {
        assertEquals(1, ICX.compareTo(IOX));
    }

    @Test
    public void ensureICXIsBeforeCXCY() {
        assertEquals(-1, ICX.compareTo(CXCY));
    }

    @Test
    public void ensureICXIsBeforeCXOY() {
        assertEquals(-1, ICX.compareTo(CXOY));
    }

    @Test
    public void ensureICXIsBeforeOXCY() {
        assertEquals(-1, ICX.compareTo(OXCY));
    }

    @Test
    public void ensureICXIsBeforeOXOY() {
        assertEquals(-1, ICX.compareTo(OXOY));
    }

    @Test
    public void ensureICXIsBeforeCXI() {
        assertEquals(-1, ICX.compareTo(CXI));
    }

    @Test
    public void ensureICXIsBeforeOXI() {
        assertEquals(-1, ICX.compareTo(OXI));
    }
}
