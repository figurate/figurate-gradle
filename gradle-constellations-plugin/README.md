# Gradle Constellations Plugin

The purpose of this plugin is to simplify dependency management by providing
well-known dependency constellations and support for constructing new constellations.

## Transitive Dependencies

The promise of transitive dependencies was that you could include a third-party
dependency in your project and it will automatically include all the dependencies
required to support that library. Often this is promise is not realises, especially
with more complicated dependency trees and optional inclusions.

## Constellations

A dependency constellation provides an opinion about the true dependency tree
required to support third-party libraries. This is identical to how you might
specify the required dependencies in a project, but is provided as a portable
definition that may be used in multiple projects.
