package eapli.framework.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ActionTest {

    @Test
    public void testSucess() {
        assertTrue(Actions.SUCCESS.execute());
    }

    @Test
    public void testFail() {
        assertFalse(Actions.FAIL.execute());
    }

    private static class AdderHelper {
        private int i = 0;
    }

    @Test
    public void ensureRepeat10Times() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.repeat(add1, 10);

        assertEquals(10, helper.i);
    }

    @Test
    public void ensureRepeat0Times() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.repeat(add1, 0);

        assertEquals(0, helper.i);
    }

    @Test
    public void ensureRepeatMinus1Times() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.repeat(add1, -1);

        assertEquals(0, helper.i);
    }

    @Test
    public void ensureRetryWorksAtFirst() {
        assertTrue(Actions.retry(Actions.SUCCESS, 10, 10));
    }

    @Test
    public void ensureRetryFailsAfterNIfActionFails() {
        assertFalse(Actions.retry(Actions.FAIL, 10, 3));
    }

    @Test
    public void ensureRetryRunsNTimes() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return false;
        };
        final boolean result = Actions.retry(add1, 10, 3);

        assertTrue(result == false && helper.i == 10);
    }
}
