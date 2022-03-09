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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A repository that only has reporting (query) methods. Reporting is a different bounded
 * context than the rest of the application business logic and should be treated as that.
 * {@link DomainRepository} has finders and save methods to support the application use cases, while
 * a separate {@code ReportingRepository } should support the reporting needs of the application.
 * <p>
 * <img src="reporting.svg">
 * </p>
 *
 * @author Paulo Gandra Sousa
 *
 *         <!--
 * @startuml reporting.svg
 *
 *           package domain{
 *           note "the domain is not needed \nfor reporting" as N1
 *
 *           Vehicle <|-down- Automobile
 *           Vehicle <|-down- MotorBike
 *           }
 *
 *           package dto{
 *           class AutomobilesGroupedByNumberOfDoors <<DTO>> {
 *           +numberOfDoors
 *           +licensePlate
 *           . . .
 *           }
 *           class VehiclesGroupedByHorsePowerRange <<DTO>>{
 *           +horsePowerRange
 *           +licensePlate
 *           . . .
 *           }
 *           }
 *
 *           package repositories{
 *
 *           interface VehicleReportingRepository{
 *           +findAllAutomobilesGroupedByNumberOfDoors()
 *           +findAllVehiclesGroupedByHorsePowerRange()
 *           }
 *
 *           VehicleReportingRepository ..> AutomobilesGroupedByNumberOfDoors
 *           VehicleReportingRepository ..> VehiclesGroupedByHorsePowerRange
 *
 *           note left of VehicleReportingRepository
 *           finder methods to support
 *           the reporting features
 *
 *           return of these finders are DTOs
 *           end note
 *           }
 *
 *
 *           package repositories.impl.jpa{
 *           class VehicleReportingRepositoryJpaImpl
 *           VehicleReportingRepositoryJpaImpl ..|> VehicleReportingRepository
 *           }
 * @enduml
 *         -->
 *
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE })
public @interface ReportingRepository {
    // empty
}
