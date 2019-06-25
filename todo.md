# TO DO

Ideas about improvements

- use annotations, e.g., Utilitarian

  - create a processor for @Utility that will force the class to be final and has no public constructors and will check that all methods are static
  - create a processor for @Singleton ? force the class to be final and has no public constructors
  - create annotation and processor @HashCoded to build the hashcode? see also project Loombok
  - [annotation processing 101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)
  - [Baeldung](https://www.baeldung.com/java-annotation-processing-builder)
  - [jintin](https://medium.com/@jintin/annotation-processing-in-java-3621cb05343a)

- Java 8 time

  - [toughts on java](https://thoughts-on-java.org/persist-localdate-localdatetime-jpa/)
  - [baeldung](https://www.baeldung.com/spring-data-java-8)

- POST the build result to the download page in bitbucket

  - [deploying to bitbucket](https://confluence.atlassian.com/bitbucket/deploy-build-artifacts-to-bitbucket-downloads-872124574.html)
  - [bitbucket api](https://developer.atlassian.com/bitbucket/api/2/reference/resource/repositories/%7Busername%7D/%7Brepo_slug%7D/downloads)

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
