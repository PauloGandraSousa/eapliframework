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

    /**
     * A generic NO-OP action that will always succeed
     *
     */
    public static final Action SUCCESS = () -> true;

    /**
     * A generic NO-OP action that will always fail
     *
     */
    public static final Action FAIL = () -> false;

    /**
     * Executes an action n times.
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
     * Retries an action with a given interval time between invocations up to a
     * certain number of attempts. The retry stops if the action is successful
     * (returns true) or the maximum number of attempts is reached.
     *
     * @param op
     * @param sleep
     * @param maxAttempts
     * @return
     */
    public static boolean retry(final Action op, final int sleep, final int maxAttempts, final boolean progressive) {
        int atempts = 1;
        boolean result = op.execute();
        while (!result && atempts <= maxAttempts) {
            atempts++;
            if (progressive) {
                TimedActions.delay(sleep, atempts);
            } else {
                TimedActions.delay(sleep);
            }
            result = op.execute();
        }
        return result;
    }

    public static boolean retry(final Action op, final int sleep, final int maxAttempts) {
        return retry(op, sleep, maxAttempts, true);
    }
}
