package eapli.framework.persistence.repositories.impl.inmemory;

import java.io.Serializable;

import eapli.framework.domain.model.DomainEntity;

public abstract class InMemoryDomainEntityRepository<T extends DomainEntity<K>, K extends Serializable>
        extends InMemoryRepository<T, K> {

    public InMemoryDomainEntityRepository() {
        super(DomainEntity::id);
    }
}