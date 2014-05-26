package org.figurate.gradle.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 8/05/14.
 */
class FiguratePluginSpec extends Specification {

    Project project

    def setup() {
        project = ProjectBuilder.builder().withName('figurate-test').withProjectDir(new File('build', 'figurate-test')).build()
        project.apply plugin: 'figurate'
    }

    def 'verify plugin is applied'() {
        expect:
        project.configurations.bundle
    }

    def 'verify copy bundles task'() {
        given:
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            bundle 'org.slf4j:slf4j-api:1.7.5'
        }

        when:
        project.tasks.copyBundles.execute()

        then:
        new File(project.buildDir, 'bundles/slf4j-api-1.7.5.jar').exists()
    }

    def 'verify default launcher config task'() {
        given:
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            bundle 'org.slf4j:slf4j-api:1.7.5'
        }

        when:
        project.tasks.launcherConfig.execute()

        then:
        new File(project.buildDir, "config/${project.name}.conf").exists()
    }

    def 'verify custom launcher config task'() {
        given:
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            bundle 'org.slf4j:slf4j-api:1.7.5'
        }

        and:
        project.task('testLauncherConfig', type: LauncherConfigTask) {
            outputDir = project.file("$project.buildDir/test-config")
            binding['configProps'] << ['felix.systembundle.activators': '''
[new org.osgi.framework.BundleActivator() {
                                                    public void start(org.osgi.framework.BundleContext context) throws Exception {
                                                        binding.serviceLocator = new org.figurate.osgi.OsgiServiceLocator(context)
                                                        binding.bundleContext = context
                                                    }

                                                    public void stop(org.osgi.framework.BundleContext context) throws Exception {
                                                    }
                                                }]''']
        }

        when:
        project.tasks.testLauncherConfig.execute()

        then:
        new File(project.buildDir, "test-config/${project.name}.conf").exists()
    }

    def 'verify installApp task'() {
        given:
        project.repositories {
            mavenCentral()
        }
        project.dependencies {
            bundle 'org.slf4j:slf4j-api:1.7.5'
        }

        when:
        project.tasks.copyBundles.execute()
        project.tasks.launcherConfig.execute()
        project.tasks.startScripts.execute()
        project.tasks.installApp.execute()

        then:
        new File(project.buildDir, "install/${project.name}").exists()
    }
}
