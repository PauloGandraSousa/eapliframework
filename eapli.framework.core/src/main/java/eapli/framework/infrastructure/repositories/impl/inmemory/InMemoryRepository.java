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

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eapli.framework.infrastructure.repositories.IterableRepository;
import eapli.framework.validations.Invariants;

/**
 * an implementation of repositories in memory. useful for test purposes. this
 * class is parameterized with an identity generation mapping function in the
 * constructor
 *
 * @param <T>
 *            the type to be used of the objects stored in the repository
 * @param <K>
 *            the type to be used as key of an object
 */
public class InMemoryRepository<T, K> implements IterableRepository<T, K> {

    // Ideally this would be a typed generic Map but since it is a static member
    // it cannot be generic. the solution is to use the old-style untyped Map
    // and cast whenever needed
    @SuppressWarnings("rawtypes")
    private static final Map DATA = new HashMap();
    private final Class<T> entityClass;

    private final Function<? super T, ? extends K> identityGenerator;

    /**
     *
     * @param identityGenerator
     *            a function that maps an Entity to its key
     */
    @SuppressWarnings("unchecked")
    public InMemoryRepository(final Function<? super T, ? extends K> identityGenerator) {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.identityGenerator = identityGenerator;
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "static-access", "squid:S2209" })
    protected Map<K, T> data() {
        if (!this.DATA.containsKey(this.entityClass)) {
            this.DATA.put(this.entityClass, new HashMap());
        }
        return (Map<K, T>) this.DATA.get(this.entityClass);
    }

    @Override
    public Optional<T> first() {
        final Iterator<T> it = data().values().iterator();
        return it.hasNext() ? Optional.of(it.next()) : Optional.empty();
    }

    @Override
    public Iterable<T> first(final int n) {
        final List<T> ret = new ArrayList<>();
        final Iterator<T> it = data().values().iterator();
        for (int i = n; i > 0 && it.hasNext(); i--) {
            ret.add(it.next());
        }
        return ret;
    }

    @Override
    public void delete(final T entity) {
        //
        // this could be made more efficient if we had a direct way to get the
        // ID of the entity, e.g.,
        // deleteById(keyOf(entity))
        //
        for (final Entry<K, T> each : data().entrySet()) {
            if (each.getValue().equals(entity)) {
                data().remove(each.getKey());
                break;
            }
        }
    }

    @Override
    public void deleteById(final K entityId) {
        data().remove(entityId);
    }

    @Override
    public Iterator<T> iterator(final int pagesize) {
        return data().values().iterator();
    }

    @Override
    public <S extends T> S save(final S entity) {
        data().put(identityGenerator.apply(entity), entity);
        return entity;
    }

    @Override
    public Iterable<T> findAll() {
        return data().values();
    }

    /**
     *
     * @param id
     *            K identifier for object
     * @return T if object identified by K is found, otherwise returns null.
     */
    @Override
    public Optional<T> findById(final K id) {
        return Optional.ofNullable(data().get(id));
    }

    @Override
    public long count() {
        return data().size();
    }

    @Override
    public Iterator<T> iterator() {
        return data().values().iterator();
    }

    private List<T> matchAll(final Predicate<T> filterFunc) {
        return valuesStream().filter(filterFunc).collect(Collectors.toList());
    }

    protected Stream<T> valuesStream() {
        return data().values().stream();
    }

    protected Iterable<T> match(final Predicate<T> filterFunc) {
        return matchAll(filterFunc);
    }

    protected Optional<T> matchOne(final Predicate<T> filterFunc) {
        final List<T> all = matchAll(filterFunc);
        if (all.isEmpty()) {
            return Optional.empty();
        }
        Invariants.ensure(all.size() == 1, "More than result was returned");
        final T ret = all.get(0);
        return Optional.of(ret);
    }

    public long size() {
        return count();
    }
}
