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
     * a generic NO-OP action that will always succeed
     *
     */
    public static final Action SUCCESS = () -> true;

    /**
     * a generic NO-OP action that will always fail
     *
     */
    public static final Action FAIL = () -> false;

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
