package eapli.framework.util.function;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import eapli.framework.util.Preconditions;

/**
 * A simple approach to the functional Monad Either
 *
 * objects of this class can represent one value or either T or U, useful for
 * error handling where, by convention, the left type is the correct response
 * and the right type is the error response
 *
 * @author pgsou_000
 *
 * @param <T>
 * @param <U>
 */
public final class Either<T, U> {

    private enum SideType {
        LEFT, RIGHT
    };

    private T left;
    private final SideType type;
    private U right;

    /**
     * factory method to create a left value Either
     *
     * @param left
     * @return
     */
    public static <T, U> Either<T, U> ofLeft(final T left) {
        return new Either<>(left, true);
    }

    /**
     * factory method to create a right value Either
     *
     * @param left
     * @return
     */
    public static <T, U> Either<T, U> ofRight(final U right) {
        return new Either<>(right);
    }

    /**
     *
     * @param left
     * @param marker
     *            just to make the compiler happy due to the erasure of both
     *            constructors
     */
    @SuppressWarnings("squid:S1172")
    private Either(final T left, final boolean marker) {
        Preconditions.nonNull(left);

        type = SideType.LEFT;
        this.left = left;
    }

    private Either(final U right) {
        Preconditions.nonNull(right);

        type = SideType.RIGHT;
        this.right = right;
    }

    /**
     * applies a function to the left value if this Either instance holds a left
     * value
     *
     * @param func
     */
    public void projectLeft(final Consumer<T> func) {
        if (type == SideType.LEFT) {
            func.accept(left);
        }
    }

    /**
     * applies a function to the right value if this Either instance holds a
     * right value
     *
     * @param func
     */
    public void projectRight(final Consumer<U> func) {
        if (type == SideType.RIGHT) {
            func.accept(right);
        }
    }

    /**
     * applies the "normal" function to the left value if this Either instance
     * holds a left value, otherwise applies the "error" function to the right
     * value. since this method returns a new Either it is suitable for
     * composition.
     *
     * @param normal
     * @param error
     */
    public Either<T, U> project(final UnaryOperator<T> normal, final UnaryOperator<U> error) {
        if (type == SideType.LEFT) {
            return ofLeft(normal.apply(left));
        }
        return ofRight(error.apply(right));
    }

    /**
     * applies the "normal" function to the left value if this Either instance
     * holds a left value, otherwise applies the "error" function to the right
     * value
     *
     * @param normal
     * @param error
     */
    public void project(final Consumer<T> normal, final Consumer<U> error) {
        if (type == SideType.LEFT) {
            normal.accept(left);
        } else {
            error.accept(right);
        }
    }
}
