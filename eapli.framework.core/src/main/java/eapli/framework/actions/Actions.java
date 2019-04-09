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

import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * utility class
 *
 * @author Paulo Gandra de Sousa
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
        while (!result && atempts < maxAttempts) {
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
