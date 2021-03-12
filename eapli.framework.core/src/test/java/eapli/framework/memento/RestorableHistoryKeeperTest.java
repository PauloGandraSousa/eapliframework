/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
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
package eapli.framework.memento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra Sousa
 *
 */
class RestorableHistoryKeeperTest {

    private SampleAdder subject;
    private RestorableHistoryKeeper<SampleAdder> instance;

    @BeforeEach
    void setUp() {
        subject = new SampleAdder();
        instance = new RestorableHistoryKeeper<>(subject);
    }

    @Test
    void testSavepointRestore2() {
        final int NRUNS = 15;
        // create savepoints
        int expected = 0;
        for (int i = 1; i <= NRUNS; i++) {
            subject.add(i);
            instance.savepoint();

            expected += i;
        }

        // go back to savepoints
        for (int j = NRUNS; j >= 1; j--) {
            instance.restore();
            final int curr = subject.current();
            if (curr != expected) {
                fail("wrong state @ " + j + ". expected " + expected + " but got " + curr);
            }
            expected -= j;
        }
    }

    @Test
    void testSavepointRestore() {
        // create savepoints
        subject.add(2);
        assertEquals(2, subject.current());
        instance.savepoint();

        subject.add(4);
        assertEquals(6, subject.current());
        instance.savepoint();

        subject.add(8);
        assertEquals(14, subject.current());

        // go back to savepoints
        instance.restore();
        int expected = 6;
        int curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.restore();
        expected = 2;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        assertFalse(instance.canRestore());
    }

    /**
     * Test method for
     * {@link eapli.framework.memento.RestorableHistoryKeeper#canRestore()}.
     */
    @Test
    void ensureCanotRestoreIfThereWereNoSavepoints() {
        assertFalse(instance.canRestore());
    }

    /**
     * Test method for
     * {@link eapli.framework.memento.RestorableHistoryKeeper#canRestore()}.
     */
    @Test
    void ensureCanRestoreIfThereWereSavepoints() {
        instance.savepoint();
        assertTrue(instance.canRestore());
    }
}
