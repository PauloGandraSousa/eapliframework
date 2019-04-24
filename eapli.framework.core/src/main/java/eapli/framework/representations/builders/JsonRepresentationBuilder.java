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
package eapli.framework.representations.builders;

import java.math.BigDecimal;
import java.math.BigInteger;

import eapli.framework.domain.model.general.Money;
import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.util.Invariants;

/**
 * A simple JSON Representation builder. null values are ignored and no property
 * is created. Dates are represented as ISO8601
 *
 * This is class is mainly for pedagogical purposes; consider using a well-known
 * library for marshaling and unmarshaling like
 * <a href="https://github.com/FasterXML/jackson">Jackson</a>
 *
 * @author sou03408
 *
 */
public class JsonRepresentationBuilder implements RepresentationBuilder<String> {

    private static final String CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED = "Cannot add an element to a collection that has not started";

    private static final String CALL_START_FIRST = "call start() first";

    private final StringBuilder buffer = new StringBuilder("{");

    /**
     * level of object nesting to track down child objects
     */
    private int childDepth = 0;

    /**
     * level of object nesting to track down collections
     */
    private int collectionDepth = 0;

    /**
     * flag to indicate if the starting method {@link #of(String)} has been
     * called
     */
    private boolean hasStarted = false;

    private void appendWrappedStringValue(final String value) {
        if (value != null) {
            buffer.append('"');
            buffer.append(value);
            buffer.append('"');
        } else {
            buffer.append("null");
        }
    }

    private void appendStringProperty(final String name, final String value) {
        appendSeparatorIfNeeded();
        appendWrappedStringValue(name);
        buffer.append(':');
        appendWrappedStringValue(value);
    }

    private void appendSeparatorIfNeeded() {
        final char last = buffer.charAt(buffer.length() - 1);
        if (last != '{' && last != '[') {
            buffer.append(',');
        }
    }

    private void appendNativeProperty(final String name, final String value) {
        appendSeparatorIfNeeded();
        appendWrappedStringValue(name);
        buffer.append(':');
        buffer.append(value);
    }

    @Override
    public String build() {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(childDepth == 0, "cannot build because there are child objects that weren't ended");
        Invariants.ensure(collectionDepth == 0, "cannot build because there are collections that weren't ended");

        buffer.append('}');
        return buffer.toString();
    }

    @Override
    public RepresentationBuilder<String> of(final String typeName) {
        Invariants.ensure(!hasStarted, "cannot start more than once");

        hasStarted = true;
        return this;
    }

    @Override
    public RepresentationBuilder<String> startChild(final String name) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        childDepth++;

        appendSeparatorIfNeeded();

        appendWrappedStringValue(name);
        buffer.append(':');
        buffer.append('{');
        return this;
    }

    @Override
    public RepresentationBuilder<String> endChild() {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.isPositive(childDepth, "Cannot end a child if there was no child started");

        childDepth--;

        buffer.append('}');
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final String value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        appendWrappedStringValue(value);
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Long value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Integer value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Double value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Float value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Boolean value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final BigInteger value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final BigDecimal value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Money value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);

        appendSeparatorIfNeeded();
        if (value != null) {
            appendMoneyObject(value);
        } else {
            buffer.append("null");
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> startCollection(final String name) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        collectionDepth++;

        appendSeparatorIfNeeded();
        appendWrappedStringValue(name);
        buffer.append(':');
        buffer.append('[');
        return this;
    }

    @Override
    public RepresentationBuilder<String> endCollection() {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        Invariants.ensure(collectionDepth > 0, "Cannot end a collection that has not started");

        collectionDepth--;

        buffer.append(']');
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final String value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendStringProperty(name, value);
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Long value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Integer value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Double value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Float value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);
        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Boolean value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Money value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendWrappedStringValue(name);
            buffer.append(':');
            appendMoneyObject(value);
        }
        return this;
    }

    private void appendMoneyObject(final Money value) {
        buffer.append('{');
        appendNativeProperty("amount", String.valueOf(value.amount()));
        appendStringProperty("currency", value.currency().getCurrencyCode());
        buffer.append('}');
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final BigInteger value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final BigDecimal value) {
        Invariants.ensure(hasStarted, CALL_START_FIRST);

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }
}
