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

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.validations.Invariants;

/**
 * A helper class to keep a simple history of actions and manage undo/redo
 *
 * @author Paulo Gandra Sousa
 *
 */
public class ActionHistoryKeeper {
    /**
     * the history of actions already performed, i.e., an Undo list
     */
    private final Deque<UndoableAction> history = new ArrayDeque<>();
    /**
     * the actions that were undone and can eventually be redone
     */
    private final Deque<UndoableAction> forward = new ArrayDeque<>();

    /**
     * Executes the action and saves it in the history
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
     * action
     *
     * @param action
     *            the action to save
     */
    public void save(final UndoableAction action) {
        history.push(action);
        forward.clear();
    }

    /**
     * undoes the last action. the action is removed from history and placed in
     * the redo list
     */
    public void undo() {
        Invariants.ensure(this::canUndo);

        final UndoableAction action = history.poll();
        action.undo();
        forward.push(action);
    }

    /**
     * Checks if there are any actions in the history
     *
     * @return true if the history is not empty
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * Redo the last undone action
     */
    public void redo() {
        Invariants.ensure(this::canRedo);

        final UndoableAction action = forward.poll();
        execute(action);
    }

    /**
     * Checks if there are any actions to redo
     *
     * @return true if there are actions in the redo history
     */
    public boolean canRedo() {
        return !forward.isEmpty();
    }
}
