/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.functional;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.Before;
import org.junit.Test;

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

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureRightHoldsValue() {
        final Either<String, Integer> subject = rightSubject();

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureAcceptOnLeft() {
        final Either<String, Integer> subject = leftSubject();

        subject.consume(stringLength, intValue);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureAcceptOnRight() {
        final Either<String, Integer> subject = rightSubject();

        subject.consume(stringLength, intValue);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }

    @Test
    public void ensureProjectToLeft() {
        final Either<String, Integer> subject = leftSubject();

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    public void ensureProjectToRight() {
        final Either<String, Integer> subject = rightSubject();

        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, new Integer(helper.i));
    }
}
