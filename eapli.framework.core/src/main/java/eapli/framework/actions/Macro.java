/**
 *
 */
package eapli.framework.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a collection of actions that can be executed as one single action (Composite
 * pattern). execution stops on the first action that returns false
 *
 * @author sou03408
 *
 */
public class Macro implements Action {

    private final List<Action> actions = new ArrayList<>();

    private Action errorCause;

    public Macro() {
    }

    public Macro(final Action... actions) {
        for (final Action a : actions) {
            this.actions.add(a);
        }
    }

    public void record(final Action action) {
        actions.add(action);
    }

    /**
     * returns the action that caused macro execution to stop
     * 
     * @return
     */
    public Optional<Action> errorCause() {
        return Optional.ofNullable(errorCause);
    }

    /**
     * execute all the actions in the Macro stopping on the first action that
     * fails (i.e., returns false)
     *
     * @see eapli.framework.actions.Action#execute()
     */
    @Override
    public boolean execute() {
        for (final Action e : actions) {
            if (!e.execute()) {
                errorCause = e;
                return false;
            }
        }
        return true;
    }
}
