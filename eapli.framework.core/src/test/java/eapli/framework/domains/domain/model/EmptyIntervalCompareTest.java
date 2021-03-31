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
class EmptyIntervalCompareTest extends IntervalCompareTestBase {

    //
    // EMPTY interval, i.e., []
    //

    @Test
    void ensureIsSame() {
        assertEquals(0, E.compareTo(E));
    }

    @Test
    void ensureEmptyIsBeforeIOX() {
        assertEquals(-1, E.compareTo(I_O10));
    }

    @Test
    void ensureEmptyIsBeforeICX() {
        assertEquals(-1, E.compareTo(I_C10));
    }

    @Test
    void ensureEmptyIsBeforeOXCY() {
        assertEquals(-1, E.compareTo(O5_C10));
    }

    @Test
    void ensureEmptyIsBeforeOXOY() {
        assertEquals(-1, E.compareTo(O5_O10));
    }

    @Test
    void ensureEmptyIsBeforeCXCY() {
        assertEquals(-1, E.compareTo(C5_C10));
    }

    @Test
    void ensureEmptyIsBeforeCXOY() {
        assertEquals(-1, E.compareTo(C5_O10));
    }

    @Test
    void ensureEmptyIsBeforeOXI() {
        assertEquals(-1, E.compareTo(O5_I));
    }

    @Test
    void ensureEmptyIsBeforeCXI() {
        assertEquals(-1, E.compareTo(C5_I));
    }
}
