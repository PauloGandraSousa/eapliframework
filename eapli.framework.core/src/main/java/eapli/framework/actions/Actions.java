/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.actions;

import java.util.function.BooleanSupplier;

import eapli.framework.util.Utility;
import eapli.framework.validations.Preconditions;

/**
 * Utility class for working with {@link Action}.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
public final class Actions {

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
     * A default action to throw {@link IllegalArgumentException}.
     */
    public static final Action THROW_ARGUMENT = () -> {
        throw new IllegalArgumentException();
    };

    /**
     * A default action to throw {@link IllegalStateException}.
     */
    public static final Action THROW_STATE = () -> {
        throw new IllegalStateException();
    };

    /**
     * A default action to throw {@link IllegalStateException} binded to a message.
     *
     * @param m
     *            the message to set as the description of the exception to be
     *            thrown
     *
     * @return a new Action object that will throw an IllegalStateException
     */
    public static Action throwState(final String m) {
        return new ThrowStateAction(m);
    }

    /**
     * A default action to throw {@link IllegalArgumentException} binded to a message.
     *
     * @param m
     *            the message to set as the description of the exception to be
     *            thrown
     *
     * @return a new Action object that will throw an IllegalArgumentException
     */
    public static Action throwArgument(final String m) {
        return new ThrowArgumentAction(m);
    }

    /**
     * A generic NO-OP action that will always succeed.
     *
     */
    public static final Action SUCCESS = () -> true;

    /**
     * A generic NO-OP action that will always fail.
     *
     */
    public static final Action FAIL = () -> false;

    /**
     * Executes a function if a predicate is not met.
     *
     * @param consequence
     *            the action to execute if the condition evaluates to {@code false}
     * @param condition
     *            the condition to be evaluated
     * @return {@code false} if the condition is met. otherwise, the result of
     *         the {@code consequence} action
     */
    public static boolean doIfNot(final Action consequence, final BooleanSupplier condition) {
        if (!condition.getAsBoolean()) {
            return consequence.execute();
        }
        return false;
    }

    /**
     * Executes a function conditionally.
     *
     * @param consequence
     *            the action to execute if the value evaluates to {@code false}
     * @param condition
     *            the value to test
     * @return {@code false} if {@code condition} is {@code true}. otherwise,
     *         the result of the {@code consequence} action
     */
    public static boolean doIfNot(final Action consequence, final boolean condition) {
        return doIfNot(consequence, () -> condition);
    }

    /**
     * Executes a function if a predicate is met.
     *
     * @param consequence
     *            the action to execute if the condition evaluates to {@code true}
     * @param condition
     *            the condition to be evaluated
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
     * Executes a function conditionally.
     *
     * @param consequence
     *            the action to execute if the value evaluates to {@code true}
     * @param condition
     *            the value to test
     * @return {@code false} if {@code condition} is {@code false}. otherwise,
     *         the result of the {@code consequence} action
     */
    public static boolean doIf(final Action consequence, final boolean condition) {
        return doIf(consequence, () -> condition);
    }

    /**
     * Executes an action n times.
     *
     * @param action
     *            the action to repeat
     * @param n
     *            the number of time to repeat
     */
    public static void repeat(final Action action, final int n) {
        Preconditions.nonNull(action);

        for (int i = n; i > 0; i--) {
            action.execute();
        }
    }

    /**
     * Retries an action with a given interval time between invocations up to a
     * certain number of attempts. The retry stops if the action is successful
     * (returns true) or the maximum number of attempts is reached.
     *
     * @param action
     *            the action to execute
     * @param sleep
     *            the delay between retries
     * @param maxAttempts
     *            the maximum number of retries
     * @param progressive
     *            if {@code true} the delay between retries will progressively increase, otherwise
     *            it will be constant
     * @return {@code true} if the action is successful or {@code false} if the maximum number
     *         of retries has been reached
     */
    public static boolean retry(final Action action, final int sleep, final int maxAttempts,
            final boolean progressive) {
        int atempts = 1;
        boolean result = action.execute();
        while (!result && atempts < maxAttempts) {
            atempts++;
            if (progressive) {
                TimedActions.delay(sleep, atempts);
            } else {
                TimedActions.delay(sleep);
            }
            result = action.execute();
        }
        return result;
    }

    /**
     * Retries an action with a given interval time between invocations up to a
     * certain number of attempts. The retry stops if the action is successful
     * (returns true) or the maximum number of attempts is reached.
     *
     * @param action
     *            the action to execute
     * @param sleep
     *            the delay between retries
     * @param maxAttempts
     *            the maximum number of retries
     * @return {@code true} if the action is successful or {@code false} if the maximum number
     *         of retries has been reached
     */
    public static boolean retry(final Action action, final int sleep, final int maxAttempts) {
        return retry(action, sleep, maxAttempts, true);
    }
}
