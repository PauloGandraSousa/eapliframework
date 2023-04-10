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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import eapli.framework.quantities.domain.model.Temperature.TemperatureUnit;

/**
 * @author Paulo Gandra de Sousa 22/11/2022
 *
 */
class TemperatureCreationTest {

    @ParameterizedTest
    @ValueSource(doubles = { -273.15, -100, -10, 0, 32, 37.5, 42, 99.5, 100, 107.6, 212 })
    void ensureCelsius(final double in) {
        final var subject = Temperature.celsius(in);

        assertEquals(BigDecimal.valueOf(in), subject.value());
        assertEquals(TemperatureUnit.CELSIUS, subject.unit());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0, 32, 37.5, 42, 99.5, 100, 107.6, 212, 273.15, 469.65, 500 })
    void ensureKelvin(final double in) {
        final var subject = Temperature.kelvin(in);

        assertEquals(BigDecimal.valueOf(in), subject.value());
        assertEquals(TemperatureUnit.KELVIN, subject.unit());
    }

    @ParameterizedTest
    @ValueSource(doubles = { -459.67, -384.07, -279.67, 0, 32, 42, 99.68, 100, 212, 273.15, 310.75, 373.15 })
    void ensureFarenheit() {
        final var subject = Temperature.farenheit(100);

        assertEquals(BigDecimal.valueOf(100.0), subject.value());
        assertEquals(TemperatureUnit.FARENHEIT, subject.unit());
    }

    @Test
    void ensureUnitZeroK() {
        final var subject = TemperatureUnit.KELVIN.zero();

        assertEquals(0d, subject.magnitude().doubleValue());
        assertEquals(TemperatureUnit.KELVIN, subject.unit());

    }

    @Test
    void ensureUnitZeroC() {
        final var subject = TemperatureUnit.CELSIUS.zero();

        assertEquals(0d, subject.magnitude().doubleValue());
        assertEquals(TemperatureUnit.CELSIUS, subject.unit());

    }

    @Test
    void ensureUnitZeroF() {
        final var subject = TemperatureUnit.FARENHEIT.zero();

        assertEquals(0d, subject.magnitude().doubleValue());
        assertEquals(TemperatureUnit.FARENHEIT, subject.unit());

    }

    @Test
    void ensureItIsNotPossibleToHaveTemperaturesBellow0K() {
        assertThrows(IllegalArgumentException.class, () -> {
            Temperature.kelvin(-1);
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveCelsiusTemperaturesBellow0K() {

        assertThrows(IllegalArgumentException.class, () -> {
            Temperature.celsius(-273.16);
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveFarenheitTemperaturesBellow0K() {

        assertThrows(IllegalArgumentException.class, () -> {
            Temperature.farenheit(-459.68);
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveTemperaturesBellow0KWhenNegating() {

        final var t = Temperature.celsius(500);
        assertThrows(IllegalArgumentException.class, () -> {
            t.negate();
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveTemperaturesBellow0KWhenSubtracting() {

        final var t = Temperature.celsius(-270);
        final var t2 = Temperature.celsius(100);
        assertThrows(IllegalArgumentException.class, () -> {
            t.subtract(t2);
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveTemperaturesBellow0KWhenDecreasing() {

        final var t = Temperature.celsius(-270);
        assertThrows(IllegalArgumentException.class, () -> {
            t.decrease(100);
        });
    }

    @Test
    void ensureItIsNotPossibleToHaveTemperaturesBellow0KWhenScaling() {

        final var t = Temperature.celsius(-175);
        assertThrows(IllegalArgumentException.class, () -> {
            t.scale(2);
        });
    }
}
