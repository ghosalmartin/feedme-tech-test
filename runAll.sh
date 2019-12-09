#!/bin/sh

./gradlew clean test bootJar

docker-compose up --scale consumer=5