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
package eapli.framework.functional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa
 *
 */
class StreamsTest {

    /**
     * Test method for
     * {@link eapli.framework.functional.Streams#zip(java.util.stream.Stream, java.util.stream.Stream, java.util.function.BiFunction)}.
     */
    @Test
    void testZipIntArrays() {
        final Integer[] a = new Integer[] { 2, 4, 6 };
        final Integer[] b = new Integer[] { 2, 4, 6 };

        final Integer[] expected = new Integer[] { 4, 8, 12 };
        final Integer[] result = Streams.zip(Arrays.stream(a), Arrays.stream(b), (x, y) -> x + y)
                .toArray(Integer[]::new);
        assertArrayEquals(expected, result);
    }
}
