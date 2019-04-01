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
package eapli.framework.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.util.Invariants;
import eapli.framework.util.Preconditions;

/**
 * @author Paulo Gandra Sousa
 *
 */
public class RestorableHistoryKeeper<T extends Restorable> {
    private final Deque<Memento> history = new ArrayDeque<>();
    private final T subject;

    public RestorableHistoryKeeper(final T subject) {
        Preconditions.nonNull(subject);

        this.subject = subject;
    }

    public void savepoint() {
        history.push(subject.snapshot());
    }

    public void restore() {
        Invariants.ensure(this::canRestore);

        subject.restoreTo(history.poll());
    }

    public boolean canRestore() {
        return !history.isEmpty();
    }
}
