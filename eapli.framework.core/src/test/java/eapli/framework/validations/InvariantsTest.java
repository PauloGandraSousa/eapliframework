package eapli.framework.validations;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class InvariantsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAreEqualLong() {
        Invariants.areEqual(42, 42);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualLongFail() {
        Invariants.areEqual(42, 24);
    }

    @Test
    public void testAreEqualObject() {
        Invariants.areEqual("abc", "abc", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFail() {
        Invariants.areEqual("abc", "xpto", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn1Null() {
        Invariants.areEqual(null, "xpto", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn2Null() {
        Invariants.areEqual("abc", null, "message");
    }

    @Test
    public void testMatches() {
        fail("Not yet implemented");
    }

    @Test(expected = IllegalStateException.class)
    public void testMatchesFail() {
        fail("Not yet implemented");
    }

    @Test
    public void testEnsureBoolean() {
        Invariants.ensure(true);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanFail() {
        Invariants.ensure(false);
    }

    @Test
    public void testEnsureBooleanSupplier() {
        Invariants.ensure(() -> true);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanSupplierFail() {
        Invariants.ensure(() -> false);
    }

    @Test
    public void testEnsureBooleanSupplierString() {
        Invariants.ensure(() -> true, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanSupplierStringFail() {
        Invariants.ensure(() -> false, "message");
    }

    @Test
    public void testEnsureBooleanString() {
        Invariants.ensure(true, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureBooleanStringFail() {
        Invariants.ensure(false, "message");
    }

    @Test
    public void testNonNullObject() {
        Invariants.nonNull("abc");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectFail() {
        final String x = null;
        Invariants.nonNull(x);
    }

    @Test
    public void testNonNullObjectString() {
        Invariants.nonNull("abc", "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectStringFail() {
        final String x = null;
        Invariants.nonNull(x, "message");
    }

    @Test
    public void testNonNullObjectArray() {
        Invariants.nonNull("abc", Integer.valueOf(42), BigDecimal.ONE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullObjectArrayFail() {
        Invariants.nonNull("abc", Integer.valueOf(42), null, BigDecimal.ONE);
    }

    @Test
    public void testNonEmptyCollection() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Invariants.nonEmpty(items);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionFailOnNull() {
        final Collection<String> items = null;
        Invariants.nonEmpty(items);
    }

    @Test
    public void testNonEmptyCollectionString() {
        final Collection<String> items = new ArrayList<>();
        items.add("abc");
        Invariants.nonEmpty(items, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionStringFailOnEmpty() {
        final Collection<String> items = new ArrayList<>();
        Invariants.nonEmpty(items, "message");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionStringFailOnNull() {
        final Collection<String> items = null;
        Invariants.nonEmpty(items, "message");
    }
    /*
     * @Test public void testNonEmptyString() { fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testNonEmptyStringFail() { fail("Not yet implemented"); }
     * 
     * @Test public void testNonEmptyStringString() {
     * fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testNonEmptyStringStringFail() { fail("Not yet implemented"); }
     * 
     * @Test public void testIsPositiveLong() { fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testIsPositiveLongFail() { fail("Not yet implemented"); }
     * 
     * @Test public void testIsPositiveLongString() {
     * fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testIsPositiveLongStringFail() { fail("Not yet implemented"); }
     * 
     * @Test public void testNonNegativeLong() { fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testNonNegativeLongFail() { fail("Not yet implemented"); }
     * 
     * @Test public void testNonNegativeLongString() {
     * fail("Not yet implemented"); }
     * 
     * @Test(expected = IllegalStateException.class) public void
     * testNonNegativeLongStringFail() { fail("Not yet implemented"); }
     */
}
