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
package eapli.framework.representations.builders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JsonRepresentationBuilderCollectionOfCollectionTest {
    private JsonRepresentationBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new JsonRepresentationBuilder();
    }

    @Test
    public void ensureCollectionOfOneCollection() {
        final String expected = "{\"name\":[[1,2,3]]}";
        final String actual = subject.startObject("Test").startCollection("name").startCollection("c1").withElement(1)
                .withElement(2).withElement(3).endCollection().endCollection().build();

        System.out.println("ensureCollectionOfOneCollection");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    public void ensureCollectionOfTwoCollections() {
        final String expected = "{\"name\":[[1,2],[3,4]]}";
        final String actual = subject.startObject("Test").startCollection("name").startCollection("c1").withElement(1)
                .withElement(2).endCollection().startCollection("c2").withElement(3).withElement(4).endCollection()
                .endCollection().build();

        System.out.println("ensureCollectionOfTwoCollections");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }

    @Test
    public void ensurePolymorphicCollection() {
        final String expected = "{\"name\":[[1,2],\"a\",[3,4]]}";
        final String actual = subject.startObject("Test").startCollection("name").startCollection("c1").withElement(1)
                .withElement(2).endCollection().withElement("a").startCollection("c2").withElement(3).withElement(4)
                .endCollection().endCollection().build();

        System.out.println("ensurePolymorphicCollection");
        System.out.println("Expected: " + expected);
        System.out.println("Actual:   " + actual);

        assertEquals(expected, actual);
    }
}
