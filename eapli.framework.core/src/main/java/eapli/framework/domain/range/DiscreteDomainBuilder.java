package eapli.framework.domain.range;

import java.util.HashSet;
import java.util.Set;

import eapli.framework.util.Preconditions;
import eapli.framework.util.Factory;

public class DiscreteDomainBuilder<T> implements Factory<DiscreteDomain<T>> {
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
