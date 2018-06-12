/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.range;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.LongStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class EmptyRangeTest extends AbstractRangeTest {

    public EmptyRangeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("EmptyRange");
        instance = Range.empty();
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

    @Test
    public void ensureIsEmpty() {
        assertTrue(instance.isEmpty());
    }

    @Test
    public void ensureDoesNotInclude() {
        final boolean result = LongStream.range(START_VALUE * -2L, END_VALUE * -2L)
                .noneMatch(x -> instance.includes(x));
        assertTrue(result);
    }

    @Test
    public void ensureNotFromInfinity() {
        assertFalse(instance.isFromInfinity());
    }

    @Test
    public void ensureNotToInfinity() {
        assertFalse(instance.isToInfinity());
    }

    @Test(expected = IllegalStateException.class)
    public void ensureStart() {
        instance.start();
    }

    @Test(expected = IllegalStateException.class)
    public void ensureEnd() {
        instance.end();
    }
}
