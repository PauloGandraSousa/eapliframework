package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OXIIntervalCompareTest extends IntervalCompareTestBase {

    //
    // ]x, +I[
    //
    private final Interval<Integer> subject = OXI;

    @Test
    public void ensureIsAfterEmpty() {
        assertEquals(1, subject.compareTo(E));
    }

    @Test
    public void ensureIsAfterUniversal() {
        assertEquals(1, subject.compareTo(U));
    }

    @Test
    public void ensureIsSame() {
        assertEquals(0, subject.compareTo(subject));
    }

    @Test
    public void ensureIsAfterICX() {
        assertEquals(1, subject.compareTo(ICX));
    }

    @Test
    public void ensureIsAfterIOX() {
        assertEquals(1, subject.compareTo(IOX));
    }

    // TODO other comparison scenarios
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;

    @Test
    public void ensureIsAfterCXI() {
        assertEquals(1, subject.compareTo(CXI));
    }
}
