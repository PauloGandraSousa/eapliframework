/*
 * Copyright (c) 2013-2022 the original author or authors.
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
package eapli.framework.math.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Paulo Gandra Sousa 2021.05.12
 *
 */
class RomanNumeralTest {

    @ParameterizedTest
    @CsvSource(value = {
            "1, 'I'",
            "2, 'II'",
            "3, 'III'",
            "4, 'IV'",
            "5, 'V'",
            "6, 'VI'",
            "7, 'VII'",
            "8, 'VIII'",
            "9, 'IX'",
            "10, 'X'",
            "11, 'XI'",
            "12, 'XII'",
            "13, 'XIII'",
            "14, 'XIV'",
            "15, 'XV'",
            "16, 'XVI'",
            "17, 'XVII'",
            "18, 'XVIII'",
            "19, 'XIX'",
            "20, 'XX'",
            "21, 'XXI'",
            "30, 'XXX'",
            "40, 'XL'",
            "50, 'L'",
            "60, 'LX'",
            "70, 'LXX'",
            "80, 'LXXX'",
            "90, 'XC'",
            "99, 'XCIX'",
            "100, 'C'",
            "199, 'CXCIX'",
            "200, 'CC'",
            "300, 'CCC'",
            "400, 'CD'",
            "499, 'CDXCIX'",
            "500, 'D'",
            "600, 'DC'",
            "700, 'DCC'",
            "800, 'DCCC'",
            "900, 'CM'",
            "1000, 'M'",
            "2000, 'MM'",
            "3000, 'MMM'",
            "3999, 'MMMCMXCIX'",
    })
    void ensureTransformIntToRoman(final int given, final String expected) {
        final String result = RomanNumeral.valueOf(given).toString();
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'I', 1",
            "'II', 2",
            "'III', 3",
            "'IV', 4",
            "'V', 5",
            "'VI', 6",
            "'VII', 7",
            "'VIII', 8",
            "'IX', 9",
            "'X', 10",
            "'XI', 11",
            "'XII', 12",
            "'XIII', 13",
            "'XIV', 14",
            "'XV', 15",
            "'XVI', 16",
            "'XVII', 17",
            "'XVIII', 18",
            "'XIX', 19",
            "'XX', 20",
            "'XXI', 21",
            "'XXX', 30",
            "'XL', 40",
            "'L', 50",
            "'LX', 60",
            "'LXX', 70",
            "'LXXX', 80",
            "'XC', 90",
            "'XCIX', 99",
            "'C', 100",
            "'CXCIX', 199",
            "'CC', 200",
            "'CCC', 300",
            "'CD', 400",
            "'CDXCIX', 499",
            "'D', 500",
            "'DC', 600",
            "'DCC', 700",
            "'DCCC', 800",
            "'CM', 900",
            "'M', 1000",
            "'MM', 2000",
            "'MMM', 3000",
            "'MMMCMXCIX', 3999",
    })
    void ensureParsesStringRoman(final String given, final int expected) {
        final int result = RomanNumeral.valueOf(given).toInt();
        assertEquals(expected, result);
    }

    @Test
    void ensureThrowsIfInvalidNumeralString() {
        assertThrows(NumberFormatException.class, () -> new RomanNumeral("XXaI"));
    }

    @Test
    void ensureThrowsIfEmptyString() {
        assertThrows(NumberFormatException.class, () -> new RomanNumeral(""));
    }

    @Test
    void ensureCannotTransform0() {
        assertThrows(IllegalArgumentException.class, () -> new RomanNumeral(0));
    }

    @Test
    void ensureCannotTransformNegative() {
        assertThrows(IllegalArgumentException.class, () -> new RomanNumeral(-1));
    }

    @Test
    void ensureCannotTransformBiggerThan3999() {
        assertThrows(NumberFormatException.class, () -> new RomanNumeral(4000));
    }

    @Test
    void ensureCannotRepresentBiggerThan3999() {
        assertThrows(NumberFormatException.class, () -> new RomanNumeral("MMMMX"));
    }
}
