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

/**
 * Test cases for compare described in the documentation of
 * {@link Interval#compareTo(Interval)}
 *
 * <p>
 * The same interval
 *
 * <pre>
 * [4, 7] = [4, 7]
 * {@link C5_C10_IntervalCompareTest#ensureIsSame()}
 *
 * ]4, 7] = ]4, 7]
 * {@link O5_C10_IntervalCompareTest#ensureIsSame()}
 *
 * [4, 7[ = [4, 7[
 * {@link C5_O10_IntervalCompareTest#ensureIsSame()}
 *
 * ]4, 7[ = ]4, 7[
 * {@link O5_O10_IntervalCompareTest#ensureIsSame()}
 *
 * ]-oo, 5] = ]-oo, 5]
 * {@link I_C10_IntervalCompareTest#ensureIsSame()}
 *
 * ]-oo, 5[ = ]-oo, 5[
 * {@link I_O10_IntervalCompareTest#ensureIsSame()}
 *
 * [5, +oo[ = [5, +oo[
 * {@link C5_I_IntervalCompareTest#ensureIsSame()}
 *
 * ]5, +oo[ = ]5, +oo[
 * {@link O5_I_IntervalCompareTest#ensureIsSame()}
 *
 * ]-oo, +oo[ = ]-oo, +oo[
 * {@link UniversalIntervalCompareTest#ensureIsSame()}
 *
 * [] = []
 * {@link EmptyIntervalCompareTest#ensureIsSame()}
 * </pre>
 *
 * <p>
 * Start is before the other start
 *
 * <pre>
 * [4, 7] < [5, 7]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeC10_C50()}
 *
 * [4, 7] < ]5, 7]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeO10_C50()}
 *
 * ]4, 7] < [5, 7]
 * {@link O5_C10_IntervalCompareTest#ensureIsBeforeC10_C50()}
 *
 * ]4, 7] < ]5, 7]
 * {@link O5_C10_IntervalCompareTest#ensureIsBeforeO10_C50()}
 *
 * ]-oo, 5] < [-100, 5[
 * {@link I_C10_IntervalCompareTest#ensureICXIsBeforeCXOY()}
 *
 * ]-oo, 5] < ]-100, 5[
 * {@link I_C10_IntervalCompareTest#ensureICXIsBeforeOXCY()}
 *
 * ]-oo, 5] < ]-100, +00[
 * {@link I_C10_IntervalCompareTest#ensureICXIsBeforeOXI()}
 *
 * ]-oo, +oo[ < [3, +oo[
 * {@link UniversalIntervalCompareTest#ensureUniversalIsBeforeCXI()}
 * </pre>
 *
 * <p>
 * Same start but different openness
 *
 * <pre>
 * [4, 10] < ]4, 7]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeO5_C7()}
 *
 * [4, 10] < ]4, 10]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeOXCY()}
 *
 * [4, 10] < ]4, 50]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeO10_C50()}
 *
 * </pre>
 *
 * <p>
 * Same start and same openness
 *
 * <pre>
 * [4, 7] < [4, 10]
 * {@link C5_C10_IntervalCompareTest#ensureIsBeforeC10_C50()}
 *
 * ]4, 7] < ]4, 10]
 * {@link O5_C10_IntervalCompareTest#ensureIsBeforeO10_C50()}
 *
 * ]-oo, 1] < ]-oo, 4[
 * {@link I_C10_IntervalCompareTest#ensureIsBeforeI_O50()}
 *
 * ]-oo, 5] < ]-oo, +oo[
 * {@link I_C10_IntervalCompareTest#ensureIsBeforeUniversal()}
 *
 * ]-oo, 4[ < ]-oo, 4]
 * {@link I_O10_IntervalCompareTest#ensureIOXIsBeforeICX()}
 *
 * [4, 10[ < [4, 10]
 * {@link C5_O10_IntervalCompareTest#ensureIsBeforeCXCY()}
 * </pre>
 *
 * <p>
 * Empty interval special case
 *
 * <pre>
 * <code>
 * [] < X, for any X != []
 * {@link EmptyIntervalCompareTest}
 * </code>
 * </pre>
 *
 * @author Paulo Gandra de Sousa
 *
 */
abstract class IntervalCompareTestBase {

    protected static final int ANCHOR_1 = 4;
    protected static final int ANCHOR_2 = 7;
    protected static final int ANCHOR_3 = 10;
    protected static final int ANCHOR_4 = 50;

    // []
    protected static final Interval<Integer> E = Interval.empty();

    // ]-I, +I[
    protected static final Interval<Integer> U = Interval.universal();

    // ]-I, x[
    protected static Interval<Integer> I_O10 = Interval.<Integer>fromInfinity().openTo(ANCHOR_3);

    // ]-I, x]
    protected static Interval<Integer> I_C10 = Interval.<Integer>fromInfinity().closedTo(ANCHOR_3);

    // [x, y]
    protected static final Interval<Integer> C5_C10 = Interval.closedFrom(ANCHOR_1)
            .closedTo(ANCHOR_3);

    // [x, y[
    protected static final Interval<Integer> C5_O10 = Interval.closedFrom(ANCHOR_1)
            .openTo(ANCHOR_3);

    // ]x, y]
    protected static final Interval<Integer> O5_C10 = Interval.openFrom(ANCHOR_1)
            .closedTo(ANCHOR_3);

    // ]x, y[
    protected static final Interval<Integer> O5_O10 = Interval.openFrom(ANCHOR_1).openTo(ANCHOR_3);

    // [x, +I[
    protected static final Interval<Integer> C5_I = Interval.closedFrom(ANCHOR_1).toInfinity();

    // ]x, +I[
    protected static final Interval<Integer> O5_I = Interval.openFrom(ANCHOR_1).toInfinity();

    // Additional intervals for comparison cases
    protected static final Interval<Integer> C10_C50 = Interval.closedFrom(ANCHOR_3)
            .closedTo(ANCHOR_4);
    protected static final Interval<Integer> O10_C50 = Interval.openFrom(ANCHOR_3)
            .closedTo(ANCHOR_4);
    protected static final Interval<Integer> C5_C50 = Interval.closedFrom(ANCHOR_1)
            .closedTo(ANCHOR_4);
    protected static final Interval<Integer> O5_C50 = Interval.openFrom(ANCHOR_1)
            .closedTo(ANCHOR_4);
    protected static final Interval<Integer> O5_C7 = Interval.closedFrom(ANCHOR_1)
            .closedTo(ANCHOR_2);
    protected static final Interval<Integer> I_O50 = Interval.<Integer>fromInfinity()
            .openTo(ANCHOR_4);
}
