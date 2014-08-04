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

        project.repositories {
            // XXX: temporary..
            mavenLocal()
            // maven central is required to retrieve plugin dependencies.
            mavenCentral()
        }

        // add custom configurations.
        project.configurations {
            // the bundle configuration is a non-transitive dependency set.
            bundle
            bundle.transitive = false

            // the provided configuration is a dependency set that is not included in packaging.
            provided
            provided.transitive = true
        }

        // provided dependencies should be included in the compile classpath.
        project.sourceSets.main.compileClasspath += project.configurations.provided

        // add default project dependencies.
        project.dependencies {
            // require inclusion of the figurate bootstrap dependency.
            runtime 'org.figurate:bootstrap:master-SNAPSHOT'
            // require inclusion of (an) OSGi runtime (XXX: in future support configurable runtime)
            runtime 'org.apache.felix:org.apache.felix.framework:4.0.2'
            // require inclusion of bootstrap dependencies (XXX: this can probably be removed once bootstrap is properly published)
//            runtime project.dependencies.localGroovy()
            runtime 'org.codehaus.groovy:groovy-all:2.3.0-rc-1',
                    'org.slf4j:slf4j-api:1.7.5'

            // used for genscr task
//            provided 'org.apache.felix:org.apache.felix.scr.ant:1.9.0'
        }

        // copy bundles required for application install.
        project.task('copyBundles', type: Copy) {
            from "$project.buildDir/libs"
            from project.configurations.bundle

            into "$project.buildDir/bundles"
        }

        // create default launcher configuration task.
        project.task('launcherConfig', type: LauncherConfigTask)
        // create default logger configuration task.
        project.task('loggerConfig', type: LoggerConfigTask)
        // create default configuration task.
        project.task('configurationConfig', type: ConfigurationConfigTask)

        /*
        project.task('genscr', dependsOn: project.tasks.compileJava) {
//            println configurations.compile.asPath
            ant.taskdef(resource: 'scrtask.properties', classpath: project.sourceSets.main.compileClasspath.asPath )
            ant.scr(srcdir: 'build/classes/main', destdir: 'build/classes/main', scanClasses: true, classpath: project.sourceSets.main.compileClasspath.asPath)
        }
        */

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
        // include configuration config in application distribution.
        project.applicationDistribution.from(project.tasks.configurationConfig) {
            into ConfigurationConfigTask.DEFAULT_CONFIG_DIR
        }

        // application run depends on default custom tasks.
        project.tasks.with {
            copyBundles.mustRunAfter jar
            run.dependsOn copyBundles, launcherConfig, loggerConfig, configurationConfig
//            jar.dependsOn genscr
        }

        // configure the default main class from the bootstrap dependency.
        project.mainClassName = 'org.figurate.FrameworkLauncher'
        project.tasks.run.with {
            // provide default arguments for the application run task.
            args "$LauncherConfigTask.DEFAULT_CONFIG_DIR/$project.tasks.launcherConfig.configFilename"
            // set the working directory for the application run task.
            workingDir 'build'
        }
        // set the default JVM arguments for the application.
        project.applicationDefaultJvmArgs = [
                "-Xmx512m",
                "-Dlogback.configurationFile=$LauncherConfigTask.DEFAULT_CONFIG_DIR/$project.tasks.loggerConfig.configFilename",
                "-DconfigurationAdmin.configurationFile=$ConfigurationConfigTask.DEFAULT_CONFIG_DIR/$project.tasks.configurationConfig.configFilename"
        ]
    }
}
