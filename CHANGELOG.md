# EAPLI Framework Changelog

## 13.0.0

### What's new

- `FormattedString` (and its specializations `PrettyXmlString`, `PrettyJsonString`, `FormattedMoney`)

- `Money` class implements `FormattedString`

- `Money.amountAsDouble`

### Changes

- deprecated `Stringformatter`; you should use the OO-way `FormattedString` classes

- removed `NumberFormatter` class

- removed `Collections.contains` as it was duplicated from `Arrays.contains`

- moved `StringMixin` to `eapli.framework.strings` package

- moved `Strings` to `eapli.framework.strings.util` package

- `Money.amountAsDecimal` renamed to `Money.amount`. This method name was already in use returning a `double`, now it returns a `BigDecimal`. For the `double` value use `amountAsDouble`

- removed `Money.formatted`


## 12.0.1

- fix transaction handling

## 12.0.0

### What's new

- new overload `Collections.sizeOf(Collection)`

- "null" object `InMemoryTransactionalContext`

### Changes

- reverse the order of parametrized types in `InMemoryDomainRepository` to fix in memory repository: the data holder map was keyed by the KEY type and not by the TYPE type

- property `Description.theDescription` renamed to `value` - check if you used the old name in JPQL queries and in `@AttributeOverride` annotations

- property `Username.theUsername` renamed to `value` - check if you used the old name in JPQL queries and in `@AttributeOverride` annotations


## 11.0.x

### What's new:

- `Strategy` annotation 

- `TemplateMethod` annotation

- `Immutable` annotation


### Changes

- method `Calendars#calendarFromDate()` renamed to `fromDate`

- `ReportingRepository` is now an annotation and not a marker interface

- `DTO` is now an annotation and not a marker interface

- class `ExitWithMessageAction` 
    - requires an argument in the constructor with the message to show

- class `AbstractListUI<T>` 
    - requires a new overide `emptyMessage()` with the message to show when there are no elements
    - there is no overide of the `headline()` method 

- class `ListUI<T>`
    - requires two additional parameters in the constructor: `headline` and `emptyMessage`

- package renaming to better follow vertical slicing
    - `eapli.framework.domain.model.general` -> `eapli.framework.general.domain.model`
    - `eapli.framework.domain.model.math` -> `eapli.framework.math.domain.model`
    - `eapli.framework.domain.model.time` -> `eapli.framework.time.domain.model`
    - `eapli.framework.domain.model.domains` -> `eapli.framework.domains.domain.model`
    - `eapli.framework.domain.model.identities.*` -> `eapli.framework.identities.*`
    - `eapli.framework.domain.activerecord` -> `eapli.framework.activerecord`

- class `Calendars` moved to `eapli.framework.time.util`

- class `NumberGenerator` moved to `eapli.framework.math.util`

- class `Math` moved to `eapli.framework.math.util`


## 9.x.x --> 10.0.x

- interface `Controller` is deleted

- new `UseCaseController` annotation

- upgrade spring boot to 2.2.6

- upgrade hibernate 5.4.14 to avoid problem with H2 drop-and-create

- fix `SystemUserBuilder` NPE


