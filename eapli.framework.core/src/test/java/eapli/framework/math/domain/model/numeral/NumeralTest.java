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
package eapli.framework.math.domain.model.numeral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import eapli.framework.math.domain.model.Numeral;

/**
 * @author Paulo Gandra de Sousa
 *
 */
class NumeralTest {

    @Test
    void ensureBaseIsBiggerThan2() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("0", 1));
    }

    @Test
    void ensureBaseIsSmallerThan36() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("0", 37));
    }

    @Test
    void ensureValueIsNonNegative() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf(-1));
    }

    @Test
    void ensureValueIsNotEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("", 8));
    }

    @Test
    void ensureValueIsNotNull() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf(null, 8));
    }

    @Test
    void ensureDecimalValueHasValidSymbols() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("123A", 10));
    }

    @Test
    void ensureBinaryValueHasValidSymbols() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("12", 2));
    }

    @Test
    void ensureOctalValueHasValidSymbols() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("1238", 8));
    }

    @Test
    void ensureHexaDecimalValueHasValidSymbols() {
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf("123AG", 16));
    }

    private class TestCaseData {
        public long decimal;
        public String binary;
        public String octal;
        public String hexa;

        public TestCaseData(final long decimal, final String binary, final String octal, final String hexa) {
            this.decimal = decimal;
            this.binary = binary;
            this.octal = octal;
            this.hexa = hexa;
        }
    }

    private final TestCaseData[] testCases = { new TestCaseData(0, "0", "0", "0"), new TestCaseData(1, "1", "1", "1"),
            new TestCaseData(2, "10", "2", "2"), new TestCaseData(3, "11", "3", "3"),
            new TestCaseData(4, "100", "4", "4"), new TestCaseData(5, "101", "5", "5"),
            new TestCaseData(6, "110", "6", "6"), new TestCaseData(7, "111", "7", "7"),
            new TestCaseData(8, "1000", "10", "8"), new TestCaseData(9, "1001", "11", "9"),
            new TestCaseData(10, "1010", "12", "A"), new TestCaseData(11, "1011", "13", "B"),
            new TestCaseData(12, "1100", "14", "C"), new TestCaseData(13, "1101", "15", "D"),
            new TestCaseData(14, "1110", "16", "E"), new TestCaseData(15, "1111", "17", "F"),
            new TestCaseData(16, "10000", "20", "10"),

            new TestCaseData(126, "1111110", "176", "7E"), new TestCaseData(84, "1010100", "124", "54"),
            new TestCaseData(45, "101101", "55", "2D"), new TestCaseData(99, "1100011", "143", "63"),

            new TestCaseData(1024, "10000000000", "2000", "400"), new TestCaseData(2048, "100000000000", "4000", "800"),
            new TestCaseData(999999, "11110100001000111111", "3641077", "F423F"), };

    @Test
    void testCases() {
        for (final TestCaseData data : testCases) {
            testFromBase2(data);
            testFromBase8(data);
            testFromBase10(data);
            testFromBase10EqualsLongToString(data);
            testFromBase16(data);

            testToSameBase(data);

            testValueOf(data);
            testDecimalValue(data);

            testToBase2(data);
            testToBase8(data);
            testToBase10(data);
            testToBase16(data);
        }
    }

    private void testToSameBase(final TestCaseData data) {
        assertEquals(data.binary, Numeral.valueOf(data.binary, 2).toBase2().toString());
        assertEquals(data.octal, Numeral.valueOf(data.octal, 8).toBase8().toString());
        assertEquals(String.valueOf(data.decimal), Numeral.valueOf(data.decimal).toBase10().toString());
        assertEquals(data.hexa, Numeral.valueOf(data.hexa, 16).toBase16().toString());
    }

    private void testFromBase10(final TestCaseData data) {
        assertEquals(data.binary, Numeral.valueOf(data.decimal).toBase2().toString());
        assertEquals(data.octal, Numeral.valueOf(data.decimal).toBase8().toString());
        assertEquals(data.hexa, Numeral.valueOf(data.decimal).toBase16().toString());
    }

    private void testFromBase10EqualsLongToString(final TestCaseData data) {
        assertEquals(Long.toBinaryString(data.decimal), Numeral.valueOf(data.decimal).toBase2().toString());
        assertEquals(Long.toOctalString(data.decimal), Numeral.valueOf(data.decimal).toBase8().toString());
        assertTrue(
                Long.toHexString(data.decimal).equalsIgnoreCase(Numeral.valueOf(data.decimal).toBase16().toString()));
    }

    private void testFromBase2(final TestCaseData data) {
        assertEquals(Long.toString(data.decimal), Numeral.valueOf(data.binary, 2).toBase10().toString());
        assertEquals(data.octal, Numeral.valueOf(data.binary, 2).toBase8().toString());
        assertEquals(data.hexa, Numeral.valueOf(data.binary, 2).toBase16().toString());
    }

    private void testFromBase8(final TestCaseData data) {
        assertEquals(Long.toString(data.decimal), Numeral.valueOf(data.octal, 8).toBase10().toString());
        assertEquals(data.binary, Numeral.valueOf(data.octal, 8).toBase2().toString());
        assertEquals(data.hexa, Numeral.valueOf(data.octal, 8).toBase16().toString());
    }

    private void testFromBase16(final TestCaseData data) {
        assertEquals(Long.toString(data.decimal), Numeral.valueOf(data.hexa, 16).toBase10().toString());
        assertEquals(data.binary, Numeral.valueOf(data.hexa, 16).toBase2().toString());
        assertEquals(data.hexa, Numeral.valueOf(data.hexa, 16).toBase16().toString());
    }

    private void testValueOf(final TestCaseData data) {
        assertEquals(String.valueOf(data.decimal), Numeral.valueOf(String.valueOf(data.decimal), 10).toString());
        assertEquals(data.binary, Numeral.valueOf(data.binary, 2).toString());
        assertEquals(data.octal, Numeral.valueOf(data.octal, 8).toString());
        assertEquals(data.hexa, Numeral.valueOf(data.hexa, 16).toString());
    }

    private void testDecimalValue(final TestCaseData data) {
        assertEquals(data.decimal, Numeral.valueOf(String.valueOf(data.decimal), 10).decimalValue());
        assertEquals(data.decimal, Numeral.valueOf(data.binary, 2).decimalValue());
        assertEquals(data.decimal, Numeral.valueOf(data.octal, 8).decimalValue());
        assertEquals(data.decimal, Numeral.valueOf(data.hexa, 16).decimalValue());
    }

    private void testToBase2(final TestCaseData data) {
        final Numeral expected = Numeral.valueOf(data.binary, 2);
        assertEquals(expected, Numeral.valueOf(String.valueOf(data.decimal), 10).toBase2());
        assertEquals(expected, Numeral.valueOf(data.binary, 2).toBase2());
        assertEquals(expected, Numeral.valueOf(data.octal, 8).toBase2());
        assertEquals(expected, Numeral.valueOf(data.hexa, 16).toBase2());
    }

    private void testToBase8(final TestCaseData data) {
        final Numeral expected = Numeral.valueOf(data.octal, 8);
        assertEquals(expected, Numeral.valueOf(String.valueOf(data.decimal), 10).toBase8());
        assertEquals(expected, Numeral.valueOf(data.binary, 2).toBase8());
        assertEquals(expected, Numeral.valueOf(data.octal, 8).toBase8());
        assertEquals(expected, Numeral.valueOf(data.hexa, 16).toBase8());
    }

    private void testToBase10(final TestCaseData data) {
        final Numeral expected = Numeral.valueOf(data.decimal);
        assertEquals(expected, Numeral.valueOf(String.valueOf(data.decimal), 10).toBase10());
        assertEquals(expected, Numeral.valueOf(data.binary, 2).toBase10());
        assertEquals(expected, Numeral.valueOf(data.octal, 8).toBase10());
        assertEquals(expected, Numeral.valueOf(data.hexa, 16).toBase10());
    }

    private void testToBase16(final TestCaseData data) {
        final Numeral expected = Numeral.valueOf(data.hexa, 16);
        assertEquals(expected, Numeral.valueOf(String.valueOf(data.decimal), 10).toBase16());
        assertEquals(expected, Numeral.valueOf(data.binary, 2).toBase16());
        assertEquals(expected, Numeral.valueOf(data.octal, 8).toBase16());
        assertEquals(expected, Numeral.valueOf(data.hexa, 16).toBase16());
    }
}
