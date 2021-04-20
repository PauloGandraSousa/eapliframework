/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.general.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.stream.Collector;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.FormattedString;
import eapli.framework.strings.XmlCurrencyAdapter;
import eapli.framework.validations.Preconditions;

/**
 * A money value (amount and currency).
 *
 * <p>
 * For a full description see the book Patterns of Enterprise Application Architecture (Martin
 * Fowler) page 488 or <a href="https://www.martinfowler.com/eaaCatalog/money.html">PoEAA Money</a>.
 * See also a <a href=
 * "https://deque.blog/2017/08/17/a-study-of-4-money-class-designs-featuring-martin-fowler-kent-beck-and-ward-cunningham-implementations/">good
 * discussion</a> about pros and cons and different approaches.
 *
 * <p>
 * <blockquote> A large proportion of the computers in this world manipulate money, so it's always
 * puzzled me that money isn't actually a first class data type in any mainstream programming
 * language. The lack of a type causes problems, the most obvious surrounding currencies. If all
 * your calculations are done in a single m_currency, this isn't a huge problem, but once you
 * involve multiple currencies you want to avoid adding your dollars to your yen without taking the
 * m_currency differences into account. The more subtle problem is with rounding. Monetary
 * calculations are often rounded to the smallest m_currency unit. When you do this it's easy to
 * lose pennies (or your local equivalent) because of rounding errors.
 *
 * <p>
 * The good thing about object-oriented programming is that you can fix these problems by creating a
 * Money class that handles them. Of course, it's still surprising that none of the mainstream base
 * class libraries actually do this.
 * <p>
 * - Martin Fowler </blockquote>
 * <p>
 * Example Usage:
 *
 * <pre>
 * <code>
 * Money a = Money.dollars(12.98);
 * Money b = Money.dollars(-11.98);
 * Money expected = Money.dollars(1.00);
 * Money result = a.add(b);
 * assert expected.equals(result);
 * </code>
 * </pre>
 *
 * <p>
 * You'll notice there are no setters. Money is a Value Object and is thus immutable. It helps to
 * have a variety of constructors to make it easy to make monies. Constructors that convert from
 * basic numeric types are always useful.
 *
 * <p>
 * <strong>NOTE:</strong> The original source code was available at
 * <a href="http://martinfowler.com/eaaDev/quantity.html">MF bliki</a>, however that link is now
 * broken.
 *
 * @author Martin Fowler
 * @author Paulo Gandra de Sousa
 */
@Embeddable
public class Money implements Comparable<Money>, Serializable, ValueObject, FormattedString {

    private static final long serialVersionUID = 1L;

    private static final transient int HUNDRED = 100;

    @XmlAttribute
    @JsonProperty
    private final BigInteger amount;

    @XmlAttribute
    @XmlJavaTypeAdapter(XmlCurrencyAdapter.class)
    @JsonProperty
    private final Currency currency;

    /**
     * For ORM tool only
     * <p>
     * Author Paulo Gandra de Sousa
     */
    protected Money() {
        amount = null;
        currency = null;
    }

    /**
     * Constructs a new Money object.
     *
     * @param amount
     *            the amount in the units of currency, e.g., 100.50 EUR
     * @param currency
     */
    public Money(final double amount, final Currency currency) {
        this.amount = BigInteger.valueOf(Math.round(amount * HUNDRED));
        this.currency = currency;
    }

    /**
     * Constructs a new Money object.
     *
     * @param amount
     * @param currency
     */
    public Money(final long amount, final Currency currency) {
        this.amount = BigInteger.valueOf(amount * HUNDRED);
        this.currency = currency;
    }

    /**
     * Special constructor to reconstruct objects from the database - to be used in SELECT NEW JPQL
     * queries <strong>only</strong>.
     * <p/>
     * This is an example of a "public but not published" interface
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param amount
     * @param currency
     */
    public Money(final BigInteger amount, final String currency) {
        this.amount = amount == null ? BigInteger.ZERO : amount;
        this.currency = Currency.getInstance(currency);
    }

    /**
     * Constructs a new Money object.
     *
     * @param amountInCents
     * @param currency
     */
    private Money(final BigInteger amountInCents, final Currency currency) {
        assert amountInCents != null;
        assert currency != null;

        amount = amountInCents;
        this.currency = currency;
    }

    /**
     * Factory method to construct a Money object of {@code amount} EUR. If you use one currency a
     * lot,
     * you may want a special constructor for that currency.
     *
     * @param amount
     * @return a Money object of {@code amount} EUR
     *
     */
    public static Money dollars(final double amount) {
        return new Money(amount, Currency.getInstance("USD"));
    }

    /**
     * Factory method to construct a Money object of amount EUR.
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param amount
     * @return a Money object of amount EUR
     */
    public static Money euros(final double amount) {
        return new Money(amount, Currency.getInstance("EUR"));
    }

    /**
     * Parses a string representation of a money, e.g., "123.50 EUR".
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param value
     * @return a new object corresponding to the value
     */
    public static Money valueOf(final String value) {
        final String[] parts = value.split(" ");
        Preconditions.areEqual(parts.length, 2);

        final double am = Double.parseDouble(parts[0]);
        final Currency curr = Currency.getInstance(parts[1]);
        return new Money(am, curr);
    }

    /**
     * Creates a collector that can be used to sum up a stream of Money objects in a reduction
     * style.
     * That is, since Money is an immutable object, collecting Money objects from streams have the
     * implication of creating a new Money object for each collect step which might impact
     * performance.
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param zero
     * @return a collector instance
     */
    public static Collector<Money, MoneyCollector, Money> collector(final Money zero) {
        /*
         * alternatively the collector returned could use an internal mutable representation of
         * Money and
         * just create an instance when combining streams
         *
         */
        Preconditions.nonNull(zero);

        return Collector.of(() -> new MoneyCollector(zero), (a, e) -> a.add(e),
                (a1, a2) -> a1.combine(a2),
                a -> a.current);
    }

    /**
     * A collector class to use with Java 8 Streams.
     *
     * @author Paulo Gandra de Sousa
     *
     */
    private static class MoneyCollector {
        private Money current;

        public MoneyCollector(final Money zero) {
            current = zero;
        }

        public void add(final Money b) {
            current = current.add(b);
        }

        public MoneyCollector combine(final MoneyCollector other) {
            current = current.add(other.current);
            return this;
        }
    }

    /**
     * Returns the amount portion of this Money object as a {@code double}. Notice that there might
     * be precision
     * problems.
     *
     * @return the amount portion of this Money object
     */
    public double amountAsDouble() {
        /*
         * Notice that I [Martin Fowler] use a BigInteger. In Java I could equally well use a
         * BigDecimal,
         * but in many languages an integer is the only decent option, so using an integer seems the
         * best
         * for explanation. Don't be afraid to choose your representation of the amount part of a
         * quantity
         * based on performance factors. The beauty of objects is that you can choose any data
         * structure you
         * like on the inside, providing you hide it on the outside.
         */
        return amount.doubleValue() / HUNDRED;
    }

    /**
     * Returns the amount portion of this Money object.
     *
     * @return the amount portion of this Money object
     */
    public BigDecimal amount() {
        return new BigDecimal(amount).divide(BigDecimal.valueOf(HUNDRED));
    }

    /**
     * Returns the currency of this Money object.
     *
     * @return the currency
     */
    public Currency currency() {
        return currency;
    }

    /**
     * Adds two Money objects and returns the result as a new object.
     *
     * @param arg
     * @return a new Money with the value of the addition
     * @throws IllegalArgumentException
     *             if the currencies are not the same
     */
    public Money add(final Money arg) {
        /*
         * For addition and subtraction I'm [Martin Fowler] not trying to do any fancy conversion.
         * Notice
         * that I'm using a special constructor with a marker argument.
         */
        Preconditions.ensure(currency.equals(arg.currency), "Cannot add different currencies");

        return new Money(amount.add(arg.amount), currency);
    }

    /**
     * Subtracts two Money instances and returns a third one with the result.
     *
     * @param arg
     * @return a new value corresponding to the subtraction
     * @throws IllegalArgumentException
     *             if the currencies are not the same
     */
    public Money subtract(final Money arg) {
        return add(arg.negate());
    }

    /**
     * Returns a new object which amount's sign is swapped, i.e., -this.amount. E.g.
     *
     * <p>
     *
     * <pre>
     * <code>
     * Money m1 = Money.euros(100);
     * Money m2 = m1.negate(); // -100 EUR
     * </code>
     * </pre>
     *
     * @return a new object which amount's sign is swapped
     */
    public Money negate() {
        return new Money(amount.negate(), currency);
    }

    /**
     * Multiplies this Money object by a scalar and returns the result as a new Money. Business-wise
     * it
     * doesn't make sense to perform 5 EUR * 3 EUR but it makes sense to perform 12 * 5 EUR, e.g.,
     * to
     * get the total annual sum of a monthly payment, or .06 * 100 EUR to calculate the VAT value of
     * a grocery item.
     *
     * @param arg
     * @return a new Money with the value of the multiplication
     *
     */
    public Money multiply(final double arg) {
        return new Money(amountAsDouble() * arg, currency);
    }

    /**
     * Divides this Money object by a certain integer denominator doing the right allocation of
     * cents.
     *
     * <p>
     * Multiplication is very straightforward. But division is not, as we have to take care of
     * errant
     * pennies. We'll do that by returning an array of monies, such that the sum of the array is
     * equal
     * to the original amount, and the original amount is distributed fairly between the elements of
     * the
     * array. Fairly in this sense means those at the beginning get the extra pennies.
     *
     * @param denominator
     * @return an array of Money objects corresponding to the allocation of each denominator
     */
    public Money[] divide(final int denominator) {
        final BigInteger bigDenominator = BigInteger.valueOf(denominator);
        final Money[] result = new Money[denominator];
        final BigInteger simpleResult = amount.divide(bigDenominator);
        for (int i = 0; i < denominator; i++) {
            result[i] = new Money(simpleResult, currency);
        }
        final int remainder = amount.subtract(simpleResult.multiply(bigDenominator)).intValue();
        for (int i = 0; i < remainder; i++) {
            result[i] = result[i].add(new Money(BigInteger.valueOf(1), currency));
        }
        return result;
    }

    /**
     * Compares two Money objects.
     *
     */
    @Override
    public int compareTo(final Money arg) {
        Preconditions.areEqual(currency, arg.currency, "Cannot add different currencies");

        /*
         * Next we'll look at comparing monies, in Java the approach is to implement comparable.
         */
        return amount.compareTo(arg.amount);
    }

    /**
     * Compares two Money objects checking if this money's amount is greater than the other
     *
     * @param arg
     * @return true if this money's amount is greater than the other
     */
    public boolean isGreaterThan(final Money arg) {
        /*
         * It's also useful to provide some better named operations such as greaterThan. That makes
         * methods
         * that need the comparison much easier to read.
         */
        return compareTo(arg) > 0;
    }

    /**
     * Compares two Money objects checking if this money's amount is less than the other
     *
     * @param arg
     * @return true if this money's amount is less than the other
     */
    public boolean isLessThan(final Money arg) {
        /*
         * It's also useful to provide some better named operations such as greaterThan. That makes
         * methods
         * that need the comparison much easier to read.
         */
        return compareTo(arg) < 0;
    }

    /**
     * Compares two Money objects checking if this money's amount is greater than or equal to the
     * other.
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param arg
     * @return {@code true} if this money's amount is greater than or equal to the other
     */
    public boolean isGreaterThanOrEqual(final Money arg) {
        return compareTo(arg) >= 0;
    }

    /**
     * Compares two Money objects checking if this money's amount is less than or equal to the
     * other.
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @param arg
     * @return {@code true} if this money's amount is less than or equal to the other
     */
    public boolean isLessThanOrEqual(final Money arg) {
        return compareTo(arg) <= 0;
    }

    /**
     * Since money is a value, it should override equals.
     */
    @Override
    public boolean equals(final Object arg) {
        if (!(arg instanceof Money)) {
            return false;
        }
        final Money other = (Money) arg;
        return currency.equals(other.currency) && amount.equals(other.amount);
    }

    /**
     * Since you override equals, don't forget to also override hash (here's a simple suggestion for
     * that).
     */
    @Override
    public int hashCode() {
        int result = 11;
        result = 37 * result + amount.hashCode();
        result = 37 * result + currency.hashCode();
        return result;
    }

    public int signum() {
        return amount.signum();
    }

    /**
     * Checks if this amount is negative.
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @return {@code true} if this amount is negative
     */
    @JsonIgnore
    public boolean isNegative() {
        return signum() == -1;
    }

    /**
     * Checks if this amount is positive.
     * <p>
     * Author Paulo Gandra de Sousa
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
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @return {@code true} if this amount is zero
     */
    @JsonIgnore
    public boolean isZero() {
        return signum() == 0;
    }

    /**
     * Returns a formatted representation of this money value according to the Locale currency
     * format.
     * <p>
     * Scraped from
     * <a href=
     * "http://cameotutorials.blogspot.com/2009/06/money-class-for-use-in-currency.html">cameotutorials</a>
     *
     * <p>
     * Author Paulo Gandra de Sousa
     *
     * @return a formatted representation of this Money value based on the rules defined by the
     *         currency Locale
     */
    @Override
    public String toString() {
        final NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setCurrency(currency);
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        return nf.format(amount().doubleValue());
    }

    /**
     * Returns a simple representation of the amount and the currency code not taking into account
     * the Locale.
     *
     * @return a representation of the money value
     */
    public String toSimpleString() {
        return amountAsDouble() + " " + currency();
    }
}
