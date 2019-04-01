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

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.DateTime;
import eapli.framework.util.HashCoder;
import eapli.framework.util.Preconditions;

/**
 * @author Paulo Gandra Sousa
 */
@Entity
public class RoleAssignment implements ValueObject, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue // (strategy = GenerationType.IDENTITY)
    private Long pk;

    private final Role type;
    @Temporal(TemporalType.DATE)
    private final Calendar assignedOn;

    public RoleAssignment(final Role type) {
        this(type, DateTime.now());
    }

    public RoleAssignment(final Role type, final Calendar assignedOn) {
        Preconditions.nonNull(type, assignedOn);

        this.type = type;
        this.assignedOn = assignedOn;
    }

    protected RoleAssignment() {
        // for ORM
        type = null;
        assignedOn = null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleAssignment)) {
            return false;
        }

        final RoleAssignment other = (RoleAssignment) o;
        return type == other.type && assignedOn.equals(other.assignedOn);
    }

    @Override
    public int hashCode() {
        return new HashCoder().of(type).code();
    }

    @Override

    public String toString() {
        return type + "@" + assignedOn;
    }

    public Role type() {
        return type;
    }

    public boolean isOf(final Role r) {
        return type.equals(r);
    }
}
