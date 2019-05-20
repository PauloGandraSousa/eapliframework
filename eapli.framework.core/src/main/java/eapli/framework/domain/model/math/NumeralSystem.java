/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.domain.model.math;

import eapli.framework.domain.model.ValueObject;

/**
 * A Numeral System to encode numeric values.
 *
 * @author Paulo Gandra de Sousa
 *
 */
@FunctionalInterface
public interface NumeralSystem extends ValueObject {
    /**
     * returns the symbols to be used as representation of the number. the length of this string of
     * symbols determines the base of the numeral system. e.g.,
     *
     * <p/>
     * symbols = "01234567" ==> base = 8
     *
     * <p/>
     * be careful not to use unicode symbols which actually occupy two bytes and will make the base
     * calculation wrong. the string of symbols is handled as an ordered string, however,
     * implementations are free to define which symbols to use. e.g., a base 2 numeral system may define
     * that its symbols are "TF" and not "01"
     *
     * @return
     */
    String symbols();

    /**
     *
     * @param digit
     * @return
     */
    default char symbol(final int digit) {
        return symbols().charAt(digit);
    }

    /**
     *
     * @param symbol
     * @return
     */
    default int digit(final char symbol) {
        return symbols().indexOf(symbol);
    }

    /**
     *
     * @return
     */
    default int base() {
        return symbols().length();
    }

    /**
     *
     * @return
     */
    default String zero() {
        return String.valueOf(symbol(0));
    }

    /**
     * checks if a string representation is a valid numeral in the current numeral system
     *
     * @param numeralToTest
     * @return
     */
    default boolean isValidNumeral(final String numeralToTest) {
        return numeralToTest.chars().allMatch(c -> digit((char) c) != -1);
    }
}
