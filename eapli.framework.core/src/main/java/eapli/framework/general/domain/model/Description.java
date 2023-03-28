/*
 * Copyright (c) 2013-2023 the original author or authors.
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
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import eapli.framework.validations.CheckIf;
import lombok.EqualsAndHashCode;

/**
 * Generic description concept. It must have some content but no special rules
 * about the content exist.
 *
 * @author Paulo Gandra de Sousa
 */
@Embeddable
@EqualsAndHashCode
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
        value = name;
    }

    protected Description() {
        // for ORM
        value = null;
    }

    /**
     * Factory method for obtaining Designation value objects.
     * <p>
     * This factory method constructs the object or throws an exception if it is not possible to
     * create the value object due to business rules.
     *
     * @param name
     *
     * @throws IllegalArgumentException
     *
     * @return a new object corresponding to the value
     */
    public static Description valueOf(final String name) {
        return tryValueOf(name).rightValueOrElseThrow(IllegalArgumentException::new);
    }

    /**
     * Factory method for obtaining Designation value objects.
     * <p>
     * This factory method avoids the use of exceptions as a way to control the execution flow and
     * instead makes it explicit that creation of the value object might fail due to business rules.
     *
     * @param name
     *
     * @return
     */
    public static Either<String, Description> tryValueOf(final String name) {
        return CheckIf.nonEmpty(name, () -> new Description(name),
                () -> "Description should neither be null nor empty");
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return value;
    }
}
