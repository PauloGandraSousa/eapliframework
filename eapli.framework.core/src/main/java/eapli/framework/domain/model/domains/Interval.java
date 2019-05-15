/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.domain.model.domains;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Invariants;

/**
 * A generic immutable interval class, i.e., a continuous domain.
 *
 * See <a href="https://martinfowler.com/eaaDev/Range.html">Martin Fowler's Range pattern</a> for a
 * general discussion
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 */
@Embeddable
public class Interval<T extends Serializable & Comparable<T>> implements ValueObject, Comparable<Interval<T>> {

    private static final long serialVersionUID = 1L;

    protected enum BoundaryLimitType {
        INFINITY, OPEN, CLOSED;

        protected BoundaryLimitType negate() {
            if (this.equals(OPEN)) {
                return CLOSED;
            } else if (this.equals(CLOSED)) {
                return OPEN;
            } else {
                return INFINITY;
            }
        }
    }

    private final T start;
    private final T end;
    private final BoundaryLimitType startBoundary;
    private final BoundaryLimitType endBoundary;

    /**
     * The builder of intervals
     *
     * @author Paulo Gandra Sousa
     *
     * @param <R>
     */
    public static class IntervalBuilder<R extends Comparable<R> & Serializable> {
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
        private IntervalBuilder(final R start, final BoundaryLimitType startBoundary) {
            assert (startBoundary == BoundaryLimitType.INFINITY && start == null)
                    || (startBoundary != BoundaryLimitType.INFINITY && start != null);
            this.start = start;
            this.startBoundary = startBoundary;
        }

        public Interval<R> closedTo(final R anchor) {
            this.end = anchor;
            this.endBoundary = BoundaryLimitType.CLOSED;
            return build();
        }

        public Interval<R> openTo(final R anchor) {
            this.end = anchor;
            this.endBoundary = BoundaryLimitType.OPEN;
            return build();
        }

        public Interval<R> toInfinity() {
            this.end = null;
            this.endBoundary = BoundaryLimitType.INFINITY;
            return build();
        }

        private Interval<R> build() {
            return new Interval<>(this.start, this.startBoundary, this.end, this.endBoundary);
        }
    }

    protected Interval() {
        // for ORM
        start = end = null;
        endBoundary = startBoundary = null;
    }

    /**
     * special private constructor for empty intervals
     *
     * @param b
     *          marker parameter to enable the compiler to distinguish the constructors
     */
    @SuppressWarnings("squid:S1172")
    private Interval(final boolean b) {
        start = end = null;
        endBoundary = startBoundary = null;
    }

    /**
     * constructs an interval.
     *
     * @param start
     *                      anchor start of the interval or null to represent infinity
     * @param end
     *                      anchor end of the interval or null to represent infinity
     * @param startBoundary
     *                      indicates if the interval is open or closed at the start anchor
     * @param endBoundary
     *                      indicates if the interval is open or closed at the end anchor
     */
    protected Interval(final T start, final BoundaryLimitType startBoundary, final T end,
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
     * A factory method for intervals that start at "negative infinity"
     *
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> fromInfinity() {
        return new IntervalBuilder<>(null, BoundaryLimitType.INFINITY);
    }

    /**
     * A factory method for closed intervals that start at a specific anchor point
     *
     * @param start
     *
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> closedFrom(final U start) {
        return new IntervalBuilder<>(start, BoundaryLimitType.CLOSED);
    }

    /**
     * A factory method for open intervals that start at a specific anchor point
     *
     * @param start
     *
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> openFrom(final U start) {
        return new IntervalBuilder<>(start, BoundaryLimitType.OPEN);
    }

    @SuppressWarnings("rawtypes")
    private static final Interval EMPTY = new Interval(true);

    /**
     * returns an empty interval, i.e., []
     *
     * @return an empty interval
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T> & Serializable> Interval<T> empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return start == end && start == null && endBoundary == startBoundary && endBoundary == null;
    }

    /**
     * returns an universal interval, i.e., ]-oo, +oo[
     *
     * @return an universal interval
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T> & Serializable> Interval<T> universal() {
        final Interval.IntervalBuilder<T> builder = fromInfinity();
        return builder.toInfinity();
    }

    public boolean isUniversal() {
        return endBoundary == startBoundary && endBoundary == BoundaryLimitType.INFINITY;
    }

    /**
     * checks if a value belongs in this interval
     *
     * @param target
     * @return {@code true} if a value belongs in this interval
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
        Invariants.ensure(!isEmpty());

        return this.start;
    }

    public T end() {
        Invariants.ensure(!isEmpty());

        return this.end;
    }

    /**
     * checks if this interval intersects another interval
     *
     * @param other
     * @return {@code true} if this interval intersects another interval
     */
    public boolean intersects(final Interval<T> other) {
        return !this.intersection(other).equals(empty());
    }

    /**
     * returns the interval consisting of the intersection of this interval and another one
     *
     * @param other
     * @return the interval consisting of the intersection of this interval and another one
     */
    public Interval<T> intersection(final Interval<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    /**
     * returns a new interval by extending the start of this one. the openness/closeness of the start
     * boundary as well as the end will be the same of the original interval
     *
     * @param newStart
     * @return a new interval by extending the start of this one
     */
    public Interval<T> withStart(final T newStart) {
        return new Interval<>(newStart, this.startBoundary, this.end, this.endBoundary);
    }

    /**
     * returns a new interval by extending the end of this one. the openness/closeness of the end
     * boundary as well as the start will be the same of the original interval
     *
     * @param newEnd
     * @return a new interval by extending the end of this one
     */
    public Interval<T> withEnd(final T newEnd) {
        return new Interval<>(this.start, this.startBoundary, newEnd, this.endBoundary);
    }

    /**
     * checks if this interval overlaps another one, that is, if this interval fully contains the other
     * one.
     *
     * for instance
     *
     * <code>
     * A = [2..5]
     * B = [0..10]
     * C = ]2..4]
     * D = [8..10]
     *
     * A.overlaps(C) == true
     * A.overlaps(B) == false
     * B.overlaps(A) == true
     * C.overlaps(D) == false
     * D.overlaps(C) == false
     * B.overlaps(D) == true
     * </code>
     *
     * @param other
     * @return {@code true} if this interval overlaps another one
     */
    public boolean overlaps(final Interval<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    public boolean hasOpenEnd() {
        return this.endBoundary == BoundaryLimitType.OPEN || endBoundary == BoundaryLimitType.INFINITY;
    }

    public boolean hasOpenStart() {
        return this.startBoundary == BoundaryLimitType.OPEN || startBoundary == BoundaryLimitType.INFINITY;
    }

    /**
     * find out what the gap between two ranges
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @param arg
     * @return a new interval representing the gap between this and another range
     */
    @SuppressWarnings("unchecked")
    public Interval<T> gap(final Interval<T> arg) {
        if (this.overlaps(arg)) {
            return Interval.EMPTY;
        }
        Interval<T> lower;
        Interval<T> higher;
        if (this.compareTo(arg) < 0) {
            lower = this;
            higher = arg;
        } else {
            lower = arg;
            higher = this;
        }
        return new Interval<>(lower.end, lower.endBoundary.negate(), higher.start, higher.startBoundary.negate());
    }

    /**
     * Compares to intervals to determine the natural order. Assumes an interval is "first" than another
     * if it starts at a point which is "first" than the start of the other interval.
     *
     * the empty interval cannot be represented on a line and as such it doesn't make much sense to
     * order it, however, we need to handle that special case and throwing an exception does not solve
     * the problem if we happen to have an empty interval as part of a collection and we want to sort
     * those intervals. we assume the empty interval, i.e., [], is always first to any other interval
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @TODO take into account the open/close of each end of the interval
     * @param arg
     * @return
     */
    @Override
    public int compareTo(final Interval<T> other) {
        // if we are empty, we are either the first or the same if the other is empty
        if (isEmpty()) {
            return compareEmptyToX(other);
        }
        // we are not empty, but if the other is, then we are after
        else if (other.isEmpty()) {
            return 1;
        }

        // neither of us is empty
        if (isFromInfinity()) {
            return compareStartAtInfinityToX(other);
        } else {
            return compareStartAtXToY(other);
        }
    }

    private int compareEmptyToX(final Interval<T> other) {
        if (other.isEmpty()) {
            return 0;
        } else {
            return -1;
        }
    }

    private int compareStartAtXToY(final Interval<T> other) {
        // we don't start at infinity, does the other one start at infinity?
        if (other.isFromInfinity()) {
            // we are after
            return 1;
        } else {
            // neither starts at infinity, so let's check who starts first
            final int orderStart = start.compareTo(other.start);
            if (orderStart == 0) {
                // we start at the same "point", so it is up to the end
                // boundary
                return end.compareTo(other.end);
            } else {
                // whoever starts first, is the first
                return orderStart;
            }
        }
    }

    private int compareStartAtInfinityToX(final Interval<T> other) {
        // we start at infinity
        // if the other does not start at infinity
        if (!other.isFromInfinity()) {
            // we are first
            return -1;
        } else {
            // we both start at infinity, so it is up to the end of each
            if (isToInfinity()) {
                return compareUniversalToStartAtInfinity(other);
            } else {
                // we don't end at infinity
                return compareInfinityToXToY(other);
            }
        }
    }

    private int compareInfinityToXToY(final Interval<T> other) {
        if (other.isToInfinity()) {
            return -1;
        }
        // let's check if we end first than the other
        final int order = end.compareTo(other.end);
        if (order == 0) {
            // let's check openess/closeness
            if (endBoundary == BoundaryLimitType.OPEN) {
                if (other.endBoundary == BoundaryLimitType.OPEN) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (other.endBoundary == BoundaryLimitType.OPEN) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return order;
        }
    }

    private int compareUniversalToStartAtInfinity(final Interval<T> other) {
        if (other.isToInfinity()) {
            // we both start and end at infinity
            return 0;
        } else {
            // we end at infinity but the other does not. the other comes first
            return 1;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Interval<?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final Interval<T> other = (Interval<T>) o;
        if (isEmpty()) {
            return other.isEmpty();
        } else {
            return startIsTheSame(other) && endIsTheSame(other);
        }
    }

    private boolean startIsTheSame(final Interval<T> other) {
        // both are infinity
        final boolean bothInfinity = this.startBoundary.equals(BoundaryLimitType.INFINITY)
                && this.startBoundary.equals(other.startBoundary);
        // our start is not infinity and the other start is the same as ours
        final boolean sameStart = !this.startBoundary.equals(BoundaryLimitType.INFINITY)
                && this.start.equals(other.start);

        return bothInfinity || sameStart;
    }

    private boolean endIsTheSame(final Interval<T> other) {
        // both are infinity
        final boolean bothInfinity = this.endBoundary.equals(BoundaryLimitType.INFINITY)
                && this.endBoundary.equals(other.endBoundary);
        // our end is not infinity and the other end is the same as ours
        final boolean sameEnd = !this.endBoundary.equals(BoundaryLimitType.INFINITY) && this.end.equals(other.end);

        return bothInfinity || sameEnd;
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(startBoundary).of(start).of(endBoundary).of(end).code();
    }

    /**
     * to detect whether two ranges abut each other.
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @param arg
     * @return {@code true} if this interval abuts the other
     */
    public boolean abuts(final Interval<T> arg) {
        return !this.overlaps(arg) && this.gap(arg).isEmpty();
    }

    /**
     * see if a group of ranges completely partitions this range.
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @param args
     * @return {@code true} if the group of ranges completely partitions this range
     */
    public boolean partitionedBy(final Interval<T>[] args) {
        if (!areContiguous(args)) {
            return false;
        }
        return this.equals(combination(args));
    }

    /**
     * Combines a group of contiguous intervals into a single interval
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @param args
     * @return a new Interval spanning the the contiguous group of intervals
     */
    public static <T extends Serializable & Comparable<T>> Interval<T> combination(final Interval<T>[] args) {
        Arrays.sort(args);
        if (!areContiguous(args)) {
            throw new IllegalArgumentException("Unable to combine date ranges");
        }
        return new Interval<>(args[0].start, args[0].startBoundary, args[args.length - 1].end,
                args[args.length - 1].endBoundary);
    }

    /**
     * Checks if a group of intervals forms a contiguous interval
     *
     * based on https://martinfowler.com/eaaDev/Range.html
     *
     * @TODO need to take into account the boundary limits to see if the intervals are really contiguous
     *
     * @param args
     * @return {@code true} if a group of intervals forms a contiguous interval
     */
    public static <T extends Serializable & Comparable<T>> boolean areContiguous(final Interval<T>[] args) {
        Arrays.sort(args);
        for (int i = 0; i < args.length - 1; i++) {
            if (!args[i].abuts(args[i + 1])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(startBracket(hasOpenStart()));
        sb.append(rangeValue(this.isFromInfinity(), start));
        sb.append(", ");
        sb.append(rangeValue(this.isToInfinity(), end));
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
