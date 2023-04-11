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
package eapli.framework.money.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.FormattedString;
import eapli.framework.validations.Preconditions;

/**
 * A cash flow, i.e., an income or expenditure on a certain date.
 * <p>
 * Example Usage:
 *
 * <pre>
 * <code>
 * var today = LocalDateTime.now();
 * var a = new CashFlow(Money.dollars(12.98), today);
 * var b = new CashFlow(Money.dollars(-11.98), today);
 * var expected = new CashFlow(Money.dollars(1.00), today);
 * var result = a.add(b);
 * assert expected.equals(result);
 * </code>
 * </pre>
 *
 * @author Paulo Gandra de Sousa 2023.03.31
 */
@Embeddable
public class CashFlow implements Comparable<CashFlow>, Serializable, ValueObject, FormattedString {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    @JsonProperty
    private final Money amount;

    @XmlAttribute
    @JsonProperty
    private final LocalDate happeneddOn;

    /**
     * For ORM tool only
     */
    protected CashFlow() {
        amount = null;
        happeneddOn = null;
    }

    /**
     * Constructs a new cash flow.
     */
    public CashFlow(final Money amount, final LocalDate on) {
        Preconditions.noneNull(amount, on);

        this.amount = amount;
        this.happeneddOn = on;
    }

    /**
     * Returns the amount.
     *
     * @return the amount
     */
    public Money amount() {
        return amount;
    }

    /**
     * Returns the date this cash flow happened.
     *
     * @return the date
     */
    public LocalDate happenedOn() {
        return happeneddOn;
    }

    /**
     * Adds two cash flows objects and returns the result as a new object.
     *
     * @param arg
     *
     * @return a new cash flow with the value of the addition
     *
     * @throws IllegalArgumentException
     *             if the dates of the cash flows are not the same
     */
    public CashFlow add(final CashFlow arg) {
        /*
         * For addition and subtraction I'm [Martin Fowler] not trying to do any fancy
         * conversion. Notice that I'm using a special constructor with a marker
         * argument.
         */
        Preconditions.areEqual(happeneddOn, arg.happeneddOn, "Cannot add different currencies");

        return new CashFlow(amount.add(arg.amount), happeneddOn);
    }

    /**
     * Subtracts two cash flows .
     *
     * @param arg
     *
     * @return a new value corresponding to the subtraction
     *
     * @throws IllegalArgumentException
     *             if the dates of the cash flows are not the same
     */
    public CashFlow subtract(final CashFlow arg) {
        return add(arg.negate());
    }

    /**
     * Returns a new object which amount's sign is swapped, i.e., -this.amount. E.g.
     * <p>
     *
     * <pre>
     * <code>
     * var c1 = new CashFlow(Money.euros(100), LocalDate.now());
     * var c2 = c1.negate(); // -100 EUR
     * </code>
     * </pre>
     *
     * @return a new object which amount's sign is swapped
     */
    public CashFlow negate() {
        return new CashFlow(amount.negate(), happeneddOn);
    }

    /**
     * Compares two cash flows.
     * <ul>
     * <li>Two cashflows are the same if they have the same amount and date.
     * <li>Otherwise a cashflow is prior to another if it was realized on a previous day, or
     * <li>is after another cashflow if it was realized on a date after the other.
     * <li>if two cashflows are realized on the same date, the cashflow with the smaller amount will
     * be
     * prior to the other.
     * </ul>
     */
    @Override
    public int compareTo(final CashFlow arg) {

        final var d = happeneddOn.compareTo(arg.happeneddOn);
        if (d != 0) {
            return d;
        } else {
            return amount.compareTo(arg.amount);
        }
    }

    @Override
    public boolean equals(final Object arg) {
        if (!(arg instanceof CashFlow)) {
            return false;
        }
        final CashFlow other = (CashFlow) arg;
        return happeneddOn.equals(other.happeneddOn) && amount.equals(other.amount);
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = 37 * result + amount.hashCode();
        result = 37 * result + happeneddOn.hashCode();
        return result;
    }

    /**
     * @return
     */
    public int signum() {
        return amount.signum();
    }

    /**
     * Checks if this amount is negative.
     *
     * @return {@code true} if this amount is negative
     */
    @JsonIgnore
    public boolean isNegative() {
        return signum() == -1;
    }

    /**
     * Checks if this amount is positive.
     *
     * @return {@code true} if this amount is positive
     */
    @JsonIgnore
    public boolean isPositive() {
        return signum() == 1;
    }

    /**
     * Checks if this amount is zero.
     *
     * @return {@code true} if this amount is zero
     */
    @JsonIgnore
    public boolean isZero() {
        return signum() == 0;
    }

    /**
     * Returns a formatted representation of this cash flow
     *
     * @return a formatted representation of this cash flow
     */
    @Override
    public String toString() {
        return amount() + " (" + happeneddOn + ")";
    }
}
