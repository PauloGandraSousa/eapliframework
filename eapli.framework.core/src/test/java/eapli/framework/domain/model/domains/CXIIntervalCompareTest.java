package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CXIIntervalCompareTest extends IntervalCompareTestBase {

    //
    // [x, +I[
    //
    private final Interval<Integer> subject = CXI;

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

    // TODO other comparison scenarios
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;
}
