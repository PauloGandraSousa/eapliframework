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
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ClosedIntervalTest extends AbstractIntervalTest {

    @BeforeClass
    public static void setUpClass() {
        System.out.println("closedRange");
        instance = Interval.closedFrom(START).closedTo(END);
    }

    @Test
    public void ensureStartIsInRange() {
        System.out.println("ensureStartIsInRange");
        final Long target = new Long(START_VALUE);
        final boolean result = instance.includes(target);
        assertTrue("start must be part of a closed range", result);
    }

    @Test
    public void ensureEndIsInRange() {
        System.out.println("ensureEndIsInRange");
        final Long target = new Long(END_VALUE);
        final boolean result = instance.includes(target);
        assertTrue("end must be part of a closed range", result);
    }

    @Test
    public void ensureStringRepresentationIsOk() {
        final String expected = "[" + START + ", " + END + "]";
        final String actual = instance.toString();
        assertEquals(expected, actual);
    }
}
