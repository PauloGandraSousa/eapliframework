/*
 * Copyright (c) 2013-2021 the original author or authors.
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
package eapli.framework.infrastructure.repositories.impl.inmemory;

import java.util.Optional;
import java.util.function.Function;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.domain.repositories.LockableDomainRepository;

/**
 * An in-memory repository that uses the domain object's identity as primary
 * key. Expects the domain identity to be assigned to the object prior to
 * passing it to the repository.
 *
 * @param <T>
 *            the entity type that we want to build a repository for
 * @param <I>
 *            the type of the <b>business identity</b> of the entity
 *
 * @author Paulo Gandra de Sousa
 */
public class InMemoryDomainRepository<T extends AggregateRoot<I>, I extends Comparable<I>/*
                                                                                          * &
                                                                                          * ValueObject
                                                                                          */>
        extends InMemoryRepository<T, I> implements DomainRepository<I, T>, LockableDomainRepository<I, T> {

    public InMemoryDomainRepository() {
        super(DomainEntity::identity);
    }

    protected InMemoryDomainRepository(final Function<? super T, I> identityGenerator) {
        super(identityGenerator);
    }

    @Override
    public Optional<T> ofIdentity(final I id) {
        return findById(id);
    }

    @Override
    public void deleteOfIdentity(final I id) {
        deleteById(id);
    }

    @Override
    public T lockItUp(final T entity) {
        // NOP
        return entity;
    }

    @Override
    public Optional<T> lockOfIdentity(final I id) {
        return ofIdentity(id);
    }
}