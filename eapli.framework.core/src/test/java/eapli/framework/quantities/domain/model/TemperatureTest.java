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
package eapli.framework.quantities.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import eapli.framework.quantities.domain.model.Temperature.TemperatureUnit;

/**
 * @author Paulo Gandra de Sousa 18/11/2022
 *
 */
class TemperatureTest {

    @Test
    void ensure100C() {
        final var subject = Temperature.celsius(100);

        assertEquals(BigDecimal.valueOf(100.0), subject.value());
        assertEquals(TemperatureUnit.CELSIUS, subject.unit());
    }

    @Test
    void ensure100K() {
        final var subject = Temperature.kelvin(100);

        assertEquals(BigDecimal.valueOf(100.0), subject.value());
        assertEquals(TemperatureUnit.KELVIN, subject.unit());
    }

    @Test
    void ensure100F() {
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

    // ----------

    @ParameterizedTest
    @CsvSource({ "0,32", "-273.15,-459.67", "37.5,99.5", "42,107.6", "100,212", "-100,-148", "-10,14" })
    void ensureConvertsCelsiusToFarenheit(final double in, final double out) {
        final var subject = Temperature.celsius(in);
        final var r = subject.toFarenheit();

        assertEquals(out, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.FARENHEIT, r.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0,273.15", "-273.15,0", "37.5,310.65", "42,315.15", "100,373.15", "-100,173.15", "-10,263.15" })
    void ensureConvertsCelsiusToKelvin(final double in, final double out) {
        final var subject = Temperature.celsius(in);
        final var r = subject.toKelvin();

        assertEquals(out, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.KELVIN, r.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0,-459.67", "42,-384.07", "100,-279.67", "273.15,32", "310.75,99.68", "373.15,212" })
    void ensureConvertsKelvinToFarenheit(final double in, final double out) {
        final var subject = Temperature.kelvin(in);
        final var r = subject.toFarenheit();

        assertEquals(out, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.FARENHEIT, r.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0,-273.15", "42,-231.15", "100,-173.15", "273.15,0", "310.65,37.5", "373.15,100" })
    void ensureConvertsKelvinToCelsius(final double in, final double out) {
        final var subject = Temperature.kelvin(in);
        final var r = subject.toCelsius();

        assertEquals(out, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.CELSIUS, r.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0,-17.7777778", "42,5.5555556", "100,37.7777778", "-459.67, -273.15", "99.5,37.5", "212,100" })
    void ensureConvertsFarenheitToCelsius(final double in, final double out) {
        final var subject = Temperature.farenheit(in);
        final var r = subject.toCelsius();

        assertEquals(out, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.CELSIUS, r.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0,255.3722222", "42,278.7055556", "100,310.9277778", "-459.67,0", "99.5,310.65", "212,373.15" })
    void ensureConvertsFarenheitToKelvin(final double in, final double expected) {
        final var subject = Temperature.farenheit(in);
        final var r = subject.toKelvin();

        assertEquals(expected, r.magnitude().doubleValue());
        assertEquals(TemperatureUnit.KELVIN, r.unit());
    }

    // ----------

    @Test
    void ensureCannotAddDifferentUnitsCK() {
        final var t1 = Temperature.celsius(100);
        final var t2 = Temperature.kelvin(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @Test
    void ensureCannotAddDifferentUnitsCF() {
        final var t1 = Temperature.celsius(100);
        final var t2 = Temperature.farenheit(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @Test
    void ensureCannotAddDifferentUnitsKC() {
        final var t1 = Temperature.kelvin(100);
        final var t2 = Temperature.celsius(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @Test
    void ensureCannotAddDifferentUnitsKF() {
        final var t1 = Temperature.kelvin(100);
        final var t2 = Temperature.farenheit(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @Test
    void ensureCannotAddDifferentUnitsFC() {
        final var t1 = Temperature.farenheit(100);
        final var t2 = Temperature.celsius(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @Test
    void ensureCannotAddDifferentUnitsFK() {
        final var t1 = Temperature.farenheit(100);
        final var t2 = Temperature.kelvin(100);

        assertThrows(IllegalArgumentException.class, () -> {
            t1.add(t2);
        });
    }

    @ParameterizedTest
    @CsvSource({ "0,0,0", "0,10,10", "0,42,42", "0,-10,-10", "10,-10,0", "10,10,20", "1,0,1", "1,-1,0", "21,21,42" })
    void ensureAddsCelsius(final double in1, final double in2, final double expected) {
        final var t1 = Temperature.celsius(in1);
        final var t2 = Temperature.celsius(in2);
        final var actual = t1.add(t2).magnitude().doubleValue();

        assertEquals(expected, actual);
    }

    // ----------

    void ensureCannotSubtractDifferentUnits() {

    }

    void ensureSubtractsProperly() {

    }

    // ----------

    void ensureScalesProperly() {

    }
}
