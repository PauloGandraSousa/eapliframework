/**
 *
 */
package eapli.framework.actions;

/**
 * An action that should only be executed if another action succeeds (IIF - if
 * and only if)
 *
 * @author Paulo Gandra Sousa
 *
 */
public class IifAction extends CompoundAction {

    private final Action condition;

    /**
     * @param then
     *            the action to execute
     * @param condition
     *            the action that must complete successfully in order to the
     *            action of this object to be executed
     */
    public IifAction(final Action then, final Action condition) {
        super(then);
        this.condition = condition;
    }

    @Override
    public boolean execute() {
        if (condition.execute()) {
            return next();
        }
        return false;
    }
}
