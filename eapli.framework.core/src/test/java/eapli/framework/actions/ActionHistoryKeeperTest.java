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
package eapli.framework.actions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.framework.actions.AdderHelper.AddAction;

/**
 * @author Paulo Gandra de Sousa
 *
 */
class ActionHistoryKeeperTest {

    private AdderHelper target;
    private ActionHistoryKeeper subject;

    @BeforeEach
    void setUp() {
        target = new AdderHelper();
        subject = new ActionHistoryKeeper();
    }

    @Test
    void ensureCannotUndoIfThereWereNoActions() {
        assertFalse(subject.canUndo());
    }

    @Test
    void ensureCanUndoIfThereWereActions() {
        final AddAction action = new AddAction(target, 5);
        subject.execute(action);
        assertTrue(subject.canUndo());
    }

    @Test
    void ensureCannotRedoIfThereWereNoUndos() {
        assertFalse(subject.canRedo());
    }

    @Test
    void ensureCanRedoIfThereWereUndos() {
        final AddAction action = new AddAction(target, 5);
        subject.execute(action);
        subject.undo();
        assertTrue(subject.canRedo());
    }

    @Test
    void testDoUndo() {
        // DO
        AddAction action = new AddAction(target, 2);
        subject.execute(action);
        assert target.current() == 2;

        action = new AddAction(target, 4);
        subject.execute(action);
        assert target.current() == 6;

        action = new AddAction(target, 8);
        subject.execute(action);
        assert target.current() == 14;

        // UNDO
        subject.undo();
        int expected = 6;
        int curr = target.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        subject.undo();
        expected = 2;
        curr = target.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        subject.undo();
        expected = 0;
        curr = target.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        assertFalse(subject.canUndo());
    }

}
