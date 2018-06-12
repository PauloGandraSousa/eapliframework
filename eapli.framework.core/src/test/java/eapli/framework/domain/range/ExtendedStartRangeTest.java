/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.range;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ExtendedStartRangeTest extends AbstractRangeTest {

    private static final Long NEW_START = START - 2;

    public ExtendedStartRangeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Extended Start/End Range");
        instance = Range.closedFrom(START).closedTo(END).build().withStart(NEW_START);
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
    public void ensureNewStart() {
        assertEquals(NEW_START, instance.start());
    }

    @Test
    public void ensureClosedStart() {
        assertFalse(instance.hasOpenStart());
    }

    @Test
    public void ensureEnd() {
        assertEquals(END, instance.end());
    }

    @Test
    public void ensureClosedend() {
        assertFalse(instance.hasOpenEnd());
    }
}
