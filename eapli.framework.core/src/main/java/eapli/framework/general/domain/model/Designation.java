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

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.CheckIf;
import lombok.EqualsAndHashCode;

/**
 * Generic designation concept, e.g., name, short name, nickname. A designation
 * consists of some character string without heading and trailing white spaces
 * (but spaces in between are allowed).
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 * @author Paulo Gandra de Sousa
 */
@Embeddable
@EqualsAndHashCode
public class Designation implements ValueObject, Comparable<Designation>, StringMixin {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    @JsonProperty
    private final String name;

    /**
     * Protected constructor. To construct a new Designation instance use the
     * {@link #valueOf(String)} or {@link #tryValueOf(String)} method.
     *
     * @param name
     */
    protected Designation(final String name) {
        this.name = name;
    }

    protected Designation() {
        // for ORM
        name = null;
    }

    /**
     * Factory method for obtaining Designation value objects.
     * <p>
     * This factory method constructs the object or throws an exception if it is not possible to
     * create the value object due to business rules.
     *
     * @param name
     *
     * @return a new object corresponding to the value
     *
     * @throws IllegalArgumentException
     */
    public static Designation valueOf(final String name) {
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
    public static Either<String, Designation> tryValueOf(final String name) {
        return CheckIf.succeeds(StringPredicates.isPhrase(name), () -> new Designation(name),
                () -> "Name should neither be null nor empty nor have starting blank spaces");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(final Designation o) {
        return name.compareTo(o.name);
    }
}
