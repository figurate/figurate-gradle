#!/usr/bin/env bash
GRADLE_VERSION=$1

./gradlew wrapper --gradle-version=$GRADLE_VERSION --distribution-type=bin
