/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.general.domain.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * An email address.
 *
 * @author Paulo Gandra Sousa
 */
@Embeddable
public class EmailAddress implements ValueObject, Comparable<EmailAddress>, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private final String email;

    protected EmailAddress(final String address) {
        Preconditions.nonEmpty(address, "email address  should neither be null nor empty");
        Preconditions.ensure(StringPredicates.isEmail(address), "Invalid E-mail format");

        email = address;
    }

    protected EmailAddress() {
        // for ORM
        email = "";
    }

    public static EmailAddress valueOf(final String address) {
        return new EmailAddress(address);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailAddress)) {
            return false;
        }

        final EmailAddress that = (EmailAddress) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(email).code();
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public int compareTo(final EmailAddress o) {
        return email.compareTo(o.email);
    }
}
