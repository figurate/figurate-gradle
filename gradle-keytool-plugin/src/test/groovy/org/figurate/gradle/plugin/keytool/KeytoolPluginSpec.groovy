package org.figurate.gradle.plugin.keytool

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 6/05/14.
 */
class KeytoolPluginSpec extends Specification {

    def 'verify plugin is loaded correctly'() {
        setup:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'keytool'

        and:
        project.tasks.keytool {
            args '-list'
            options {
                keystore = "${System.getenv()['JAVA_HOME']}/jre/lib/security/cacerts"
                storepass = 'changeit'
//                alias = 'test-host'
            }
        }

        expect:
        project.tasks.keytool instanceof KeytoolTask
        println project.tasks.keytool.workingDir
        println project.tasks.keytool.commandLine

        project.tasks.keytool.execute()
    }

    def 'verify genkey works'() {
        setup:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'keytool'

        and:
        project.tasks.genkey {
            options {
                alias = 'genkey'
            }
        }


        when:
        project.tasks.genkey.execute()

        then:
        new File(project.buildDir, 'client.keystore').exists()
    }

    def 'verify storepasswd works'() {
        setup:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'keytool'

        and:
        project.tasks.genkey.execute()

        and:
        project.tasks.storepasswd {
            options {
                $new = 'changeit2'
            }
        }

        when:
        project.tasks.storepasswd.execute()

        then:
        new File(project.buildDir, 'client.keystore').exists()
    }

    def 'verify genkeypair works'() {
        setup:
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'keytool'

        when:
        project.tasks.genkeypair.execute()

        then:
        new File(project.buildDir, 'client.keystore').exists()
    }
}
