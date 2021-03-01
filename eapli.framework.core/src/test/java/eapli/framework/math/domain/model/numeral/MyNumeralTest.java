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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import eapli.framework.math.domain.model.Numeral;
import eapli.framework.math.domain.model.NumeralSystem;

/**
 * @author Paulo Gandra de Sousa
 *
 */
class MyNumeralTest {

    private Numeral instance;

    private static final String SYMBOLS = "=!\"#$%&/()";
    private static final NumeralSystem SYSTEM = () -> SYMBOLS;

    @Test
    void ensure0is0() {
        instance = Numeral.valueOf("=", SYSTEM);
        assertEquals(0, instance.decimalValue());
    }

    @Test
    void ensureBaseSymbols() {
        for (int i = 0; i < 10; i++) {
            final String result = Numeral.valueOf(i).toBase(SYSTEM).toString();
            final String expected = String.valueOf(SYMBOLS.charAt(i));
            if (!result.equals(expected)) {
                fail("expected [" + expected + "] but was [" + result + "]");
            }
        }
        assertTrue(true);
    }

    @Test
    void ensureNoStrangeSymbols() {
        final Stream<String> results = IntStream.range(0, 10000)
                .<String>mapToObj(x -> Numeral.valueOf(x).toBase(SYSTEM).toString());
        assertTrue(results.allMatch(x -> validateSymbols(x)));
    }

    private boolean validateSymbols(final String x) {
        final String pattern = "^[" + SYMBOLS + "]+$";
        return x.matches(pattern);
    }

    @Test
    void testSomeCases() {
        assertEquals(128, Numeral.valueOf("!\"(", SYSTEM).decimalValue());
        assertEquals(1024, Numeral.valueOf("!=\"$", SYSTEM).decimalValue());
        assertEquals(9999, Numeral.valueOf("))))", SYSTEM).decimalValue());
        assertEquals(100000, Numeral.valueOf("!=====", SYSTEM).decimalValue());
    }
}
