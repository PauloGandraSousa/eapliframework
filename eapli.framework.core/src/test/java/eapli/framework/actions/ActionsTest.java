package eapli.framework.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ActionsTest {

    private static class AdderHelper {
        private int i = 0;
    }

    @Test
    public void testSucess() {
        assertTrue(Actions.SUCCESS.execute());
    }

    @Test
    public void testFail() {
        assertFalse(Actions.FAIL.execute());
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowState() {
        Actions.THROW_STATE.execute();
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowStateWithMsg() {
        Actions.throwState("Test").execute();
    }

    @Test
    public void testThrowStateWithMsgHasTheRightMessage() {
        try {
            Actions.throwState("Test").execute();
        } catch (final IllegalStateException e) {
            assertEquals("Test", e.getMessage());
            return;
        }
        fail("somethign terrible just happened...");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowArgument() {
        Actions.THROW_ARGUMENT.execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowArgumentWithMsg() {
        Actions.throwArgument("Test").execute();
    }

    @Test
    public void testThrowArgumentWithMsgHasTheRightMessage() {
        try {
            Actions.throwArgument("Test").execute();
        } catch (final IllegalArgumentException e) {
            assertEquals("Test", e.getMessage());
            return;
        }
        fail("somethign terrible just happened...");
    }

    @Test
    public void ensureDoIf() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.doIf(add1, () -> true);
        assertEquals(1, helper.i);
    }

    @Test
    public void ensureDoIfDoesnotExecuteIfFalse() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.doIf(add1, () -> false);
        assertEquals(0, helper.i);
    }

    @Test
    public void ensureDoIfNot() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.doIfNot(add1, () -> false);
        assertEquals(1, helper.i);
    }

    @Test
    public void ensureDoIfNotDoesnotExecuteIfTrue() {
        final AdderHelper helper = new AdderHelper();
        final Action add1 = () -> {
            helper.i++;
            return true;
        };
        Actions.doIfNot(add1, () -> true);
        assertEquals(0, helper.i);
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

        assertTrue(result == false && helper.i == 3);
    }
}
