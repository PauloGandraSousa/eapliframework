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
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *
 * @author Paulo Gandra de Sousa
 *
 */
class ActionsTest {

    @Test
    void testSucess() {
        assertTrue(Actions.SUCCESS.execute());
    }

    @Test
    void testFail() {
        assertFalse(Actions.FAIL.execute());
    }

    @Test
    void testThrowState() {
        assertThrows(IllegalStateException.class, () -> Actions.THROW_STATE.execute());
    }

    @Test
    void testThrowStateWithMsg() {
        final Action subject = Actions.throwState("Test");
        assertThrows(IllegalStateException.class, () -> subject.execute());
    }

    @Test
    void testThrowStateWithMsgHasTheRightMessage() {
        try {
            Actions.throwState("Test").execute();
        } catch (final IllegalStateException e) {
            assertEquals("Test", e.getMessage());
            return;
        }
        fail("somethign terrible just happened...");
    }

    @Test
    void testThrowArgument() {
        assertThrows(IllegalArgumentException.class, () -> Actions.THROW_ARGUMENT.execute());
    }

    @Test
    void testThrowArgumentWithMsg() {
        final Action subject = Actions.throwArgument("Test");
        assertThrows(IllegalArgumentException.class, () -> subject.execute());
    }

    @Test
    void testThrowArgumentWithMsgHasTheRightMessage() {
        try {
            Actions.throwArgument("Test").execute();
        } catch (final IllegalArgumentException e) {
            assertEquals("Test", e.getMessage());
            return;
        }
        fail("somethign terrible just happened...");
    }

    @Test
    void ensureDoIf() {
        final AdderHelper helper = new AdderHelper();
        Actions.doIf(helper.adder(), () -> true);
        assertEquals(1, helper.current());
    }

    @Test
    void ensureDoIfDoesnotExecuteIfFalse() {
        final AdderHelper helper = new AdderHelper();
        Actions.doIf(helper.adder(), () -> false);
        assertEquals(0, helper.current());
    }

    @Test
    void ensureDoIfNot() {
        final AdderHelper helper = new AdderHelper();
        Actions.doIfNot(helper.adder(), () -> false);
        assertEquals(1, helper.current());
    }

    @Test
    void ensureDoIfNotDoesnotExecuteIfTrue() {
        final AdderHelper helper = new AdderHelper();
        Actions.doIfNot(helper.adder(), () -> true);
        assertEquals(0, helper.current());
    }

    @Test
    void ensureRepeat10Times() {
        final AdderHelper helper = new AdderHelper();
        Actions.repeat(helper.adder(), 10);
        assertEquals(10, helper.current());
    }

    @Test
    void ensureRepeat0Times() {
        final AdderHelper helper = new AdderHelper();
        Actions.repeat(helper.adder(), 0);
        assertEquals(0, helper.current());
    }

    @Test
    void ensureRepeatMinus1Times() {
        final AdderHelper helper = new AdderHelper();
        Actions.repeat(helper.adder(), -1);
        assertEquals(0, helper.current());
    }

    @Test
    void ensureRetryWorksIfActionSucceeds() {
        assertTrue(Actions.retry(Actions.SUCCESS, 10, 20));
    }

    @Test
    void ensureRetryWorksAtFirst() {
        final AdderHelper helper = new AdderHelper();
        final boolean result = Actions.retry(helper.adder(), 10, 20);
        assertTrue(result && helper.current() == 1);
    }

    @Test
    void ensureRetryFailsIfActionFails() {
        assertFalse(Actions.retry(Actions.FAIL, 10, 3));
    }

    @Test
    void ensureRetryRunsNTimesIfActionFails() {
        final AdderHelper helper = new AdderHelper();
        final Action add1AndFail = () -> {
            helper.add1();
            // returning false to force the retry
            return false;
        };
        final boolean result = Actions.retry(add1AndFail, 10, 3);
        assertTrue(!result && helper.current() == 3);
    }
}
