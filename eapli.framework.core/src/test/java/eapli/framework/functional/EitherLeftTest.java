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
class EitherLeftTest extends EitherBaseTest {

    @Override
    Either<String, Integer> subject() {
        final Either<String, Integer> subject = Either.left(TESTING);
        return subject;
    }

    @Override
    @Test
    void ensureCannotCreateFromNull() {
        assertThrows(IllegalArgumentException.class, () -> Either.left(null));
    }

    @Override
    @Test
    void ensureHoldsValue() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING, subject.leftValueOrElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Test
    void ensureOppositeValueOrElseThrowThrowsException() {
        final Either<String, Integer> subject = subject();
        assertThrows(IllegalArgumentException.class,
                () -> subject.rightValueOrElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Test
    void ensureOppositeValueOrElseReturnsDefault() {
        final Either<String, Integer> subject = subject();
        final Integer DEFAULT = 1000;
        assertEquals(DEFAULT, subject.rightValueOrElse(DEFAULT));
    }

    @Override
    @Test
    void ensureConsume() {
        final Either<String, Integer> subject = subject();
        subject.consume(stringLength, intValue);
        assertEquals(TESTING.length(), helper.i);
    }

    @Override
    @Test
    void ensureMap() {
        final Either<String, Integer> subject = subject();
        subject.map(stringLengthOp, intValueOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Override
    @Test
    void ensureFilterIsEmpty() {
        final Either<String, Integer> subject = subject();
        final Either<String, Integer> result = subject.filter(x -> x.equals(TESTING.concat("ABC")),
                y -> y == TESTING_INT + 1000);
        assertTrue(result instanceof EmptyEither);
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
        assertEquals(TESTING.length(), actual);
    }

    @Override
    @Test
    void ensureSwap() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING, subject.swap().rightValueOrElseThrow(IllegalStateException::new));
    }

    @Test
    @Override
    void ensureProjectLeft() {
        final Either<String, Integer> subject = subject();
        assertTrue(subject.left() instanceof LeftProjection);
    }

    @Test
    @Override
    void ensureProjectRight() {
        final Either<String, Integer> subject = subject();
        assertTrue(subject.right() instanceof EmptyRight);
    }

    @Test
    @Override
    void ensureOnLeft() {
        final Either<String, Integer> subject = subject();
        subject.onLeft(stringLength);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    @Override
    void ensureOnRight() {
        final Either<String, Integer> subject = subject();
        subject.onRight(intValue);
        assertEquals(-1, helper.i);
    }

    @Test
    @Override
    void ensureHoldsValueOrDefault() {
        final Either<String, Integer> subject = subject();
        assertEquals(TESTING, subject.leftValueOrElse("-10"));
    }

    @Test
    @Override
    void ensureProjectionMap() {
        final Either<String, Integer> subject = subject();
        subject.left().map(stringLengthOp);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    @Override
    void ensureProjectionFold() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> left = subject.left();
        final int actual = left.fold((x, y) -> x + y.length(), 0);
        assertEquals(TESTING.length(), actual);
    }

    @Test
    @Override
    void ensureProjectionFilter() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> left = subject.left();
        assertFalse(left.filter(x -> x.equals(TESTING)) instanceof EmptyLeft);
    }

    @Test
    @Override
    void ensureProjectionConsume() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> left = subject.left();
        left.consume(stringLength);
        assertEquals(TESTING.length(), helper.i);
    }

    @Test
    @Override
    void ensureProjectionValue() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> left = subject.left();
        final String actual = left.orElse("-10");
        assertEquals(TESTING, actual);
    }

    @Test
    @Override
    void ensureProjectionValueOrThrows() {
        final Either<String, Integer> subject = subject();
        final LeftProjection<String> left = subject.left();
        final String actual = left.orElseThrow(IllegalArgumentException::new);
        assertEquals(TESTING, actual);
    }

    @Test
    @Override
    void ensureOppositeProjectionMap() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> opposite = subject.right();
        assertTrue(opposite.map(intValueOp) instanceof EmptyRight);
    }

    @Test
    @Override
    void ensureOppositeProjectionFold() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> opposite = subject.right();
        final int actual = opposite.fold((x, y) -> x + y, 12345);
        assertEquals(12345, actual);
    }

    @Test
    @Override
    void ensureOppositeProjectionFilter() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> opposite = subject.right();
        assertTrue(opposite.filter(x -> x == 0) instanceof EmptyRight);
    }

    @Test
    @Override
    void ensureOppositeProjectionConsumeDoesNothing() {
        final Either<String, Integer> subject = subject();
        subject.right().consume(intValue);
        assertEquals(Helper.DEFAULT_INT, helper.i);
    }

    @Test
    @Override
    void ensureOppositeProjectionOrElseReturnsDefault() {
        final Either<String, Integer> subject = subject();
        final int actual = subject.right().orElse(-10);
        assertEquals(-10, actual);
    }

    @Test
    @Override
    void ensureOppositeProjectionOrElseThrowThrows() {
        final Either<String, Integer> subject = subject();
        final RightProjection<Integer> opposite = subject.right();
        assertThrows(IllegalArgumentException.class, () -> {
            opposite.orElseThrow(IllegalArgumentException::new);
        });
    }
}
