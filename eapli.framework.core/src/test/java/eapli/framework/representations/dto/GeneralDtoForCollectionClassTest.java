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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class GeneralDtoForCollectionClassTest extends GeneralDtoBaseTest {

    private static final WithListClass BIZ_OBJ = new WithListClass(INT_FIELD_VALUE_1, BIZ_OBJ_S1);
    private static GeneralDTO subject;

    protected static class WithListClass {
        protected final int intField;
        protected final List<SimpleClass> data = new ArrayList<>();

        public WithListClass(final int n, final SimpleClass c) {
            intField = n;
            for (int i = 0; i < intField; i++) {
                data.add(c);
            }
        }
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForCollectionClassTest");

        subject = GeneralDTO.of(BIZ_OBJ);

        System.out.println("===========");
        System.out.println("type:" + subject.type());
        for (final Map.Entry<String, Object> e : subject.entrySet()) {
            System.out.println("[" + e.getKey() + "] => [" + e.getValue() + "]");
        }
        System.out.println("===========");
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", BIZ_OBJ.getClass().getName(), subject.type());
    }

    @Test
    public void ensureDTOHas2Fields() {
        System.out.println("ensureDTOHas2Fields");
        System.out.println(subject.keySet());

        assertEquals(2, subject.size());
    }

    @Test
    public void ensureListFieldIsList() {
        System.out.println("ensureListFieldIsList");

        assertTrue("'data' is not a List", List.class.isAssignableFrom(subject.get("data").getClass()));
    }

    @Test
    public void ensureListFieldHasAllMembers() {
        System.out.println("ensureListFieldHasAllMembers");

        assertEquals("'data' list is missing elements", INT_FIELD_VALUE_1, ((List<?>) (subject.get("data"))).size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void ensureListFieldIsTransformed() {
        System.out.println("ensureListFieldIsTransformed");

        for (final GeneralDTO e : (List<GeneralDTO>) (subject.get("data"))) {
            assertEquals("'intField' is incorrect", INT_FIELD_VALUE_1, e.get("intField"));
            assertEquals("'stringField' is incorrect", STRING_FIELD_VALUE_1, e.get("stringField"));
        }
    }

    @Test
    public void ensureIntFieldIsTransformed() {
        System.out.println("ensureIntFieldIsTransformed");

        assertEquals("'intField' is incorrectly transformed", INT_FIELD_VALUE_1, subject.get("intField"));
    }
}
