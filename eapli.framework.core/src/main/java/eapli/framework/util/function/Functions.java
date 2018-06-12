/**
 *
 */
package eapli.framework.util.function;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import eapli.framework.actions.Action;
import eapli.framework.actions.TimedActions;
import eapli.framework.util.Utilitarian;

/**
 * utility class
 *
 * @author sou03408
 *
 */
public final class Functions implements Utilitarian {

    private Functions() {
        // ensure utility
    }

    public static final Action THROW_ARGUMENT = () -> {
        throw new IllegalArgumentException();
    };

    public static final Action THROW_STATE = () -> {
        throw new IllegalStateException();
    };

    public static final Consumer<String> THROW_ARGUMENT_WITH_MESSAGE = m -> {
        throw new IllegalArgumentException(m);
    };

    public static final Consumer<String> THROW_STATE_WITH_MESSAGE = m -> {
        throw new IllegalStateException(m);
    };

    /**
     * executes the predicate on the parameter arg. if the predicate is not met, the
     * action is executed
     *
     * @param arg
     * @param validator
     *            the invariant to validate arg
     * @param onFail
     * @return
     */
    public static <T> void ensure(final T arg, final Predicate<T> validator, final Action onFail) {
        if (!validator.test(arg)) {
            onFail.execute();
        }
    }

    /**
     * executes the predicate on the parameter arg. if the predicate is not met, the
     * action is executed passing the parameter message
     *
     * @param arg
     * @param validator
     * @param onFail
     * @param msg
     */
    public static <T> void ifNotThen(final T arg, final Predicate<T> validator, final Consumer<String> onFail,
            final String msg) {
        if (!validator.test(arg)) {
            onFail.accept(msg);
        }
    }

    public static void ifNotThen(final Action test, final Action onFail) {
        if (!test.execute()) {
            onFail.execute();
        }
    }

    public static void ifNotThen(final Action test, final Consumer<String> onFail, final String msg) {
        if (!test.execute()) {
            onFail.accept(msg);
        }
    }

    //
    // ========================================================================
    //

    /**
     * retries an operation with a given interval time between invocations up to a
     * certain number of attempts. the retry stops if the operation returned
     * optional contains a value or the maximum number of attempts is reached.
     *
     * @param op
     * @param sleep
     * @param maxAttempts
     * @return
     */
    public static <T> Optional<T> retry(final OptionalSupplier<T> op, final int sleep, final int maxAttempts) {
        int atempts = 1;
        Optional<T> u = op.get();
        while (!u.isPresent() && atempts <= maxAttempts) {
            atempts++;
            TimedActions.delay(sleep, atempts);
            u = op.get();
        }
        return u;
    }

    //
    // ========================================================================
    //

    /**
     * zips two streams
     *
     * extracted from
     * https://stackoverflow.com/questions/17640754/zipping-streams-using-jdk8-with-lambda-java-util-stream-streams-zip/32342172
     *
     * @param a
     * @param b
     * @param zipper
     * @return
     */
    public static <A, B, C> Stream<C> zip(final Stream<? extends A> a, final Stream<? extends B> b,
            final BiFunction<? super A, ? super B, ? extends C> zipper) {
        Objects.requireNonNull(zipper);
        final Spliterator<? extends A> aSpliterator = Objects.requireNonNull(a).spliterator();
        final Spliterator<? extends B> bSpliterator = Objects.requireNonNull(b).spliterator();

        // Zipping looses DISTINCT and SORTED characteristics
        final int characteristics = aSpliterator.characteristics() & bSpliterator.characteristics()
                & ~(Spliterator.DISTINCT | Spliterator.SORTED);

        final long zipSize = ((characteristics & Spliterator.SIZED) != 0)
                ? Math.min(aSpliterator.getExactSizeIfKnown(), bSpliterator.getExactSizeIfKnown())
                : -1;

        final Iterator<A> aIterator = Spliterators.iterator(aSpliterator);
        final Iterator<B> bIterator = Spliterators.iterator(bSpliterator);
        final Iterator<C> cIterator = new Iterator<C>() {
            @Override
            public boolean hasNext() {
                return aIterator.hasNext() && bIterator.hasNext();
            }

            @Override
            public C next() {
                return zipper.apply(aIterator.next(), bIterator.next());
            }
        };

        final Spliterator<C> split = Spliterators.spliterator(cIterator, zipSize, characteristics);
        return (a.isParallel() || b.isParallel()) ? StreamSupport.stream(split, true)
                : StreamSupport.stream(split, false);
    }
}
