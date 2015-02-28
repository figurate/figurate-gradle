package org.figurate.gradle.plugin.aem

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * Created by fortuna on 27/02/15.
 */
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

        when: 'the unpack aem task is executed'
        project.tasks.unpackAem.execute()

        then: 'the aem application is copied to the build directory'
        new File(project.buildDir, 'aem/crx-quickstart').exists()
    }

    @Ignore
    def 'verify run aem task'() {
        given: 'a configured project'

        when: 'the run aem task is executed'
        project.tasks.runAem.execute()

        then: 'aem is started'
        new File(project.buildDir, 'aem/error.log').exists()
    }

    @IgnoreRest
    def 'verify start aem task'() {
        given: 'a configured project'
        project.aem.installDir = '/tmp'

        when: 'the start aem task is executed'
        project.tasks.startAem.execute()

        then: 'aem is started'
        new File(project.aem.installDir, 'crx-quickstart/conf/cq.pid').exists()
    }

    def 'verify stop aem task'() {
        given: 'a configured project'
        project.aem.installDir = '/tmp'

        when: 'the stop aem task is executed'
        project.tasks.stopAem.execute()

        then: 'aem is stopped'
        new File(project.aem.installDir, 'crx-quickstart/conf/cq.pid').exists()
    }

}
