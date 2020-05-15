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

import eapli.framework.util.Collections;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class GeneralDtoForCollectionTest extends GeneralDtoBaseTest {

    private static int N_SAMPLES = 5;
    private static final List<SimpleClass> BIZ_OBJ = new ArrayList<>();
    private static Iterable<GeneralDTO> subject;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForCollectionClassTest");

        for (int i = 0; i < N_SAMPLES; i++) {
            final SimpleClass sample = new SimpleClass(STRING_FIELD_VALUE_1 + i, INT_FIELD_VALUE_1 + i);
            BIZ_OBJ.add(sample);
        }

        subject = GeneralDTO.ofMany(BIZ_OBJ);

        System.out.println("===========");
        System.out.println(subject);
        for (final GeneralDTO o : subject) {
            for (final Map.Entry<String, Object> e : o.entrySet()) {
                System.out.println("[" + e.getKey() + "] => [" + e.getValue() + "]");
            }
        }
        System.out.println("===========");
    }

    @Test
    public void ensureListHasAllMembers() {
        System.out.println("ensureListHasAllMembers");
        final int n = Collections.sizeOf(subject);
        assertEquals("list is missing elements", N_SAMPLES, n);
    }

    @Test
    public void ensureListFieldIsTransformed() {
        System.out.println("ensureListFieldIsTransformed");

        int i = 0;
        for (final GeneralDTO e : subject) {
            assertEquals("'intField' is incorrect", INT_FIELD_VALUE_1 + i, e.get("intField"));
            assertEquals("'stringField' is incorrect", STRING_FIELD_VALUE_1 + i, e.get("stringField"));

            i++;
        }
        assertTrue(true);
    }
}
