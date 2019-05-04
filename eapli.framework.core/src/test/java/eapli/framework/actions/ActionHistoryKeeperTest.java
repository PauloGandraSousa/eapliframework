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
package eapli.framework.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import eapli.framework.actions.AdderHelper.AddAction;

/**
 * @author Paulo Gandra de Sousa
 *
 */
public class ActionHistoryKeeperTest {

    private AdderHelper subject;
    private ActionHistoryKeeper instance;

    @Before
    public void setUp() {
        subject = new AdderHelper();
        instance = new ActionHistoryKeeper();
    }

    @Test
    public void ensureCannotUndoIfThereWereNoActions() {
        assertFalse(instance.canUndo());
    }

    @Test
    public void ensureCanUndoIfThereWereActions() {
        final AddAction action = new AddAction(subject, 5);
        instance.execute(action);
        assertTrue(instance.canUndo());
    }

    @Test
    public void ensureCannotRedoIfThereWereNoUndos() {
        assertFalse(instance.canRedo());
    }

    @Test
    public void ensureCanRedoIfThereWereUndos() {
        final AddAction action = new AddAction(subject, 5);
        instance.execute(action);
        instance.undo();
        assertTrue(instance.canRedo());
    }

    @Test
    public void testDoUndo() {
        // DO
        AddAction action = new AddAction(subject, 2);
        instance.execute(action);
        assert subject.current() == 2;

        action = new AddAction(subject, 4);
        instance.execute(action);
        assert subject.current() == 6;

        action = new AddAction(subject, 8);
        instance.execute(action);
        assert subject.current() == 14;

        // UNDO
        instance.undo();
        int expected = 6;
        int curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.undo();
        expected = 2;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.undo();
        expected = 0;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        assertFalse(instance.canUndo());
    }

}
