package eapli.framework.util;

import java.util.Random;

/**
 * very simple pseudo random generator support.
 *
 * make sure you check
 * https://sonarcloud.io/organizations/pag_isep-bitbucket/rules?open=squid%3AS2245&rule_key=squid%3AS2245
 * in order to understand the security issues that rise from using pseudorandon
 * number generators
 */
public final class NumberGenerator implements Utilitarian {
    @SuppressWarnings("squid:S2245")
    private static final Random RANDOM_GEN = new Random(DateTime.now().getTimeInMillis());

    private NumberGenerator() {
        // ensure utility
    }

    public static int anInt() {
        return RANDOM_GEN.nextInt();
    }

    public static int anInt(final int bound) {
        return RANDOM_GEN.nextInt(bound);
    }

    public static float aFloat() {
        return RANDOM_GEN.nextFloat();
    }

    /**
     * fifty/fifty chance
     *
     * @return
     */
    public static boolean heads() {
        return anInt() % 2 == 0;
    }
}
