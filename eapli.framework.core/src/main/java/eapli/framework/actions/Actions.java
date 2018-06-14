/**
 *
 */
package eapli.framework.actions;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * utility class
 *
 * @author sou03408
 *
 */
public final class Actions implements Utilitarian {

    private Actions() {
        // ensure utility
    }

    private static final Action NOOP_SUCESS = () -> true;
    private static final Action NOOP_FAIL = () -> false;

    /**
     * creates a generic NO-OP action that will always succeed or fail
     *
     * @param ret
     *            the desired return value for the action
     * @return
     */
    public static Action noop(final boolean ret) {
        return ret ? NOOP_SUCESS : NOOP_FAIL;
    }

    /**
     * executes an action n times
     *
     * @param cmd
     * @param n
     */
    public static void repeat(final Action cmd, final int n) {
        Preconditions.nonNull(cmd);

        for (int i = n; i > 0; i--) {
            cmd.execute();
        }
    }

    /**
     * retries an action with a given interval time between invocations up to a
     * certain number of attempts. the retry stops if the action is successful
     * (returns true) or the maximum number of attempts is reached.
     *
     * @param op
     * @param sleep
     * @param maxAttempts
     * @return
     */
    public static boolean retry(final Action op, final int sleep, final int maxAttempts) {
        int atempts = 1;
        boolean result = op.execute();
        while (!result && atempts <= maxAttempts) {
            atempts++;
            TimedActions.delay(sleep, atempts);
            result = op.execute();
        }
        return result;
    }
}
