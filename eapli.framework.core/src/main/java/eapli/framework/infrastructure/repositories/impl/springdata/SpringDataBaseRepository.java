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
package eapli.framework.infrastructure.repositories.impl.springdata;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * Base interface for Spring Data repositories. Note that this interface expects {@code <K>} to be
 * the primary key type, which may or may not be the business identity of the type {@code <T>} being
 * managed.
 *
 *
 * @param <T>
 *            the managed entity type
 * @param <K>
 *            the primary key of the entity in the database
 *
 * @see SpringDataDomainBaseRepository
 *
 * @author Paulo Gandra Sousa
 *
 */
@NoRepositoryBean
public interface SpringDataBaseRepository<T, K> extends CrudRepository<T, K> {

    /**
     * Acquires an exclusive write lock to a managed entity previously fetched from the persistence
     * storage.
     *
     * @param entity
     * @return
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e = :entity")
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    T lock(@Param("entity") T entity);

    /**
     * Loads and acquires an exclusive write lock to a managed entity.
     * <p>
     * Each specific repository MUST implement this method and define the exact JPQL query
     *
     * @param id
     * @return
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.«pk» = :id")
    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    T lockById(@Param("entity") K id);
}
