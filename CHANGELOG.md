# EAPLI Framework Changelog

## 9.x.x --> 10.0.x

`Controller` is renamed to `UseCaseController`

upgrade spring boot to 2.2.6

upgrade hibernate 5.4.14 to avoid problem with H2 drop-and-create

fix `SystemUserBuilder` NPE


## 10.0.x --> 11.0.0

### What's new:

`Strategy` marker interface 

`TemplateMethod` annotation

### Changes

class `ExitWithMessageAction` 

- requires an argument in the constructor with the message to show

class `AbstractListUI<T>` 

- requires a new overide `emptyMessage()` with the message to show when there are no elements
- there is no overide of the `headline()` method 

class `ListUI<T>`

- requires two additional parameters in the constructor: `headline` and `emptyMessage`