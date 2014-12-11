package org.figurate.gradle.plugin.curl

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 11/12/14.
 */
class DigitalOceanTaskTest extends Specification {

    def 'test list all droplets'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('do', type: DigitalOceanTask)

        and:
        project.tasks.do.token = System.getenv()['DO_API_TOKEN']
        project.tasks.do.command = '/v2/droplets'

        and: 'capture output'
        project.tasks.do.standardOutput = new ByteArrayOutputStream()

        when:
        project.tasks.do.execute()

        then:
        project.tasks.do.standardOutput.toString().startsWith('{"droplets":')

    }
}
