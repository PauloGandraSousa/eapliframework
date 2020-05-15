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
package eapli.framework.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A class that only has one object instance, i.e., Singleton.
 *
 * <p>
 * Singleton classes must not allow to create more than one
 * instance. Usually this is done by making the class final, making the
 * constructors private and providing a static method to access the only
 * instance of the class. For example:
 *
 * <pre>
 * <code>
 * &#64;Singleton
 * public final NumberFive {
 *      private NumberFive() {
 *              // avoid instantiation
 *      }
 *
 *      // singleton instance holder
 *      private static final class LazyHolder{
 *              static final NumberFive INSTANCE = new NumberFive();
 *      }
 *
 *      // factory method
 *      public static NumberFive instance(){
 *              return LazyHolder.INSTANCE;
 *      }
 *
 *      public int value() {
 *              return 5;
 *      }
 * }
 * </code>
 * </pre>
 * <p>
 * See the <a href=
 * "https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom">initialization
 * on demand</a> pattern for thread safety.
 *
 * @author Paulo Gandra Sousa
 *
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE })
public @interface Singleton {
    // empty
}
