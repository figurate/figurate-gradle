package org.figurate.gradle.plugin.keytool

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 8/05/14.
 */
class KeytoolTaskSpec extends Specification {

    def 'test list certs'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('keytool', type: KeytoolTask)

        and:
        project.tasks.keytool {
            args '-list'
            options {
                keystore = "${System.getenv()['JAVA_HOME']}/jre/lib/security/cacerts"
                storepass = 'changeit'
            }
        }

        and: 'capture output'
        project.tasks.keytool.standardOutput = new ByteArrayOutputStream()

        when:
        project.tasks.keytool.execute()

        then:
        project.tasks.keytool.standardOutput.toString().startsWith('\nKeystore type: ')
    }
}
