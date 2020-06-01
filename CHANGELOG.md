# EAPLI Framework Changelog

## OPEN-SNAPSHOT

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
