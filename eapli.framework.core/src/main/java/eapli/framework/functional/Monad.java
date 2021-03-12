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
package eapli.framework.functional;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A Monad is a parametrized Wrapper type that is immutable and does not provide
 * direct access to the value it is wrapping. In order to use the value one must
 * map it or fold it.
 *
 *
 * @param <T>
 * @author Paulo Gandra de Sousa 25/06/2019
 *
 */
public interface Monad<T> {

    /**
     * Maps the current value using the provided <code>mapper</code> function
     * and returns the result wrapped in a new Monad.
     *
     * @param mapper
     *            a mapper function from <code>T</code> to <code>R</code>
     * @return a new Monad wrapping the result of the mapping
     */
    <R> Monad<R> map(final Function<? super T, R> mapper);

    /**
     * Maps the current value using the Monad-bearing <code>mapper</code> and
     * returns the result flatten out and wrapped in a new Monad. This method
     * avoids wrapping Monads inside other monads in case you are composing
     * multiple mappers.
     * <p>
     * If the mapping function does not return a Monad you should use the
     * {@link #map} method instead.
     *
     * @param mapper
     *            a mapper function from <code>T</code> to <code>Monad&lt;R&gt;</code>
     * @return a new Monad wrapping the result of the mapping
     */
    <R> Monad<R> flatMap(final Function<? super T, ? extends Monad<R>> mapper);

    /**
     * Filters the value(s) of the Monad based on a condition.
     *
     * @param filter
     * @return a new Monad without the filtered value(s).
     */
    Monad<T> filter(Predicate<T> filter);

    /**
     *
     * @param def
     * @return the wrapped value or the default value if this Monad is empty
     */
    T orElse(T def);

    /**
     *
     * @param defFunc
     * @return the wrapped value or the value provided by the default supplier
     *         if this Monad is empty
     */
    T orElseGet(Supplier<? super T> defFunc);

    /**
     * (Left) Folds the contents of the Monad using the provided initial value.
     *
     * @param initial
     * @param folder
     * @return the value
     */
    <R> R fold(R initial, BiFunction<R, T, R> folder);

    /**
     * Right Folds the contents of the Monad using the provided initial value.
     *
     * @param initial
     * @param folder
     * @return the value
     */
    <R> R foldr(R initial, BiFunction<R, T, R> folder);
}