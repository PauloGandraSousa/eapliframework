/**
 *
 */
package eapli.framework.domain.math;

/**
 * @author SOU03408
 *
 */
@FunctionalInterface
public interface NumeralSystem {
    /**
     * returns the symbols to be used as representation of the number. the length of
     * this string of symbols determines the base of the numeral system. e.g.,
     *
     * symbols = "01234567" ==> base = 8
     *
     * the string of symbols is handled as an ordered string, however,
     * implementations are free to define which symbols to use. e.g., a base 2
     * numeral system may define that its symbols are "TF" and not "01"
     *
     * @return
     */
    String symbols();

    default char symbol(int digit) {
        return symbols().charAt(digit);
    }

    default int digit(char symbol) {
        return symbols().indexOf(symbol);
    }

    default int base() {
        return symbols().length();
    }

    default String zero() {
        return String.valueOf(symbol(0));
    }

    /**
     * checks if a string representation is a valid numeral in the current numeral
     * system
     * 
     * @param numeralToTest
     * @return
     */
    default boolean isValidNumeral(String numeralToTest) {
        final String pattern = "^[" + symbols() + "]+$";
        return numeralToTest.matches(pattern);
    }
}
