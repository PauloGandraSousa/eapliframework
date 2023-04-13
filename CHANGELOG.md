# EAPLI Framework Changelog

## v22.0.0

### 22.0.0 [CORE]

#### What's new

- `ValueList` and `ValueSet` interfaces
- `HashValueSet` class
- `CashFlow` class
- `Functions.ffx` method
- `Math.add1` method
- `Math.twice` method

#### What's changed

- `Money` class moved to `eapli.framework.money.domain.model`
- `Arrays`, `ArrayPredicates` and `Collections` classes moved to `eapli.framework.collections.util`

#### Fixed

- `NumberPredicates.isPrime` handles the special case for number 2 correctly

## v21.2.0

### 21.2.0 [CORE]

#### What's new

- `Try`class
- `Check`class
- `CheckIf` utility class
- `Designation.tryValueOf` method
- `Description.tryValueOf` method
- `StringPredicates.isNonEmpty` method
- `EmailAddress.tryValueOf` method

## v21.1.3

### 21.1.1 [CORE]

#### Fixed

- workaround for use of SpringBoot 2.7.9 native queries

## v21.1.1

### 21.1.1 [CORE]

#### Fixed

- handling of `OptimisticLockException` in `JpaTransactionalRepository`

## v21.1.0

### 21.1.0 [CORE]

#### What's new

- `Temperature.parse(String)` method
- `TemperatureUnit.zero()` method
- `Percentage.increase(double)` method
- `Percentage.decrease(double)` method
- `Percentage.signum()` method
- `Percentage.isNegative()` method
- `Percentage.iPositive()` method
- `Percentage.isZero()` method

### 19.1.0 [AUTHZ]

#### What's new

- `Authenticator` interface


## v21.0.0

### 21.0.0 [CORE]

#### What's new

- `RandomString` factory `of` method
- `PrettyJsonString` factory `of` method
- `PrettyXmlString` factory `of` method
- `Percentage` class
- `FrequencyTable.frequencyOfAsPercentage` method
- `FrequencyTable.entrySet` method
- `Temperature` class

#### Changed

- removed `RandomString` constructor. use factory `of` method
- removed `PrettyJsonString(String)` constructor. use factory `of` method 
- removed `PrettyJsonString.fromString` method. use factory `of` method
- removed `PrettyJsonString.fromObject` method. use factory `of` method
- removed `PrettyXmlString.fromString` method. use factory `of` method
- removed `PrettyXmlString.fromObject` method. use factory `of` method
- `Money` class moved to package `eapli.framework.quantities.domain.model`
- `FrequencyTable.entries` method returns a Stream instead of Set
- renamed `FrequencyTable.sortedEntriesByFrequency` method to `FrequencyTable.entriesSortedByFrequency` 
- renamed `FrequencyTable.sortedEntriesByToken` method to `FrequencyTable.entriesSortedByToken`
- fixed descending sorting order of `FrequencyTable.sortedEntriesByFrequency` 


## v20.0.0-RELEASE

### 20.0.0 [CORE]

#### what's new

- `RandomString`
- `Files.getExtension`

#### Changed

- removed methods `randomString` from class `Strings`

### 19.0.0 [AUTHZ]

#### What's new

- n.a.

#### Changed

- marked as final classes
-- `Name`
-- `NilPasswordPolicy`
-- `Password`
-- `PlainTextEncoder`
-- `RandomRawPassword`
-- `Username`
-- `UserSession`

### 20.0.0 [PUBSUB]

#### What's new

- n.a.

#### Changed

- marked as final classes
-- `SimplePersistentPubSub`
-- `EventRecord`
-- `EventConsumption`


## 18.0.1 [AUTHZ]

### What's new

- N.A.

### Changed

- Each repository implementation is in its own package
- removed `final` specifier of `AuthorizationService`


## 19.0.0 [ PUB/SUB ]

### new

- N.A.

### Changed

- renamed package structure from `eapli.infrastructure.eventpubsub` to `eapli.infrastructure.pubsub`


## 19.0.0 [CORE]

### What's new

- Parametrizable `Command` pattern

### Changed

- `Calendars` and `CalendarPredicates` where split into `CurrentTimeCalendars` and `CurrentTimeCalendarPredicates` to distinguish between pure deterministic utility functions and the ones that are not deterministic since they depend on the system clock


## 18.0.0 [ PUB/SUB ]

### What's new

- `SimplePersistentPubSub` and `PubSubRegistry` for scenarios without spring injection

### Changed

- use of Spring Scheduled for persistent event pooling

- renamed `SimplePersistentEventPubSub` top `SimplePersistentPublisherDispatcher`


## 17.0.0 [ PUB/SUB ]

### Changed

- added JPA repositories to SimplePersistent implementation

- restructured packages

	- `eapli.framework.infrastructure.eventpubsub.impl.inprocess` -> `eapli.framework.infrastructure.eventpubsub.impl.inprocess.service` 
	
	- `eapli.framework.infrastructure.eventpubsub.impl.simplepersistent` -> `eapli.framework.infrastructure.eventpubsub.impl.simplepersistent.service` 
	
	
## 18.2.0 [ CORE ]

### What's new

- `Money.isOfSameCurrency`

### Changed

- Fix missing `PersistenceContext` annotation on `JpaAbstractRepository` for scenarios with Spring

- Fix missing `Transactional` annotation for scenarios with Spring and JPA repositories (not Spring Data)


## 18.1.1 [ CORE ]

### What's new

- n.a.

### Changed

- removed `Macro.record`; use `with()`

- renamed `DateInterval` internal member names to `dateStart` and `dateEnd` due to conflicts with some RDBMS


## 18.0.0 [ CORE ]

### What's new

- n.a.

### Changed

- removed `Macro.record`; use `with()`


## 17.0.0 [ CORE ]

### What's new

- n.a.

### Changed

- removed deprecated `JpaAutoTxRepository.isInTransaction`. Use `isInTransactionalContext`.

- `JpaAutoTxRepository.context` now returns an `Optional<TransactionalContext>`.

- removed `Macro.record`; use `with()`


## 17.1.0 [ AUTHZ ]

### What's new

- `Name` now supports dot (`.`), dash (`-`) and apostrophe (`'`)

### Changed

- removed deprecated `RoleAssignment.getUnassignedOn`. Use `unassignedOn`.


## 16.6.0 [ CORE ]

### What's new

- `JpaAutoTxRepository.entityManager`

### Changed

- n.a.

## 16.5.0 [ CORE ]

### What's new

- `JpaAbstractRepository.createNativeQuery`

- `JpaAutoTxRepository.createNativeQuery`

### Changed

- n.a.


## 17.0.0 [ AUTHZ ]

### What's new

- n.a.

### Changed

- `Role` was mapped as a java object. it is now mapped as a VARCHAR column. 


## 16.4.0

### What's new

- `EmailAddress` implements `Comparable`

- `Money.valueOf(double, String)`

- `RomanNumeral.valueOf(int)`

### Changed

- `RandomRawPassword` ensures at least a capital letter, a number and a symbol are present.


## 16.3.0

### What's new

- `RoleAssignment.unassignedOn`

- `TransactionalContext.isActive`

- `DomainRepository.lock`

- `JpaAutoTxRepository.isPartOfTransactionalContext`

- `SpringDataLockableRepository`

- `LockableRepository`

- `LockableDomainRepository`

### Changed

- deprecated 
	- `RoleAssignment.getUnassignedOn`; use `RoleAssignment.unassignedOn` instead 
	- `JpaAutoTxRepository.isInTransaction`; use `JpaAutoTxRepository.isPartOfTransactionalContext` instead


## 16.2.0

### What's new

- `CsvRecord.valueOf` method allows to pass a bit mask for determining if a field is quoted or not

### Changed

- n.a. 

## 16.1.0

### What's new

- `CsvLineMarshaler.unquote` method

### Changed

- n.a. 


## 16.0.0

### What's new

- `CsvRecord` class to parse/build CSV strings

### Changed

- `XmlDateAdapter` use RFC 1123 for representing dates in XML
- removed deprecated methods from `CsvLineMarshaler` and utility methods. 


## 15.0.0

### What's new

- n.a

### Changed

- Class `PrettyXmlString` is now a pure utility class
- Removed class:
	- `eapli.framework.util.Console`. Use `eapli.framework.io.util.Console` instead
	- `eapli.framework.util.Files`. Use `eapli.framework.io.util.Files` instead
	- `eapli.framework.util.StringFormatter`. See `eapli.framework.strings.FormattedString`
- Removed deprecated methods:
    - `RomanNumeral.toString(int)`
    - `NumberGenerator.heads`
    - `PrettyXmlString.PrettyXmlString(String)`
    - `PrettyXmlString.PrettyXmlString(String, int)`
    - `PrettyXmlString.toString()`
    - `Arrays.contains(T[], T)`
- fix handling of null values for Money constructor when reconstructing from the DB and there are no records in the table


## 14.0.0

### What's new

- Class `JpaAutoTxRepository` now implements `IterableRepository`

### Changed

- Use Java 11


## 13.5.0

### What's new

- In class `Strings`:
    - `matchResults`
    - `split`
- New class `CsvLineMarshaler`

### Changed

- n.a.


## 13.4.0

### What's new

- new annotations:
    - `ApplicationService`
    - `DomainService`
    - `InfrastructureService`
- new methods:
    - `Math.fibonacci` and `Math.fibonacciSeries`
    - `Math.heads`
    - `NumberGenerator.nBytes`
    - `NumberGenerator.anHex`
    - `Strings.asHexadecimal`
    - `Strings.shuffle`
- new classes:
    - `ArrayPredicates`
    - `RandomRawPassword`
    - `FrequencyTable`

### Changed

- In-process Pub/Sub uses a separate thread to publish the events and separate threads to call each subscriber

- Fix 
    - `openInOSViewer` for Mac
    - make sure the active transaction is rollbacked on closing the transactional context

- deprecated:
    - `NumberGenerator.heads`; you should use `Math.heads` instead
    - `Arrays.contains`


## 13.3.1

### What's new

- n.a.

### Changed

- Deprecated class method `RomanNumeral#toString`

- Fixes thread-safety in:
    - `in-memory pub/sub`
    - `ActionHistoryKeeper`
    - `Macro`
    - `RestorableHistoryKeeper`
    - `GeneralDTO`
    - `GeneralDTORepresentationalBuilder`
    - `InMemoryRepository`

- `InMemoryRepository` is now abstract (this could potentially be a breaking change but we are ignoring it since the published interface is to derive this class and not to use it directly)

- Concurrent unit tests for InMemoryRepository using [Concurrent JUnit](https://github.com/ThomasKrieger/concurrent-junit)


## 13.3.0

### What's new

- `FormattingOrTransformationException`

- Coupling back the version number of all components to make it easier to deploy


## 13.2.0

### What's new

- Null (No-Op) `InMemoryTransactionalContext`

- New `eapli.framework.io` package
    - `StreamGobbler`
    - `util.Files`
    - `util.Console`

### Changed

- Deprecated:
    - `eapli.framework.util.Files`
    - `eapli.framework.util.Console`


## 13.1.0

### What's new
 
- `PrettyJsonString` has a new constructor to map java objects to JSON

- Decoupling the versioning of Core from AUTHZ and PUB/SUB

- Improve JSON support in `Money`, `Designation` and `Description`

- Improve XML support in `Money`, `Designation` and `Description`

- `PrettyXmlString` has two static factory methods `fromString` and `fromObject`. note that the constructor of this is class are deprecated and should not be used.

### Changes

- `Repository#deleteOfIdentity` is now loading the object in order to remove it with all cascading in place.

- Fix transaction handling in `JpaAutoTxRepository.update`

- Upgrade SpringBoot to 2.3.0


## 13.0.0

### What's new

- `FormattedString` (and its specializations `PrettyXmlString`, `PrettyJsonString`, `FormattedMoney`)

- `Money` class implements `FormattedString`

- `Money.amountAsDouble`

- `Strings.leftPadded` and `Strings.rightPadded`

### Changes

- Deprecated `Stringformatter`; you should use the OO-way `FormattedString` classes

- Removed:
    - `NumberFormatter` class
	 - `Collections.contains` as it was duplicated from `Arrays.contains`
    - `Money.formatted`

- Moved 
    - `StringMixin` to `eapli.framework.strings` package
    - `Strings` to `eapli.framework.strings.util` package
    - `StringPredicates` to `eapli.framework.strings.util`
    - `CalendarPredicates` to `eapli.framework.time.util`
    - `NumberPredicates` to `eapli.framework.math.util`

- `Money.amountAsDecimal` renamed to `Money.amount`. This method name was already in use returning a `double`, now it returns a `BigDecimal`. For the `double` value use `amountAsDouble`


## 12.0.1

- Fix transaction handling in `deleteOfIdentity`


## 12.0.0

### What's new

- New overload `Collections.sizeOf(Collection)`

- "null" object `InMemoryTransactionalContext`

### Changes

- Reverse the order of parametrized types in `InMemoryDomainRepository` to fix in memory repository: the data holder map was keyed by the KEY type and not by the TYPE type

- Property `Description.theDescription` renamed to `value` - check if you used the old name in JPQL queries and in `@AttributeOverride` annotations

- Property `Username.theUsername` renamed to `value` - check if you used the old name in JPQL queries and in `@AttributeOverride` annotations


## 11.0.x

### What's new:

- `Strategy` annotation 

- `TemplateMethod` annotation

- `Immutable` annotation


### Changes

- Method `Calendars#calendarFromDate()` renamed to `fromDate`

- `ReportingRepository` is now an annotation and not a marker interface

- `DTO` is now an annotation and not a marker interface

- Class `ExitWithMessageAction` 
    - requires an argument in the constructor with the message to show

- Class `AbstractListUI<T>` 
    - requires a new overide `emptyMessage()` with the message to show when there are no elements
    - there is no overide of the `headline()` method 

- Class `ListUI<T>`
    - requires two additional parameters in the constructor: `headline` and `emptyMessage`

- Package renaming to better follow vertical slicing
    - `eapli.framework.domain.model.general` -> `eapli.framework.general.domain.model`
    - `eapli.framework.domain.model.math` -> `eapli.framework.math.domain.model`
    - `eapli.framework.domain.model.time` -> `eapli.framework.time.domain.model`
    - `eapli.framework.domain.model.domains` -> `eapli.framework.domains.domain.model`
    - `eapli.framework.domain.model.identities.*` -> `eapli.framework.identities.*`
    - `eapli.framework.domain.activerecord` -> `eapli.framework.activerecord`

- moved:
    - class `Calendars` to `eapli.framework.time.util`
    - class `NumberGenerator` to `eapli.framework.math.util`
    - class `Math` to `eapli.framework.math.util`


## 9.x.x --> 10.0.x

- Interface `Controller` is deleted

- New `UseCaseController` annotation

- Upgrade spring boot to 2.2.6

- Upgrade hibernate 5.4.14 to avoid problem with H2 drop-and-create

- Fix `SystemUserBuilder` NPE
