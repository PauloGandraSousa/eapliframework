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
package eapli.framework.representations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.model.general.EmailAddress;
import eapli.framework.domain.model.general.Money;
import eapli.framework.util.DateTime;
import eapli.framework.util.Factory;

/**
 * A Builder interface to generate representations of business objects, usually
 * a DTO
 *
 * the agreed contract is that the driver object will call start() prior to any
 * other method in the interface. however, implementations of this contract are
 * advised to program defensively and account for situation where start() is not
 * called or called out of order.
 *
 * Implementations are free to handle duplicate properties in whatever form
 * suits it best, either by ignoring, adding duplicates or launching exceptions.
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
     * @param typeName
     *            the name of the type of object being represented. usually the
     *            simplified class name
     * @return this builder instance
     */
    default RepresentationBuilder<T> of(final String typeName) {
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
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    RepresentationBuilder<T> withElement(String value);

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Long value) {
        return withElement(String.valueOf(value));
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Integer value) {
        return withElement(String.valueOf(value));
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Double value) {
        return withElement(String.valueOf(value));
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Float value) {
        return withElement(String.valueOf(value));
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Boolean value) {
        return withElement(String.valueOf(value));
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final BigInteger value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final BigDecimal value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Money value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Designation value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Description value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final EmailAddress value) {
        return withElement(value.toString());
    }

    /**
     * Adds an element to the current collection. Requires that
     * {@link #startCollection(String)} was called priorly
     *
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withElement(final Calendar value) {
        return withElement(DateTime.iso8601(value));
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
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Long value) {
        return withProperty(name, String.valueOf(value));
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Integer value) {
        return withProperty(name, String.valueOf(value));
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Double value) {
        return withProperty(name, String.valueOf(value));
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Float value) {
        return withProperty(name, String.valueOf(value));
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Boolean value) {
        return withProperty(name, String.valueOf(value));
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final BigInteger value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final BigDecimal value) {
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
    default RepresentationBuilder<T> withProperty(final String name, final Money value) {
        return withProperty(name, value.toString());
    }

    /**
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
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
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
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
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
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
     * Adds (or updates) a property to the current scope, i.e., root or current
     * child object
     *
     * @param name
     *            the name of the property
     * @param value
     *            the value of the property
     * @return this builder instance
     */
    default RepresentationBuilder<T> withProperty(final String name, final Calendar value) {
        return withProperty(name, DateTime.iso8601(value));
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
     * Signals the end of a collection
     *
     * @return this builder instance
     */
    default RepresentationBuilder<T> endCollection() {
        return this;
    }
}
