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
package eapli.framework.strings;

import java.util.Currency;
import java.util.Locale;

import eapli.framework.general.domain.model.Money;

/**
 * Formatted money values (even if we are not using the Money class).
 *
 * @author Paulo Gandra de Sousa 14/05/2020
 *
 */
public class FormattedMoney implements FormattedString {
    private final Money input;

    /**
     * Simple wrapper.
     *
     * @param input
     */
    public FormattedMoney(final Money input) {
        this.input = input;
    }

    /**
     * A money value with the current Locale's currency.
     *
     * @param amount
     */
    public FormattedMoney(final double amount) {
        this(amount, Currency.getInstance(Locale.getDefault()));
    }

    /**
     * A money value with the current Locale's currency.
     *
     * @param amount
     * @param cur
     *
     */
    public FormattedMoney(final double amount, final Currency cur) {
        input = new Money(amount, cur);
    }

    @Override
    public String toString() {
        return input.toString();
    }
}
