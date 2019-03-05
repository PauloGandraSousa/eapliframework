/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.framework.domain.model.range;

/**
 * base class for testing Range
 * 
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S2187")
public class AbstractRangeTest {

    protected static final Long START_VALUE = 5L;
    protected static final Long DELTA_VALUE = 5L;
    protected static final Long END_VALUE = START_VALUE + DELTA_VALUE;

    protected static final Long START = START_VALUE;
    protected static final Long END = END_VALUE;

    protected static Range<Long> instance;
}
