/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
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
package eapli.framework.domains.domain.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Invariants;

/**
 * A generic immutable interval class, i.e., a continuous domain.
 *
 * <p>
 * See <a href="https://martinfowler.com/eaaDev/Range.html">Martin Fowler's
 * Range pattern</a> for a general discussion.
 *
 * <p>
 * Also interesting read, the Google's Guava discussion on their
 * <a href="https://github.com/google/guava/wiki/RangesExplained">Range
 * class</a>.
 *
 * @author Paulo Gandra Sousa
 * @param <T>
 */
@Embeddable
public class Interval<T extends Serializable & Comparable<T>> implements ValueObject, Comparable<Interval<T>> {

    private static final long serialVersionUID = 1L;

    /**
     *
     *
     */
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

    @JsonProperty
    private final T start;

    @JsonProperty
    private final T end;

    @JsonProperty
    private final BoundaryLimitType startBoundary;

    @JsonProperty
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
     * Special private constructor for empty intervals.
     *
     * @param b
     *            marker parameter to enable the compiler to distinguish the
     *            constructors
     */
    @SuppressWarnings("squid:S1172")
    private Interval(final boolean b) {
        start = end = null;
        endBoundary = startBoundary = null;
    }

    /**
     * Constructs an interval.
     *
     * @param start
     *            anchor start of the interval or null to represent
     *            infinity
     * @param end
     *            anchor end of the interval or null to represent infinity
     * @param startBoundary
     *            indicates if the interval is open or closed at the start
     *            anchor
     * @param endBoundary
     *            indicates if the interval is open or closed at the end
     *            anchor
     */
    protected Interval(final T start, final BoundaryLimitType startBoundary, final T end,
            final BoundaryLimitType endBoundary) {
        if ((start == null && startBoundary != BoundaryLimitType.INFINITY)
                || (end == null && endBoundary != BoundaryLimitType.INFINITY)) {
            throw new IllegalArgumentException("start or end must be non-null");
        }

        if (end != null && start != null && end.compareTo(start) < 0) {
            throw new IllegalArgumentException(
                    "The end value of a range must be bigger than its start");
        }
        if (end != null && start != null && end.compareTo(start) == 0
                && (startBoundary == BoundaryLimitType.OPEN
                        || endBoundary == BoundaryLimitType.OPEN)) {
            throw new IllegalArgumentException("An empty range is not allowed");
        }

        this.start = start;
        this.end = end;
        this.startBoundary = startBoundary;
        this.endBoundary = endBoundary;
    }

    /**
     * A factory method for intervals that start at "negative infinity".
     *
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> fromInfinity() {
        return new IntervalBuilder<>(null, BoundaryLimitType.INFINITY);
    }

    /**
     * A factory method for closed intervals that start at a specific anchor point.
     *
     * @param start
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> closedFrom(final U start) {
        return new IntervalBuilder<>(start, BoundaryLimitType.CLOSED);
    }

    /**
     * A factory method for open intervals that start at a specific anchor point.
     *
     * @param start
     *
     * @return a builder
     */
    public static <U extends Comparable<U> & Serializable> IntervalBuilder<U> openFrom(final U start) {
        return new IntervalBuilder<>(start, BoundaryLimitType.OPEN);
    }

    @SuppressWarnings({ "rawtypes", "java:S3740" })
    private static final Interval EMPTY = new Interval(true);

    /**
     * Returns an empty interval, i.e., [].
     *
     * @return an empty interval
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T> & Serializable> Interval<T> empty() {
        return EMPTY;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return start == end && start == null && endBoundary == startBoundary && endBoundary == null;
    }

    /**
     * Returns an universal interval, i.e., ]-oo, +oo[.
     *
     * @return an universal interval
     */
    public static <T extends Comparable<T> & Serializable> Interval<T> universal() {
        final Interval.IntervalBuilder<T> builder = fromInfinity();
        return builder.toInfinity();
    }

    /**
     *
     * @return
     */
    public boolean isUniversal() {
        return endBoundary == startBoundary && endBoundary == BoundaryLimitType.INFINITY;
    }

    /**
     * Checks if a value belongs in this interval.
     *
     * @param target
     * @return {@code true} if the value is part of this interval
     */
    public boolean includes(final T target) {
        if (isEmpty()) {
            return false;
        } else if (this.startBoundary != BoundaryLimitType.INFINITY
                && this.endBoundary != BoundaryLimitType.INFINITY) {
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
        return (!hasOpenEnd() || (target.compareTo(this.end) != 0));
    }

    private boolean includesInToInfinityRanges(final T target) {
        if (target.compareTo(this.start) < 0) {
            return false;
        }
        return (!hasOpenStart() || (target.compareTo(this.start) != 0));
    }

    private boolean includesInFiniteRanges(final T target) {
        if (target.compareTo(this.start) < 0 || target.compareTo(this.end) > 0) {
            return false;
        }
        if (hasOpenStart() && target.compareTo(this.start) == 0) {
            return false;
        }
        return (!hasOpenEnd() || (target.compareTo(this.end) != 0));
    }

    public boolean endsAtInfinity() {
        return this.endBoundary == BoundaryLimitType.INFINITY;
    }

    public boolean startsAtInfinity() {
        return this.startBoundary == BoundaryLimitType.INFINITY;
    }

    @JsonGetter
    public T start() {
        Invariants.ensure(!isEmpty());

        return this.start;
    }

    @JsonGetter
    public T end() {
        Invariants.ensure(!isEmpty());

        return this.end;
    }

    /**
     * Checks if this interval intersects another interval.
     *
     * @param other
     * @return {@code true} if this interval intersects another interval
     */
    public boolean intersects(final Interval<T> other) {
        return !this.intersection(other).equals(empty());
    }

    /**
     * Returns the interval consisting of the intersection of this interval and
     * another one.
     *
     * @param other
     * @return a new interval corresponding to the intersection
     */
    public Interval<T> intersection(final Interval<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    /**
     * Returns a new interval by extending/contracting the start of this one. The
     * openness/closeness of the start boundary as well as the end will be the same
     * of the original interval
     *
     * @param newStart
     * @return a new interval corresponding to the expansion/contraction
     */
    public Interval<T> withStart(final T newStart) {
        return new Interval<>(newStart, this.startBoundary, this.end, this.endBoundary);
    }

    /**
     * Returns a new interval by extending/contracting the end of this one. The
     * openness/closeness of the end boundary as well as the start will be the same
     * of the original interval
     *
     * @param newEnd
     * @return a new interval corresponding to the extension/contraction
     */
    public Interval<T> withEnd(final T newEnd) {
        return new Interval<>(this.start, this.startBoundary, newEnd, this.endBoundary);
    }

    /**
     * Checks if this interval overlaps another one, that is, if this interval fully
     * contains the other one.
     *
     * <p>
     * For instance:
     *
     * <pre>
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
     * </pre>
     *
     * @param other
     * @return {@code true} if this intervals overlaps the other
     */
    public boolean overlaps(final Interval<T> other) {
        throw new UnsupportedOperationException("Not yet developed");
    }

    public boolean hasOpenEnd() {
        return this.endBoundary == BoundaryLimitType.OPEN
                || endBoundary == BoundaryLimitType.INFINITY;
    }

    public boolean hasOpenStart() {
        return this.startBoundary == BoundaryLimitType.OPEN
                || startBoundary == BoundaryLimitType.INFINITY;
    }

    /**
     * Find out what's the gap between two ranges.
     *
     * <p>
     * Based on <a href="https://martinfowler.com/eaaDev/Range.html">Martin Fowler's
     * Range</a>
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
        return new Interval<>(lower.end, lower.endBoundary.negate(),
                higher.start, higher.startBoundary.negate());
    }

    /**
     * Compares two intervals to determine the natural order. Two intervals will be
     * the same (the method will return 0) if and only if they have the same start
     * and end, and the same openness/closeness at each side.
     *
     * <pre>
     * [4, 7] = [4, 7]
     * ]4, 7] = ]4, 7]
     * [4, 7[ = [4, 7[
     * ]4, 7[ = ]4, 7[
     * ]-oo, 5] = ]-oo, 5]
     * ]-oo, 5[ = ]-oo, 5[
     * [5, +oo[ = [5, +oo[
     * ]5, +oo[ = ]5, +oo[
     * ]-oo, +oo[ = ]-oo, +oo[
     * [] = []
     * </pre>
     *
     * <p>
     * An interval is "before" another if it starts at a point which is before the
     * start of the other interval.
     *
     * <pre>
     * [4, 7] &lt; [5, 7]
     * [4, 7] &lt; ]5, 7]
     * ]4, 7] &lt; [5, 7]
     * ]4, 7] &lt; ]5, 7]
     * ]-oo, 5] &lt; [-100, 5[
     * ]-oo, 5] &lt; ]-100, 5[
     * ]-oo, +oo[ &lt; [3, +oo[
     * </pre>
     *
     * <p>
     * If two intervals start at the same point, closed intervals will be before
     * closed ones.
     *
     * <pre>
     * [4, 10] &lt; ]4, 7]
     * [4, 10] &lt; ]4, 10]
     * [4, 10] &lt; ]4, 50]
     * </pre>
     *
     * <p>
     * If two intervals start at the same point (including infinity) and have the
     * same openness/closeness, then the end point will determine the order. If the
     * endpoint is the same, open intervals will be before closed ones.
     *
     * <pre>
     * [4, 7] &lt; [4, 10]
     * ]4, 7] &lt; ]4, 10]
     * ]-oo, 1] &lt; ]-oo, 4[
     * ]-oo, 5] &lt; ]-oo, +oo[
     *
     * ]-oo, 4[ &lt; ]-oo, 4]
     * [4, 10[ &lt; [4, 10]
     * </pre>
     *
     * <p>
     * The empty interval cannot be represented on a line and as such it doesn't
     * make much sense to order it, however, we need to handle that special case and
     * throwing an exception does not solve the problem if we happen to have an
     * empty interval as part of a collection and we want to sort those intervals.
     * The empty interval, i.e., [], is always first to any other interval.
     *
     * <pre>
     * [] &lt; X, for any X != []
     * </pre>
     *
     * @return -1 if this object is naturally before the other, 1 if this object is
     *         naturally after the other, 0 if they are naturally ordered the same
     */
    @Override
    public int compareTo(final Interval<T> other) {
        // if we are empty, we are either the first or the same
        if (isEmpty()) {
            return other.isEmpty() ? 0 : -1;
        }
        // we are not empty, but if the other is, then we are after
        if (other.isEmpty()) {
            return 1;
        }

        // neither of us is empty, let's check if we are the same
        if (startIsTheSameAs(other) && endIsTheSameAs(other)) {
            return 0;
        }

        // any of us start first?
        if (this.startsBeforeThan(other)) {
            return -1;
        }
        if (other.startsBeforeThan(this)) {
            return 1;
        }

        // we have the same start, so let's check the end
        if (this.endsAfterThan(other)) {
            return 1;
        }
        if (other.endsAfterThan(this)) {
            return -1;
        }

        return 0;
    }

    private boolean endsAfterThan(final Interval<T> other) {
        if (this.endsAtInfinity()) {
            return !other.endsAtInfinity();
        }
        if (other.endsAtInfinity()) {
            return false;
        }
        final int order = end.compareTo(other.end);
        if (order < 0) {
            return false;
        } else if (order == 0) {
            return (endBoundary == BoundaryLimitType.CLOSED
                    && other.endBoundary == BoundaryLimitType.OPEN);
        } else {
            return true;
        }
    }

    private boolean startsBeforeThan(final Interval<T> other) {
        if (this.startsAtInfinity()) {
            return !other.startsAtInfinity();
        }
        if (other.startsAtInfinity()) {
            return false;
        }
        final int order = start.compareTo(other.start);
        if (order < 0) {
            return true;
        } else if (order == 0) {
            return (startBoundary == BoundaryLimitType.CLOSED
                    && other.startBoundary == BoundaryLimitType.OPEN);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Interval<?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final Interval<T> other = (Interval<T>) o;
        return startIsTheSameAs(other) && endIsTheSameAs(other);
    }

    private boolean startIsTheSameAs(final Interval<T> other) {
        return this.startBoundary == other.startBoundary && this.start == other.start;
    }

    private boolean endIsTheSameAs(final Interval<T> other) {
        return this.endBoundary == other.endBoundary && this.end == other.end;
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(startBoundary).with(start).with(endBoundary).with(end).code();
    }

    /**
     * Detects whether two ranges abut each other.
     *
     * <p>
     * Based on <a href="https://martinfowler.com/eaaDev/Range.html">Martin fowler's Range</a>
     * </p>
     *
     * @param arg
     * @return {@code true} if this interval abuts the other
     */
    public boolean abuts(final Interval<T> arg) {
        return !this.overlaps(arg) && this.gap(arg).isEmpty();
    }

    /**
     * Checks if a group of ranges completely partitions this range.
     *
     * <p>
     * Based on <a href="https://martinfowler.com/eaaDev/Range.html">Martin fowler's Range</a>
     * </p>
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
     * <p>
     * Based on <a href="https://martinfowler.com/eaaDev/Range.html">Martin fowler's Range</a>
     * </p>
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
     * Checks if a group of intervals forms a contiguous interval.
     *
     * <p>
     * Based on <a href="https://martinfowler.com/eaaDev/Range.html">Martin fowler's Range</a>
     * </p>
     *
     *
     * @param args
     * @return {@code true} if a group of intervals forms a contiguous interval
     */
    public static <T extends Serializable & Comparable<T>> boolean areContiguous(final Interval<T>[] args) {
        // TODO need to take into account the boundary limits to see if the intervals are really
        // contiguous
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
        final var sb = new StringBuilder();
        sb.append(startBracket(hasOpenStart()));
        sb.append(rangeValue(this.startsAtInfinity(), start));
        sb.append(", ");
        sb.append(rangeValue(this.endsAtInfinity(), end));
        sb.append(endBracket(hasOpenEnd()));
        return sb.toString();
    }

    private char startBracket(final boolean isOpen) {
        return isOpen ? ']' : '[';
    }

    private char endBracket(final boolean isOpen) {
        return isOpen ? '[' : ']';
    }

    private String rangeValue(final boolean isInfinity, final T value) {
        return isInfinity ? "oo" : value.toString();
    }
}
