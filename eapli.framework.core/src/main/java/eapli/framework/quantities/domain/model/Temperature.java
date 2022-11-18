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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Collector;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.FormattedString;
import eapli.framework.validations.Preconditions;

/**
 * A temperature (magnitude and unit) with a precision of two decimals.
 * <p>
 * This is a «quantity» value object, thus immutable.
 *
 * <p>
 * Example Usage:
 *
 * <pre>
 * <code>
 * Temperature a = Temperature.dollars(12.98);
 * Temperature b = Temperature.dollars(-11.98);
 * Temperature expected = Temperature.dollars(1.00);
 * Temperature result = a.add(b);
 * assert expected.equals(result);
 * </code>
 * </pre>
 *
 * @author Paulo Gandra de Sousa 18/11/2022
 */
@Embeddable
public class Temperature implements Comparable<Temperature>, Serializable, ValueObject, FormattedString {

    /**
     * @author Paulo Gandra de Sousa 18/11/2022
     *
     */
    public enum TemperatureUnit {
        KELVIN("K"),

        CELSIUS("C"),

        FARENHEIT("F");

        private String symbol;

        TemperatureUnit(final String symbol) {
            this.symbol = symbol;
        }

        abstract BigDecimal toCelsius(BigDecimal org);

        abstract BigDecimal toKelvin(BigDecimal org);

        abstract BigDecimal toFarenheit(BigDecimal org);
    }

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    @JsonProperty
    private final BigDecimal magnitude;

    @XmlAttribute
    @JsonProperty
    private final TemperatureUnit unit;

    /**
     * For ORM tool only
     */
    protected Temperature() {
        magnitude = null;
        unit = null;
    }

    /**
     * Constructs a new Temperature object.
     *
     * @param magnitude
     * @param unit
     */
    private Temperature(final double magnitude, final TemperatureUnit unit) {
        this(BigDecimal.valueOf(magnitude), unit);
    }

    private Temperature(final BigDecimal magnitude, final TemperatureUnit unit) {
        assert magnitude != null;
        assert unit != null;

        this.magnitude = magnitude;
        this.unit = unit;
    }

    /**
     * Factory method to construct a Temperature object of {@code magnitude} degrees Celsius.
     *
     * @param magnitude
     * @return a Temperature object of {@code magnitude}
     *
     */
    public static Temperature celsius(final double magnitude) {
        return new Temperature(magnitude, TemperatureUnit.CELSIUS);
    }

    /**
     * Factory method to construct a Temperature object of {@code magnitude} degrees Kelvin.
     *
     * @param magnitude
     * @return a Temperature object of magnitude EUR
     */
    public static Temperature kelvin(final double magnitude) {
        return new Temperature(magnitude, TemperatureUnit.KELVIN);
    }

    /**
     * Factory method to construct a Temperature object of {@code magnitude} degrees Farenheit.
     *
     * @param magnitude
     * @return a Temperature object of magnitude EUR
     */
    public static Temperature farenheit(final double magnitude) {
        return new Temperature(magnitude, TemperatureUnit.FARENHEIT);
    }

    public Temperature toCelsius() {

    }

    public Temperature toKelvin() {

    }

    public Temperature toFarenheit() {

    }

    /**
     * Parses a string representation of a Temperature, e.g., "123.50 C".
     *
     * @param value
     * @return a new object corresponding to the value
     */
    public static Temperature valueOf(final String value) {
        final String[] parts = value.split(" ");
        Preconditions.areEqual(parts.length, 2);

        final double am = Double.parseDouble(parts[0]);
        final TemperatureUnit curr = TemperatureUnit.valueOf(parts[1]);
        return new Temperature(am, curr);
    }

    /**
     * Creates a collector that can be used to sum up a stream of Temperature objects in a
     * reduction style. That is, since Temperature is an immutable object, collecting
     * Temperature objects from streams have the implication of creating a new Temperature
     * object for each collect step which might impact performance.
     *
     * @param zero
     * @return a collector instance
     */
    public static Collector<Temperature, TemperatureCollector, Temperature> collector(final Temperature zero) {
        /*
         * alternatively the collector returned could use an internal mutable
         * representation of Temperature and just create an instance when combining streams
         *
         */
        Preconditions.nonNull(zero);

        return Collector.of(() -> new TemperatureCollector(zero), TemperatureCollector::add,
                TemperatureCollector::combine,
                a -> a.current);
    }

    /**
     * A collector class to use with Java 8 Streams.
     *
     */
    private static class TemperatureCollector {
        private Temperature current;

        public TemperatureCollector(final Temperature zero) {
            current = zero;
        }

        public void add(final Temperature b) {
            current = current.add(b);
        }

        public TemperatureCollector combine(final TemperatureCollector other) {
            current = current.add(other.current);
            return this;
        }
    }

    /**
     * Returns the magnitude portion of this Temperature object as a {@code double}. Notice
     * that there might be precision problems.
     *
     * @return the magnitude portion of this Temperature object
     */
    public double magnitudeAsDouble() {
        /*
         * Notice that I [Martin Fowler] use a BigInteger. In Java I could equally well
         * use a BigDecimal, but in many languages an integer is the only decent option,
         * so using an integer seems the best for explanation. Don't be afraid to choose
         * your representation of the magnitude part of a quantity based on performance
         * factors. The beauty of objects is that you can choose any data structure you
         * like on the inside, providing you hide it on the outside.
         */
        return magnitude.doubleValue() / HUNDRED;
    }

    /**
     * Returns the magnitude portion of this Temperature object.
     *
     * @return the magnitude portion of this Temperature object
     */
    public BigDecimal value() {
        return new BigDecimal(magnitude).divide(BigDecimal.valueOf(HUNDRED));
    }

    /**
     * Returns the unit of this Temperature object.
     *
     * @return the unit
     */
    public TemperatureUnit unit() {
        return unit;
    }

    /**
     * Adds two Temperature objects and returns the result as a new object.
     *
     * @param arg
     * @return a new Temperature with the value of the addition
     * @throws IllegalArgumentException
     *             if the units are not the same
     */
    public Temperature add(final Temperature arg) {
        Preconditions.ensure(this.isOfSameunit(arg), "Cannot add different units");

        return new Temperature(magnitude.add(arg.magnitude), unit);
    }

    public Temperature increase(final double delta) {

    }

    public Temperature decrease(final double delta) {

    }

    /**
     * Subtracts two Temperature instances and returns a third one with the result.
     *
     * @param arg
     * @return a new value corresponding to the subtraction
     * @throws IllegalArgumentException
     *             if the currencies are not the same
     */
    public Temperature subtract(final Temperature arg) {
        return add(arg.negate());
    }

    /**
     * Returns a new object which magnitude's sign is swapped, i.e., -this.magnitude. E.g.
     *
     * <p>
     *
     * <pre>
     * <code>
     * Temperature m1 = Temperature.euros(100);
     * Temperature m2 = m1.negate(); // -100 EUR
     * </code>
     * </pre>
     *
     * @return a new object which magnitude's sign is swapped
     */
    public Temperature negate() {
        return new Temperature(magnitude.negate(), unit);
    }

    /**
     * Multiplies this Temperature object by a scalar and returns the result as a new
     * Temperature.
     *
     * @param arg
     * @return a new Temperature with the value of the multiplication
     *
     */
    public Temperature scale(final double arg) {
        return new Temperature(magnitudeAsDouble() * arg, unit);
    }

    /**
     * Divides this Temperature object by a certain integer denominator doing the right
     * allocation of cents.
     *
     * <p>
     * Multiplication is very straightforward. But division is not, as we have to
     * take care of errant pennies. We'll do that by returning an array of monies,
     * such that the sum of the array is equal to the original magnitude, and the
     * original magnitude is distributed fairly between the elements of the array.
     * Fairly in this sense means those at the beginning get the extra pennies.
     *
     * @param denominator
     * @return an array of Temperature objects corresponding to the allocation of each
     *         denominator
     */
    public Temperature[] divide(final int denominator) {
        final BigInteger bigDenominator = BigInteger.valueOf(denominator);
        final Temperature[] result = new Temperature[denominator];
        final BigInteger simpleResult = magnitude.divide(bigDenominator);
        for (int i = 0; i < denominator; i++) {
            result[i] = new Temperature(simpleResult, unit);
        }
        final int remainder = magnitude.subtract(simpleResult.multiply(bigDenominator)).intValue();
        for (int i = 0; i < remainder; i++) {
            result[i] = result[i].add(new Temperature(BigInteger.valueOf(1), unit));
        }
        return result;
    }

    /**
     * Checks if this object is of the same unit than another object.
     *
     * @param other
     * @return {@code true} if this object is of the same unit than another
     *         object
     */
    public boolean isOfSameunit(final Temperature other) {
        return this.unit().equals(other.unit());
    }

    /**
     * Compares two Temperature objects.
     *
     */
    @Override
    public int compareTo(final Temperature arg) {
        Preconditions.areEqual(unit, arg.unit, "Cannot add different currencies");

        /*
         * Next we'll look at comparing monies, in Java the approach is to implement
         * comparable.
         */
        return magnitude.compareTo(arg.magnitude);
    }

    /**
     * Compares two Temperature objects checking if this Temperature's magnitude is greater than
     * the other
     *
     * @param arg
     * @return true if this Temperature's magnitude is greater than the other
     */
    public boolean isGreaterThan(final Temperature arg) {
        /*
         * It's also useful to provide some better named operations such as greaterThan.
         * That makes methods that need the comparison much easier to read.
         */
        return compareTo(arg) > 0;
    }

    /**
     * Compares two Temperature objects checking if this Temperature's magnitude is less than the
     * other
     *
     * @param arg
     * @return true if this Temperature's magnitude is less than the other
     */
    public boolean isLessThan(final Temperature arg) {
        /*
         * It's also useful to provide some better named operations such as greaterThan.
         * That makes methods that need the comparison much easier to read.
         */
        return compareTo(arg) < 0;
    }

    /**
     * Compares two Temperature objects checking if this Temperature's magnitude is greater than or
     * equal to the other.
     *
     * @param arg
     * @return {@code true} if this Temperature's magnitude is greater than or equal to the
     *         other
     */
    public boolean isGreaterThanOrEqual(final Temperature arg) {
        return compareTo(arg) >= 0;
    }

    /**
     * Compares two Temperature objects checking if this Temperature's magnitude is less than or
     * equal to the other.
     *
     * @param arg
     * @return {@code true} if this Temperature's magnitude is less than or equal to the
     *         other
     */
    public boolean isLessThanOrEqual(final Temperature arg) {
        return compareTo(arg) <= 0;
    }

    /**
     * Since Temperature is a value, it should override equals.
     */
    @Override
    public boolean equals(final Object arg) {
        if (!(arg instanceof Temperature)) {
            return false;
        }
        final Temperature other = (Temperature) arg;
        return unit.equals(other.unit) && magnitude.equals(other.magnitude);
    }

    /**
     * Since you override equals, don't forget to also override hash (here's a
     * simple suggestion for that).
     */
    @Override
    public int hashCode() {
        int result = 11;
        result = 37 * result + magnitude.hashCode();
        result = 37 * result + unit.hashCode();
        return result;
    }

    public int signum() {
        return magnitude.signum();
    }

    /**
     * Checks if this magnitude is negative.
     *
     * @return {@code true} if this magnitude is negative
     */
    @JsonIgnore
    public boolean isNegative() {
        return signum() == -1;
    }

    /**
     * Checks if this magnitude is positive.
     *
     * @return {@code true} if this magnitude is positive
     */
    @JsonIgnore
    public boolean isPositive() {
        return signum() == 1;
    }

    /**
     * Checks if this magnitude is zero.
     *
     * @return {@code true} if this magnitude is zero
     */
    @JsonIgnore
    public boolean isZero() {
        return signum() == 0;
    }

    /**
     * Returns a formatted representation of this Temperature value according to the
     * Locale unit format.
     * <p>
     * Scraped from <a href=
     * "http://cameotutorials.blogspot.com/2009/06/Temperature-class-for-use-in-unit.html">cameotutorials</a>
     *
     * @return a formatted representation of this Temperature value based on the rules
     *         defined by the unit Locale
     */
    @Override
    public String toString() {
        return magnitude + " " + unit;
    }
}
