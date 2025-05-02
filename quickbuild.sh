#!/usr/bin/env bash
mvn -B $1 verify dependency:copy-dependencies -D maven.javadoc.skip=true