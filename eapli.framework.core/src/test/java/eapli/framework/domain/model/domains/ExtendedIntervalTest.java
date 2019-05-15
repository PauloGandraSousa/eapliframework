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

import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ExtendedIntervalTest extends IntervalTestBase {

    private static final Long NEW_START = END + 2;
    private static final Long NEW_END = START - 2;

    public ExtendedIntervalTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNewStartCannotBeAfterEnd() {
        subject = Interval.closedFrom(START).closedTo(END).withStart(NEW_START);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNewEndCannotBeBeforeStart() {
        subject = Interval.closedFrom(START).closedTo(END).withEnd(NEW_END);
    }
}
