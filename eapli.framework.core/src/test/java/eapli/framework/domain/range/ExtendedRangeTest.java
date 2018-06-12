/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.range;

import org.junit.Test;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class ExtendedRangeTest extends AbstractRangeTest {

    private static final Long NEW_START = END + 2;
    private static final Long NEW_END = START - 2;

    public ExtendedRangeTest() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNewStartCannotBeAfterEnd() {
        instance = Range.closedFrom(START).closedTo(END).build().withStart(NEW_START);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNewEndCannotBeBeforeStart() {
        instance = Range.closedFrom(START).closedTo(END).build().withEnd(NEW_END);
    }
}
