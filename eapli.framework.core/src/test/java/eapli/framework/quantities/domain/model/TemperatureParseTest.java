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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import eapli.framework.quantities.domain.model.Temperature.TemperatureUnit;

/**
 * @author Paulo Gandra de Sousa 22/11/2022
 *
 */
class TemperatureParseTest {

    @ParameterizedTest
    @CsvSource({ "0K,0", "0 K,0", ".5 K,0.5", "+.5 K,0.5", "37.5K,37.5", "42.5 K,42.5", "+37.5K,37.5", "+42.5 K,42.5",
            "5778 K,5778",
            // center of the sun
            "15000273.15 K,15000273.15",
            // "atom smasher" at the Brookhaven National Laboratory in Upton
            "40000000000000000273.15 K,40000000000000000273.15"
    })

    void ensureParsesKelvin(final String in, final double value) {
        final var subject = Temperature.parse(in);

        assertEquals(BigDecimal.valueOf(value), subject.value());
        assertEquals(TemperatureUnit.KELVIN, subject.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0C,0", "0 C,0", ".5 C,0.5", "+.5 C,0.5", "-.5 C,-0.5", "37.5C,37.5", "42.5 C,42.5",
            "-273.15C,-273.15", "-273.15 C,-273.15", "+273.15C,273.15", "+273.15 C,273.15",
            "5505 C,5505",
            // center of the sun
            "15000000 C,15000000",
            // "atom smasher" at the Brookhaven National Laboratory in Upton
            "40000000000000000000 C,40000000000000000000" })
    void ensureParsesCelsius(final String in, final double value) {
        final var subject = Temperature.parse(in);

        assertEquals(BigDecimal.valueOf(value), subject.value());
        assertEquals(TemperatureUnit.CELSIUS, subject.unit());
    }

    @ParameterizedTest
    @CsvSource({ "0F,0", "0 F,0", ".5 F,0.5", "+.5 F,0.5", "-.5 F,-0.5", "37.5F,37.5", "42.5 F,42.5",
            "-273.15F,-273.15", "-273.15 F,-273.15", "+273.15F,273.15", "+273.15 F,273.15", "9941 F, 9941",
            // center of the sun
            "27000032 F,27000032",
            // "atom smasher" at the Brookhaven National Laboratory in Upton
            "72000000000000000000 F,72000000000000000000"
    })
    void ensureParsesFarenheit(final String in, final double value) {
        final var subject = Temperature.parse(in);

        assertEquals(BigDecimal.valueOf(value), subject.value());
        assertEquals(TemperatureUnit.FARENHEIT, subject.unit());
    }

    @ParameterizedTest
    @ValueSource(strings = { "0A", "0 A", "+1A", "+1 A", "+1.5A", "+1.5 A", "-1A", "-1 A", "-1.5A", "-1.5 A" })
    void ensureCannotParseUnknownUnit(final String in) {
        assertThrows(IllegalArgumentException.class, () -> {
            Temperature.parse(in);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "0", "K", "C", "F", "+1.5", "-1", "1", "+1", "-K", "+K" })
    void ensureCannotParseInvalidString(final String in) {
        assertThrows(IllegalArgumentException.class, () -> {
            Temperature.parse(in);
        });
    }
}
