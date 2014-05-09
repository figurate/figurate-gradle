package org.figurate.gradle.plugin

import groovy.text.GStringTemplateEngine
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
        project.dependencies {
//            runtime 'org.figurate:figurate-bootstrap:1.0'
            runtime project.files('/Users/fortuna/Development/figurate-core/bootstrap/build/libs/bootstrap.jar')
            runtime 'org.apache.felix:org.apache.felix.framework:4.0.2'
//            runtime project.dependencies.localGroovy()
            runtime 'org.codehaus.groovy:groovy-all:2.3.0-rc-1'
        }

        project.mainClassName = 'org.figurate.FrameworkLauncher'
        project.tasks.run.args "${project.buildDir}/config/${project.name}.conf"
        project.tasks.run.workingDir 'build'
        project.applicationDefaultJvmArgs = ["-Xmx512m"]

        project.configurations {
            bundle
            bundle.transitive = false
        }

        // copy bundles for osgi runtime..
        project.task('copyBundles', type: Copy) {
            from project.configurations.bundle
            into "${project.buildDir}/bundles"
        }

        // create launcher configuration:
        // * system properties
        // * bundles
        project.task('launcherConfig') {
            def configDir = project.file("$project.buildDir/config")
            outputs.dir configDir
            doLast {
                configDir.mkdirs()
                def configTemplate = new GStringTemplateEngine().createTemplate(getClass().getResource('/config/figurate.config.template'))
                def binding = [
                        configProps: [
                                'org.osgi.framework.storage': 'bundle-cache',
                                 'org.osgi.framework.storage.clean': 'onFirstInit'
                        ].collectEntries {["'$it.key'": "'$it.value'"]},
//                        bundles: project.configurations.bundle.collect {"'$project.buildDir/bundles/${it.name}'"}
                        bundles: project.configurations.bundle.collect {"'${it.name}'"}
                ]
                def config = configTemplate.make(binding)
                new File(configDir, "${project.name}.conf").write(config as String)
            }
        }

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
//        project.tasks.installApp.dependsOn project.tasks.launcherConfig
//        if (project.plugins.hasPlugin('java')) {
//            project.tasks.test.dependsOn project.tasks.copyBundles
//        }
    }
}
