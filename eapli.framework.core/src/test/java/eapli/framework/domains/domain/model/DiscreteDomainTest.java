/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.domains.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra Sousa
 *
 */
class DiscreteDomainTest {

    private static DiscreteDomain<Long> EMPTY;
    private static DiscreteDomain<Long> TEN;
    private static DiscreteDomain<Long> ONE_TO_TEN;
    private static DiscreteDomain<Long> FIVE_TO_SEVEN;
    private static DiscreteDomain<Long> TEN_TO_FIFTHTEEN;
    private static DiscreteDomain<Long> FIVE_TO_SEVEN_TEN_TO_FIFHTEEN;
    private static DiscreteDomain<Long> ONE_TO_FOUR_EIGTH_TO_TEN;
    private static DiscreteDomain<Long> ONE_TO_NINE;

    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        EMPTY = DiscreteDomain.empty();

        TEN = build(10, 10);
        ONE_TO_TEN = build(1, 10);
        FIVE_TO_SEVEN = build(5, 7);
        TEN_TO_FIFTHTEEN = build(10, 15);
        FIVE_TO_SEVEN_TEN_TO_FIFHTEEN = builder(10, 15).with(5L).with(6L).with(7L).build();
        ONE_TO_FOUR_EIGTH_TO_TEN = builder(1, 4).with(8L, 9L, 10L).build();
        ONE_TO_NINE = build(1, 9);
    }

    private static DiscreteDomain<Long> build(final long begin, final long end) {
        return builder(begin, end).build();
    }

    private static DiscreteDomainBuilder<Long> builder(final long begin, final long end) {
        final DiscreteDomainBuilder<Long> builder = new DiscreteDomainBuilder<>();
        for (long i = begin; i <= end; i++) {
            builder.with(i);
        }
        return builder;
    }

    @Test
    void ensureEmptyDomainIsEmpty() {
        assertTrue(EMPTY.isEmpty(), "Empty domain is not empty");
    }

    @Test
    void ensureNonEmptyDomainIsNotEmpty() {
        assertFalse(ONE_TO_TEN.isEmpty(), "Non Empty domain is empty");
    }

    @Test
    void ensureTheDomainDoesNotContainNotAddedElement() {
        assertFalse(ONE_TO_TEN.contains(50L), "TheDomainDoesNotContainNotAddedElement");
    }

    @Test
    void ensureTheDomainContainsAddedElement() {
        assertTrue(ONE_TO_TEN.contains(5L), "TheDomainContainsAddedElement");
    }

    @Test
    void ensureTheDomainContainsAllAddedElements() {
        for (long e = 1; e <= 10; e++) {
            if (!ONE_TO_TEN.contains(e)) {
                fail("Element " + e + " is missing in the domain");
            }
        }
    }

    @Test
    void ensureTheDomainDoesNotContainExtraElements() {
        for (final long e : ONE_TO_TEN) {
            if (e > 10 || e < 1) {
                fail("Element " + e + " should not be in the domain");
            }
        }
    }

    @Test
    void ensureImmutable() {
        assertThrows(UnsupportedOperationException.class, () -> ONE_TO_TEN.iterator().remove());
    }

    //
    // intersection
    //

    @Test
    void ensureIntersection1_10And5_7Is5_7() {
        final DiscreteDomain<Long> i = ONE_TO_TEN.intersection(FIVE_TO_SEVEN);
        assertEquals(FIVE_TO_SEVEN, i);
    }

    @Test
    void ensureIntersection1_10And10_15Is10() {
        final DiscreteDomain<Long> i = ONE_TO_TEN.intersection(TEN_TO_FIFTHTEEN);
        assertEquals(TEN, i);
    }

    @Test
    void ensureIntersection10_15And5_7IsEmpty() {
        final DiscreteDomain<Long> i = TEN_TO_FIFTHTEEN.intersection(FIVE_TO_SEVEN);
        assertEquals(EMPTY, i);
    }

    @Test
    void ensureIntersection10_15AndEmptyIsEmpty() {
        final DiscreteDomain<Long> i = TEN_TO_FIFTHTEEN.intersection(EMPTY);
        assertEquals(EMPTY, i);
    }

    //
    // Union
    //

    @Test
    void ensureUnion10_15And5_7Is5_7_10_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.union(FIVE_TO_SEVEN);
        assertEquals(FIVE_TO_SEVEN_TEN_TO_FIFHTEEN, u);
    }

    @Test
    void ensureUnion1_10And5_7Is1_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.union(FIVE_TO_SEVEN);
        assertEquals(ONE_TO_TEN, u);
    }

    @Test
    void ensureUnion1_10AndEmptyIs1_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.union(EMPTY);
        assertEquals(ONE_TO_TEN, u);
    }

    //
    // Complement
    //

    @Test
    void ensure1_10Minus5_7Is1_4_8_10() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.without(FIVE_TO_SEVEN);
        assertEquals(ONE_TO_FOUR_EIGTH_TO_TEN, u);
    }

    @Test
    void ensure1_10Minus10_to_15Is1_TO_9() {
        final DiscreteDomain<Long> u = ONE_TO_TEN.without(TEN_TO_FIFTHTEEN);
        assertEquals(ONE_TO_NINE, u);
    }

    @Test
    void ensure10_to_15Minus5_to7Is10_to_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.without(FIVE_TO_SEVEN);
        assertEquals(TEN_TO_FIFTHTEEN, u);
    }

    @Test
    void ensure10_to_15MinusEmptyIs10_to_15() {
        final DiscreteDomain<Long> u = TEN_TO_FIFTHTEEN.without(EMPTY);
        assertEquals(TEN_TO_FIFTHTEEN, u);
    }

    @Test
    void ensureEmptyMinus10_to_15IsEmpty() {
        final DiscreteDomain<Long> u = EMPTY.without(TEN_TO_FIFTHTEEN);
        assertEquals(EMPTY, u);
    }

    @Test
    void ensureAMinusBUnionAIntersectionBIsA() {
        final DiscreteDomain<Long> aMinusB = ONE_TO_TEN.without(TEN_TO_FIFTHTEEN);
        final DiscreteDomain<Long> aIntersectionB = ONE_TO_TEN.intersection(TEN_TO_FIFTHTEEN);
        final DiscreteDomain<Long> u = aMinusB.union(aIntersectionB);
        assertEquals(ONE_TO_TEN, u);
    }
}
