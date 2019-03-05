package eapli.framework.util.function;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.Before;
import org.junit.Test;

public class EitherTest {

    private static final String TESTING = "testing";
    private static final Integer TESTING_INT = 42;

    private Helper helper;
    private Consumer<String> stringLength;
    private Consumer<Integer> intValue;
    private UnaryOperator<String> stringLengthOp;
    private UnaryOperator<Integer> intValueOp;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        helper = new Helper();
        stringLength = x -> helper.i = x.length();
        stringLengthOp = x -> {
            helper.i = x.length();
            return x;
        };
        intValue = y -> helper.i = y;
        intValueOp = y -> helper.i = y;
    }

    private Either<String, Integer> leftSubject() {
        final Either<String, Integer> subject = Either.ofLeft(TESTING);
        return subject;
    }

    private Either<String, Integer> rightSubject() {
        final Either<String, Integer> subject = Either.ofRight(TESTING_INT);
        return subject;
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotCreateLeftFromNull() {
        Either.ofLeft(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotCreateRightFromNull() {
        Either.ofRight(null);
    }

    private static class Helper {
        int i;
    }

    @Test
    public void ensureLeftHoldsValue() {
        final Either<String, Integer> subject = leftSubject();

        subject.project(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureRightHoldsValue() {
        final Either<String, Integer> subject = rightSubject();

        subject.project(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureProjectToLeftConsumer() {
        final Either<String, Integer> subject = leftSubject();

        subject.visit(stringLength, intValue);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureProjectToRightConsumer() {
        final Either<String, Integer> subject = rightSubject();

        subject.visit(stringLength, intValue);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureProjectToLeft() {
        final Either<String, Integer> subject = leftSubject();

        subject.project(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureProjectToRight() {
        final Either<String, Integer> subject = rightSubject();

        subject.project(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }
}
