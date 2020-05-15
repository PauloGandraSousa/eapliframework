/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.framework.representations.dto;

import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.representations.Representationable;

/**
 *
 * @author Paulo Gandra Sousa
 */
public abstract class GeneralDtoBaseTest {

    protected static String STRING_FIELD_VALUE_1 = "abc";
    protected static int INT_FIELD_VALUE_1 = 3;
    protected static String STRING_FIELD_VALUE_2 = "xyz";
    protected static int INT_FIELD_VALUE_2 = 9;
    protected static float FLOAT_FIELD_VALUE = 3.1415f;

    protected static final SimpleClass BIZ_OBJ_S1 = new SimpleClass(STRING_FIELD_VALUE_1, INT_FIELD_VALUE_1);
    protected static final SimpleClass BIZ_OBJ_S2 = new SimpleClass(STRING_FIELD_VALUE_2, INT_FIELD_VALUE_2);
    protected static final ComplexClass BIZ_OBJ_C1 = new ComplexClass(BIZ_OBJ_S1, BIZ_OBJ_S2, FLOAT_FIELD_VALUE);

    protected static class SimpleClass implements Representationable {
        protected final String stringField;
        protected final int intField;

        public SimpleClass(final String s, final int i) {
            stringField = s;
            intField = i;
        }

        @Override
        public <R> R buildRepresentation(final RepresentationBuilder<R> builder) {
            return builder.startObject("SimpleClass").withProperty("intField", intField)
                    .withProperty("stringField", stringField).build();
        }
    }

    protected static class ComplexClass {
        protected SimpleClass one;
        protected SimpleClass two;
        protected SimpleClass sameAsOne;
        protected float floatField;

        public ComplexClass(final SimpleClass a, final SimpleClass b, final float f) {
            one = a;
            two = b;
            sameAsOne = a;
            floatField = f;
        }
    }

    protected static class MoreComplexClass {
        protected SimpleClass[] one;
        protected ComplexClass two;
        protected float floatField;

        public MoreComplexClass(final SimpleClass a, final ComplexClass b, final float f) {
            one = new SimpleClass[2];
            one[0] = a;
            one[1] = null;
            two = b;
            floatField = f;
        }

        public MoreComplexClass(final boolean markerEmpty, final ComplexClass b, final float f) {
            one = new SimpleClass[0];
            two = b;
            floatField = f;
        }

        public MoreComplexClass(final ComplexClass b, final float f) {
            one = null;
            two = b;
            floatField = f;
        }

        public MoreComplexClass(final SimpleClass a, final float f) {
            one = new SimpleClass[1];
            one[0] = a;
            two = null;
            floatField = f;
        }
    }
}