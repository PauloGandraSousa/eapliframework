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
package eapli.framework.domain.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.model.general.EmailAddress;
import eapli.framework.domain.model.general.Money;
import eapli.framework.util.Factory;

/**
 * A Builder interface to generate representations of business objects, usually
 * a DTO
 *
 * the agreed contract is that the driver object will call start() prior to any
 * other method in the interface. however, implementations of this contract are
 * advised to program defensively and account for situation where start() is not
 * called or called out of order
 *
 * @param <T>
 *            the type of Representation that will be generated, e.g., a DTO
 *
 * @author sou03408
 *
 */
public interface RepresentationBuilder<T> extends Factory<T> {
    /**
     * Signals the starting of the object
     *
     * @return this builder instance
     */
    default RepresentationBuilder<T> start(final String name) {
        return this;
    }

    /**
     * Signals the start of a named collection of elements
     *
     * @param name
     * @return this builder instance
     */
    default RepresentationBuilder<T> startCollection(final String name) {
        return this;
    }

    /**
     * Signals the start of a collection's element
     *
     * @param hasPrevious
     *            indicates if there was an element prior to this one and as
     *            such the builder should account for a seperator between the
     *            previous element ans this one
     * @return this builder instance
     */
    default RepresentationBuilder<T> startCollectionElement(final boolean hasPrevious) {
        return this;
    }

    /**
     * Signals the end of an element
     *
     * @return this builder instance
     */
    default RepresentationBuilder<T> endCollectionElement() {
        return this;
    }

    /**
     * Signals the end of a collection
     *
     * @return this builder instance
     */
    default RepresentationBuilder<T> endCollection() {
        return this;
    }

    /**
     * Signals the start of a child object
     *
     * @param name
     * @return this builder instance
     */
    default RepresentationBuilder<T> startChild(final String name) {
        return this;
    }

    /**
     * Signals the end of a child object
     *
     * @return this builder instance
     */
    default RepresentationBuilder<T> endChild() {
        return this;
    }

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, String value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, long value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, double value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, boolean value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, BigInteger value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, BigDecimal value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Money value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Designation value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Description value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final EmailAddress value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, Calendar value);

    /**
     * Adds (or updates) a property
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withProperty(String name, Object value);

    /**
     * Adds a property which value is an array
     *
     * @param name
     * @param value
     * @return this builder instance
     */
    default RepresentationBuilder<T> withArrayProperty(final String name, final Object[] value) {
        startCollection(name);
        for (int i = 0; i < value.length; i++) {
            startCollectionElement(i >= 1);
            withProperty(name + "_" + i, value[i]);
            endCollectionElement();
        }
        return endCollection();
    }

    /**
     * Adds a property which value is a collection
     *
     * @param name
     * @param value
     * @return this builder instance
     */
    default RepresentationBuilder<T> withCollectionProperty(final String name, final Iterable<Object> value) {
        startCollection(name);
        int i = 0;
        for (final Object each : value) {
            startCollectionElement(i >= 1);
            withProperty(name + "_" + i, each);
            endCollectionElement();
            i++;
        }
        return endCollection();
    }
}
