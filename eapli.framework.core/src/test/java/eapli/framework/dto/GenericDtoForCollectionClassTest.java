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

import java.util.ArrayList;
import java.util.List;
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
public class GenericDtoForCollectionClassTest {

    private static String STRING_FIELD_VALUE = "abc";
    private static int INT_FIELD_VALUE = 3;
    private static final SimpleClass sample = new SimpleClass(STRING_FIELD_VALUE, INT_FIELD_VALUE);
    private static final WithListClass subject = new WithListClass(INT_FIELD_VALUE, sample);
    private static GenericDTO instance;

    public GenericDtoForCollectionClassTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForCollectionClassTest");

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

    private static class WithListClass {
        private final int intField;
        private final List<SimpleClass> data = new ArrayList<SimpleClass>();

        public WithListClass(final int n, final SimpleClass c) {
            intField = n;
            for (int i = 0; i < intField; i++) {
                data.add(c);
            }
        }
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", subject.getClass().getName(), instance.type());
    }

    @Test
    public void ensureDTOHas2Fields() {
        System.out.println("ensureDTOHas2Fields");

        assertEquals("Name of type is incorrect", 2, instance.size());
    }

    @Test
    public void ensureListFieldIsList() {
        System.out.println("ensureListFieldIsList");

        assertTrue("'data' is not a List", List.class.isAssignableFrom(instance.get("data").getClass()));
    }

    @Test
    public void ensureListFieldHasAllMembers() {
        System.out.println("ensureListFieldHasAllMembers");

        assertEquals("'data' list is missing elements", INT_FIELD_VALUE, ((List<?>) (instance.get("data"))).size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void ensureListFieldIsTransformed() {
        System.out.println("ensureListFieldIsTransformed");

        for (final GenericDTO e : (List<GenericDTO>) (instance.get("data"))) {
            assertEquals("'intField' is incorrect", INT_FIELD_VALUE, e.get("intField"));
            assertEquals("'stringField' is incorrect", STRING_FIELD_VALUE, e.get("stringField"));
        }
        assertTrue(true);
    }

    @Test
    public void ensureIntFieldIsTransformed() {
        System.out.println("ensureIntFieldIsTransformed");

        assertEquals("'intField' is incorrectly transformed", INT_FIELD_VALUE, instance.get("intField"));
    }
}
