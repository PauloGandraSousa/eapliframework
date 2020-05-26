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
package eapli.framework.actions;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A collection of actions that can be executed as one single action. Execution
 * stops on the first action that returns {@code false}.
 * <p>
 * This class is thread-safe. Several threads can share the same Macro object and execute it; each
 * one will get its own {@code errorCause} upon failure to execute an action.
 *
 * @author Paulo Gandra de Sousa
 *
 */
public class Macro implements Action {

    private final Queue<Action> actions = new ConcurrentLinkedQueue<>();

    private final ThreadLocal<Action> errorCause = ThreadLocal.withInitial(null);

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
     * Returns the action that caused macro execution to stop. Each thread that executed the macro
     * will get the action that failed on that thread execution.
     *
     * @return the action that caused macro execution to stop
     */
    public Optional<Action> errorCause() {
        return Optional.ofNullable(errorCause.get());
    }

    /**
     * Execute all the actions in the Macro stopping on the first action that
     * fails (i.e., returns false).
     *
     * @see eapli.framework.actions.Action#execute()
     */
    @Override
    public boolean execute() {
        for (final Action e : actions) {
            if (!e.execute()) {
                errorCause.set(e);
                return false;
            }
        }
        errorCause.remove();
        return true;
    }
}
