# TO DO

Ideas about improvements

- validator.inRange(long, long, long)
- factories with tryBuild() which return Either<T, ConstraintViolations>
- use annotations, e.g., Utilitarian
    - create a processor for @Utility that will force the class to be final and has no public constructors and will check that all methods are static
    - create a processor for @Singleton ? force the class to be final and has no public constructors
    - create annotation and processor @HashCoded to build the hashcode? see also project Loombok
    - [annotation processing 101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)
    - [Baeldung](https://www.baeldung.com/java-annotation-processing-builder)
    - [jintin](https://medium.com/@jintin/annotation-processing-in-java-3621cb05343a)
- JSR-380 Validation
    - [Baeldung](https://www.baeldung.com/javax-validation)
    - [Baeldung on method constraints](https://www.baeldung.com/javax-validation-method-constraints)
    - [Baeldung on spring](https://www.baeldung.com/spring-boot-bean-validation)
- Design by Contract
    - [the checker framework](https://checkerframework.org/)
    - [contracts for java](https://github.com/nhatminhle/cofoja#usage)
- Java 8 time
    - [toughts on java](https://thoughts-on-java.org/persist-localdate-localdatetime-jpa/)
    - [baeldung](https://www.baeldung.com/spring-data-java-8)
- Functional
    - [memoization](https://dzone.com/articles/java-8-automatic-memoization)
    - discretedomain first?, rest/tail?
    - [Atalasian fugue](https://bitbucket.org/atlassian/fugue/src/master/)
    - Google [Guava](https://github.com/google/guava)
    - [vavr](https://www.vavr.io/)
    - Try Monad
        - https://dzone.com/articles/whats-wrong-java-8-part-iv 
        - https://dzone.com/articles/why-try-better-exception-handling-in-java-with-try
        - https://github.com/jasongoodwin/better-java-monads
    - Tuples
        - [Apache Commons Tuple](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/tuple/package-summary.html)
        - [javatuple](https://www.javatuples.org/) and [Baeldung on javatuples](https://www.baeldung.com/java-tuples)
