/*
 * Copyright (c) 2013-2020 the original author or authors.
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
/**
 * Provides a generic base class for implementing repositories using JPA. this
 * class is compatible with the Repository interface defined in
 * {@link eapli.framework.domain.repositories.DomainRepository DomainRepository}
 *
 * <p>
 * <img src="repositories-overview.svg">
 * </p>
 *
 * @author Paulo Gandra Sousa
 *
 *         <!--
 * @startuml repositories-overview.svg
 *           skinparam classAttributeIconSize 0
 *
 *           interface Repository<T, K> {
 *           void delete(T entity)
 *           void deleteById(K entityId)
 *           T save(T entity)
 *           Iterable<T> findAll();
 *           Optional<T> findById(K id);
 *           long count();
 *           }
 *
 *           class JpaAutoTxRepository<T, K, I> {
 *
 *           # final JpaBaseRepository<T, K> repo
 *           - final TransactionalContext autoTx
 *
 *           + TransactionalContext buildTransactionalContext(String persistenceUnitName)
 *           + TransactionalContext context()
 *           + boolean isInTransaction()
 *           + void delete(T entity)
 *           + T save(T entity)
 *           + Iterable<T> findAll()
 *           + Optional<T> findById(K id)
 *           + Optional<T> ofIdentity(I)
 *           + long count()
 *           + Iterator<T> iterator()
 *           # List<T> match(String where)
 *           # Optional<T> matchOne(String where)
 *           }
 *
 *           interface IterableRepository<T, K> {
 *           Iterator<T> iterator(int pagesize);
 *           T first();
 *           Iterable<T> first(int n);
 *           }
 *
 *           class JpaBaseRepository<T, K, I> {
 *           # Class<T> entityClass
 *           - EntityManagerFactory emFactory
 *           - EntityManager entityManager
 *
 *           # EntityManagerFactory entityManagerFactory()
 *           # EntityManager entityManager()
 *           + T create(T entity)
 *           + Optional<T> read(K id)
 *           + Optional<T> findById(K id)
 *           + Optional<T> ofIdentity(I)
 *           + T update(T entity)
 *           + void delete(T entity)
 *           + void deleteById(K entityId)
 *           + long count()
 *           + boolean contains(K key)
 *           + boolean add(T entity)
 *           + T save(T entity)
 *           # TypedQuery<T> queryAll()
 *           # TypedQuery<T> queryAll(String where, Map<String, Object> params)
 *           + List<T> first(int n)
 *           }
 *
 *           class JpaWithTransactionalContextRepository<T, K> {
 *           # EntityManagerFactory entityManagerFactory()
 *           # EntityManager entityManager()
 *           # TransactionalContext context()
 *           }
 *
 *           class JpaTransactionalRepository<T, K> {
 *           + void delete(final T entity)
 *           + void delete(final K entityId)
 *           + T create(final T entity)
 *           + T save(T entity)
 *           }
 *
 *           interface TransactionalContext {
 *           void beginTransaction()
 *           void commit()
 *           void rollback()
 *           void close()
 *           }
 *
 *           class JpaTransactionalContext {
 *           - final String persistenceUnitName
 *           - static volatile EntityManagerFactory singletonEMF
 *           EntityManager entityManager()
 *           + void beginTransaction()
 *           + void commit()
 *           + void rollback()
 *           + void close()
 *           }
 *
 *           Repository <|.. JpaAutoTxRepository
 *           Repository <|.. IterableRepository
 *           IterableRepository <|.. JpaBaseRepository
 *           JpaBaseRepository <|-- JpaWithTransactionalContextRepository
 *           JpaWithTransactionalContextRepository <|-- JpaTransactionalRepository
 *
 *           TransactionalContext <-- "autoTx" JpaAutoTxRepository
 *           TransactionalContext <.. JpaWithTransactionalContextRepository
 *           JpaBaseRepository <-- "repo" JpaAutoTxRepository
 *
 *           class JpaTransactionalContext implements TransactionalContext
 *
 *           class IntegrityViolationException
 *           class ConcurrencyException
 *           Repository ..> IntegrityViolationException : might throw
 *           Repository ..> ConcurrencyException : might throw
 *
 * @enduml
 *         -->
 */
package eapli.framework.infrastructure.repositories.impl.jpa;