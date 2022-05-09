/*
 * Copyright (c) 2013-2022 the original author or authors.
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
package eapli.framework.representations.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eapli.framework.representations.dto.GeneralDTO;
import eapli.framework.representations.dto.GeneralDtoBaseTest;

/**
 *
 * @author Paulo Gandra Sousa
 */
class GeneralDTOOfSimpleRepresentationBuilderTest extends GeneralDtoBaseTest {

    private GeneralDTORepresentationBuilder subject;
    private GeneralDTO representation;

    @BeforeEach
    void setUp() throws Exception {
        subject = new GeneralDTORepresentationBuilder("SimpleClass");
        representation = BIZ_OBJ_S1.buildRepresentation(subject);
    }

    @Test
    void ensureDTOHas2Fields() {
        System.out.println("ensureDTOHas2Fields");

        assertEquals(2, representation.size(), "Name of type is incorrect");
    }

    @Test
    void ensureStringFieldIsTransformed() {
        System.out.println("ensureStringFieldIsTransformed");

        assertEquals(STRING_FIELD_VALUE_1, representation.get("stringField"),
                "'stringField' is incorrectly transformed");
    }

    @Test
    void ensureIntFieldIsTransformed() {
        System.out.println("ensureIntFieldIsTransformed");

        assertEquals(Integer.valueOf(INT_FIELD_VALUE_1), representation.get("intField"),
                "'intField' is incorrectly transformed");
    }

    @Test
    void ensureSamePropertyTwiceIsIgnored() {
        final GeneralDTORepresentationBuilder otherSubject = new GeneralDTORepresentationBuilder("Test");
        final GeneralDTO actual = otherSubject.withProperty("name", "value").withProperty("name", "value").build();
        assertEquals(1, actual.size());
    }
}
