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

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Paulo Gandra de Sousa 04/03/2021
 *
 */
abstract class EitherBaseTest {

    protected static final String TESTING = "testing";
    protected static final Integer TESTING_INT = 42;
    protected Helper helper;
    protected Consumer<String> stringLength;
    protected Consumer<Integer> intValue;
    protected UnaryOperator<String> stringLengthOp;
    protected UnaryOperator<Integer> intValueOp;

    protected static class Helper {
        /**
         *
         */
        protected static final int DEFAULT_INT = -1;
        protected int i = DEFAULT_INT;
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        helper = new Helper();
        stringLength = x -> helper.i = x.length();
        stringLengthOp = x -> {
            helper.i = x.length();
            return x;
        };
        intValue = y -> helper.i = y;
        intValueOp = y -> helper.i = y;

    }

    abstract Either<String, Integer> subject();

    @Test
    abstract void ensureCannotCreateFromNull();

    @Test
    abstract void ensureHoldsValue();

    @Test
    abstract void ensureHoldsValueOrDefault();

    @Test
    abstract void ensureOppositeValueOrElseThrowThrowsException();

    @Test
    abstract void ensureOppositeValueOrElseReturnsDefault();

    @Test
    abstract void ensureConsume();

    @Test
    abstract void ensureMap();

    @Test
    abstract void ensureFilterIsEmpty();

    @Test
    abstract void ensureFilterIsNotEmpty();

    @Test
    abstract void ensureFolding();

    @Test
    abstract void ensureSwap();

    @Test
    abstract void ensureProjectLeft();

    @Test
    abstract void ensureProjectRight();

    @Test
    abstract void ensureOnLeft();

    @Test
    abstract void ensureOnRight();

    @Test
    abstract void ensureProjectionMap();

    @Test
    abstract void ensureProjectionFold();

    @Test
    abstract void ensureProjectionFilter();

    @Test
    abstract void ensureProjectionConsume();

    @Test
    abstract void ensureProjectionValue();

    @Test
    abstract void ensureProjectionValueOrThrows();

    @Test
    abstract void ensureOppositeProjectionMap();

    @Test
    abstract void ensureOppositeProjectionFold();

    @Test
    abstract void ensureOppositeProjectionFilter();

    @Test
    abstract void ensureOppositeProjectionConsumeDoesNothing();

    @Test
    abstract void ensureOppositeProjectionOrElseReturnsDefault();

    @Test
    abstract void ensureOppositeProjectionOrElseThrowThrows();
}