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

/**
 * A factory or <a href="https://refactoring.guru/design-patterns/builder">Builder</a> object.
 *
 * <p>
 * Factories will be very helpful to provide extension and modification in our code as they can hide
 * the actual object being created. Following the Dependency Inversion Principle (DIP), one should
 * program to an abstraction and not to a realization. Thus a factory is needed to create the actual
 * object while hiding which object is created from the rest of the code.
 * </p>
 *
 * @param <T>
 *            the type of object this factory builds.
 *
 * @author Paulo Gandra de Sousa
 *
 *         <!--
 * @startuml factories.svg
 *
 *           interface X{
 *           +mxa()
 *           }
 *
 *           class Client{
 *           +m()
 *           }
 *
 *           note left of Client
 *           m() {
 *           X x = factory.build();
 *           . . .
 *           x.mxa();
 *           . . .
 *           }
 *           end note
 *
 *           class XptoFactory{
 *           +build()
 *           }
 *
 *           Client .right..> X
 *           Client .down.> XptoFactory
 *
 *           X <|-up- X1
 *           X <|-up- X2
 *
 *           XptoFactory .up.> X
 *           XptoFactory ..> X1
 *           XptoFactory ..> X2
 * @enduml
 *         -->
 */
@FunctionalInterface
public interface Factory<T> {
    /**
     * Constructs a new instance. Most likely this method is the last step in
     * the building process (following the Builder GoF pattern) where a series
     * of steps to provide the necessary information to the object constructor
     * is previously loaded into the builder.
     *
     * @return the newly created instance
     */
    T build();
}
