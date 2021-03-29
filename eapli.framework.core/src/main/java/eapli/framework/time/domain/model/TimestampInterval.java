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

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

/**
 * A persistent time period. Both the date and the time part of calendar are
 * relevant.
 *
 * @author Paulo Gandra Sousa
 *
 */
@Embeddable
public class TimestampInterval implements ValueObject {

    private static final long serialVersionUID = -4658136141745243778L;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private final Calendar start;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private final Calendar end;

    @JsonProperty
    @Transient
    private final TimeInterval period;

    /**
     * Constructs a closed time interval
     *
     * @param start
     * @param end
     */
    public TimestampInterval(final Calendar start, final Calendar end) {
        Preconditions.noneNull(start, end);

        this.start = start;
        this.end = end;
        period = new TimeInterval(start, end);
    }

    protected TimestampInterval() {
        // ORM
        start = end = null;
        period = null;
    }

    /**
     * Returns the start of the interval.
     *
     * @return the start of the interval.
     */
    public Calendar start() {
        return start;
    }

    /**
     * Returns the end of the interval.
     *
     * @return the end of the interval
     */
    public Calendar end() {
        return start;
    }

    /**
     * Checks if the given date is within this interval.
     *
     * @param target
     * @return true if the given date is within this interval
     */
    public boolean includes(final Calendar target) {
        Preconditions.nonNull(target);

        return period.includes(target);
    }
}
