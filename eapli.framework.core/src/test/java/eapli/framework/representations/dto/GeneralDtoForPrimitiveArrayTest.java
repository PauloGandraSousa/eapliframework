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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class GeneralDtoForPrimitiveArrayTest extends GeneralDtoBaseTest {

    private static final WithPrimitiveArrayClass BIZ_OBJ = new WithPrimitiveArrayClass(INT_FIELD_VALUE_1);

    private static GeneralDTO subject;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForPrimitiveArrayTest");

        subject = GeneralDTO.of(BIZ_OBJ);

        System.out.println("===========");
        System.out.println(subject);
        System.out.println(subject.type());
        for (final Map.Entry<String, Object> e : subject.entrySet()) {
            System.out.println("[" + e.getKey() + "] => [" + e.getValue() + "]");
        }
        System.out.println("===========");
    }

    private static class WithPrimitiveArrayClass {
        private final int[] data;

        public WithPrimitiveArrayClass(final int n) {
            data = new int[n];
            for (int i = 0; i < n; i++) {
                data[i] = i;
            }
        }
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", BIZ_OBJ.getClass().getName(), subject.type());
    }

    @Test
    public void ensureDTOHas1Field() {
        System.out.println("ensureDTOHas1Field");

        assertEquals("Number of fields is incorrect", 1, subject.size());
    }

    @Test
    public void ensureArrayFieldIsArray() {
        System.out.println("ensureArrayFieldIsArray");

        assertTrue("'data' is not an array", subject.get("data").getClass().isArray());
    }

    @Test
    public void ensureArrayFieldHasAllMembers() {
        System.out.println("ensureArrayFieldHasAllMembers");

        assertEquals("'data' list is missing elements", INT_FIELD_VALUE_1, Array.getLength(subject.get("data")));
    }

    @Test
    public void ensureArrayFieldIsTransformed() {
        System.out.println("ensureArrayFieldIsTransformed");

        int i = 0;
        for (final int e : (int[]) (subject.get("data"))) {
            assertEquals("ith member is incorrect", i, e);
            i++;
        }
    }
}
