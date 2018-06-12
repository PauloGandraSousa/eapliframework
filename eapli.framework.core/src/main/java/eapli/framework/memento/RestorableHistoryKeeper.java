/**
 *
 */
package eapli.framework.memento;

import java.util.ArrayDeque;
import java.util.Deque;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Invariants;

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
