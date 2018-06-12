/**
 *
 */
package eapli.framework.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author SOU03408
 *
 */
public class ActionHistoryKeeperTest {

    private SampleAdder subject;
    private ActionHistoryKeeper instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        subject = new SampleAdder();
        instance = new ActionHistoryKeeper();
    }

    @Test
    public void ensureCannotUndoIfThereWereNoActions() {
        assertFalse(instance.canUndo());
    }

    @Test
    public void ensureCanUndoIfThereWereActions() {
        final SampleAddAction action = new SampleAddAction(subject, 5);
        instance.execute(action);
        assertTrue(instance.canUndo());
    }

    @Test
    public void ensureCannotRedoIfThereWereNoUndos() {
        assertFalse(instance.canRedo());
    }

    @Test
    public void ensureCanRedoIfThereWereUndos() {
        final SampleAddAction action = new SampleAddAction(subject, 5);
        instance.execute(action);
        instance.undo();
        assertTrue(instance.canRedo());
    }

    @Test
    public void testDoUndo() {
        // DO
        SampleAddAction action = new SampleAddAction(subject, 2);
        instance.execute(action);
        assert subject.current() == 2;

        action = new SampleAddAction(subject, 4);
        instance.execute(action);
        assert subject.current() == 6;

        action = new SampleAddAction(subject, 8);
        instance.execute(action);
        assert subject.current() == 14;

        // UNDO
        instance.undo();
        int expected = 6;
        int curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.undo();
        expected = 2;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.undo();
        expected = 0;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        assertFalse(instance.canUndo());
    }

}
