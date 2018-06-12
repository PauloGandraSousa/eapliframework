/**
 *
 */
package eapli.framework.domain.money;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import eapli.framework.util.Preconditions;

/**
 * a Collector to be used with Stream<Money> objects
 *
 * @author sou03408
 *
 */
public class MoneySumCollector {

    private Money result;

    /**
     * you will need to define a Supplier function that initialises the Collector
     * with the correct seed value, e.g., Money.euros(0)
     *
     * @param seed
     */
    public MoneySumCollector(final Money seed) {
        Preconditions.nonNull(seed);

        result = seed;
    }

    /**
     * the accumulator function of the collector
     *
     * @param o
     * @return
     */
    public Money add(final Money o) {
        Preconditions.nonNull(o);

        result = result.add(o);
        return result;
    }

    /**
     * the combiner function of the Collector
     *
     * @param o
     * @return
     */
    public MoneySumCollector aggregate(final MoneySumCollector o) {
        Preconditions.nonNull(o);

        result = result.add(o.result);
        return this;
    }

    public Money result() {
        return result;
    }

    /**
     * helper factory method
     *
     * @param seed
     * @return
     */
    public static Collector<Money, MoneySumCollector, MoneySumCollector> collector(final Money seed) {
        return new Collector<Money, MoneySumCollector, MoneySumCollector>() {

            @Override
            public BiConsumer<MoneySumCollector, Money> accumulator() {
                return MoneySumCollector::add;
            }

            @Override
            public BinaryOperator<MoneySumCollector> combiner() {
                return MoneySumCollector::aggregate;
            }

            @Override
            public Function<MoneySumCollector, MoneySumCollector> finisher() {
                return e -> e;
            }

            @Override
            public Supplier<MoneySumCollector> supplier() {
                return () -> new MoneySumCollector(seed);
            }

            @Override
            public Set<java.util.stream.Collector.Characteristics> characteristics() {

                final Set<java.util.stream.Collector.Characteristics> ch = new HashSet<>();
                ch.add(Collector.Characteristics.IDENTITY_FINISH);
                return ch;
            }
        };
    }
}
