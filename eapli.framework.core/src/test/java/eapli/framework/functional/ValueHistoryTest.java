/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eapli.framework.util.Collections;

/**
 * @author Paulo Gandra de Sousa 25/06/2019
 *
 */
class ValueHistoryTest {

    private static final Integer FORTY_TWO = Integer.valueOf(42);

    private ValueHistory<Integer> singleSubject() {
        return ValueHistory.of(FORTY_TWO);
    }

    private ValueHistory<Integer> tripleMapSubject() {
        return ValueHistory.of(FORTY_TWO).map(x -> x + 1).map(x -> x + 1).map(x -> x + 1);
    }

    @Test
    void ensureSingleHasValue() {
        final ValueHistory<Integer> subject = singleSubject();

        assertEquals(FORTY_TWO, subject.value());
    }

    @Test
    void ensureSingleHasRightDateTime() {
        final ValueHistory<Integer> subject = singleSubject();
        final Instant i = Instant.now();

        assertTrue(Duration.between(i, subject.when()).toMillis() < 100);
    }

    @Test
    void ensureSingleHasSingleHistory() {
        final ValueHistory<Integer> subject = singleSubject();

        final int i = Collections.sizeOf(subject.history());
        assertEquals(1, i);
    }

    @Test
    void ensureSingleHasMessage() {
        final String MESSAGE = "initial";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO, MESSAGE);

        assertEquals(MESSAGE, subject.message().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureDoubleHasMessage() {
        final String MESSAGE = "second";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO).map(x -> x + 1, MESSAGE);
        assertEquals(MESSAGE, subject.message().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureOneAndThenHasValue() {
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO).andThen(1000);

        assertEquals(Integer.valueOf(1000), subject.value());
    }

    @Test
    void ensureOneAndThenWithMessageHasValue() {
        final String MESSAGE = "just because";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO).andThen(1000, MESSAGE);

        assertEquals(Integer.valueOf(1000), subject.value());
    }

    @Test
    void ensureOneAndThenWithMessageHasMessage() {
        final String MESSAGE = "just because";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO).andThen(1000, MESSAGE);

        assertEquals(MESSAGE, subject.message().orElseThrow(IllegalStateException::new));
    }

    @Test
    void ensureTripleMapHasValue() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        assertEquals(Integer.valueOf(45), subject.value());
    }

    @Test
    void ensureTripleMapHas4History() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        int i = 0;
        for (@SuppressWarnings("unused")
        final ValueHistory<Integer> each : subject.history()) {
            i++;
        }
        assertEquals(4, i);
    }

    @Test
    void ensureTripleMapHasHistoryInRightOrderAndValues() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        final int[] actual = new int[4];
        int i = 0;
        for (final ValueHistory<Integer> each : subject.history()) {
            actual[i++] = each.value();
        }

        final int[] expected = new int[] { 45, 44, 43, 42 };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void ensureCannotUndoTheInitialValue() {
        final ValueHistory<Integer> subject = singleSubject();

        assertFalse(subject.previous().isPresent());
    }

    @Test
    void ensureUndoingTheInitialValueHasEmptyHistory() {
        final ValueHistory<Integer> subject = singleSubject();

        assertFalse(subject.previous().isPresent());
    }

    @Test
    void ensureUndoingTripleMapGetsTheSecondLastEntry() {
        final ValueHistory<Integer> subject = tripleMapSubject();
        final ValueHistory<Integer> second = subject.previous()
                .orElseThrow(IllegalStateException::new);
        assertEquals(Integer.valueOf(44), second.value());
    }

    private ValueHistory<Integer> wrapper1(final Integer v) {
        return ValueHistory.of(v + 1);
    }

    private ValueHistory<Integer> wrapper2(final Integer v) {
        return ValueHistory.of(v + 1).andThen(v + 2);
    }

    @Test
    void ensureFlatMap1HasValue() {
        final ValueHistory<Integer> subject = singleSubject();

        assertEquals(Integer.valueOf(FORTY_TWO + 1), subject.flatMap(this::wrapper1).value());
    }

    @Test
    void ensureFlatMap1HasRightHistory() {
        final ValueHistory<Integer> subject = singleSubject().flatMap(this::wrapper1);

        final int[] actual = new int[2];
        int i = 0;
        for (final ValueHistory<Integer> each : subject.history()) {
            actual[i++] = each.value();
        }

        final int[] expected = new int[] { 43, 42 };
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void ensureFlatMap2HasRightHistory() {
        final ValueHistory<Integer> subject = singleSubject().flatMap(this::wrapper2);

        final int[] actual = new int[3];
        int i = 0;
        for (final ValueHistory<Integer> each : subject.history()) {
            actual[i++] = each.value();
        }

        final int[] expected = new int[] { 44, 43, 42 };
        Assertions.assertArrayEquals(expected, actual);
    }
}
