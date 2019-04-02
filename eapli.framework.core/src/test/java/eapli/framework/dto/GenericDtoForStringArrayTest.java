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

import java.lang.reflect.Array;
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
public class GenericDtoForStringArrayTest {

    private static String STRING_FIELD_VALUE = "abc";
    private static int INT_FIELD_VALUE = 3;
    private static final WithPrimitiveArrayClass subject = new WithPrimitiveArrayClass(INT_FIELD_VALUE,
            STRING_FIELD_VALUE);
    private static GenericDTO instance;

    public GenericDtoForStringArrayTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForPrimitiveArrayTest");

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

    private static class WithPrimitiveArrayClass {
        private final String[] data;

        public WithPrimitiveArrayClass(final int n, final String seed) {
            data = new String[n];
            for (int i = 0; i < n; i++) {
                data[i] = seed + i;
            }
        }
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", subject.getClass().getName(), instance.type());
    }

    @Test
    public void ensureDTOHas1Field() {
        System.out.println("ensureDTOHas1Field");

        assertEquals("Number of fields is incorrect", 1, instance.size());
    }

    @Test
    public void ensureArrayFieldIsList() {
        System.out.println("ensureArrayFieldIsList");

        assertTrue("'data' is not an array", instance.get("data").getClass().isArray());
    }

    @Test
    public void ensureArrayFieldHasAllMembers() {
        System.out.println("ensureArrayFieldHasAllMembers");

        assertEquals("'data' list is missing elements", INT_FIELD_VALUE, Array.getLength(instance.get("data")));
    }

    @Test
    public void ensureArrayFieldIsTransformed() {
        System.out.println("ensureArrayFieldIsTransformed");

        int i = 0;
        for (final String e : (String[]) (instance.get("data"))) {
            assertEquals("ith member is incorrect", STRING_FIELD_VALUE + i, e);
            i++;
        }
    }
}
