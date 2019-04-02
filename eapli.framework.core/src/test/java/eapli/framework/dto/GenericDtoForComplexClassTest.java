/*
 * MIT License
 *
 * Copyright (c) 2013-2019 Paulo Gandra de Sousa
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
package eapli.framework.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class GenericDtoForComplexClassTest {

    private static String STRING_FIELD_VALUE_1 = "abc";
    private static int INT_FIELD_VALUE_1 = 3;
    private static String STRING_FIELD_VALUE_2 = "xyz";
    private static int INT_FIELD_VALUE_2 = 9;
    private static final SimpleClass childOne = new SimpleClass(STRING_FIELD_VALUE_1, INT_FIELD_VALUE_1);
    private static final SimpleClass childTwo = new SimpleClass(STRING_FIELD_VALUE_2, INT_FIELD_VALUE_2);
    private static float FLOAT_FIELD_VALUE = 3.1415f;
    private static final ComplexClass subject = new ComplexClass(childOne, childTwo, FLOAT_FIELD_VALUE);

    private static GenericDTO instance;

    public GenericDtoForComplexClassTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForComplexClassTest");

        instance = GenericDTO.of(subject);

        System.out.println("===========");
        System.out.println(instance);
        System.out.println(instance.type());
        for (final Map.Entry<String, Object> e : instance.entrySet()) {
            System.out.println("[" + e.getKey() + "] => [" + e.getValue() + "]");
        }
        System.out.println("===========");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @SuppressWarnings("unused")
    private static class SimpleClass {
        private final String stringField;
        private final int intField;

        public SimpleClass(final String s, final int i) {
            stringField = s;
            intField = i;
        }
    }

    @SuppressWarnings("unused")
    private static class ComplexClass {
        SimpleClass one;
        SimpleClass two;
        SimpleClass sameAsOne;
        float floatField;

        public ComplexClass(final SimpleClass a, final SimpleClass b, final float f) {
            one = a;
            two = b;
            sameAsOne = a;
            floatField = f;
        }
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", subject.getClass().getName(), instance.type());
    }

    @Test
    public void ensureDTOHas4Fields() {
        System.out.println("ensureDTOHas2Fields");

        assertEquals("Name of type is incorrect", 4, instance.size());
    }

    @Test
    public void ensureChildOneIsDTO() {
        System.out.println("ensureChildIsDTO");

        assertTrue("'one' is incorrectly transformed", instance.get("one") instanceof GenericDTO);
    }

    @Test
    public void ensureChildTwoIsDTO() {
        System.out.println("ensureChildTwoIsDTO");

        assertTrue("'two' is incorrectly transformed", instance.get("two") instanceof GenericDTO);
    }

    @Test
    public void ensureChildSameAsOneIsDTO() {
        System.out.println("ensureChildSameAsOneIsDTO");

        assertTrue("'sameAsOne' is incorrectly transformed", instance.get("sameAsOne") instanceof GenericDTO);
    }

    @Test
    public void ensureFloatFieldIsTransformed() {
        System.out.println("ensureFloatFieldIsTransformed");

        assertEquals("'floatField' is incorrectly transformed", FLOAT_FIELD_VALUE, instance.get("floatField"));
    }
}
