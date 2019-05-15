package eapli.framework.domain.model.domains;

import org.junit.BeforeClass;

public abstract class IntervalCompareTestBase {

    // []
    protected static Interval<Integer> E;
    // ]-I, +I[
    protected static Interval<Integer> U;
    // ]-I, x[
    protected static Interval<Integer> IOX;
    // ]-I, x]
    protected static Interval<Integer> ICX;
    // [x, y]
    protected static Interval<Integer> CXCY;
    // [x, y[
    protected static Interval<Integer> CXOY;
    // ]x, y]
    protected static Interval<Integer> OXCY;
    // ]x, y[
    protected static Interval<Integer> OXOY;
    // [x, +I[
    protected static Interval<Integer> CXI;
    // ]x, +I[
    protected static Interval<Integer> OXI;

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
}
