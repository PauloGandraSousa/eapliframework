/**
 *
 */
package eapli.framework.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Paulo Gandra de Sousa 25/06/2019
 *
 */
public class ValueHistoryTest {

    private static final Integer FORTY_TWO = Integer.valueOf(42);

    private ValueHistory<Integer> singleSubject() {
        return ValueHistory.of(FORTY_TWO);
    }

    private ValueHistory<Integer> tripleMapSubject() {
        return ValueHistory.of(FORTY_TWO).map(x -> x + 1).map(x -> x + 1).map(x -> x + 1);
    }

    @Test
    public void ensureSingleHasValue() {
        final ValueHistory<Integer> subject = singleSubject();

        assertEquals(FORTY_TWO, subject.value());
    }

    @Test
    public void ensureSingleHasSingleHistory() {
        final ValueHistory<Integer> subject = singleSubject();

        int i = 0;
        for (final ValueHistory<Integer> each : subject.history()) {
            i++;
        }
        assertEquals(1, i);
    }

    @Test
    public void ensureSingleHasMessage() {
        final String MESSAGE = "initial";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO, MESSAGE);

        assertEquals(MESSAGE, subject.message().orElseThrow(IllegalStateException::new));
    }

    @Test
    public void ensureDoubleHasMessage() {
        final String MESSAGE = "second";
        final ValueHistory<Integer> subject = ValueHistory.of(FORTY_TWO).map(x -> x + 1, MESSAGE);
        assertEquals(MESSAGE, subject.message().orElseThrow(IllegalStateException::new));
    }

    @Test
    public void ensureTripleMapHasValue() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        assertEquals(Integer.valueOf(45), subject.value());
    }

    @Test
    public void ensureTripleMapHas4History() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        int i = 0;
        for (@SuppressWarnings("unused")
        final ValueHistory<Integer> each : subject.history()) {
            i++;
        }
        assertEquals(4, i);
    }

    @Test
    public void ensureTripleMapHasHistoryInRightOrderAndValues() {
        final ValueHistory<Integer> subject = tripleMapSubject();

        final int[] actual = new int[4];
        int i = 0;
        for (final ValueHistory<Integer> each : subject.history()) {
            actual[i++] = each.value();
        }

        final int[] expected = new int[] { 45, 44, 43, 42 };
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void ensureCannotUndoTheInitialValue() {
        final ValueHistory<Integer> subject = singleSubject();

        assertFalse(subject.hasPrevious());
    }

    @Test(expected = IllegalStateException.class)
    public void ensureUndoingTheInitialValueThrows() {
        final ValueHistory<Integer> subject = singleSubject();

        subject.previous();
    }

    @Test
    public void ensureUndoingTripleMapGetsTheSecondLastEntry() {
        final ValueHistory<Integer> subject = tripleMapSubject();
        final ValueHistory<Integer> second = subject.previous();
        assertEquals(Integer.valueOf(44), second.value());
    }
}
