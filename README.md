# EAPLI Framework

Version: **8.0.0**

## What is this repository for?

Application Framework for teaching Domain Driven Design and JPA Persistence in the course unit "Engenharia de Aplicações" (Software Application Engeneering) of the course "Licenciatura em Engenharia Informática" (Informatics Engineering) of the [Instituto Superior de Engenharia do Porto](http://www.isep.ipp.pt).

Feel free to fork and pull-request.

Have a look at the companion applications eCafeteria for examples on how to use the framework.

- [eCafeteria Spring](https://bitbucket.org/pag_isep/ecafeteria-spring) - spring boot based command line applications and REST services
- [eCafeteria Base](https://bitbucket.org/pag_isep/ecafeteria-base) - a "traditional" command line application without dependency injection

## Who do I talk to?

Paulo Gandra de Sousa [pag@isep.ipp.pt](emailto:pag@isep.ipp.pt) / [pagsousa@gmail.com](emailto:pagsousa@gmail.com)

## License and copyright

Copyright (c) 2013-2019 the original author or authors.

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

- **core** (v8.0.0): the core classes
- **authz** (v8.0.0): simple authentication and authorization
- **pub/sub** (v1.0.0): simple event dispatcher

### Core library

This library contains the core classes, namely for DDD and JPA repositories.

### Authz library

This library provides for simple Domain objects and Services to handle authentication and authorization. Note that this library is mainly for demonstration spurposes and a full scale autentication and anthorization framework such as Spring Security should be used for production code.

### Pub/sub library

This is avery simple event dispatcher for demonstration purposes of DDD Domain Events.

## How do I get set up?

### Summary of set up

Start by reading the technical description available in the [documentation folder](/documentation).

Most top level packages have UML diagrams to provide more context of classes in that package. diagrams were created using [PlantUML](http://plantuml.com/).

To update images from the PantUML files use the following instruction:

```cmd
java -jar plantuml.jar -tpng *.puml
```

### Configuration

t.b.d.

### Dependencies

All compile, test and runtime dependencies are managed thru maven. Main dependencies include:

- Java 1.8+
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

Feel free to fork the repo, commit, push and create pull-requests. Please follow the same approach as the existing code, add the necessary and meaningfull unit tests.

### Writing tests

Unit tests should focus on the domain invariants.

### Code review

t.b.d.

### Other guidelines

t.b.d.
