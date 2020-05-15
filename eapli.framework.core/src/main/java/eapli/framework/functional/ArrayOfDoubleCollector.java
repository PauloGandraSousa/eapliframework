/*
 * Copyright (c) 2013-2020 the original author or authors.
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
package eapli.framework.functional;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import eapli.framework.validations.Preconditions;

/**
 * A Collector to be used with Stream<Double> objects to collect the elements of
 * the stream into a double array.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class ArrayOfDoubleCollector {

    private final double[] result;
    private int lastIdx = -1;

    /**
     * You will need to define a Supplier function that initializes the
     * Collector with the correct destination array.
     *
     * @param destination
     */
    public ArrayOfDoubleCollector(final double[] destination) {
        Preconditions.nonNull(destination);

        result = destination;
    }

    /**
     * The accumulator function of the collector.
     *
     * @param o
     */
    public void push(final Double o) {
        Preconditions.nonNull(o);

        result[++lastIdx] = o;
    }

    /**
     * Combines the current result with a partial result - i.e., the combiner
     * function of the Collector.
     *
     * @param o
     * @return the current collector object with an updated status
     */
    public ArrayOfDoubleCollector aggregate(final ArrayOfDoubleCollector o) {
        Preconditions.nonNull(o);

        for (int i = 0; i <= o.lastIdx; i++) {
            push(o.result[i]);
        }
        return this;
    }

    public double[] result() {
        return result;
    }

    /**
     * Factory method to create a new instance of a collector.
     *
     * @param destination
     *            the underlying array where to collect the values
     * @return the new object
     */
    public static Collector<Double, ArrayOfDoubleCollector, ArrayOfDoubleCollector> collector(
            final double[] destination) {
        return new Collector<Double, ArrayOfDoubleCollector, ArrayOfDoubleCollector>() {

            @Override
            public BiConsumer<ArrayOfDoubleCollector, Double> accumulator() {
                return ArrayOfDoubleCollector::push;
            }

            @Override
            public BinaryOperator<ArrayOfDoubleCollector> combiner() {
                return ArrayOfDoubleCollector::aggregate;
            }

            @Override
            public Function<ArrayOfDoubleCollector, ArrayOfDoubleCollector> finisher() {
                return e -> e;
            }

            @Override
            public Supplier<ArrayOfDoubleCollector> supplier() {
                return () -> new ArrayOfDoubleCollector(destination);
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
