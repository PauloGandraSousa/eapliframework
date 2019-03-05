/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.model.range;

import java.io.Serializable;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.Factory;
import eapli.framework.util.Invariants;

/**
 * a generic immutable range class, i.e., a continuous domain.
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 */
@Embeddable
public class Range<T extends Comparable<T> & Serializable> implements ValueObject {

    private static final long serialVersionUID = 1L;

    protected enum BoundaryLimitType {
        INFINITY, OPEN, CLOSED
    }

    private final T start;
    private final T end;
    private final BoundaryLimitType startBoundary;
    private final BoundaryLimitType endBoundary;

    /**
     * The builder of ranges
     *
     * @author Paulo Gandra Sousa
     *
     * @param <R>
     */
    public static class RangeBuilder<R extends Comparable<R> & Serializable> implements Factory<Range<R>> {
        private final R start;
        private R end;
        private final BoundaryLimitType startBoundary;
        private BoundaryLimitType endBoundary;

        /**
         * starts building a range at start
         *
         * @param start
         * @param startBoundary
         */
        private RangeBuilder(final R start, final BoundaryLimitType startBoundary) {
            assert (startBoundary == BoundaryLimitType.INFINITY && start == null)
                    || (startBoundary != BoundaryLimitType.INFINITY && start != null);
            this.start = start;
            this.startBoundary = startBoundary;
        }

        public RangeBuilder<R> closedTo(final R anchor) {
            this.end = anchor;
            this.endBoundary = BoundaryLimitType.CLOSED;
            return this;
        }

        public RangeBuilder<R> openTo(final R anchor) {
            this.end = anchor;
            this.endBoundary = BoundaryLimitType.OPEN;
            return this;
        }

        public RangeBuilder<R> toInfinity() {
            this.end = null;
            this.endBoundary = BoundaryLimitType.INFINITY;
            return this;
        }

        @Override
        public Range<R> build() {
            return new Range<>(this.start, this.startBoundary, this.end, this.endBoundary);
        }
    }

    protected Range() {
        // for ORM
        start = end = null;
        endBoundary = startBoundary = null;
    }

    /**
     * special private constructor for empty ranges
     *
     * @param b
     *            marker parameter to enable the compiler to distinguish the
     *            constructors
     */
    @SuppressWarnings("squid:S1172")
    private Range(final boolean b) {
        start = end = null;
        endBoundary = startBoundary = null;
    }

    /**
     * constructs a range.
     *
     * @param start
     *            anchor start of the range or null to represent infinity
     * @param end
     *            anchor end of the range or null to represent infinity
     * @param startBoundary
     *            indicates if the range is open or closed at the start anchor
     * @param endBoundary
     *            indicates if the range is open or closed at the end anchor
     */
    protected Range(final T start, final BoundaryLimitType startBoundary, final T end,
            final BoundaryLimitType endBoundary) {
        if ((start == null && startBoundary != BoundaryLimitType.INFINITY)
                || (end == null && endBoundary != BoundaryLimitType.INFINITY)) {
            throw new IllegalArgumentException("start or end must be non-null");
        }

        if (end != null && start != null && end.compareTo(start) < 0) {
            throw new IllegalArgumentException("The end value of a range must be bigger than its start");
        }
        if (end != null && start != null && end.compareTo(start) == 0
                && (startBoundary == BoundaryLimitType.OPEN || endBoundary == BoundaryLimitType.OPEN)) {
            throw new IllegalArgumentException("An empty range is not allowed");
        }

        this.start = start;
        this.end = end;
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
    }

    /**
     * A factory method for ranges that start at "negative infinity"
     *
     * @return a builder
     */
    public static <T extends Comparable<T> & Serializable> RangeBuilder<T> fromInfinity() {
        return new RangeBuilder<>(null, BoundaryLimitType.INFINITY);
    }

    /**
     * A factory method for closed ranges that start at a specific anchor point
     *
     * @return a builder
     */
    public static <T extends Comparable<T> & Serializable> RangeBuilder<T> closedFrom(final T start) {
        return new RangeBuilder<>(start, BoundaryLimitType.CLOSED);
    }

    /**
     * A factory method for open ranges that start at a specific anchor point
     *
     * @return a builder
     */
    public static <T extends Comparable<T> & Serializable> RangeBuilder<T> openFrom(final T start) {
        return new RangeBuilder<>(start, BoundaryLimitType.OPEN);
    }

    @SuppressWarnings("rawtypes")
    private static final Range EMPTY = new Range(true);

    /**
     * returns an empty range
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T> & Serializable> Range<T> empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return start == end && start == null && endBoundary == startBoundary && endBoundary == null;
    }

    private boolean notEmpty() {
        return !isEmpty();
    }

    /**
     * checks if a value belongs in this range
     *
     * @param target
     * @return
     */
    public boolean includes(final T target) {
        if (isEmpty()) {
            return false;
        } else if (this.startBoundary != BoundaryLimitType.INFINITY && this.endBoundary != BoundaryLimitType.INFINITY) {
            return includesInFiniteRanges(target);
        } else if (this.endBoundary == BoundaryLimitType.INFINITY) {
            return includesInToInfinityRanges(target);
        } else {
            assert this.startBoundary == BoundaryLimitType.INFINITY;
            return includesInFromInfinityRanges(target);
        }
    }

    private boolean includesInFromInfinityRanges(final T target) {
        if (target.compareTo(this.end) > 0) {
            return false;
        }
        return !(hasOpenEnd() && target.compareTo(this.end) == 0);
    }

    private boolean includesInToInfinityRanges(final T target) {
        if (target.compareTo(this.start) < 0) {
            return false;
        }
        return !(hasOpenStart() && target.compareTo(this.start) == 0);
    }

    private boolean includesInFiniteRanges(final T target) {
        if (target.compareTo(this.start) < 0 || target.compareTo(this.end) > 0) {
            return false;
        }
        if (hasOpenStart() && target.compareTo(this.start) == 0) {
            return false;
        }
        return !(hasOpenEnd() && target.compareTo(this.end) == 0);
    }

    public boolean isToInfinity() {
        return this.endBoundary == BoundaryLimitType.INFINITY;
    }

    public boolean isFromInfinity() {
        return this.startBoundary == BoundaryLimitType.INFINITY;
    }

    public T start() {
        Invariants.ensure(this::notEmpty);

        return this.start;
    }

    public T end() {
        Invariants.ensure(this::notEmpty);

        return this.end;
    }

    /**
     * checks if this range intersects another range
     *
     * @param other
     * @return
     */
    public boolean intersects(final Range<T> other) {
        return !this.intersection(other).equals(empty());
    }

    /**
     * returns the range consisting of the intersection of this range and
     * another one
     *
     * @param other
     * @return
     */
    public Range<T> intersection(final Range<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    /**
     * returns a new range by extending the start of this one. the
     * openness/closeness of the start boundary as well as the end will be the
     * same of the original range
     *
     * @param other
     * @return
     */
    public Range<T> withStart(final T newStart) {
        return new Range<>(newStart, this.startBoundary, this.end, this.endBoundary);
    }

    /**
     * returns a new range by extending the end of this one. the
     * openness/closeness of the end boundary as well as the start will be the
     * same of the original range
     *
     * @param other
     * @return
     */
    public Range<T> withEnd(final T newEnd) {
        return new Range<>(this.start, this.startBoundary, newEnd, this.endBoundary);
    }

    /**
     * checks if this range overlaps another one, that is, if this range fully
     * contains the other one.
     *
     * for instance
     *
     * A = [2..5]
     *
     * B = [0..10]
     *
     * C = ]2..4]
     *
     * D = [8..10]
     *
     * A.overlaps(C) == true
     *
     * A.overlaps(B) == false
     *
     * B.overlaps(A) == true
     *
     * C.overlaps(D) == false
     *
     * D.overlaps(C) == false
     *
     * B.overlaps(D) == true
     *
     * @param other
     * @return
     */
    public boolean overlaps(final Range<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    public boolean hasOpenEnd() {
        return this.endBoundary == BoundaryLimitType.OPEN;
    }

    public boolean hasOpenStart() {
        return this.startBoundary == BoundaryLimitType.OPEN;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(startBracket(hasOpenStart()));
        sb.append(rangeValue(this.isFromInfinity(), this.start));
        sb.append(", ");
        sb.append(rangeValue(this.isToInfinity(), this.end));
        sb.append(endBracket(hasOpenEnd()));
        return sb.toString();
    }

    private char startBracket(final boolean isOpen) {
        if (isOpen) {
            return ']';
        } else {
            return '[';
        }
    }

    private char endBracket(final boolean isOpen) {
        if (isOpen) {
            return '[';
        } else {
            return ']';
        }
    }

    private String rangeValue(final boolean isInfinity, final T value) {
        if (isInfinity) {
            return "oo";
        } else {
            return value.toString();
        }
    }
}
