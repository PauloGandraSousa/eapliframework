/**
 *
 */
package eapli.framework.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author SOU03408
 *
 */
public class SampleAddActionTest {
    private SampleAdder target;
    private SampleAddAction instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        target = new SampleAdder();
        instance = new SampleAddAction(target, 5);
    }

    @Test
    public void ensureExecuteAdds() {
        instance.execute();
        assertEquals(target.current(), 5);
    }

    @Test
    public void ensureUndo() {
        instance.execute();
        instance.undo();
        assertEquals(target.current(), 0);
    }
}
