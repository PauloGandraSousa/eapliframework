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
package eapli.framework.general.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * test cases for the valueOf() method of the Money class
 *
 * @author Paulo Gandra Sousa
 *
 */
class MoneyValueOfTest {

    @ParameterizedTest
    @CsvSource({
            "125.50 EUR, 125.5",
            "125.527 EUR, 125.53",
            "0.09 EUR, 0.09",
            "9 EUR, 9",
            "999999999999.50 EUR, 999999999999.50"
    })
    void properString(final String test, final double e) {
        final Money instance = Money.valueOf(test);
        final Money expected = Money.euros(e);
        assertEquals(expected, instance);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2X9 EUR", // invalid amount
            "29 AAA", // invalid currency
            "9EUR", // no spaces
            "9.4", // no currency
            "USD" // no amount
    })
    void ensureInvalidStringThrows(final String test) {
        assertThrows(IllegalArgumentException.class, () -> Money.valueOf(test));
    }
}
