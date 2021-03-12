/*
 * Copyright (c) 2013-2020 the original author or authors.
 *
 * MIT License
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

/**
 * very simple sample class for the use of memento
 *
 * @author Paulo Gandra de Sousa
 *
 */
class SampleAdder implements Restorable {

    private int current;

    int add(final int x) {
        current += x;
        return current();
    }

    int subtract(final int x) {
        current -= x;
        return current();
    }

    int current() {
        return current;
    }

    //
    // MEMENTO
    //

    private class SampleAdderMemento implements Memento {
        private final int state;

        public SampleAdderMemento(final int x) {
            state = x;
        }

        public int getState() {
            return state;
        }
    }

    @Override
    public Memento snapshot() {
        return new SampleAdderMemento(current);
    }

    @Override
    public void restoreTo(final Memento previousState) {
        if (!(previousState instanceof SampleAdderMemento)) {
            throw new IllegalArgumentException();
        }
        final SampleAdderMemento previous = (SampleAdderMemento) previousState;
        current = previous.getState();
    }

}
