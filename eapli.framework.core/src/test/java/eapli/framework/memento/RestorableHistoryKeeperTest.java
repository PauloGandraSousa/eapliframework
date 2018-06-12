/**
 *
 */
package eapli.framework.memento;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class RestorableHistoryKeeperTest {

    private SampleAdder subject;
    private RestorableHistoryKeeper<SampleAdder> instance;

    @Before
    public void setUp() {
        subject = new SampleAdder();
        instance = new RestorableHistoryKeeper<SampleAdder>(subject);
    }

    public void testSavepointRestore2() {
        final int NRUNS = 3;
        // create savepoints
        int expected = 0;
        for (int i = 1; i <= NRUNS; i++) {
            subject.add(i);
            instance.savepoint();

            expected += i;
        }

        // go back to savepoints
        for (int j = NRUNS; j >= 1; j--) {
            final int curr = subject.current();
            if (curr != expected) {
                fail("wrong state @ " + j + ". expected " + expected + " but got " + curr);
            }
            instance.restore();
            expected -= j;
        }
    }

    @Test
    public void testSavepointRestore() {
        // create savepoints
        subject.add(2);
        assert subject.current() == 2;
        instance.savepoint();

        subject.add(4);
        assert subject.current() == 6;
        instance.savepoint();

        subject.add(8);
        assert subject.current() == 14;

        // go back to savepoints
        instance.restore();
        int expected = 6;
        int curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        instance.restore();
        expected = 2;
        curr = subject.current();
        if (curr != expected) {
            fail("expected " + expected + " but got " + curr);
        }

        assertFalse(instance.canRestore());
    }

    /**
     * Test method for
     * {@link eapli.framework.memento.RestorableHistoryKeeper#canRestore()}.
     */
    @Test
    public void ensureCanotRestoreIfThereWereNoSavepoints() {
        assertFalse(instance.canRestore());
    }

    /**
     * Test method for
     * {@link eapli.framework.memento.RestorableHistoryKeeper#canRestore()}.
     */
    @Test
    public void ensureCanRestoreIfThereWereSavepoints() {
        instance.savepoint();
        assertTrue(instance.canRestore());
    }
}
