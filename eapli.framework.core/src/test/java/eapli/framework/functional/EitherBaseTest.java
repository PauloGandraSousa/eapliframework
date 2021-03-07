/**
 *
 */
package eapli.framework.functional;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;

/**
 * @author Paulo Gandra de Sousa 04/03/2021
 *
 */
abstract class EitherBaseTest {

    protected static final String TESTING = "testing";
    protected static final Integer TESTING_INT = 42;
    protected Helper helper;
    protected Consumer<String> stringLength;
    protected Consumer<Integer> intValue;
    protected UnaryOperator<String> stringLengthOp;
    protected UnaryOperator<Integer> intValueOp;

    protected static class Helper {
        protected int i;
    }

    /**
     *
     */
    public EitherBaseTest() {
        super();
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
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

    abstract Either<String, Integer> subject();

    abstract void ensureCannotCreateFromNull();

    abstract void ensureHoldsValue();

    abstract void ensureOppositeValueThrowsException();

    abstract void ensureOppositeValueReturnsDefault();

    abstract void ensureConsume();

    abstract void ensureMap();

    abstract void ensureFilterIsEmpty();

    abstract void ensureFilterIsNotEmpty();

    abstract void ensureFolding();

    abstract void ensureSwap();
}