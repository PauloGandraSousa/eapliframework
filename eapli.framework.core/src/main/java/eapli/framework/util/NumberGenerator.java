package eapli.framework.util;

import java.util.Random;

/**
 * very simple pseudo random generator support
 */
public final class NumberGenerator implements Utilitarian {
    private static final Random RANDOM_GEN = new Random(DateTime.now().getTimeInMillis());

    private NumberGenerator() {
        // ensure utility
    }

    public static int anInt() {
        return RANDOM_GEN.nextInt();
    }

    public static int anInt(int bound) {
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
