/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.domain.model.domains;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class O5_C10_IntervalCompareTest extends IntervalCompareTestBase {

    //
    // [x, y]
    //
    private final Interval<Integer> subject = O5_C10;

    @Test
    public void ensureIsAfterEmpty() {
        assertEquals(1, subject.compareTo(E));
    }

    @Test
    public void ensureIsAfterUniversal() {
        assertEquals(1, subject.compareTo(U));
    }

    @Test
    public void ensureIsSame() {
        assertEquals(0, subject.compareTo(subject));
    }

    // ]4, 7] < [5, 7]
    @Test
    public void ensureIsBeforeC10_C50() {
        assertEquals(-1, subject.compareTo(C10_C50));
    }

    // ]4, 7] < ]5, 7]
    @Test
    public void ensureIsBeforeO10_C50() {
        assertEquals(-1, subject.compareTo(O10_C50));
    }

    // ]4, 7] < ]4, 10]
    @Test
    public void ensureIsBeforeO5_C50() {
        assertEquals(-1, subject.compareTo(O5_C50));
    }

    // other comparison scenarios
    // IOX;
    // ICX;
    // CXCY;
    // CXOY;
    // OXCY;
    // OXOY;
    // CXI;
    // OXI;
}
