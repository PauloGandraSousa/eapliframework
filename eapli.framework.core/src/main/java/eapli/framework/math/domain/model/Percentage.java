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
package eapli.framework.math.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import lombok.EqualsAndHashCode;

/**
 * @author Paulo Gandra de Sousa 06/09/2022
 *
 */
@Embeddable
@EqualsAndHashCode
public class Percentage implements ValueObject, Comparable<Percentage>, Serializable {

	public static final Percentage ZERO_PER_CENT = new Percentage(BigDecimal.ZERO);
	public static final Percentage ONE_PER_CENT = new Percentage(BigDecimal.ONE);
	public static final Percentage ONE_HUNDRED_PER_CENT = new Percentage(BigDecimal.valueOf(100));

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "percentage")
	@XmlAttribute
	@JsonProperty("percentage")
	private final BigDecimal value;

	/**
	 *
	 * @param perc
	 */
	public Percentage(final BigDecimal perc) {
		Preconditions.nonNull(perc);
		value = perc;
	}

	public Percentage(final long perc) {
		this(BigDecimal.valueOf(perc));
	}

	public Percentage(final double perc) {
		this(BigDecimal.valueOf(perc));
	}

	/**
	 * Constructs a percentage value from a ratio.
	 * <p>
	 * For example, 2 out of 4, the ratio 2:4, is equivalent to 50%
	 *
	 * @param n
	 * @param many
	 * @return
	 */
	public static Percentage ofRatio(final long n, final long many) {
		return new Percentage(BigDecimal.valueOf((double) n / many * 100));
	}

	public static Percentage valueOf(final BigDecimal perc) {
		return new Percentage(perc);
	}

	/**
	 * Adds two percentages.
	 * <p>
	 * For example:
	 * <ul>
	 * <li>2.25% + 1% = 3.25%
	 * <li>2.25% + 0.25% = 2.5%
	 * </ul>
	 *
	 * @param other
	 * @return
	 */
	public Percentage increase(final Percentage other) {
		return new Percentage(value.add(other.value));
	}

	/**
	 * Increases the current percentage by <em>n</em> basis points (BP). A basis
	 * point is a unit frequently used in finance and corresponds to the hundredth
	 * part of 1%, or 1 permyriad.
	 * <p>
	 * For example,
	 * <ul>
	 * <li>2.25% + 1 bps = 2.26%
	 * <li>2.25% + 25 bps = 2.5%
	 * <li>2.50% + 0.5 bps = 2.505%
	 * </ul>
	 *
	 * @param basisPoints
	 *
	 * @return
	 */
	public Percentage increase(final double basisPoints) {
		final var bps = BigDecimal.valueOf(basisPoints).divide(BigDecimal.valueOf(100));
		return new Percentage(value.add(bps));
	}

	/**
	 *
	 * @param other
	 * @return
	 */
	public Percentage decrease(final Percentage other) {
		return new Percentage(value.subtract(other.value));
	}

	/**
	 * Decreases the current percentage by <em>n</em> basis points (BP). A basis
	 * point is a unit frequently used in finance and corresponds to the hundredth
	 * part of 1%, or 1 permyriad.
	 * <p>
	 * For example,
	 * <ul>
	 * <li>2.25% - 1 bps = 2.24%
	 * <li>2.25% - 25 bps = 2.0%
	 * <li>2.50% - 0.5 bps = 2.495%
	 * </ul>
	 *
	 * @param basisPoints
	 *
	 * @return
	 */
	public Percentage decrease(final double basisPoints) {
		final var bps = BigDecimal.valueOf(basisPoints).divide(BigDecimal.valueOf(100));
		return new Percentage(value.subtract(bps));
	}

	/**
	 *
	 * @return
	 */
	public Percentage negate() {
		return new Percentage(value.negate());
	}

	/**
	 * Calculates the value of this percentage out of a number.
	 * <p>
	 * For example, 10% out of 50 is 5.
	 *
	 * @param value
	 * @return
	 */
	public BigDecimal outOf(final BigDecimal value) {
		return this.value.divide(BigDecimal.valueOf(100)).multiply(value);
	}

	/**
	 * Checks the "signal" of this percentage. That is, if it is negative, zero, or
	 * positive.
	 *
	 * @return -1 if the percentage value is negative, 1 if the percentage value is
	 *         positive, 0 otherwise.
	 */
	public int signum() {
		return value.signum();
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

	@Override
	public int compareTo(final Percentage o) {
		return value.compareTo(o.value);
	}

	@Override
	public String toString() {
		return value.toString() + "%";
	}
}
