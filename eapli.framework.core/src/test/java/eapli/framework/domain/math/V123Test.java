package eapli.framework.domain.math;

public class V123Test extends AbstractVectorTest {

    private final double[] elems = new double[] { 1, 2, 3 };

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
        return new double[] { 2, 4, 6 };
    }

    @Override
    protected double[] expectedSubtract1() {
        return new double[] { 0, 1, 2 };
    }

    @Override
    protected double[] expectedAdd1() {
        return new double[] { 2, 3, 4 };
    }

    @Override
    protected double expectedDotProduct1() {
        return 6.0;
    }
}
