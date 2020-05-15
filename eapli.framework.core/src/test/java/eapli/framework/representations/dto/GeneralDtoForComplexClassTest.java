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

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class GeneralDtoForComplexClassTest extends GeneralDtoBaseTest {

    private static GeneralDTO subject;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("GenericDtoForComplexClassTest");

        subject = GeneralDTO.of(BIZ_OBJ_C1);

        System.out.println("===========");
        System.out.println(subject);
        System.out.println(subject.type());
        for (final Map.Entry<String, Object> e : subject.entrySet()) {
            System.out.println("[" + e.getKey() + "] => [" + e.getValue() + "]");
        }
        System.out.println("===========");
    }

    @Test
    public void ensureType() {
        System.out.println("ensureType");

        assertEquals("Name of type is incorrect", BIZ_OBJ_C1.getClass().getName(), subject.type());
    }

    @Test
    public void ensureDTOHas4Fields() {
        System.out.println("ensureDTOHas2Fields");
        System.out.println(subject.keySet());
        assertEquals(4, subject.size());
    }

    @Test
    public void ensureChildOneIsDTO() {
        System.out.println("ensureChildIsDTO");

        assertTrue("'one' is incorrectly transformed", subject.get("one") instanceof GeneralDTO);
    }

    @Test
    public void ensureChildTwoIsDTO() {
        System.out.println("ensureChildTwoIsDTO");

        assertTrue("'two' is incorrectly transformed", subject.get("two") instanceof GeneralDTO);
    }

    @Test
    public void ensureChildSameAsOneIsDTO() {
        System.out.println("ensureChildSameAsOneIsDTO");

        assertTrue("'sameAsOne' is incorrectly transformed", subject.get("sameAsOne") instanceof GeneralDTO);
    }

    @Test
    public void ensureFloatFieldIsTransformed() {
        System.out.println("ensureFloatFieldIsTransformed");

        assertEquals("'floatField' is incorrectly transformed", FLOAT_FIELD_VALUE, subject.get("floatField"));
    }
}
