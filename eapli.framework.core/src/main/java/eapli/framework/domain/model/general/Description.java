/*
 * Copyright (c) 2013-2019 the original author or authors.
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
package eapli.framework.domain.model.general;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.validations.Preconditions;

/**
 * Generic description concept.it must have some content but no special rules
 * about the content exist.
 *
 * <p>
 * For Marshaling purposes we are setting the visibility to ANY to allow Jackson
 * to find the private field as there are no setters and getters.
 *
 * @author Paulo Gandra de Sousa
 */
@Embeddable
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Description implements ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String theDescription;

    /**
     * protected constructor. to construct a new Designation instance use the
     * valueOf() method
     *
     * @param name
     */
    protected Description(final String name) {
        Preconditions.nonEmpty(name, "Description should neither be null nor empty");

        theDescription = name;
    }

    protected Description() {
        // for ORM
        theDescription = null;
    }

    @Override
    public int length() {
        return theDescription.length();
    }

    /**
     * factory method for obtaining Designation value objects.
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
        return theDescription.equals(other.theDescription);
    }

    @Override
    public String toString() {
        return theDescription;
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(theDescription).code();
    }
}
