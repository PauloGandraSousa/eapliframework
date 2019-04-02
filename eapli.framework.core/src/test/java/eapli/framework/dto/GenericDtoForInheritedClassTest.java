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
public class GenericDtoForInheritedClassTest {

    private static String STRING_FIELD_VALUE = "abc";
    private static int INT_FIELD_VALUE = 3;
    private static float FLOAT_FIELD_VALUE = 3.1415f;

    private static final SimpleClass subject = new SimpleClass(STRING_FIELD_VALUE, INT_FIELD_VALUE, FLOAT_FIELD_VALUE);
    private static GenericDTO instance;

    public GenericDtoForInheritedClassTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForInheritedClassTest");

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
    private static class SimpleClassBase {
        private final String stringField;
        private final int intField;

        public SimpleClassBase(final String s, final int i) {
            stringField = s;
            intField = i;
        }
    }

    @SuppressWarnings("unused")
    private static class SimpleClass extends SimpleClassBase {
        private final float floatField;

        public SimpleClass(final String s, final int i, final float f) {
            super(s, i);
            floatField = f;
        }
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", instance.type(), subject.getClass().getName());
    }

    @Test
    public void ensureDTOHas3Fields() {
        System.out.println("ensureDTOHas3Fields");

        assertEquals("Name of type is incorrect", 3, instance.size());
    }

    @Test
    public void ensureStringFieldIsTransformed() {
        System.out.println("ensureStringFieldIsTransformed");

        assertEquals("'stringField' is incorrectly transformed", STRING_FIELD_VALUE, instance.get("stringField"));
    }

    @Test
    public void ensureIntFieldIsTransformed() {
        System.out.println("ensureIntFieldIsTransformed");

        assertEquals("'intField' is incorrectly transformed", INT_FIELD_VALUE, instance.get("intField"));
    }

    @Test
    public void ensureFloatFieldIsTransformed() {
        System.out.println("ensureFloatFieldIsTransformed");

        assertEquals("'floatField' is incorrectly transformed", FLOAT_FIELD_VALUE, instance.get("floatField"));
    }
}
