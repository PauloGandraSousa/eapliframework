package eapli.framework.domain.math;

public class V321Test extends AbstractVectorTest {

    private final double[] elems = new double[] { 3, 2, 1 };

    @Override
    protected Vector expectedNormalized() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected double expectedMagnitude() {
        return 3.74166;
    }

    @Override
    protected double[] expectedElements() {
        return elems;
    }

    @Override
    protected double[] expectedScale2() {
        return new double[] { 6, 4, 2 };
    }

    @Override
    protected double[] expectedSubtract1() {
        return new double[] { 2, 1, 0 };
    }

    @Override
    protected double[] expectedAdd1() {
        return new double[] { 4, 3, 2 };
    }

    @Override
    protected double expectedDotProduct1() {
        return 6.0;
    }
}
