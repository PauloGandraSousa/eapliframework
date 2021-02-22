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
package eapli.framework.math.domain.model.frequencytable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map.Entry;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.framework.math.domain.model.FrequencyTable;

/**
 * Test Frequency Table accepting with any value.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 */
public class FrequencyTableTest {

    private FrequencyTable<String> subject;

    @BeforeEach
    public void setup() {
        subject = new FrequencyTable<>();
    }

    @Test
    public void ensureEmptyHas0Tokens() {
        assertEquals(0, subject.tokens().size());
    }

    @Test
    public void ensureEmptyHasFrequency0() {
        assertEquals(0L, subject.frequencyOf("a"));
        assertEquals(0L, subject.frequencyOf("w"));
    }

    @Test
    public void ensureCollectingOneHasOneToken() {
        subject.collect("a");

        assertEquals(1, subject.tokens().size());
        assertTrue(subject.tokens().contains("a"));
    }

    @Test
    public void ensureCollectingOneHasFrequency1() {
        subject.collect("a");

        assertEquals(1L, subject.frequencyOf("a"));
    }

    @Test
    public void ensureHasRightTokens() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("c");
        subject.collect("a");
        subject.collect("c");

        assertEquals(3, subject.tokens().size());
        assertTrue(subject.tokens().contains("a"));
        assertTrue(subject.tokens().contains("b"));
        assertTrue(subject.tokens().contains("c"));
    }

    @Test
    public void ensureHasRightFrequencies() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");

        assertEquals(3L, subject.frequencyOf("a"));
        assertEquals(1L, subject.frequencyOf("b"));
        assertEquals(1L, subject.frequencyOf("c"));
    }

    @Test
    public void ensureUnknownTokenHasFrequency0() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");

        assertEquals(0L, subject.frequencyOf("z"));
    }

    @Test
    public void ensureHasRightTotalOccurrences() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");

        assertEquals(5L, subject.totalOccurrences());
    }

    @Test
    public void testSortedByToken() {
        initData();

        final Stream<Entry<String, Long>> r = subject.sortedEntriesByToken();

        System.out.println("Sorted by value ascending");
        final String last = "";
        r.forEach(e -> {
            System.out.println(e);
            if (e.getKey().compareTo(last) < 0) {
                fail("Not in ascending order");
            }
        });
        assertTrue(true);
    }

    private void initData() {
        subject.collect("c");
        subject.collect("b");
        subject.collect("a");
        subject.collect("b");
        subject.collect("b");
        subject.collect("b");
        subject.collect("a");
        subject.collect("a");
        subject.collect("b");
    }

    @Test
    public void testSortedByFrequency() {
        initData();

        final Stream<Entry<String, Long>> r = subject.sortedEntriesByFrequency();

        System.out.println("Sorted by frequency descending");
        final Long last = 0L;
        r.forEach(e -> {
            System.out.println(e);
            if (e.getValue().compareTo(last) < 0) {
                fail("Not in descending order");
            }
        });
        assertTrue(true);
    }

    @Test
    public void ensureContainsCollectedToken() {
        subject.collect("a");

        assertTrue(subject.contains("a"));
    }

    @Test
    public void ensureDoesNotContainUnknownToken() {
        subject.collect("a");

        assertFalse(subject.contains("w"));
    }

}
