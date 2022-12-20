# EAPLI Framework

## What is this repository for?

Application Framework for teaching Domain Driven Design and JPA Persistence in 
the course unit "Engenharia de Aplica��es" (Software Application Engineering) of 
the course "Licenciatura em Engenharia Inform�tica" (Informatics Engineering) of 
the [Instituto Superior de Engenharia do Porto](http://www.isep.ipp.pt).

Feel free to fork and pull-request.

Have a look at the companion applications eCafeteria for examples on how to use the framework.

- [eCafeteria Spring](https://bitbucket.org/pag_isep/ecafeteria-spring) - spring boot based command line applications and REST services
- [eCafeteria Base](https://bitbucket.org/pag_isep/ecafeteria-base) - a "traditional" command line application without dependency injection

## Who do I talk to?

Paulo Gandra de Sousa [pag@isep.ipp.pt](emailto:pag@isep.ipp.pt) / [pagsousa@gmail.com](emailto:pagsousa@gmail.com)

## License and copyright

Copyright (c) 2013-2023 the original author or authors.

MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Features

There are three main components to the framework:

- **core**: the core classes
- **authz**: simple authentication and authorization
- **pub/sub**: simple event dispatcher

The javadocs are browsable online:

- [Core](https://pagsousa.bitbucket.io/13.1/eapli.framework.core/)
- [Authz](https://pagsousa.bitbucket.io/13.0/eapli.framework.authz/)
- [Pub/Sub](https://pagsousa.bitbucket.io/13.0/eapli.framework.pubsub/)

### Core library

This library contains the core classes, namely for DDD and JPA repositories.

### Authz library

This library provides for simple Domain objects and Services to handle authentication 
and authorization. Note that this library is mainly for demonstration spurposes and a 
full scale autentication and anthorization framework such as Spring Security should be 
used for production code.

### Pub/sub library

This is a very simple event dispatcher for demonstration purposes of DDD Domain Events.

## How do I get set up?

### Summary of set up

Start by reading the technical description available in the [documentation folder](/documentation).

Most top level packages have UML diagrams to provide more context of classes 
in that package. Diagrams were created using [PlantUML](http://plantuml.com/) with the UMLDocLet and the PlantUML Taglet.


### Configuration

t.b.d.

### Dependencies

All compile, test and runtime dependencies are managed thru maven. Main 
dependencies include:

- JDK 11+
- Apache Commons Logging
- JPA
- Spring Framework
- Spring Data JPA

### Database configuration

t.b.d.

### How to run tests

Building with maven will automatically run all unit tests in the projects.

### Deployment instructions

Use maven on the top level project to compile the three components:

- core
- infrastructure authz
- infrastructure pub/sub

### Contribution guidelines

Feel free to fork the repository, commit, push and create pull-requests. Please 
follow the same approach as the existing code, add the necessary and 
meaningful unit tests.

### Writing tests

Unit tests should focus on the domain invariants.

### Code review

t.b.d.

### Other guidelines

t.b.d.

## References and bibliography

### Principles

- [Information hiding](https://stevemcconnell.com/articles/missing-in-action-information-hiding/)
- [Tell, don't ask](https://martinfowler.com/bliki/TellDontAsk.html)
- [SOLID](http://butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod)
- [avoid getters and setters](https://www.javaworld.com/article/2073723/why-getter-and-setter-methods-are-evil.html) (which violate "information expert" and "tell, don't ask"), a.k.a. don't build an [anemic domain model](https://martinfowler.com/bliki/AnemicDomainModel.html)
- [Favour composition over inheritance](https://www.thoughtworks.com/insights/blog/composition-vs-inheritance-how-choose), or [why extends is evil](https://www.javaworld.com/article/2073649/why-extends-is-evil.html)

Also have a look at [Design principles and patterns](http://www.cvc.uab.es/shared/teach/a21291/temes/object_oriented_design/materials_adicionals/principles_and_patterns.pdf) and look out for [code smells](https://blog.codinghorror.com/code-smells/)

### DDD

Some base pointers:

- [DDD reference](http://domainlanguage.com/ddd/reference/)
- [effective aggregate design](http://www.informit.com/articles/printerfriendly/2020371), also as a [couple of articles](http://dddcommunity.org/library/vernon_2011/)

But keep in mind that DDD is more about _strategic design_ than _tactical patterns_...

- [Eric Evan's on strategic design](https://www.infoq.com/presentations/strategic-design-evans/)
- [Discovering the domain architecture](https://www.microsoftpressstore.com/articles/article.aspx?p=2248811&seqNum=3)
- [How to define the bounded context](https://codeburst.io/ddd-strategic-patterns-how-to-define-bounded-contexts-2dc70927976e)

And a couple more extended ones:

- [Mirko Sertico's example](https://www.mirkosertic.de/blog/2013/04/domain-driven-design-example/)
- [Archfirst](https://archfirst.org/domain-driven-design/)
- [Zan Kavtaskin](http://www.zankavtaskin.com/2014/12/applied-domain-driven-design-ddd-part-0.html)

### Design patterns

Besides the already mentioned [Design principles and patterns](http://www.cvc.uab.es/shared/teach/a21291/temes/object_oriented_design/materials_adicionals/principles_and_patterns.pdf), have a look at a brief catalogue of GoF patterns by [DZone](https://dzone.com/refcardz/design-patterns?chapter=1).

A couple more:

- [Layers](http://posa1.blogspot.com/2008/05/layered-architecture-pattern.html)
- [Use case controller](https://moodle.isep.ipp.pt/pluginfile.php/251066/mod_resource/content/1/UseCaseCtrl-EuroPLoP2001.pdf)
- [Factory](https://www.oodesign.com/factory-pattern.html)
- [Strategy](https://www.oodesign.com/strategy-pattern.html)

### Other useful readings

T.B.D.
