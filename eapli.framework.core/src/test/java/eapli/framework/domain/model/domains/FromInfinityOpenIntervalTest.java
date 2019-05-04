/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import eapli.framework.domain.model.domains.Interval;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class FromInfinityOpenIntervalTest extends AbstractIntervalTest {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("FromInfinityOpenRangeTest");
        final Interval.RangeBuilder<Long> b = Interval.fromInfinity();
        instance = b.openTo(END).build();
    }

    @Test
    public void ensureEndIsNotInRange() {
        System.out.println("ensureEndIsNotInRange");
        final Long target = new Long(END_VALUE);
        final boolean result = instance.includes(target);
        assertFalse("end cannot be part of an open range", result);
    }

    @Test
    public void ensureSmallValueIsIncluded() {
        System.out.println("ensureSmallValueIsIncluded");
        final Long target = Long.MIN_VALUE;
        final boolean result = instance.includes(target);
        assertTrue("From infinity range must include any value smaller than end", result);
    }

    @Test
    public void ensureIsFromInfinity() {
        System.out.println("ensureIsFromInfinity");
        final boolean result = instance.isFromInfinity();
        assertTrue("from infinity ranges must be from infinity", result);
    }

    @Test
    public void ensureStringRepresentationIsOk() {
        final String expected = "]oo, " + END + "[";
        final String actual = instance.toString();
        assertEquals(expected, actual);
    }
}
