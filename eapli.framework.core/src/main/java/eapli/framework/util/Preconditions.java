/**
 *
 */
package eapli.framework.util;

import static eapli.framework.util.function.Functions.THROW_ARGUMENT_WITH_MESSAGE;
import static eapli.framework.util.function.Functions.ifNotThen;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eapli.framework.actions.Action;

/**
 * utility class for validating preconditions, usually on function parameters.
 * if the precondition is not met, an IllegalArgumentException is thrown
 *
 * @author SOU03408
 *
 */
public final class Preconditions implements Utilitarian {
    private Preconditions() {
    }

    public static void ensure(final Action test) {
        ensure(test, "");
    }

    public static void ensure(final Action test, final String msg) {
        ifNotThen(test, THROW_ARGUMENT_WITH_MESSAGE, msg);
    }

    public static void nonNull(final Object arg) {
        nonNull(arg, "");
    }

    public static void nonNull(final Object arg, final String msg) {
        ifNotThen(arg, Objects::nonNull, THROW_ARGUMENT_WITH_MESSAGE, msg);
    }

    public static void nonNull(final Object... arg) {
        for (final Object each : arg) {
            nonNull(each);
        }
    }

    public static void nonEmpty(final String arg) {
        nonEmpty(arg, "");
    }

    public static void nonEmpty(final String arg, final String msg) {
        ifNotThen(arg, x -> !Strings.isNullOrEmpty(x), THROW_ARGUMENT_WITH_MESSAGE, msg);
    }

    public static void isPositive(final long arg) {
        isPositive(arg, "");
    }

    public static void isPositive(final long arg, final String msg) {
        ifNotThen(arg, Numbers::isPositive, THROW_ARGUMENT_WITH_MESSAGE, msg);
    }

    public static void nonNegative(final long arg, final String msg) {
        ifNotThen(arg, Numbers::isNonNegative, THROW_ARGUMENT_WITH_MESSAGE, msg);
    }

    public static void nonEmpty(final Set<?> arg, final String msg) {
        ensure(() -> !arg.isEmpty(), msg);
    }

    public static void ensureEquals(final int a, final int b) {
        ensure(() -> a == b);
    }

    public static void ensureEquals(final Object a, final Object b, final String msg) {
        ensure(() -> a.equals(b), msg);
    }

    public static void matches(final Pattern regex, final String arg, final String msg) {
        final Matcher matcher = regex.matcher(arg);
        if (!matcher.find()) {
            throw new IllegalArgumentException(msg);
        }
    }
}
