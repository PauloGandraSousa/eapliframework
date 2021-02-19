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

import org.junit.jupiter.api.Test;

import eapli.framework.math.domain.model.FrequencyTable;

/**
 * Base test for Frequency Table accepting with any value.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 */
public abstract class FrequencyTableWithInitialTokensBaseTest {

    protected FrequencyTable<String> subject;

    @Test
    public void ensureEmptyHasInitialTokensWithFrequency0() {
        assertEquals(0L, subject.frequencyOf("a"));
        assertEquals(0L, subject.frequencyOf("w"));
    }

    @Test
    public void ensureEmptyHasInitialTokens() {
        assertEquals(2, subject.tokens().size());
        assertTrue(subject.tokens().contains("a"));
        assertTrue(subject.tokens().contains("w"));
    }

    @Test
    public void ensureContainsInitialTokenEvenIfNotCollected() {
        subject.collect("a");

        assertTrue(subject.contains("w"));
    }

    @Test
    public void ensureContainsCollectedToken() {
        subject.collect("a");

        assertTrue(subject.contains("a"));
    }

    @Test
    public void ensureDoesNotContainUnknownToken() {
        assertFalse(subject.contains("xyz"));
    }

    @Test
    public void ensureCollectingOneOfTheInitialTokensHasRightValues() {
        subject.collect("a");

        assertEquals(2, subject.size());
        assertTrue(subject.tokens().contains("a"));
        assertTrue(subject.tokens().contains("w"));
    }

    @Test
    public void ensureCollectingOneOfTheInitialTokensHasRightFrequencies() {
        subject.collect("a");

        assertEquals(1L, subject.frequencyOf("a"));
        assertEquals(0L, subject.frequencyOf("w"));
    }

    protected void initSample() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");
    }
}