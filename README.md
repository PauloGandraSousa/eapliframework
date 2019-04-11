# README #


## What is this repository for? ##

*Quick summary*

Sample Application Framework for teaching Domain Driven Design and JPA Persistence in the course unit "Engenharia de Aplica��es" of the course "Licenciatura em Engenharia Inform�tica" of the [Instituto Superior de Engenharia do Porto](http://www.isep.ipp.pt).

Feel free to fork and pull-request.

Version: 7.0.0

## How do I get set up? ##

*Summary of set up*

Start by reading the technical description available in the [documentation folder](/documentation).

Most top level packages have UML diagrams to provide more context of classes in that package. diagrams were created using [PlantUML](http://plantuml.com/).  

To update images from the PantUML files use the following instruction:

	java -jar plantuml.jar -tpng *.puml

Check the sample configuration provided in the [documentation folder](/documentation) on how to use this framework inside your projects.

For a sample project using this framework, go to https://bitbucket.org/pag_isep/ecafeteria-spring

*Configuration*

*Dependencies*

All compile, test and runtime dependencies are managed thru maven. Main dependencies include:

- Java 1.8+
- Apache Commons Logging
- JPA
- Spring Framework
- Spring Data JPA

*Database configuration*

*How to run tests*

*Deployment instructions*

use maven on the top level project to compile the three components:

- core
- infrastructure authz
- infrastructure pub/sub

### Contribution guidelines ###

*Writing tests*

*Code review*

*Other guidelines*

## Who do I talk to? ##

Paulo Gandra de Sousa [pag@isep.ipp.pt](emailto:pag@isep.ipp.pt) / [pagsousa@gmail.com](emailto:pagsousa@gmail.com)