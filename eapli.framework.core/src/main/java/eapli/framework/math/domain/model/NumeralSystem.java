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
package eapli.framework.math.domain.model;

import java.io.Serializable;

/**
 * A Numeral System to encode numeric values.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@FunctionalInterface
public interface NumeralSystem extends Serializable {
    /**
     * Returns the symbols to be used as representation of the number. the
     * length of this string of symbols determines the base of the numeral
     * system. e.g.,
     *
     * <p>
     * symbols = "01234567" ==> base = 8
     *
     * <p>
     * be careful not to use unicode symbols which actually occupy two bytes and
     * will make the base calculation wrong. the string of symbols is handled as
     * an ordered string, however, implementations are free to define which
     * symbols to use. e.g., a base 2 numeral system may define that its symbols
     * are "TF" and not "01"
     *
     * @return the symbols to be used as representation of the number
     */
    String symbols();

    /**
     * Returns the symbol to be used for the nth digit. Ordering starts at 0.
     * For example, if the symbol set is {'A', 'B', 'C', 'D'}:
     *
     * <pre>
     * <code>
     * symbol(0) == 'A'
     * symbol(1) == 'B'
     * </code>
     * </pre>
     *
     * @param digit
     * @return the symbol to be used for the nth digit.
     */
    default char symbol(final int digit) {
        return symbols().charAt(digit);
    }

    /**
     * Returns the order-index of a symbol. Ordering starts at 0. For example,
     * if the symbol set is {'A', 'B', 'C', 'D'}:
     *
     * <pre>
     * <code>
     * digit('A') == 0
     * symbol('B') == 1
     * </code>
     * </pre>
     *
     * @param symbol
     * @return the order-index of a symbol.
     */
    default int digit(final char symbol) {
        return symbols().indexOf(symbol);
    }

    /**
     * Returns the base of the numeral system. For instance, the decimal system
     * has base 10.
     *
     * @return the base of the numeral system
     */
    default int base() {
        return symbols().length();
    }

    /**
     * Returns the numeral of 0.
     *
     * @return the numeral of 0.
     */
    default String zero() {
        return String.valueOf(symbol(0));
    }

    /**
     * Checks if a string representation is a valid numeral in the current
     * numeral system.
     *
     * @param numeralToTest
     * @return true if a string representation is a valid numeral
     */
    default boolean isValidNumeral(final String numeralToTest) {
        return numeralToTest.chars().allMatch(c -> digit((char) c) != -1);
    }
}
