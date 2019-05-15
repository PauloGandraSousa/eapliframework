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
package eapli.framework.infrastructure.repositories.impl.inmemory;

import java.util.Optional;
import java.util.function.Function;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntity;
import eapli.framework.domain.repositories.DomainRepository;

/**
 * an in-memory repository that uses the domain object's identity as primary
 * key. expects the domain identity to be assigned to the object prior to
 * passing it to the repository
 *
 * @author Paulo Gandra de Sousa
 * @param <I>
 * @param <T>
 */
public class InMemoryDomainRepository<I extends Comparable<I>/* & ValueObject */, T extends AggregateRoot<I>>
        extends InMemoryRepository<T, I> implements DomainRepository<I, T> {

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
}