package eapli.framework.util.function;

import java.util.stream.IntStream;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Utilitarian;

/**
 * just a playground to use the Java 8 Streams and Lambda features
 *
 * @author sou03408
 *
 */
public final class MathFunctions implements Utilitarian {

    private MathFunctions() {
        // ensure utility
    }

    /**
     * counts the number of primes up to a certain number
     *
     * using Java Lambdas
     * http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf
     *
     * @param upTo
     * @return
     */
    public static long countPrimes(final int upTo) {
        return IntStream.range(2, upTo).filter(MathFunctions::isPrime).count();
    }

    /**
     * checks whether a number is prime or not
     *
     * using Java Lambdas
     * http://www.oreilly.com/programming/free/files/object-oriented-vs-functional-programming.pdf
     *
     * see also {@link org.apache.commons.math4.primes.Primes}
     *
     * @param number
     * @return
     */
    public static boolean isPrime(final int number) {
        Preconditions.isPositive(number);

        return IntStream.range(2, number).allMatch(x -> (number % x) != 0);
    }
}
