package eapli.framework.domain.model.range;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import eapli.framework.util.Factory;
import eapli.framework.util.Preconditions;

/**
 *
 * @author sou03408
 *
 * @param <T>
 */
public class DiscreteDomainBuilder<T extends Serializable> implements Factory<DiscreteDomain<T>> {
    private final Set<T> from = new HashSet<>();

    public DiscreteDomainBuilder<T> add(final T e) {
        Preconditions.nonNull(e);

        from.add(e);
        return this;
    }

    public DiscreteDomainBuilder<T> add(final T... elements) {
        for (final T e : elements) {
            from.add(e);
        }
        return this;
    }

    public DiscreteDomainBuilder<T> forget(final T e) {
        Preconditions.nonNull(e);

        from.remove(e);
        return this;
    }

    @Override
    public DiscreteDomain<T> build() {
        return new DiscreteDomain<>(from);
    }
}
