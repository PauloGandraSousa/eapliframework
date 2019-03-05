package eapli.framework.domain.model.general;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DesignationTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNotNull() {
        Designation.valueOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNotEmpty() {
        Designation.valueOf("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNoHeadingWhitespace() {
        Designation.valueOf("  ABC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNoTrailingWhitespace() {
        Designation.valueOf("ABC   ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNoHeadingNorTrailingWhitespace() {
        Designation.valueOf("  ABC  ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNoWhitespaceOnly() {
        Designation.valueOf("    ");
    }

    @Test
    public void ensureStartsWithSomething() {
        Assert.assertNotNull(Designation.valueOf("ABC"));
    }
}
