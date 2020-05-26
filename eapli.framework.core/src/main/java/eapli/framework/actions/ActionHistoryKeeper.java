/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.actions;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import eapli.framework.validations.Invariants;

/**
 * A helper class to keep a simple history of actions and manage undo/redo.
 * <p>
 * This class is thread-safe.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class ActionHistoryKeeper {
    /**
     * the history of actions already performed, i.e., an Undo list
     */
    private final Deque<UndoableAction> history = new ConcurrentLinkedDeque<>();
    /**
     * the actions that were undone and can eventually be redone
     */
    private final Deque<UndoableAction> forward = new ConcurrentLinkedDeque<>();

    /**
     * Eventough we use concurrent deques, we need to synchronize moving from one deque to another
     */
    private final Object lock = new Object();

    /**
     * Executes the action and saves it in the history.
     *
     * @param action
     *            the action to execute and save
     */
    public void execute(final UndoableAction action) {
        action.execute();
        save(action);
    }

    /**
     * Saves an action in the history. Assumes, the caller already executed the
     * action.
     *
     * @param action
     *            the action to save
     */
    public void save(final UndoableAction action) {
        synchronized (lock) {
            history.push(action);
            forward.clear();
        }
    }

    /**
     * Undoes the last action. The action is removed from history and placed in
     * the redo list.
     */
    public void undo() {
        Invariants.ensure(canUndo());

        UndoableAction action;
        synchronized (lock) {
            action = history.poll();
            forward.push(action);
        }
        action.undo();
    }

    /**
     * Checks if there are any actions in the history.
     *
     * @return {@code true} if the history is not empty
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * Redo the last undone action.
     */
    public void redo() {
        Invariants.ensure(canRedo());

        final UndoableAction action = forward.poll();
        execute(action);
    }

    /**
     * Checks if there are any actions to redo.
     *
     * @return {@code true} if there are actions in the redo history
     */
    public boolean canRedo() {
        return !forward.isEmpty();
    }
}
