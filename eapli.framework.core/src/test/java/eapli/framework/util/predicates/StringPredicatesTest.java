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
package eapli.framework.util.predicates;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class StringPredicatesTest {

    @BeforeClass
    public static void setUpClass() {
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

    /**
     * Test of isNullOrEmpty method, of class Validations.
     */
    @Test
    public void testStringWithContentIsNotNullNorEmpty() {
        System.out.println("string with content is not isNullOrEmpty");
        final String text = "abcdef";
        final boolean expResult = false;
        final boolean result = StringPredicates.isNullOrEmpty(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isNullOrEmpty method, of class Validations.
     */
    @Test
    public void testNullIsNullOrEmpty() {
        System.out.println("null isNullOrEmpty");
        final String text = null;
        final boolean expResult = true;
        final boolean result = StringPredicates.isNullOrEmpty(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testEmptyStringIsNullOrEmpty() {
        System.out.println("empty string isNullOrEmpty");
        final String text = "";
        final boolean expResult = true;
        final boolean result = StringPredicates.isNullOrEmpty(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testContainsDigitSingleChar() {
        System.out.println("ContainsDigit single char");
        final String text = "1";
        final boolean expResult = true;
        final boolean result = StringPredicates.containsDigit(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testContainsDigitInTheMiddle() {
        System.out.println("ContainsDigit in the middle");
        final String text = "ab1cd";
        final boolean expResult = true;
        final boolean result = StringPredicates.containsDigit(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testContainsDigitInTheBegining() {
        System.out.println("ContainsDigit in the begining");
        final String text = "1cd";
        final boolean expResult = true;
        final boolean result = StringPredicates.containsDigit(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testContainsDigitInTheEnd() {
        System.out.println("ContainsDigit in the end");
        final String text = "ad1";
        final boolean expResult = true;
        final boolean result = StringPredicates.containsDigit(text);
        assertEquals(expResult, result);
    }

    /**
     */
    @Test
    public void testContainsDigitDetectsNonDigit() {
        System.out.println("ContainsDigit without digits");
        final String text = "dd";
        final boolean expResult = false;
        final boolean result = StringPredicates.containsDigit(text);
        assertEquals(expResult, result);
    }
}
