package eapli.framework.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ChainedActionTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureFirstActionCannotBeNull() {
        new ChainedAction(null, Actions.SUCCESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNextActionCannotBeNull() {
        new ChainedAction(Actions.SUCCESS, null);
    }

    @Test
    public void ensureExecuteGoesThruChain() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = new ChainedAction(helper.adder(), helper.adder());
        chain.execute();
        assertEquals(2, helper.current());
    }

    @Test
    public void ensureExecuteGoesThruChainReturnsSuccess() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = new ChainedAction(helper.adder(), helper.adder());
        assertTrue(chain.execute());
    }

    @Test
    public void ensureExecuteGoesThruComplexChain() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain2 = build4Add1Chain(helper);
        chain2.execute();
        assertEquals(4, helper.current());
    }

    @Test
    public void ensureExecuteGoesThruComplexChainInTheRightOrder() {
        final List<Integer> helper = new ArrayList<>();
        final ChainedAction chain = new ChainedAction(() -> helper.add(1), () -> helper.add(2))
                .then(() -> helper.add(3)).then(() -> helper.add(4)).then(() -> helper.add(5));
        chain.execute();

        assertEquals(5, helper.size());
        int i = 1;
        for (final int each : helper) {
            assertEquals(i, each);
            i++;
        }
    }

    @Test
    public void ensureExecuteGoesThruComplexChainInTheRightOrder2() {
        final List<Integer> helper = new ArrayList<>();
        final ChainedAction chain0 = new ChainedAction(() -> helper.add(2), () -> helper.add(1));
        final ChainedAction chain1 = new ChainedAction(() -> helper.add(3), chain0);
        final ChainedAction chain2 = new ChainedAction(() -> helper.add(4), chain1);
        chain2.execute();

        assertEquals(4, helper.size());
        int i = 4;
        for (final int each : helper) {
            assertEquals(i, each);
            i--;
        }
    }

    @Test
    public void ensureExecuteGoesThruComplexChainReturnSucess() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain2 = build4Add1Chain(helper);
        assertTrue(chain2.execute());
    }

    private ChainedAction build4Add1Chain(final AdderHelper helper) {
        final ChainedAction chain0 = new ChainedAction(helper.adder(), helper.adder());
        final ChainedAction chain1 = new ChainedAction(helper.adder(), chain0);
        final ChainedAction chain2 = new ChainedAction(helper.adder(), chain1);
        return chain2;
    }

    @Test
    public void ensureExecuteReturnsFalseOnFail() {
        final ChainedAction chain = new ChainedAction(Actions.FAIL, Actions.SUCCESS);
        assertFalse(chain.execute());
    }

    @Test
    public void ensureExecuteReturnsFalseOnFail2() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = build4Add1Chain(helper).then(Actions.FAIL);
        assertFalse(chain.execute());
    }

    @Test
    public void ensureExecuteStopsWhenActionFails() {
        final List<Integer> helper = new ArrayList<>();
        final ChainedAction chain = new ChainedAction(() -> helper.add(1), () -> helper.add(2))
                .then(() -> helper.add(3)).then(Actions.FAIL).then(() -> helper.add(5));
        chain.execute();

        assertEquals(3, helper.size());
    }
}
