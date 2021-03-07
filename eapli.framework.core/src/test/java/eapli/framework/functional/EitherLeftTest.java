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
    void ensureOppositeValueThrowsException() {
        final Either<String, Integer> subject = subject();

        assertThrows(IllegalArgumentException.class,
                () -> subject.rightValueOrElseThrow(IllegalArgumentException::new));
    }

    @Override
    @Test
    void ensureOppositeValueReturnsDefault() {
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
}
