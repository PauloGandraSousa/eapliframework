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
package eapli.framework.domain.repositories;

import java.util.Optional;

import eapli.framework.domain.model.AggregateRoot;

/**
 * A repository is a domain-driven design pattern to abstract the details of
 * persisting domain objects. It exposes a pure domain based interface without
 * leaking details of the implementation of the actual persistence mechanism.
 *
 * <p>
 * Quoting
 * <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Eric
 * Evans</a>:
 * <blockquote>
 * For each type of aggregate that needs global access, create a service that can provide the
 * illusion of an in-memory collection of all objects of that aggregate’s root type. Set up access
 * through a well-known global interface. Provide methods to add and remove objects, which
 * will encapsulate the actual insertion or removal of data in the data store. Provide methods
 * that select objects based on criteria meaningful to domain experts. Return fully
 * instantiated objects or collections of objects whose attribute values meet the criteria,
 * thereby encapsulating the actual storage and query technology, or return proxies that give
 * the illusion of fully instantiated aggregates in a lazy way. Provide repositories only for
 * aggregate roots that actually need direct access. Keep application logic focused on the
 * model, delegating all object storage and access to the repositories.
 * </blockquote>
 *
 * <p>
 * Repository checklist:
 * <ul>
 * <li>There should be one repository per aggregate root.</li>
 * <li>Separate interface from implementation</li>
 * <li>Do not expose implementation details, e.g., database primary key</li>
 * <li>Filtering and sorting is done in the implementation (e.g., JPQL)</li>
 * </ul>
 * </p>
 * <p>
 * Repositories might have (besides the methods in this interface):
 * <ul>
 * <li>Specialized finders, e.g.,
 * <ul>
 * <li>ProductRepository.findByFamily</li>
 * <li>ExpenseRepository.findByMonth</li>
 * </ul>
 * </li>
 * <li>Aggregation functions, e.g.,
 * <ul>
 * <li>ProductRepository.getAverageRetailPrice</li>
 * <li>ExpenseRepository.getTotalExpenditure</li>
 * </ul>
 * </li>
 * </ul>
 * Note that these methods should only exist in order to support the application use cases. If there
 * are reporting needs
 * those should not be present in the Domain repository. A specialized {@link ReportingRepository}
 * should be defined for
 * such features or a separate reporting tool should be used.
 * </p>
 * <p>
 * If the aggregate is an inheritance hierarchy, the abstract root of the hierarchy should determine
 * the aggregate root,
 * not the specialized derived classes. Thus, the repository should be based on the abstract root
 * class and provide
 * specialized finders for the derived classes.
 * </p>
 * <p>
 * <img src="inheritance.svg">
 * </p>
 * <p>
 * If you are not using the DDD interfaces of the framework use
 * {@link eapli.framework.infrastructure.repositories.Repository} instead
 * </p>
 *
 * @param <I>
 *            the type of the business identity of the entity.
 * @param <T>
 *            the type of the entity we want to manage in the repository.
 *
 * @author Paulo Gandra Sousa
 *
 *         <!--
 * @startuml inheritance.svg
 *
 *           package domain{
 *
 *           abstract class Vehicle <<entity>><<root>>{
 *           -vehiclepk
 *           licensePlate()
 *           horsePower()
 *           }
 *
 *           class LicensePlate<<value object>>
 *           Vehicle -> LicensePlate
 *
 *           class Power<<value object>>
 *           Vehicle ->Power:horsePower
 *
 *           note left of Vehicle: <i>vehiclepk</i> is an implementation detail\n(primary key)
 *
 *           class Automobile <<entity>>{
 *           -numberOfDoors
 *           }
 *
 *           class MotorBike <<entity>> {
 *           -hasSideCar
 *           }
 *
 *           Vehicle <|-down- Automobile
 *           Vehicle <|-down- MotorBike
 *           }
 *
 *           package repositories{
 *
 *           interface VehicleRepository {
 *           +save()
 *           +findByLicensePlate()
 *           +findAllAutomobiles()
 *           +findAllMotorBikes()
 *           +findAutomobilesWith5Doors()
 *           +countOfMotorbikeswithSidecar()
 *           +totalHorsePower()
 *           }
 *
 *           note right of VehicleRepository
 *           The fleet
 *
 *           finder methods to support
 *           the application use cases
 *           end note
 *
 *           VehicleRepository ..> Vehicle
 *
 *           }
 *
 *
 *           package repositories.impl.jpa{
 *           class VehicleRepositoryJpaImpl{
 *           +save()
 *           +findAllAutomobiles()
 *           +findAllMotorBikes()
 *           +findAutomobilesWith5Doors()
 *           +countOfMotorbikeswithSidecar()
 *           +totalHorsePower()
 *           }
 *           VehicleRepositoryJpaImpl ..|> VehicleRepository
 *
 *           note right of VehicleRepositoryJpaImpl
 *           findAllAutomobiles: "SELECT e FROM Automobile e ORDER BY e.licensePlate"
 *           findAllMotorBikes: "SELECT e FROM MotorBike e ORDER BY e.licensePlate"
 *           findAutomobilesWith5Doors: "SELECT e from Automobile e WHERE e.numberOfDoors=5 ORDER BY
 *           e.licensePlate"
 *           countOfMotorbikeswithSidecar: "SELECT COUNT(e) FROM MotorBike e WHERE
 *           e.hasSideCar=true"
 *           totalHorsePower: "SELECT SUM(e.horsePower) FROM Vehicle e"
 *           end note
 *
 *           }
 * @enduml
 *         -->
 */
public interface DomainRepository<I extends Comparable<I>/* & ValueObject */, T extends AggregateRoot<I>> {

    /**
     * Saves an entity either by creating it or updating it in the persistence
     * store. Client code must assume the passed {@code entity} reference is no
     * longer valid and use the returned object instead. e.g.,
     *
     * <pre>
     * <code>
     * X myEntity = ...
     * ...
     * myEntity = repo.save(myEntity);
     * ...
     * </code>
     * </pre>
     *
     * @param entity
     * @return the object reference to the persisted object.
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     */
    <S extends T> S save(final S entity);

    /**
     * Gets all entities from the repository. If the repository is empty and
     * empty collection is returned.
     *
     * @return all entities from the repository.
     */
    Iterable<T> findAll();

    /**
     * Gets the entity with the specified identity.
     *
     * @param id
     * @return an Optional which value is the entity with the specified
     *         identity. An Empty Optional if there is no entity with such
     *         identity in the repository.
     */
    Optional<T> ofIdentity(final I id);

    /**
     * Checks for the existence of the entity identified by {@code id}
     *
     * @param id
     * @return true if the repository contains an entity with the specified
     *         identity.
     */
    default boolean containsOfIdentity(final I id) {
        return ofIdentity(id).isPresent();
    }

    /**
     * Checks for the existence of the entity in the repository.
     *
     * @param entity
     * @return true if the object reference is of an object managed by the
     *         repository
     */
    default boolean contains(final T entity) {
        return containsOfIdentity(entity.identity());
    }

    /**
     * removes the specified entity from the repository.
     *
     * @param entity
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void delete(final T entity);

    /**
     * Removes the entity with the specified identity from the repository.
     *
     * @param entityId
     * @throws ConcurrencyException
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    void deleteOfIdentity(final I entityId);

    /**
     * Returns the number of entities in the repository.
     *
     * @return the number of entities in the repository.
     */
    long count();

    // ALIAS

    /**
     * Returns the number of entities in the repository. An alias to
     * {@link #count()} to follow the {@code List} interface.
     *
     * @return the number of entities in the repository.
     */
    default long size() {
        return count();
    }

    /**
     * Removes the specified entity from the repository. An alias to
     * {@link #remove(AggregateRoot)} to follow the {@code List} interface.
     *
     * @param entity
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void remove(final T entity) {
        delete(entity);
    }

    /**
     * Removes the entity with the specified identity from the repository. An
     * alias to {@link #removeOfIdentity(Comparable)}
     *
     * @param entityId
     * @throws IntegrityViolationException
     * @throws UnsupportedOperationException
     *             if the delete operation makes no sense for this repository
     */
    default void removeOfIdentity(final I entityId) {
        deleteOfIdentity(entityId);
    }
}
