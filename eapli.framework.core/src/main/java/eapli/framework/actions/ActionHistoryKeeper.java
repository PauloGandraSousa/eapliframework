package eapli.framework.actions;

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.util.Invariants;

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
     * executes the action and saves it in the history
     *
     * @param action
     */
    public void execute(final UndoableAction action) {
        action.execute();
        save(action);
    }

    /**
     * saves an action in the history. assumes, the caller already executed the
     * action
     *
     * @param action
     */
    public void save(final UndoableAction action) {
        history.push(action);
        forward.clear();
    }

    /**
     * undoes the last action. the action is removed from history and placed in the
     * redo list
     */
    public void undo() {
        Invariants.ensure(this::canUndo);

        final UndoableAction action = history.poll();
        action.undo();
        forward.push(action);
    }

    /**
     * checks if there are any actions in the history
     *
     * @return
     */
    public boolean canUndo() {
        return !history.isEmpty();
    }

    /**
     * redoes the last undone action
     */
    public void redo() {
        Invariants.ensure(this::canRedo);

        final UndoableAction action = forward.poll();
        execute(action);
    }

    /**
     * checks if there are any actions to redo
     *
     * @return
     */
    public boolean canRedo() {
        return !forward.isEmpty();
    }
}
