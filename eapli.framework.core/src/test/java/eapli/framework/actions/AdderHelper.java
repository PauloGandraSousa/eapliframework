/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.actions;

/**
 * very simple sample class to test the use of actions
 *
 * @author Paulo Gandra de Sousa
 *
 */
class AdderHelper {

    static class Add1Action implements Action {
        private final AdderHelper target;

        public Add1Action(final AdderHelper target) {
            this.target = target;
        }

        @Override
        public boolean execute() {
            target.add1();
            return true;
        }
    }

    static class AddAction implements UndoableAction {
        private final AdderHelper target;
        private final int lump;

        public AddAction(final AdderHelper target, final int lump) {
            this.target = target;
            this.lump = lump;
        }

        @Override
        public boolean execute() {
            target.add(lump);
            return true;
        }

        @Override
        public void undo() {
            target.subtract(lump);
        }
    }

    public Action adder() {
        return new Add1Action(this);
    }

    public Action adder(final int inc) {
        return new AddAction(this, inc);
    }

    private int current;

    public int add(final int x) {
        current += x;
        return current();
    }

    public int add1() {
        current++;
        return current();
    }

    public int subtract(final int x) {
        current -= x;
        return current();
    }

    public int current() {
        return current;
    }
}
