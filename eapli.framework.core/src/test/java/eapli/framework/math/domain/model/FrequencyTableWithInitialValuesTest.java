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
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Test frequency table with some initial values to consider but accepting any value.
 *
 * @author Paulo Gandra Sousa 28/05/2020
 */
public class FrequencyTableWithInitialValuesTest extends FrequencyTableWithInitialValuesBaseTest {

    @Before
    public void setup() {
        subject = new FrequencyTable<>(Arrays.asList("a", "w"), false);
    }

    @Test
    public void ensureHasRightValues() {
        subject.collect("a");
        subject.collect("b");
        subject.collect("c");

        assertEquals(4, subject.values().size());
        assertTrue(subject.values().contains("a"));
        assertTrue(subject.values().contains("b"));
        assertTrue(subject.values().contains("c"));
        assertTrue(subject.values().contains("w"));
    }

    @Test
    public void ensureHasRightFrequencies() {
        initSample();

        assertEquals(BigInteger.valueOf(3), subject.frequencyOf("a"));
        assertEquals(BigInteger.ONE, subject.frequencyOf("b"));
        assertEquals(BigInteger.ONE, subject.frequencyOf("c"));
        assertEquals(BigInteger.ZERO, subject.frequencyOf("w"));
    }

    @Test
    public void ensureUnknownValueHasFrequency0() {
        initSample();

        assertEquals(BigInteger.ZERO, subject.frequencyOf("z"));
    }
}
