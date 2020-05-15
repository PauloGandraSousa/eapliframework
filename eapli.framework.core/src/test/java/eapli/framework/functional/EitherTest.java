/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.actions.Actions;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class EitherTest {

    private static final String TESTING = "testing";
    private static final Integer TESTING_INT = 42;

    private Helper helper;
    private Consumer<String> stringLength;
    private Consumer<Integer> intValue;
    private UnaryOperator<String> stringLengthOp;
    private UnaryOperator<Integer> intValueOp;

    private static class Helper {
        int i;
    }

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
        final Either<String, Integer> subject = Either.left(TESTING);
        return subject;
    }

    private Either<String, Integer> rightSubject() {
        final Either<String, Integer> subject = Either.right(TESTING_INT);
        return subject;
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotCreateLeftFromNull() {
        Either.left(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotCreateRightFromNull() {
        Either.right(null);
    }

    @Test
    public void ensureLeftHoldsValue() {
        final Either<String, Integer> subject = leftSubject();

        assertEquals(TESTING, subject.leftValueOrElseThrow(IllegalArgumentException::new));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureLeftValueOfRightEitherThrowsException() {
        final Either<String, Integer> subject = rightSubject();

        subject.leftValueOrElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void ensureRightHoldsValue() {
        final Either<String, Integer> subject = rightSubject();

        assertEquals(TESTING_INT, subject.rightValueOrElseThrow(IllegalArgumentException::new));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRightValueOfLeftEitherThrowsException() {
        final Either<String, Integer> subject = leftSubject();

        subject.rightValueOrElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void ensureRightValueOfLeftEitherReturnsDefault() {
        final Either<String, Integer> subject = leftSubject();

        final Integer DEFAULT = 1000;
        assertEquals(DEFAULT, subject.rightValueOrElse(DEFAULT));
    }

    @Test
    public void ensureLeftValueOfRightEitherReturnsDefault() {
        final Either<String, Integer> subject = rightSubject();

        final String DEFAULT = "ABC";
        assertEquals(DEFAULT, subject.leftValueOrElse(DEFAULT));
    }

    @Test
    public void ensureConsumeOnLeft() {
        final Either<String, Integer> subject = leftSubject();

        subject.consume(stringLength, intValue);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureConsumeOnRight() {
        final Either<String, Integer> subject = rightSubject();

        subject.consume(stringLength, intValue);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureConsumeEmptyDoesNothing() {
        Either.empty().consume(x -> Actions.throwState("This should never happen"),
                y -> Actions.throwState("This should never happen"));
        // it will fall thru as neither action should be invoked
        assertTrue(true);
    }

    @Test
    public void ensureMappingAnEmptyEitherReturnsEmpty() {
        assertTrue(Either.empty().map(x -> x, y -> y).isEmpty());
    }

    @Test
    public void ensureMapToLeft() {
        final Either<String, Integer> subject = leftSubject();

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureMapToRight() {
        final Either<String, Integer> subject = rightSubject();

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureFilterLeftIsEmpty() {
        final Either<String, Integer> subject = leftSubject();

        assertTrue(subject.filter(x -> x.equals(TESTING.concat("ABC")), y -> y == TESTING_INT + 1000).isEmpty());
    }

    @Test
    public void ensureFilterLeftIsNotEmpty() {
        final Either<String, Integer> subject = leftSubject();

        assertFalse(subject.filter(x -> x.equals(TESTING), y -> y == TESTING_INT).isEmpty());
    }

    @Test
    public void ensureFilterRightIsEmpty() {
        final Either<String, Integer> subject = rightSubject();

        assertTrue(subject.filter(x -> x.equals(TESTING.concat("ABC")), y -> y == TESTING_INT + 1000).isEmpty());
    }

    @Test
    public void ensureFilterRightIsNotEmpty() {
        final Either<String, Integer> subject = rightSubject();

        assertFalse(subject.filter(x -> x.equals(TESTING), y -> y == TESTING_INT).isEmpty());
    }

    @Test
    public void ensureFilteringEmptyIsEmpty() {
        assertTrue(Either.empty().filter(x -> true, y -> true).isEmpty());
    }

    @Test
    public void ensureFoldingRight() {
        final Either<String, Integer> subject = rightSubject();

        final int actual = subject.fold((x, y) -> x + y.length(), (x, y) -> x + y, 0);
        assertEquals(TESTING_INT.intValue(), actual);
    }

    @Test
    public void ensureFoldingLeft() {
        final Either<String, Integer> subject = leftSubject();

        final int actual = subject.fold((x, y) -> x + y.length(), (x, y) -> x + y, 0);
        assertEquals(TESTING.length(), actual);
    }

    @Test
    public void ensureFoldingOnEmptyEitherReturnsDefault() {
        final Either<String, Integer> subject = Either.empty();

        final int DEFAULT = 1234;
        final int actual = subject.fold((x, y) -> x + y.length(), (x, y) -> x + y, DEFAULT);
        assertEquals(DEFAULT, actual);
    }

    @Test
    public void ensureSwapLeft() {
        final Either<String, Integer> subject = leftSubject();

        assertEquals(TESTING, subject.swap().rightValueOrElseThrow(IllegalStateException::new));
    }

    @Test
    public void ensureSwapRight() {
        final Either<String, Integer> subject = rightSubject();

        assertEquals(TESTING_INT, subject.swap().leftValueOrElseThrow(IllegalStateException::new));
    }

    @Test
    public void ensureSwapingAnEmptyReturnsAnEmpty() {
        assertTrue(Either.empty().swap().isEmpty());
    }
}
