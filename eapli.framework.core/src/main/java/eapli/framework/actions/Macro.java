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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a collection of actions that can be executed as one single action (Composite
 * pattern). execution stops on the first action that returns false
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class Macro implements Action {

    private final List<Action> actions = new ArrayList<>();

    private Action errorCause;

    public Macro() {
    }

    public Macro(final Action... actions) {
        for (final Action a : actions) {
            this.actions.add(a);
        }
    }

    public void record(final Action action) {
        actions.add(action);
    }

    /**
     * Returns the action that caused macro execution to stop
     *
     * @return the action that caused macro execution to stop
     */
    public Optional<Action> errorCause() {
        return Optional.ofNullable(errorCause);
    }

    /**
     * execute all the actions in the Macro stopping on the first action that
     * fails (i.e., returns false)
     *
     * @see eapli.framework.actions.Action#execute()
     */
    @Override
    public boolean execute() {
        for (final Action e : actions) {
            if (!e.execute()) {
                errorCause = e;
                return false;
            }
        }
        return true;
    }
}
