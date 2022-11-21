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
import java.math.RoundingMode;
import java.util.stream.Collector;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.FormattedString;
import eapli.framework.validations.Preconditions;

/**
 * A temperature (magnitude and unit) in one of the Kelvin, Celsius or Farenheit
 * scales.
 * <p>
 * This is a «quantity» value object, thus immutable.
 *
 * <p>
 * Example Usage:
 *
 * <pre>
 * <code>
 * Temperature a = Temperature.celsius(22.5);
 * Temperature b = a.increase(1);
 *
 * assert b.magnitude.equals(BigDecimal.valueOf(23.5));
 * assert b.unit().equals(TemperatureUnit.CELSIUS);
 *
 * Temperature c = a.toKelvin();
 *
 * assert c.magnitude.equals(BigDecimal.valueOf(296.65));
 * assert c.unit().equals(TemperatureUnit.KELVIN);
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
		KELVIN("K") {
			@Override
			BigDecimal toCelsius(BigDecimal org) {
				// 0K − 273.15
				return org.subtract(KELVIN_DELTA);
			}

			@Override
			BigDecimal toKelvin(BigDecimal org) {
				return org;
			}

			@Override
			BigDecimal toFarenheit(BigDecimal org) {
				// (0K − 273.15) × 9/5 + 32
				return CELSIUS.toFarenheit(toCelsius(org));
			}
		},

		CELSIUS("C") {
			@Override
			BigDecimal toCelsius(BigDecimal org) {
				return org;
			}

			@Override
			BigDecimal toKelvin(BigDecimal org) {
				// 0°C + 273.15
				return org.add(KELVIN_DELTA);
			}

			@Override
			BigDecimal toFarenheit(BigDecimal org) {
				// (0°C × 9/5) + 32
				return org.multiply(BigDecimal.valueOf(1.8d)).add(FARENHEIT_DELTA);
			}
		},

		FARENHEIT("F") {
			@Override
			BigDecimal toCelsius(BigDecimal org) {
				// (32°F − 32) × 5/9
				return org.subtract(FARENHEIT_DELTA).multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(9), 7,
						RoundingMode.HALF_UP);
			}

			@Override
			BigDecimal toKelvin(BigDecimal org) {
				return CELSIUS.toKelvin(toCelsius(org));
			}

			@Override
			BigDecimal toFarenheit(BigDecimal org) {
				return org;
			}
		};

		private static final BigDecimal FARENHEIT_DELTA = BigDecimal.valueOf(32);

		private static final BigDecimal KELVIN_DELTA = BigDecimal.valueOf(273.15d);

		private String symbol;

		TemperatureUnit(final String symbol) {
			this.symbol = symbol;
		}

		abstract BigDecimal toCelsius(BigDecimal org);

		abstract BigDecimal toKelvin(BigDecimal org);

		abstract BigDecimal toFarenheit(BigDecimal org);
	}

	private static final long serialVersionUID = 1L;

	public static final Temperature ZERO_KELVIN = kelvin(0);
	public static final Temperature ZERO_CELSIUS = celsius(0);
	public static final Temperature HUNDRED_CELSIUS = celsius(100);
	public static final Temperature ZERO_FARENHEIT = farenheit(0);

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

		// there is no temperature bellow 0 K
		Preconditions.ensure(unit.toKelvin(magnitude).compareTo(BigDecimal.ZERO) >= 0);

		this.magnitude = magnitude;
		this.unit = unit;
	}

	/**
	 * Factory method to construct a Temperature object of {@code magnitude} degrees
	 * Celsius.
	 *
	 * @param magnitude
	 * @return a Temperature object of {@code magnitude}
	 *
	 */
	public static Temperature celsius(final double magnitude) {
		return new Temperature(magnitude, TemperatureUnit.CELSIUS);
	}

	/**
	 * Factory method to construct a Temperature object of {@code magnitude} degrees
	 * Kelvin.
	 *
	 * @param magnitude
	 * @return a Temperature object of magnitude EUR
	 */
	public static Temperature kelvin(final double magnitude) {
		return new Temperature(magnitude, TemperatureUnit.KELVIN);
	}

	/**
	 * Factory method to construct a Temperature object of {@code magnitude} degrees
	 * Farenheit.
	 *
	 * @param magnitude
	 * @return a Temperature object of magnitude EUR
	 */
	public static Temperature farenheit(final double magnitude) {
		return new Temperature(magnitude, TemperatureUnit.FARENHEIT);
	}

	public Temperature toCelsius() {
		return new Temperature(unit.toCelsius(magnitude), TemperatureUnit.CELSIUS);
	}

	public Temperature toKelvin() {
		return new Temperature(unit.toKelvin(magnitude), TemperatureUnit.KELVIN);
	}

	public Temperature toFarenheit() {
		return new Temperature(unit.toFarenheit(magnitude), TemperatureUnit.FARENHEIT);
	}

	/**
	 * Parses a string representation of a Temperature, e.g., "123.50 C".
	 *
	 * @param value
	 * @return a new object corresponding to the value
	 */
	public static Temperature valueOf(final String value) {
		final var parts = value.split(" ");
		Preconditions.areEqual(parts.length, 2);

		final var am = Double.parseDouble(parts[0]);
		final var curr = TemperatureUnit.valueOf(parts[1]);
		return new Temperature(am, curr);
	}

	/**
	 * Creates a collector that can be used to sum up a stream of Temperature
	 * objects in a reduction style. That is, since Temperature is an immutable
	 * object, collecting Temperature objects from streams have the implication of
	 * creating a new Temperature object for each collect step which might impact
	 * performance.
	 *
	 * @param zero
	 * @return a collector instance
	 */
	public static Collector<Temperature, TemperatureCollector, Temperature> collector(final Temperature zero) {
		/*
		 * alternatively the collector returned could use an internal mutable
		 * representation of Temperature and just create an instance when combining
		 * streams
		 *
		 */
		Preconditions.nonNull(zero);

		return Collector.of(() -> new TemperatureCollector(zero), TemperatureCollector::add,
				TemperatureCollector::combine, a -> a.current);
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
	 * Returns the magnitude portion of this Temperature object.
	 *
	 * @return the magnitude portion of this Temperature object
	 */
	public BigDecimal magnitude() {
		return magnitude;
	}

	/**
	 * Returns the magnitude portion of this Temperature object.
	 *
	 * @return the magnitude portion of this Temperature object
	 */
	public BigDecimal value() {
		return magnitude();
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
	 * @throws IllegalArgumentException if the units are not the same
	 */
	public Temperature add(final Temperature arg) {
		Preconditions.ensure(this.isOfSameUnit(arg), "Cannot add different units");

		return new Temperature(magnitude.add(arg.magnitude), unit);
	}

	/**
	 * Increases the current temperature by a specific amount
	 *
	 * @param delta
	 * @return a new temperature corresponding to the value of the current
	 *         temperature increased by <code>delta</code>
	 */
	public Temperature increase(final double delta) {
		return increase(BigDecimal.valueOf(delta));
	}

	/**
	 * Increases the current temperature by a specific amount
	 *
	 * @param delta
	 * @return a new temperature corresponding to the value of the current
	 *         temperature increased by <code>delta</code>
	 */
	public Temperature decrease(final double delta) {
		return decrease(BigDecimal.valueOf(delta));
	}

	/**
	 * Decreases the current temperature by a specific amount
	 *
	 * @param delta
	 * @return a new temperature corresponding to the value of the current
	 *         temperature decreased by <code>delta</code>
	 */
	public Temperature increase(final BigDecimal delta) {
		return new Temperature(magnitude.add(delta), unit);
	}

	/**
	 * Decreases the current temperature by a specific amount
	 *
	 * @param delta
	 * @return a new temperature corresponding to the value of the current
	 *         temperature decreased by <code>delta</code>
	 */
	public Temperature decrease(final BigDecimal delta) {
		return new Temperature(magnitude.subtract(delta), unit);
	}

	/**
	 * Subtracts two Temperature instances and returns a third one with the result.
	 *
	 * @param arg
	 * @return a new value corresponding to the subtraction
	 * @throws IllegalArgumentException if the currencies are not the same
	 */
	public Temperature subtract(final Temperature arg) {
		return add(arg.negate());
	}

	/**
	 * Returns a new object which magnitude's sign is swapped, i.e.,
	 * -this.magnitude. E.g.
	 *
	 * <p>
	 *
	 * <pre>
	 * <code>
	 * Temperature m1 = Temperature.celsius(100);
	 * Temperature m2 = m1.negate(); // -100 C
	 * </code>
	 * </pre>
	 *
	 * @return a new object which magnitude's sign is swapped
	 */
	public Temperature negate() {
		return new Temperature(magnitude.negate(), unit);
	}

	/**
	 * Multiplies this Temperature object by a scalar and returns the result as a
	 * new Temperature.
	 *
	 * @param arg
	 * @return a new Temperature with the value of the multiplication
	 *
	 */
	public Temperature scale(final double arg) {
		return new Temperature(magnitude.multiply(BigDecimal.valueOf(arg)), unit);
	}

	/**
	 * Checks if this object is of the same unit than another object.
	 *
	 * @param other
	 * @return {@code true} if this object is of the same unit than another object
	 */
	public boolean isOfSameUnit(final Temperature other) {
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
	 * Compares two Temperature objects checking if this Temperature's magnitude is
	 * greater than the other
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
	 * Compares two Temperature objects checking if this Temperature's magnitude is
	 * less than the other
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
	 * Compares two Temperature objects checking if this Temperature's magnitude is
	 * greater than or equal to the other.
	 *
	 * @param arg
	 * @return {@code true} if this Temperature's magnitude is greater than or equal
	 *         to the other
	 */
	public boolean isGreaterThanOrEqual(final Temperature arg) {
		return compareTo(arg) >= 0;
	}

	/**
	 * Compares two Temperature objects checking if this Temperature's magnitude is
	 * less than or equal to the other.
	 *
	 * @param arg
	 * @return {@code true} if this Temperature's magnitude is less than or equal to
	 *         the other
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
		final var other = (Temperature) arg;
		return unit.equals(other.unit) && magnitude.equals(other.magnitude);
	}

	/**
	 * Since you override equals, don't forget to also override hash (here's a
	 * simple suggestion for that).
	 */
	@Override
	public int hashCode() {
		var result = 11;
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
	 * @return a formatted representation of this Temperature value based on the
	 *         rules defined by the unit Locale
	 */
	@Override
	public String toString() {
		return magnitude + " " + unit;
	}
}
