package org.figurate.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

/**
 * Created by fortuna on 8/05/14.
 */
class FiguratePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.apply plugin: 'application'

        project.repositories {
            mavenCentral()
        }

        def constellations = new GroovyShell(getClass().classLoader).evaluate(
                getClass().getResourceAsStream('/constellations.groovy').newReader())

        constellations.keySet().each { key ->
            def configuration = project.configurations.create("constellation\$$key")
            configuration.transitive = false

            constellations[key].each {
                project.dependencies.add(configuration.name, it)
            }
        }

        project.configurations {
            bundle
            bundle.transitive = false
        }

        project.dependencies {
//            runtime 'org.figurate:figurate-bootstrap:1.0'
            runtime project.files('/Users/fortuna/Development/figurate-core/bootstrap/build/libs/bootstrap.jar')
            runtime 'org.apache.felix:org.apache.felix.framework:4.0.2'
//            runtime project.dependencies.localGroovy()
            runtime 'org.codehaus.groovy:groovy-all:2.3.0-rc-1',
                    'org.slf4j:slf4j-api:1.7.5'
        }

        project.mainClassName = 'org.figurate.FrameworkLauncher'
        project.tasks.run.args "${project.buildDir}/config/${project.name}.conf"
        project.tasks.run.workingDir 'build'
        project.applicationDefaultJvmArgs = ["-Xmx512m"]

        // copy bundles for osgi runtime..
        project.task('copyBundles', type: Copy) {
            from project.configurations.bundle
            into "${project.buildDir}/bundles"
        }

        // create launcher configuration:
        // * system properties
        // * bundles
        project.task('launcherConfig', type: LauncherConfigTask)
//        project.task('testLauncherConfig', type: LauncherConfigTask)  {
//            outputDir = project.file("$project.buildDir/test-config")
//        }

        project.applicationDistribution.from(project.tasks.copyBundles) {
            into "bundles"
        }
        project.applicationDistribution.from(project.tasks.launcherConfig) {
            into "config"
        }

        // assemble files for osgi runtime:
        // * bundles
        // * configuration (conf/figurate.conf)
        // * bootstrap (osgi framework - e.g. felix, figurate-bootstrap)
        // * start/stop scripts
//        project.task('assemble') {
//
//        }

        project.tasks.run.dependsOn project.tasks.copyBundles, project.tasks.launcherConfig
//        project.tasks.test.dependsOn project.tasks.copyBundles, project.tasks.testLauncherConfig
//        project.tasks.installApp.dependsOn project.tasks.launcherConfig
//        if (project.plugins.hasPlugin('java')) {
//            project.tasks.test.dependsOn project.tasks.copyBundles
//        }
    }
}
