/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.framework.time.domain.model;

import java.util.Calendar;

import eapli.framework.domains.domain.model.Interval;

/**
 * A concrete interval for Calendars.
 * <p>
 * A time interval is either a closed interval between time A and time B or a
 * closed interval starting at time A <i>ad infinitum<i>
 * </p>
 * <p>
 * Since this class inherits from a generic it cannot control how Calendar
 * objects will be persisted with JPA. See {@link TimestampInterval} and
 * {@link DateInterval} for persistent alternatives.
 *
 * @author Paulo Gandra Sousa
 *
 */
public class TimeInterval extends Interval<Calendar> {

    private static final long serialVersionUID = 1L;

    /**
     * creates a closed time interval on both ends
     *
     * @param begin
     * @param end
     */
    public TimeInterval(final Calendar begin, final Calendar end) {
        super(begin, BoundaryLimitType.CLOSED, end, BoundaryLimitType.CLOSED);
    }

    /**
     * creates a time interval from begin until "infinity"
     *
     * @param begin
     */
    public TimeInterval(final Calendar begin) {
        super(begin, BoundaryLimitType.CLOSED, null, BoundaryLimitType.INFINITY);
    }

    protected TimeInterval() {
        // ensure no instantion of incomplete object
    }
}
