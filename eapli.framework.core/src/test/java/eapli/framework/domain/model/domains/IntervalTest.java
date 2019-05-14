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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class IntervalTest extends AbstractIntervalTest {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Range");
        instance = Interval.openFrom(START).openTo(END);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureStartBiggerThanEndIsNotAllowed() {
        System.out.println("ensureStartBiggerThanEndIsNotAllowed");
        Interval.openFrom(END).openTo(START);
    }

    @Test
    public void ensureLowerIsNotInRange() {
        System.out.println("ensureLowerIsNotInRange");
        final Long target = new Long(START_VALUE - DELTA_VALUE);
        final boolean result = instance.includes(target);
        assertFalse("value lower than start cannot be part of an open range", result);
    }

    @Test
    public void ensureUpperIsNotInRange() {
        System.out.println("ensureUpperIsNotInRange");
        final Long target = new Long(END_VALUE + DELTA_VALUE);
        final boolean result = instance.includes(target);
        assertFalse("value greater than end cannot be part of an open range", result);
    }

    @Test
    public void ensureMiddleIsInRange() {
        System.out.println("ensureMiddleIsInRange");
        final Long target = new Long(START_VALUE + DELTA_VALUE / 2);
        final boolean result = instance.includes(target);
        assertTrue("value in the middle is part of an open range", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureEmptyRangeIsNotAllowed() {
        System.out.println("ensureEmptyRangeIsNotAllowed");
        Interval.openFrom(START).openTo(START);
    }

    @Test
    public void ensureIsNotEmpty() {
        assertFalse(instance.isEmpty());
    }
}
