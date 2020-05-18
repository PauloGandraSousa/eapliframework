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
package eapli.framework.general.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

/**
 * Generic description concept. It must have some content but no special rules
 * about the content exist.
 *
 * @author Paulo Gandra de Sousa
 */
@Embeddable
public class Description implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    @Column(name = "description")
    @XmlAttribute
    @JsonProperty("description")
    private final String value;

    /**
     * Protected constructor. To construct a new {@code Designation} instance use the
     * {@link Description#valueOf(String)} method
     *
     * @param name
     */
    protected Description(final String name) {
        Preconditions.nonEmpty(name, "Description should neither be null nor empty");

        value = name;
    }

    protected Description() {
        // for ORM
        value = null;
    }

    @Override
    public int length() {
        return value.length();
    }

    /**
     * Factory method for obtaining Designation value objects.
     *
     * @param name
     * @return a new object corresponding to the value
     */
    public static Description valueOf(final String name) {
        return new Description(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Description)) {
            return false;
        }

        final Description other = (Description) o;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(value).code();
    }
}
