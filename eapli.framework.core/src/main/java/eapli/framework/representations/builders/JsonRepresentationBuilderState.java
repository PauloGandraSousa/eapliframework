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

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.validations.Invariants;

/**
 * Internal state management for the JSON representation builder
 *
 * @author Paulo Gandra de Sousa
 *
 */
/* package */ class JsonRepresentationBuilderState {

    /**
     * type of JSON document we are building, either a collection or an object.
     *
     * @author Paulo Gandra de Sousa
     *
     */
    private enum JsonDocumentType {
        NOT_YET_DEFINED, ROOT_OBJECT, ROOT_COLLECTION
    }

    private JsonDocumentType type;

    /**
     * indicates whether the builder is currently building an object or a collection
     *
     * @author Paulo Gandra de Sousa
     *
     */
    private static final class StateContext {
        public enum StateContextType {
            OBJECT, COLLECTION
        }

        private final StateContextType ctxType;

        public boolean isInCollection() {
            return ctxType == StateContext.StateContextType.COLLECTION;
        }

        public boolean isInObject() {
            return ctxType == StateContext.StateContextType.OBJECT;
        }

        public StateContext(final StateContextType type) {
            ctxType = type;
        }

        public static StateContext moveToObject() {
            return new StateContext(StateContextType.OBJECT);
        }

        public static StateContext moveToCollection() {
            return new StateContext(StateContextType.COLLECTION);
        }
    }

    /**
     * stack of object/collection state
     */
    private final Deque<StateContext> state = new ArrayDeque<>();

    public JsonRepresentationBuilderState() {
        type = JsonDocumentType.NOT_YET_DEFINED;
    }

    /**
     * begins the construction state for an object document
     */
    public void startRootObject() {
        Invariants.ensure(type == JsonDocumentType.NOT_YET_DEFINED);

        state.push(StateContext.moveToObject());
        type = JsonDocumentType.ROOT_OBJECT;
    }

    /**
     * begins the construction state for a collection document
     */
    public void startRootCollection() {
        Invariants.ensure(type == JsonDocumentType.NOT_YET_DEFINED);

        state.push(StateContext.moveToCollection());
        type = JsonDocumentType.ROOT_COLLECTION;
    }

    /**
     * Returns true if the current document was signaled as an object, i.e., { ... }
     *
     * @return true if the current document was signaled as an object
     */
    public boolean isRootObject() {
        return type == JsonDocumentType.ROOT_OBJECT;
    }

    /**
     * returns true if the current document was signaled as a collection, i.e., [
     * ... ]
     *
     * @return true if the current document was signaled as a collection
     */
    public boolean isRootCollection() {
        return type == JsonDocumentType.ROOT_COLLECTION;
    }

    /**
     * Returns if the builder has been initialized.
     *
     * @return if the builder has been initialized.
     */
    public boolean hasStarted() {
        return type != JsonDocumentType.NOT_YET_DEFINED;
    }

    /**
     * signals a new state to construct a collection in the current document
     */
    public void enterCollection() {
        state.push(StateContext.moveToCollection());
    }

    /**
     * signals a new state to construct an object in the current document
     */
    public void enterObject() {
        state.push(StateContext.moveToObject());
    }

    /**
     * Returns if the current state is a collection.
     *
     * @return if the current state is a collection.
     */
    public boolean isInCollection() {
        if (state.isEmpty()) {
            return false;
        }
        return state.peek().isInCollection();
    }

    /**
     * Returns if the current state is an object.
     *
     * @return if the current state is an object.
     */
    public boolean isInObject() {
        if (state.isEmpty()) {
            return false;
        }
        return state.peek().isInObject();
    }

    /**
     * signals to leave the current state and return to the previous one
     */
    public void leave() {
        Invariants.ensure(!state.isEmpty());
        state.pop();
    }

    /**
     * Checks if the current state allows to end the creation of the root document.
     *
     * @return true if it is possible to build a json object
     */
    public boolean canFinish() {
        return state.size() <= 1;
    }

    /**
     * Checks if there is the need to close the root document.
     *
     * @return true if there is the need to close the root document
     */
    public boolean needsClosing() {
        return state.size() == 1;
    }

    /**
     * Checks if the builder has properly closed (left) all the objects/collections.
     *
     * @return true if the builder has been properly closed
     */
    public boolean hasEnded() {
        return state.isEmpty();
    }
}
