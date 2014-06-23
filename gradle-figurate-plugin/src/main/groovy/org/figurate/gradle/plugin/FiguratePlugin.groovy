package org.figurate.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

/**
 * This plugin for Gradle supports building self-contained, OSGi-based applications.
 *
 * Created by fortuna on 8/05/14.
 */
class FiguratePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // require the application plugin to support creation of self-contained install packages.
        project.apply plugin: 'application'

        // maven central is required to retrieve plugin dependencies.
        project.repositories {
            mavenCentral()
        }

        // parse a list of constellation configurations that provide an alias for a group of bundles.
        def constellations = new GroovyShell(getClass().classLoader).evaluate(
                getClass().getResourceAsStream('/constellations.groovy').newReader())

        // create a (non-transitive) configuration for each constellation.
        constellations.keySet().each { key ->
            def configuration = project.configurations.create("constellation\$$key")
            configuration.transitive = false
            // add constellation bundles to configuration dependency set
            constellations[key].each {
                project.dependencies.add(configuration.name, it)
            }
        }

        // add custom configurations.
        project.configurations {
            // the bundle configuration is a non-transitive dependency set.
            bundle
            bundle.transitive = false

            // the provided configuration is a dependency set that is not included in packaging.
            provided
        }

        // provided dependencies should be included in the compile classpath.
        project.sourceSets.main.compileClasspath += project.configurations.provided

        // add default project dependencies.
        project.dependencies {
            // require inclusion of the figurate bootstrap dependency.
//            runtime 'org.figurate:figurate-bootstrap:1.0'
            runtime project.files('/Users/fortuna/Development/figurate-core/bootstrap/build/libs/bootstrap.jar')
            // require inclusion of (an) OSGi runtime (XXX: in future support configurable runtime)
            runtime 'org.apache.felix:org.apache.felix.framework:4.0.2'
            // require inclusion of Groovy (XXX: not sure this should be mandatory?)
//            runtime project.dependencies.localGroovy()
            runtime 'org.codehaus.groovy:groovy-all:2.3.0-rc-1',
                    'org.slf4j:slf4j-api:1.7.5'
        }

        // configure the default main class from the bootstrap dependency.
        project.mainClassName = 'org.figurate.FrameworkLauncher'
        project.tasks.run.with {
            // provide default arguments for the application run task.
            args "$LauncherConfigTask.DEFAULT_CONFIG_DIR/${project.name}$LauncherConfigTask.DEFAULT_CONFIG_EXT"
            // set the working directory for the application run task.
            workingDir 'build'
        }
        // set the default JVM arguments for the application.
        project.applicationDefaultJvmArgs = [
                "-Xmx512m",
                "-Dlogback.configurationFile=$LauncherConfigTask.DEFAULT_CONFIG_DIR/logback.groovy"
        ]

        // copy bundles required for application install.
        project.task('copyBundles', type: Copy) {
            from project.configurations.bundle
            into "${project.buildDir}/bundles"
        }

        // create default launcher configuration task.
        project.task('launcherConfig', type: LauncherConfigTask)
        // create default logger configuration task.
        project.task('loggerConfig', type: LoggerConfigTask)

        // include bundles in application distribution.
        project.applicationDistribution.from(project.tasks.copyBundles) {
            into "bundles"
        }
        // include launcher config in application distribution.
        project.applicationDistribution.from(project.tasks.launcherConfig) {
            into LauncherConfigTask.DEFAULT_CONFIG_DIR
        }
        // include logger config in application distribution.
        project.applicationDistribution.from(project.tasks.loggerConfig) {
            into LoggerConfigTask.DEFAULT_CONFIG_DIR
        }

        // application run depends on default custom tasks.
        project.tasks.with {
            run.dependsOn copyBundles, launcherConfig, loggerConfig
        }
    }
}
