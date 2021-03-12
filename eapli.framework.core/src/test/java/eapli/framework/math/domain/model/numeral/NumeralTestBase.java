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
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import eapli.framework.math.domain.model.Numeral;

/**
 * @author Paulo Gandra de Sousa
 *
 */
abstract class NumeralTestBase {

    private Numeral instance;

    private static final String SYMBOLS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    protected abstract int getBase();

    @Test
    void ensure0is0() {
        instance = Numeral.valueOf("0", getBase());
        assertEquals(0, instance.decimalValue());
    }

    @Test
    void ensureBaseSymbols() {
        for (int i = 0; i < getBase(); i++) {
            final String result = Numeral.valueOf(i).toBase(getBase()).toString();
            final String expected = String.valueOf(SYMBOLS.charAt(i));
            if (!result.equals(expected)) {
                fail("expected [" + expected + "] but was [" + result + "]");
            }
        }
        assertTrue(true);
    }

    @Test
    void ensureConvertingToBaseDoesNotProduceStrangeSymbols() {
        final Stream<String> results = IntStream.range(0, 10000)
                .<String>mapToObj(x -> Numeral.valueOf(x).toBase(getBase()).toString());
        assertTrue(results.allMatch(x -> validateSymbols(x, getBase())));
    }

    private boolean validateSymbols(final String x, final int base) {
        final String pattern = "^[" + SYMBOLS.substring(0, getBase()) + "]+$";
        return x.matches(pattern);
    }

    @Test
    void ensureInvalidNumeralIsNotAccepted() {
        final int base = getBase();
        final String subject = "12=F";
        assertThrows(IllegalArgumentException.class, () -> Numeral.valueOf(subject, base));
    }
}
