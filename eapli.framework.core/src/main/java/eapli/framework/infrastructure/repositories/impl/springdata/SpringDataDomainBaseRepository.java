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
package eapli.framework.infrastructure.repositories.impl.springdata;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import eapli.framework.domain.model.AggregateRoot;

/**
 * Base interface for spring Data repositories. Note that this interface expects
 * K to be the primary key type, which mat or may not be the business identity
 * of the type <T> being managed
 *
 * we decided to use Repository instead of CrudRepository to have findById
 * return an Optional.
 *
 * @param <K>
 *            the primary key of the entity in the database, e.g., Long
 * @param <I>
 *            the type of the business identity, e.g., SSN
 * @param <T>
 *            the managed entity type, e.g., Person
 *
 * @author Paulo Gandra Sousa
 *
 */
@NoRepositoryBean
public interface SpringDataDomainBaseRepository<K, I /* extends ValueObject */, T extends AggregateRoot<I>>
        extends Repository<T, K> {

    long count();

    <S extends T> S save(S entity);

    <S extends T> void delete(S entity);

    Iterable<T> findAll();

    /**
     * Each specific repository MUST implement this method and define the exact
     * JPQL query.
     *
     * @param id
     */
    @Query("DELETE FROM #{#entityName} e WHERE e.«entity-biz-id» = :id")
    @Modifying
    void deleteOfIdentity(@Param("id") I id);

    /**
     * Each specific repository MUST implement this method and define the exact
     * JPQL query
     *
     * @param id
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.«entity-biz-id» = :id")
    Optional<T> ofIdentity(@Param("id") I id);
}
