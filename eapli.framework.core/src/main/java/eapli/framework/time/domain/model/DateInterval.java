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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;

/**
 * A persistent date period. Only the date part of calendar is relevant.
 * DateIntervals are closed ranges.
 *
 * @author Paulo Gandra Sousa
 *
 */
@Embeddable
public class DateInterval implements ValueObject {

    private static final long serialVersionUID = -3829067411069027990L;

    @XmlAttribute
    @JsonProperty
    @Temporal(TemporalType.DATE)
    private final Calendar start;

    @XmlAttribute
    @JsonProperty
    @Temporal(TemporalType.DATE)
    private final Calendar end;

    @XmlTransient
    @JsonIgnore
    @Transient
    private final TimeInterval period;

    /**
     * constructs a closed range between start and end
     *
     * @param start
     * @param end
     */
    public DateInterval(final Calendar start, final Calendar end) {
        Preconditions.noneNull(start, end);

        this.start = Calendars.dateOf(start);
        this.end = Calendars.dateOf(end);
        period = new TimeInterval(this.start, this.end);
    }

    /**
     * constructs a closed range starting at start ad infinitum
     *
     * @param start
     */
    public DateInterval(final Calendar start) {
        Preconditions.nonNull(start);

        this.start = Calendars.dateOf(start);
        end = null;
        period = new TimeInterval(this.start);
    }

    protected DateInterval() {
        // ORM
        start = end = null;
        period = null;
    }

    public Calendar start() {
        return start;
    }

    public Calendar end() {
        return start;
    }

    public boolean includes(final Calendar target) {
        Preconditions.nonNull(target);

        return period.includes(target);
    }
}
