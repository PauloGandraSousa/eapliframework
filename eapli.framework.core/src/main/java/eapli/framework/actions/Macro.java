/**
 *
 */
package eapli.framework.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * a collection of actions that can be executed as one single action (Composite
 * pattern). execution stops on the first action that returns false
 *
 * @author sou03408
 *
 */
public class Macro implements Action {

    private final List<Action> actions = new ArrayList<>();

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

    /*
     * (non-Javadoc)
     *
     * @see eapli.framework.actions.Action#execute()
     */
    @Override
    public boolean execute() {
        // if the execute method was void we could have a one-line
        // implementation
        // actions.forEach(e -> {Action::execute);
        for (final Action e : actions) {
            if (!e.execute()) {
                return false;
            }
        }
        return true;
    }
}
