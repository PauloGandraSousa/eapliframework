/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import eapli.framework.functional.Either.LeftProjection;
import eapli.framework.functional.Either.RightProjection;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class EitherRightTest extends EitherBaseTest {

    @Override
    Either<String, Integer> subject() {
        final Either<String, Integer> subject = Either.right(TESTING_INT);
        return subject;
    }

    @Override
    @Test
    void ensureCannotCreateFromNull() {
        assertThrows(IllegalArgumentException.class, () -> Either.right(null));
    }

    @Override
    @Test
    void ensureOppositeValueOrElseThrowThrowsException() {
        final Either<String, Integer> subject = subject();
        assertThrows(IllegalArgumentException.class, () -> subject.leftValueOrElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Test
    void ensureHoldsValue() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING_INT, subject.rightValueOrElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Test
    void ensureOppositeValueOrElseReturnsDefault() {
        final Either<String, Integer> subject = subject();
        final String DEFAULT = "ABC";
        assertEquals(DEFAULT, subject.leftValueOrElse(DEFAULT));
    }

    @Override
    @Test
    void ensureConsume() {
        final Either<String, Integer> subject = subject();
        subject.consume(stringLength, intValue);
        assertEquals(TESTING_INT, Integer.valueOf(helper.i));
    }

    @Override
    @Test
    void ensureMap() {
        final Either<String, Integer> subject = subject();
        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING_INT, Integer.valueOf(helper.i));
    }

    @Override
    @Test
    void ensureFilterIsEmpty() {
        final Either<String, Integer> subject = subject();
        assertTrue(subject.filter(x -> x.equals(TESTING.concat("ABC")),
                y -> y == TESTING_INT + 1000) instanceof EmptyEither);
    }

    @Override
    @Test
    void ensureFilterIsNotEmpty() {
        final Either<String, Integer> subject = subject();
        assertFalse(subject.filter(x -> x.equals(TESTING), y -> y == TESTING_INT) instanceof EmptyEither);
    }

    @Override
    @Test
    void ensureFolding() {
        final Either<String, Integer> subject = subject();
        final int actual = subject.fold((x, y) -> x + y.length(), (x, y) -> x + y, 0);
        assertEquals(TESTING_INT.intValue(), actual);
    }

    @Override
    @Test
    void ensureSwap() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING_INT, subject.swap().leftValueOrElseThrow(IllegalStateException::new));
    }

    @Test
    @Override
    void ensureProjectLeft() {
        final Either<String, Integer> subject = subject();
        assertTrue(subject.left() instanceof EmptyLeft);
    }

    @Test
    @Override
    void ensureProjectRight() {
        final Either<String, Integer> subject = subject();
        assertTrue(subject.right() instanceof RightProjection);
    }

    @Test
    @Override
    void ensureOnLeft() {
        final Either<String, Integer> subject = subject();
        subject.onLeft(stringLength);
        assertEquals(-1, Integer.valueOf(helper.i));
    }

    @Test
    @Override
    void ensureOnRight() {
        final Either<String, Integer> subject = subject();
        subject.onRight(intValue);
        assertEquals(TESTING_INT, Integer.valueOf(helper.i));
    }

    @Test
    @Override
    void ensureHoldsValueOrDefault() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING_INT, subject.rightValueOrElse(12345));
    }

    @Test
    @Override
    void ensureProjectionMap() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        right.map(intValueOp);
        assertEquals(TESTING_INT, Integer.valueOf(helper.i));
    }

    @Test
    @Override
    void ensureProjectionFold() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        final int actual = right.fold((x, y) -> x + y, 0);
        assertEquals(TESTING_INT.intValue(), actual);
    }

    @Test
    @Override
    void ensureProjectionFilter() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        assertTrue(right.filter(y -> y == TESTING_INT + 1000) instanceof EmptyRight);
    }

    @Test
    @Override
    void ensureProjectionConsume() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        right.consume(intValue);
        assertEquals(TESTING_INT, Integer.valueOf(helper.i));
    }

    @Test
    @Override
    void ensureProjectionValue() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        assertEquals(TESTING_INT, right.orElse(12345));
    }

    @Test
    @Override
    void ensureProjectionValueOrThrows() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> right = subject.right();
        assertEquals(TESTING_INT, right.orElseThrow(IllegalArgumentException::new));
    }

    @Test
    @Override
    void ensureOppositeProjectionMap() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        assertTrue(opposite.map(stringLengthOp) instanceof EmptyLeft);
    }

    @Test
    @Override
    void ensureOppositeProjectionFold() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        final int actual = opposite.fold((x, y) -> x + y.length(), 12345);
        assertEquals(12345, actual);
    }

    @Test
    @Override
    void ensureOppositeProjectionFilter() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        assertTrue(opposite.filter(x -> x.equals("-10")) instanceof EmptyLeft);
    }

    @Test
    @Override
    void ensureOppositeProjectionConsumeDoesNothing() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        opposite.consume(stringLength);
        assertEquals(Helper.DEFAULT_INT, helper.i);
    }

    @Test
    @Override
    void ensureOppositeProjectionOrElseReturnsDefault() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        final String actual = opposite.orElse("-10");
        assertEquals("-10", actual);
    }

    @Test
    @Override
    void ensureOppositeProjectionOrElseThrowThrows() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> opposite = subject.left();
        assertThrows(IllegalStateException.class, () -> opposite.orElseThrow(IllegalStateException::new));
    }
}
