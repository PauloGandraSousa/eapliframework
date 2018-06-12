/**
 *
 */
package eapli.framework.actions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * @author SOU03408
 *
 */
public final class TimedActions implements Utilitarian {

    private static final Logger LOGGER = LogManager.getLogger(TimedActions.class);

    private TimedActions() {
        // ensure utility
    }

    public static void delay(final int sleep, final int factor) {
        try {
            final int delay = (int) (sleep + sleep * (factor - 1) * 0.5);
            LOGGER.trace("Sleeping {} before retrying for the {} time", delay, factor);
            Thread.sleep(delay);
        } catch (final InterruptedException e) {
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

    private static final Timer timer = new Timer();

    /**
     * runs a periodic task each <em>interval</em> milliseconds. the first execution
     * will happen as soon as possible. There will be one single
     * {@link java.util.Timer timer} object for all the requested recurring tasks.
     *
     * consider using the method {@link #atFixedRate(Action, int) atFixedRate} which
     * makes use of Java 5+ concurrent Thread Pool Executors
     *
     * @param cmd
     * @param interval
     */
    public static void periodically(final Action cmd, final int interval) {
        Preconditions.nonNull(cmd);
        Preconditions.isPositive(interval);

        LOGGER.debug("Scheduling task {} each {} milliseconds", cmd, interval);
        timer.scheduleAtFixedRate(new RecurringTask(cmd), 0, interval);
    }

    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    /**
     * schedules a periodic action at a specified interval in milliseconds. the
     * first execution will happen as soon as possible. there will be one single
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
            LOGGER.debug("Executing recurring task {}", cmd);
            cmd.execute();
        }, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * shutdown all running threads created by the recurring task methods. client
     * code should call this function if it was used any of the scheduling functions
     * {@link #atFixedRate(Action, int) atFixedRate} or
     * {@link #periodically(Action, int) periodically}, otherwise the application
     * may not end normally as the recurring task's threads will keep executing
     *
     */
    public static void shutdownRecurringTasks() {
        LOGGER.debug("shuting down scheduled/timer tasks");
        scheduledThreadPool.shutdownNow();
        timer.cancel();
    }
}
