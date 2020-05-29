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
package eapli.framework.math.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Frequency Table accepting with any value.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 */
public class FrequencyTableTest {

    private FrequencyTable<String> subject;

    @Before
    public void setup() {
        subject = new FrequencyTable<>();
    }

    @Test
    public void ensureEmptyHas0Values() {
        assertEquals(0, subject.values().size());
    }

    @Test
    public void ensureEmptyHasFrequency0() {
        assertEquals(BigInteger.ZERO, subject.frequencyOf("a"));
        assertEquals(BigInteger.ZERO, subject.frequencyOf("w"));
    }

    @Test
    public void ensureCollectingOneHasOneValue() {
        subject.collect("a");

        assertEquals(1, subject.values().size());
        assertTrue(subject.values().contains("a"));
    }

    @Test
    public void ensureCollectingOneHasFrequency1() {
        subject.collect("a");

        assertEquals(BigInteger.ONE, subject.frequencyOf("a"));
    }

    @Test
    public void ensureHasRightValues() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("c");

        assertEquals(3, subject.values().size());
        assertTrue(subject.values().contains("a"));
        assertTrue(subject.values().contains("b"));
        assertTrue(subject.values().contains("c"));
    }

    @Test
    public void ensureHasRightFrequencies() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");

        assertEquals(BigInteger.valueOf(3), subject.frequencyOf("a"));
        assertEquals(BigInteger.ONE, subject.frequencyOf("b"));
        assertEquals(BigInteger.ONE, subject.frequencyOf("c"));
    }

    @Test
    public void ensureUnknownValueHasFrequency0() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("a");
        subject.collect("c");
        subject.collect("a");

        assertEquals(BigInteger.ZERO, subject.frequencyOf("z"));
    }
}
