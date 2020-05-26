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
package eapli.framework.memento;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import eapli.framework.validations.Invariants;
import eapli.framework.validations.Preconditions;

/**
 * A history keeper that keeps track of {@link Memento} and allows to easily restore back to a
 * previous state in a LIFO manner.
 * <p>
 * This class is thread-safe. The {@link Restorable} object managed by this object should also be
 * thread-safe.
 *
 * @param <T>
 *            the type of object to track in the history
 *
 * @author Paulo Gandra Sousa
 *
 */
public class RestorableHistoryKeeper<T extends Restorable> {
    private final Deque<Memento> history = new ConcurrentLinkedDeque<>();
    private final T subject;

    /**
     * @param subject
     *            the object to track
     */
    public RestorableHistoryKeeper(final T subject) {
        Preconditions.nonNull(subject);

        this.subject = subject;
    }

    /**
     * Saves the current state of the subject.
     */
    public void savepoint() {
        history.push(subject.snapshot());
    }

    /**
     * Returns the subject to the previous state.
     */
    public void restore() {
        Invariants.ensure(canRestore());

        subject.restoreTo(history.poll());
    }

    /**
     * Checks if it is possible for the subject to return to a previous state.
     *
     * @return {@code true} if it is possible for the subject to return to a previous state.
     */
    public boolean canRestore() {
        return !history.isEmpty();
    }
}
