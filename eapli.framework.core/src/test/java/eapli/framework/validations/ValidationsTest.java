package eapli.framework.validations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.junit.Test;

import eapli.framework.actions.Actions;

public class ValidationsTest {

    @Test
    public void testAreEqualLongDoesNothingIfTrue() {
        Validations.areEqual(42, 42, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualLongFail() {
        Validations.areEqual(42, 24, Actions.THROW_STATE);
    }

    @Test
    public void testAreEqualObjectDoesNothingIfTrue() {
        Validations.areEqual("abc", "abc", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFail() {
        Validations.areEqual("abc", "xpto", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn1Null() {
        Validations.areEqual(null, "xpto", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testAreEqualObjectFailOn2Null() {
        Validations.areEqual("abc", null, Actions.THROW_STATE);
    }

    @Test
    public void testMatchesDoNothingIfTrue() {
        Validations.matches(Pattern.compile("A"), "bcdA234", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testMatchesDoesSomethingIfFalse() {
        Validations.matches(Pattern.compile("x"), "bcdA234", Actions.THROW_STATE);
    }

    @Test
    public void testEnsureDoesNothingIfTrue() {
        Validations.ensure(() -> true, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testEnsureDoesSomethingIfFalse() {
        Validations.ensure(() -> false, Actions.THROW_STATE);
    }

    @Test
    public void testNonNullDoesNothingIfTrue() {
        Validations.nonNull(Actions.THROW_STATE, "abc");
    }

    @Test
    public void testNonNullDoesNothingIfTrueForMoreThanOneObject() {
        Validations.nonNull(Actions.THROW_STATE, new String(), Double.valueOf(1), "abc");
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullDoesSomethingIfFalse() {
        final Object x = null;
        Validations.nonNull(Actions.THROW_STATE, x);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNullDoesSomethingIfFalseForAtLeastOneObject() {
        final Object x = null;
        Validations.nonNull(Actions.THROW_STATE, "abc", x, Integer.valueOf(1));
    }

    @Test
    public void testNonEmptyCollectionDoesNothingIfTrue() {
        final Collection<String> col = new ArrayList<>();
        col.add("abc");
        Validations.nonEmpty(col, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionDoesSomethingIfNull() {
        final Collection<String> col = null;
        Validations.nonEmpty(col, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyCollectionDoesSomethingIfEmpty() {
        final Collection<String> col = new ArrayList<>();
        Validations.nonEmpty(col, Actions.THROW_STATE);
    }

    @Test
    public void testNonEmptyStringDoesNothingIfTrue() {
        Validations.nonEmpty("abc", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfNull() {
        final String x = null;
        Validations.nonEmpty(x, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfEmpty() {
        Validations.nonEmpty("", Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonEmptyStringDoesSomethingIfEmpty2() {
        Validations.nonEmpty("  ", Actions.THROW_STATE);
    }

    @Test
    public void testIsPositiveDoesNothingIfTrue() {
        Validations.isPositive(42, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveDoesSomethingIfZero() {
        Validations.isPositive(0, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testIsPositiveDoesSomethingIfNegative() {
        Validations.isPositive(-10, Actions.THROW_STATE);
    }

    @Test
    public void testNonNegativeDoesNothingIfPositive() {
        Validations.nonNegative(42, Actions.THROW_STATE);
    }

    @Test
    public void testNonNegativeDoesNothingIfZero() {
        Validations.nonNegative(0, Actions.THROW_STATE);
    }

    @Test(expected = IllegalStateException.class)
    public void testNonNegativeDoesSomethingIfNegative() {
        Validations.nonNegative(-10, Actions.THROW_STATE);
    }
}
