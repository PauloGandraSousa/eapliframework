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
package eapli.framework.representations.builders;

import java.math.BigDecimal;
import java.math.BigInteger;

import eapli.framework.general.domain.model.Money;
import eapli.framework.representations.RepresentationBuilder;
import eapli.framework.validations.Invariants;

/**
 * A simple JSON Representation builder. Null values are ignored and no property
 * is created. Dates are represented as ISO8601.
 * <p>
 * This class is mainly for pedagogical purposes; consider using a well-known
 * library for marshaling and unmarshaling like
 * <a href="https://github.com/FasterXML/jackson">Jackson</a>
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class JsonRepresentationBuilder implements RepresentationBuilder<String> {

    private static final String PROPERTIES_CAN_ONLY_BE_ADDED_TO_OBJECTS = "Properties can only be added to objects";

    private static final String CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED = "Cannot add an element to a collection that has not started";

    private static final String CALL_START_FIRST = "call start() first";

    private final StringBuilder buffer = new StringBuilder();

    private final JsonRepresentationBuilderState state = new JsonRepresentationBuilderState();

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

    private void ensurePropertyInvariants() {
        Invariants.ensure(state.hasStarted(), CALL_START_FIRST);
        Invariants.ensure(state.isInObject(), PROPERTIES_CAN_ONLY_BE_ADDED_TO_OBJECTS);
        Invariants.ensure(!state.isInCollection(), "Cannot add a property to a collection");
    }

    private void ensureElementInvariants() {
        Invariants.ensure(state.hasStarted(), CALL_START_FIRST);
        Invariants.ensure(state.isInCollection(), CANNOT_ADD_AN_ELEMENT_TO_A_COLLECTION_THAT_HAS_NOT_STARTED);
    }

    @Override
    public String build() {
        Invariants.ensure(state.hasStarted(), CALL_START_FIRST);
        Invariants.ensure(state.canFinish(),
                "cannot build because there are child objects or collections that weren't ended");

        if (state.needsClosing()) {
            if (state.isRootObject()) {
                buffer.append('}');
            } else {
                buffer.append(']');
            }
        }

        return buffer.toString();
    }

    @Override
    public RepresentationBuilder<String> startObject(final String name) {
        if (!state.hasStarted()) {
            // root object
            buffer.append('{');
            state.startRootObject();
        } else {
            // child object
            Invariants.ensure(!state.hasEnded());

            appendSeparatorIfNeeded();
            if (state.isInObject()) {
                appendWrappedStringValue(name);
                buffer.append(':');
            }
            buffer.append('{');
            state.enterObject();
        }

        return this;
    }

    @Override
    public RepresentationBuilder<String> endObject() {
        Invariants.ensure(state.hasStarted(), CALL_START_FIRST);
        Invariants.ensure(state.isInObject(), "Cannot end an object if there was no object started");

        state.leave();

        buffer.append('}');
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final String value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        appendWrappedStringValue(value);
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Long value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Integer value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Double value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Float value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Boolean value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final BigInteger value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final BigDecimal value) {
        ensureElementInvariants();

        appendSeparatorIfNeeded();
        buffer.append(value != null ? value.toString() : "null");
        return this;
    }

    @Override
    public RepresentationBuilder<String> withElement(final Money value) {
        ensureElementInvariants();

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
        if (!state.hasStarted()) {
            // root collection
            buffer.append('[');
            state.startRootCollection();
        } else {
            // collection property or element
            Invariants.ensure(!state.hasEnded());

            appendSeparatorIfNeeded();
            appendPropertyIfAppropriate(name);
            buffer.append('[');
            state.enterCollection();
        }

        return this;
    }

    private void appendPropertyIfAppropriate(final String name) {
        if (state.isInObject()) {
            appendWrappedStringValue(name);
            buffer.append(':');
        }
    }

    @Override
    public RepresentationBuilder<String> endCollection() {
        Invariants.ensure(state.hasStarted(), CALL_START_FIRST);
        Invariants.ensure(state.isInCollection(), "Cannot end a collection that has not started");

        state.leave();

        buffer.append(']');
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final String value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendStringProperty(name, value);
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Long value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Integer value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Double value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Float value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Boolean value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final Money value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendSeparatorIfNeeded();
            appendWrappedStringValue(name);
            buffer.append(':');
            appendMoneyObject(value);
        }
        return this;
    }

    private void appendMoneyObject(final Money value) {
        buffer.append('{');
        appendNativeProperty("amount", String.valueOf(value.amountAsDouble()));
        appendStringProperty("currency", value.currency().getCurrencyCode());
        buffer.append('}');
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final BigInteger value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public RepresentationBuilder<String> withProperty(final String name, final BigDecimal value) {
        ensurePropertyInvariants();

        if (value != null) {
            appendNativeProperty(name, String.valueOf(value));
        }
        return this;
    }
}
