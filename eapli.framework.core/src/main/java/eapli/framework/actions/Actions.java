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

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import eapli.framework.util.Utilitarian;
import eapli.framework.validations.Preconditions;

/**
 * utility class
 *
 * @author Paulo Gandra de Sousa
 *
 */
public final class Actions implements Utilitarian {

    private abstract static class ThrowAction implements Action {

        protected final String message;

        public ThrowAction(final String m) {
            message = m;
        }
    }

    private static class ThrowStateAction extends ThrowAction {

        public ThrowStateAction(final String m) {
            super(m);
        }

        @Override
        public boolean execute() {
            throw new IllegalStateException(message);
        }
    }

    private static class ThrowArgumentAction extends ThrowAction {

        public ThrowArgumentAction(final String m) {
            super(m);
        }

        @Override
        public boolean execute() {
            throw new IllegalArgumentException(message);
        }
    }

    private Actions() {
        // ensure utility
    }

    /**
     * a default action to throw IllegalArgumentException
     */
    public static final Action THROW_ARGUMENT = () -> {
        throw new IllegalArgumentException();
    };

    /**
     * a default action to throw IllegalStateException
     */
    public static final Action THROW_STATE = () -> {
        throw new IllegalStateException();
    };

    /**
     * A default action to throw IllegalStateException binded to a message
     *
     * @param m
     *            the message to set as the description of the exception to be
     *            thrown
     * @return a new Action object that will throw an IllegalStateException
     */
    public static final Action throwState(final String m) {
        return new ThrowStateAction(m);
    }

    /**
     * A default action to throw IllegalArgumentException binded to a message
     *
     * @param m
     *            the message to set as the description of the exception to be
     *            thrown
     * @return a new Action object that will throw an IllegalArgumentException
     */
    public static final Action throwArgument(final String m) {
        return new ThrowArgumentAction(m);
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
     * Executes a function if a predicate is not met
     *
     * @param consequence
     * @param condition
     * @return {@code false} if the condition is not met. otherwise, the result
     *         of the {@code consequence} action
     */
    public static boolean doIfNot(final Action consequence, final BooleanSupplier condition) {
        if (!condition.getAsBoolean()) {
            return consequence.execute();
        }
        return false;
    }

    /**
     * Executes a function if a predicate is not met
     *
     * @param consequence
     * @param condition
     * @param arg
     * @return {@code false} if the condition is not met. otherwise, the result
     *         of the {@code consequence} action
     */
    public static <T> boolean doIfNot(final Action consequence, final Predicate<T> condition, final T arg) {
        if (!condition.test(arg)) {
            return consequence.execute();
        }
        return false;
    }

    /**
     * Executes a function if a predicate is met
     *
     * @param consequence
     * @param condition
     * @return {@code false} if the condition is not met. otherwise, the result
     *         of the {@code consequence} action
     */
    public static boolean doIf(final Action consequence, final BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            return consequence.execute();
        }
        return false;
    }

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
     * @param progressive
     * @return true if the action is successful or false if the maximum number
     *         of retries has been reached
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
