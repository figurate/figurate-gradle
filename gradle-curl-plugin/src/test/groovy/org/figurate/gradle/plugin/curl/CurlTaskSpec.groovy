package org.figurate.gradle.plugin.curl

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 8/05/14.
 */
class CurlTaskSpec extends Specification {

    def 'test help execution'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('curl', type: CurlTask)

        and:
        project.tasks.curl.help

        and: 'capture output'
        project.tasks.curl.standardOutput = new ByteArrayOutputStream()

        when:
        project.tasks.curl.execute()

        then:
        project.tasks.curl.standardOutput.toString().startsWith('Usage: curl')
    }

    def 'test version execution'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('curl', type: CurlTask)

        and:
        project.tasks.curl.version

        and: 'capture output'
        project.tasks.curl.standardOutput = new ByteArrayOutputStream()

        when:
        project.tasks.curl.execute()

        then:
        project.tasks.curl.standardOutput.toString().startsWith('curl')

    }

    def 'test get url content'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('curl', type: CurlTask)

        and:
        project.tasks.curl.url = 'https://api.github.com/zen'

        and: 'capture output'
        project.tasks.curl.standardOutput = new ByteArrayOutputStream()

        when:
        project.tasks.curl.execute()

        then:
        println project.tasks.curl.standardOutput.toString()
        project.tasks.curl.standardOutput.toString().length() > 0
    }
}
