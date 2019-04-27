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

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.util.Invariants;

/**
 * @author sou03408
 *
 */
/* package */ class JsonRepresentationBuilderState {

    private enum StartingDocument {
        NOT_STARTED, ROOT_OBJECT, ROOT_COLLECTION
    };

    private StartingDocument hasStarted;

    private static final class StateContext {
        public enum Type {
            OBJECT, COLLECTION
        }

        private final Type type;

        public boolean isInCollection() {
            return type == StateContext.Type.COLLECTION;
        }

        public boolean isInObject() {
            return type == StateContext.Type.OBJECT;
        }

        public StateContext(final Type type) {
            this.type = type;
        }

        public static StateContext moveToObject() {
            return new StateContext(Type.OBJECT);
        }

        public static StateContext moveToCollection() {
            return new StateContext(Type.COLLECTION);
        }
    }

    private final Deque<StateContext> state = new ArrayDeque<>();

    public JsonRepresentationBuilderState() {
        hasStarted = StartingDocument.NOT_STARTED;
    }

    public void startRootObject() {
        Invariants.ensure(hasStarted == StartingDocument.NOT_STARTED);

        state.push(StateContext.moveToObject());
        hasStarted = StartingDocument.ROOT_OBJECT;
    }

    public void startRootCollection() {
        Invariants.ensure(hasStarted == StartingDocument.NOT_STARTED);

        state.push(StateContext.moveToCollection());
        hasStarted = StartingDocument.ROOT_COLLECTION;
    }

    public boolean isRootObject() {
        return hasStarted == StartingDocument.ROOT_OBJECT;
    }

    public boolean isRootCollection() {
        return hasStarted == StartingDocument.ROOT_COLLECTION;
    }

    public boolean hasStarted() {
        return hasStarted != StartingDocument.NOT_STARTED;
    }

    public void enterCollection() {
        state.push(StateContext.moveToCollection());
    }

    public void enterObject() {
        state.push(StateContext.moveToObject());
    }

    public boolean isInCollection() {
        final StateContext h = state.peek();
        return h != null ? h.isInCollection() : false;
    }

    public boolean isInObject() {
        final StateContext h = state.peek();
        return h != null ? h.isInObject() : false;
    }

    public boolean canFinish() {
        return state.size() <= 1;
    }

    public void leave() {
        Invariants.ensure(!state.isEmpty());
        state.pop();
    }

    public boolean needsClosing() {
        return state.size() == 1;
    }

    public boolean hasClosed() {
        return state.isEmpty();
    }
}
