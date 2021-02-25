/*
 * Copyright (c) 2013-2021 the original author or authors.
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

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.util.Utility;
import eapli.framework.validations.Preconditions;

/**
 * Utility functions for action execution with time control.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@Utility
public final class TimedActions {

    private static final Logger LOGGER = LogManager.getLogger(TimedActions.class);

    private TimedActions() {
        // ensure utility
    }

    /**
     * Simple delay mechanism for the current thread of execution.
     *
     * @param sleep
     *            the original delay interval
     */
    public static void delay(final int sleep) {
        try {
            LOGGER.trace("Sleeping {} ", sleep);
            Thread.sleep(sleep);
        } catch (@SuppressWarnings("unused") final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simple progressive delay mechanism for the current thread of execution.
     * Useful in conjunction with retry mechanisms where a progressive delay is
     * expected.
     *
     * @param sleep
     *            the original delay interval
     * @param factor
     *            the factor of progression to apply
     */
    public static void delay(final int sleep, final int factor) {
        try {
            final int delay = (int) (sleep + sleep * (factor - 1) * 0.5);
            LOGGER.trace("Sleeping {} before retrying for the {} time", delay, factor);
            Thread.sleep(delay);
        } catch (@SuppressWarnings("unused") final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static class RecurringTask extends TimerTask {
        private final Action cmd;

        public RecurringTask(final Action cmd) {
            this.cmd = cmd;
        }

        @Override
        public void run() {
            LOGGER.debug("Executing recurring task {}", cmd);
            cmd.execute();
        }
    }

    private static final Timer TIMER = new Timer();

    /**
     * Runs a periodic task each <em>interval</em> milliseconds. The first execution
     * will happen as soon as possible. There will be one single
     * {@link java.util.Timer timer} object for all the requested recurring tasks.
     * <p>
     * Consider using the method {@link #atFixedRate(Action, int) atFixedRate} which
     * makes use of Java 5+ concurrent Thread Pool Executors
     * </p>
     *
     * @param cmd
     * @param interval
     */
    public static void periodically(final Action cmd, final int interval) {
        Preconditions.nonNull(cmd);
        Preconditions.isPositive(interval);

        LOGGER.debug("Scheduling task {} each {} milliseconds", cmd, interval);
        TIMER.scheduleAtFixedRate(new RecurringTask(cmd), 0, interval);
    }

    private static ScheduledExecutorService scheduledThreadPool = Executors
            .newScheduledThreadPool(5);

    /**
     * Schedules a periodic action at a specified interval in milliseconds. the
     * first execution will happen as soon as possible. There will be one single
     * thread pool {@link java.util.concurrent.ScheduledExecutorService executor}
     * for scheduled recurring tasks.
     *
     * @param cmd
     *            the action to execute
     * @param period
     *            the period in milliseconds
     */
    public static void atFixedRate(final Action cmd, final int period) {
        Preconditions.nonNull(cmd);
        Preconditions.isPositive(period);

        LOGGER.debug("Scheduling task {} each {} milliseconds", cmd, period);
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            LOGGER.trace("Executing recurring task {}", cmd);
            cmd.execute();
        }, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * Shutdown all running threads created by the recurring task methods. client
     * code should call this function if it was used any of the scheduling functions
     * {@link #atFixedRate(Action, int) atFixedRate} or
     * {@link #periodically(Action, int) periodically}, otherwise the application
     * may not end normally as the recurring task's threads will keep executing
     *
     */
    public static void shutdownRecurringTasks() {
        LOGGER.debug("shuting down scheduled/timer tasks");
        scheduledThreadPool.shutdownNow();
        TIMER.cancel();
    }
}
