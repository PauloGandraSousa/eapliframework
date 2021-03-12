/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class ChainedActionTest {

    @Test
    void ensureFirstActionCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new ChainedAction(null, Actions.SUCCESS));
    }

    @Test
    void ensureNextActionCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new ChainedAction(Actions.SUCCESS, null));
    }

    @Test
    void ensureExecuteGoesThruChain() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = new ChainedAction(helper.adder(), helper.adder());
        chain.execute();
        assertEquals(2, helper.current());
    }

    @Test
    void ensureExecuteGoesThruChainReturnsSuccess() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = new ChainedAction(helper.adder(), helper.adder());
        assertTrue(chain.execute());
    }

    @Test
    void ensureExecuteGoesThruComplexChain() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain2 = build4Add1Chain(helper);
        chain2.execute();
        assertEquals(4, helper.current());
    }

    @Test
    void ensureExecuteGoesThruComplexChainInTheRightOrder() {
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
    void ensureExecuteGoesThruComplexChainInTheRightOrder2() {
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
    void ensureExecuteGoesThruComplexChainReturnSucess() {
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
    void ensureExecuteReturnsFalseOnFail() {
        final ChainedAction chain = new ChainedAction(Actions.FAIL, Actions.SUCCESS);
        assertFalse(chain.execute());
    }

    @Test
    void ensureExecuteReturnsFalseOnFail2() {
        final AdderHelper helper = new AdderHelper();
        final ChainedAction chain = build4Add1Chain(helper).then(Actions.FAIL);
        assertFalse(chain.execute());
    }

    @Test
    void ensureExecuteStopsWhenActionFails() {
        final List<Integer> helper = new ArrayList<>();
        final ChainedAction chain = new ChainedAction(() -> helper.add(1), () -> helper.add(2))
                .then(() -> helper.add(3)).then(Actions.FAIL).then(() -> helper.add(5));
        chain.execute();

        assertEquals(3, helper.size());
    }
}
