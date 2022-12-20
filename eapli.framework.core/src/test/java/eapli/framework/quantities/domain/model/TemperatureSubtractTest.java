/*
 * Copyright (c) 2013-2023 the original author or authors.
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
package eapli.framework.quantities.domain.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 22/11/2022
 *
 */
class TemperatureSubtractTest {
    @Test
    void ensureCannotSubtractDifferentUnitsCK() {
        final var t1 = Temperature.celsius(100);
        final var t2 = Temperature.kelvin(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    @Test
    void ensureCannotSubtractDifferentUnitsCF() {
        final var t1 = Temperature.celsius(100);
        final var t2 = Temperature.farenheit(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    @Test
    void ensureCannotSubtractDifferentUnitsKC() {
        final var t1 = Temperature.kelvin(100);
        final var t2 = Temperature.celsius(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    @Test
    void ensureCannotSubtractDifferentUnitsKF() {
        final var t1 = Temperature.kelvin(100);
        final var t2 = Temperature.farenheit(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    @Test
    void ensureCannotSubtractDifferentUnitsFC() {
        final var t1 = Temperature.farenheit(100);
        final var t2 = Temperature.celsius(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    @Test
    void ensureCannotSubtractDifferentUnitsFK() {
        final var t1 = Temperature.farenheit(100);
        final var t2 = Temperature.kelvin(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.subtract(t2);
        });
    }

    void ensureSubtractsProperly() {

    }
}
