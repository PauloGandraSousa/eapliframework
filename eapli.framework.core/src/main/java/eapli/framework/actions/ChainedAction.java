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
package eapli.framework.actions;

import eapli.framework.validations.Preconditions;

/**
 * A chained action allowing to compose sequences (chains) of actions. the
 * execution of the chain stops when the first action on the chain fails.
 *
 * It implements the Composite (GoF) pattern. subclasses must override execute()
 * AND call next() in the end
 *
 * @author Paulo Gandra Sousa
 *
 */
public class ChainedAction implements Action {

    private final Action current;
    private final Action next;

    /**
     * @param current
     *            the action to wrap
     * @param next
     *            the next action to execute. since this can in turn be another
     *            ChainedAction it allows to build an arbitrarily long chain of
     *            actions
     */
    public ChainedAction(final Action current, final Action next) {
        // the cast to Object is needed to force the compiler to fetch the right
        // override {@link Preconditions#nonNull(Object...)}
        Preconditions.nonNull((Object) current, next);

        this.current = current;
        this.next = next;
    }

    protected boolean next() {
        return next.execute();
    }

    @Override
    public boolean execute() {
        if (current.execute()) {
            return next();
        }
        return false;
    }

    /**
     * Builder method. Creates a chained action
     *
     * @param first
     * @return
     */
    public static ChainedAction first(final Action first) {
        return new ChainedAction(Actions.SUCCESS, first);
    }

    /**
     * Builder method. Creates a new ChainedAction where the {@code next} action
     * is appended to the end of the current chain
     *
     * @param next
     * @return
     */
    public ChainedAction then(final Action next) {
        return new ChainedAction(this, next);
    }
}
