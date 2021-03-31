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
package eapli.framework.domains.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class I_O10_IntervalCompareTest extends IntervalCompareTestBase {

    //
    // ]-oo, x[
    //
    private final Interval<Integer> subject = I_O10;

    @Test
    void ensureIsAfterEmpty() {
        assertEquals(1, subject.compareTo(E));
    }

    @Test
    void ensureIsBeforeUniversal() {
        assertEquals(-1, subject.compareTo(U));
    }

    @Test
    void ensureIsSame() {
        assertEquals(0, subject.compareTo(subject));
    }

    @Test
    void ensureIOXIsBeforeICX() {
        assertEquals(-1, I_O10.compareTo(I_C10));
    }

    @Test
    void ensureIOXIsBeforeCXCY() {
        assertEquals(-1, I_O10.compareTo(C5_C10));
    }

    @Test
    void ensureIOXIsBeforeCXOY() {
        assertEquals(-1, I_O10.compareTo(C5_O10));
    }

    @Test
    void ensureIOXIsBeforeOXCY() {
        assertEquals(-1, I_O10.compareTo(O5_C10));
    }

    @Test
    void ensureIOXIsBeforeOXOY() {
        assertEquals(-1, I_O10.compareTo(O5_O10));
    }

    @Test
    void ensureIOXIsBeforeCXI() {
        assertEquals(-1, I_O10.compareTo(C5_I));
    }

    @Test
    void ensureIOXIsBeforeOXI() {
        assertEquals(-1, I_O10.compareTo(O5_I));
    }
}
