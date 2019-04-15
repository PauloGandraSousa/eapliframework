package eapli.framework.infrastructure.repositories.impl.inmemory;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.identities.impl.NumberSequenceGenerator;

public class InMemoryDomainAutoNumberRepository<T extends AggregateRoot<Long>>
        extends InMemoryDomainRepository<Long, T> {
    private static NumberSequenceGenerator gen = new NumberSequenceGenerator();

    public InMemoryDomainAutoNumberRepository() {
        super(e -> gen.newId());
    }
}
