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
package eapli.framework.infrastructure.authz.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import eapli.framework.infrastructure.authz.domain.model.Password.PasswordStrength;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class PasswordTest {

    @Test
    public void ensurePasswordHasAtLeastOneDigitOneCapitalAnd6CharactersLong() {
        Assert.assertNotNull(new Password("abCfefgh1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsSmallerThan6CharactersAreNotAllowed() {
        new Password("ab1c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsWithoutDigitsAreNotAllowed() {
        new Password("abcefghi");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensurePasswordsWithoutCapitalLetterAreNotAllowed() {
        new Password("abcefghi1");
    }

    @Test
    public void testWeakPassword1() {
        final Password instance = new Password("A23456");
        assertEquals(PasswordStrength.WEAK, instance.strength());
    }

    @Test
    public void testWeakPassword2() {
        final Password instance = new Password("A234567");
        assertEquals(PasswordStrength.WEAK, instance.strength());
    }

    @Test
    public void testGoodPassword1() {
        final Password instance = new Password("A2345678");
        assertEquals(PasswordStrength.GOOD, instance.strength());
    }

    @Test
    public void testGoodPassword2() {
        final Password instance = new Password("A23456789");
        assertEquals(PasswordStrength.GOOD, instance.strength());
    }

    @Test
    public void testExcelentPassword1() {
        final Password instance = new Password("123456789ABC");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }

    @Test
    public void testExcelentPassword2() {
        final Password instance = new Password("123456789ABCD");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }

    @Test
    public void testExcelentPassword3() {
        final Password instance = new Password("A234$5678");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }

    @Test
    public void testExcelentPassword4() {
        final Password instance = new Password("A234#5678");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }

    @Test
    public void testExcelentPassword5() {
        final Password instance = new Password("A234!5678");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }

    @Test
    public void testExcelentPassword6() {
        final Password instance = new Password("A234?5678");
        assertEquals(PasswordStrength.EXCELENT, instance.strength());
    }
}
