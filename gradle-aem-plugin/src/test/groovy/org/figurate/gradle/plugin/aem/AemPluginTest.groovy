package org.figurate.gradle.plugin.aem

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * Created by fortuna on 27/02/15.
 */
@Ignore('Don\'t run integration tests in CI')
class AemPluginTest extends Specification {

    Project project

    def setup() {
        project = ProjectBuilder.builder()
                .withName('aem-test')
                .withProjectDir(new File('build', 'aem-test')).build()
        project.apply plugin: 'aem'
    }

    @Ignore
    def 'verify copy aem task'() {
        given: 'a configured project'

        when: 'the copy aem task is executed'
        project.tasks.copyAem.execute()

        then: 'the aem application is copied to the build directory'
        new File(project.buildDir, 'aem/cq5-author-p4502.jar').exists()
    }

    def 'verify unpack aem task'() {
        given: 'a configured project'
        project.aem {
            installDir = '/tmp/aem3'
            installFile = '/Users/fortuna/Development/cq5/author/cq5-author-p4502.jar'
        }

        when: 'the unpack aem task is executed'
        project.tasks.unpackAem.execute()

        then: 'the aem application is copied to the build directory'
        new File(project.buildDir, 'aem/crx-quickstart').exists()
    }

    @IgnoreRest
    def 'verify copy aem license task'() {
        given: 'a configured project'
        project.aem {
            installDir = '/tmp/aem3'
            licenseDir = '/Users/fortuna/Development/cq5/author'
        }

        when: 'the copy aem license task is executed'
        project.tasks.copyAemLicense.execute()

        then: 'the aem license is copied to the install dir'
        new File(project.aem.installDir, 'license.properties').exists()
    }

    @Ignore
    def 'verify run aem task'() {
        given: 'a configured project'

        when: 'the run aem task is executed'
        project.tasks.runAem.execute()

        then: 'aem is started'
        new File(project.buildDir, 'aem/error.log').exists()
    }

    def 'verify start aem task'() {
        given: 'a configured project'
        project.aem {
            installDir = '/tmp/aem3'
            licenseDir = '/Users/fortuna/Development/cq5/author'
            installFile = '/Users/fortuna/Development/cq5/author/cq5-author-p4502.jar'
        }

        when: 'the start aem task is executed'
        project.tasks.unpackAem.execute()
        project.tasks.copyAemLicense.execute()
        project.tasks.startAem.execute()

        then: 'aem is started'
        new File(project.aem.installDir, 'crx-quickstart/conf/cq.pid').exists()
    }

    def 'verify stop aem task'() {
        given: 'a configured project'
        project.aem.installDir = '/tmp/aem3'

        when: 'the stop aem task is executed'
        project.tasks.unpackAem.execute()
        project.tasks.stopAem.execute()

        then: 'aem is stopped'
        new File(project.aem.installDir, 'crx-quickstart/conf/cq.pid').exists()
    }

}
