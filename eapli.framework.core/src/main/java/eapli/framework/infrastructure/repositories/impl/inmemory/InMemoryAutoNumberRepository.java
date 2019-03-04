package eapli.framework.infrastructure.repositories.impl.inmemory;

import eapli.framework.domain.model.identities.impl.NumberSequenceGenerator;

public class InMemoryAutoNumberRepository<T> extends InMemoryRepository<T, Long> {

    private static NumberSequenceGenerator gen = new NumberSequenceGenerator();

    public InMemoryAutoNumberRepository() {
        super(e -> gen.newId());
    }
}