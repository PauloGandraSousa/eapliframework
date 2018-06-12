/**
 *
 */
package eapli.framework.util;

import static eapli.framework.util.function.Functions.THROW_STATE_WITH_MESSAGE;
import static eapli.framework.util.function.Functions.ifNotThen;

import java.util.List;
import java.util.Objects;

import eapli.framework.actions.Action;

/**
 * @author SOU03408
 *
 */
public final class Invariants implements Utilitarian {
    private Invariants() {
    }

    public static void ensure(final Action test) {
        ensure(test, "");
    }

    public static void ensure(final Action test, final String msg) {
        ifNotThen(test, THROW_STATE_WITH_MESSAGE, msg);
    }

    public static void nonNull(final Object arg) {
        nonNull(arg, "");
    }

    public static void nonNull(final Object arg, final String msg) {
        ifNotThen(arg, Objects::nonNull, THROW_STATE_WITH_MESSAGE, msg);
    }

    public static void nonEmpty(final String arg, final String msg) {
        ifNotThen(arg, x -> !Strings.isNullOrEmpty(x), THROW_STATE_WITH_MESSAGE, msg);
    }

    public static void isPositive(final long arg, final String msg) {
        ifNotThen(arg, Numbers::isPositive, THROW_STATE_WITH_MESSAGE, msg);
    }

    public static void nonNegative(final long arg, final String msg) {
        ifNotThen(arg, Numbers::isNonNegative, THROW_STATE_WITH_MESSAGE, msg);
    }

    public static void nonEmpty(final List<?> arg) {
        ensure(() -> !arg.isEmpty());
    }
}
