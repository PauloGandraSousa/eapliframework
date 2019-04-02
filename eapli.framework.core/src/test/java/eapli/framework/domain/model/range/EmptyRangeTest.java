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
package eapli.framework.domain.model.range;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.LongStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class EmptyRangeTest extends AbstractRangeTest {

    public EmptyRangeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("EmptyRange");
        instance = Range.empty();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ensureIsEmpty() {
        assertTrue(instance.isEmpty());
    }

    @Test
    public void ensureDoesNotInclude() {
        final boolean result = LongStream.range(START_VALUE * -2L, END_VALUE * -2L)
                .noneMatch(x -> instance.includes(x));
        assertTrue(result);
    }

    @Test
    public void ensureNotFromInfinity() {
        assertFalse(instance.isFromInfinity());
    }

    @Test
    public void ensureNotToInfinity() {
        assertFalse(instance.isToInfinity());
    }

    @Test(expected = IllegalStateException.class)
    public void ensureStart() {
        instance.start();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureEnd() {
        instance.end();
    }
}
