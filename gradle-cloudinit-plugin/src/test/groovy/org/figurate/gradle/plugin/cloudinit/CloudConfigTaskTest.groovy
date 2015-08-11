package org.figurate.gradle.plugin.cloudinit

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 11/08/15.
 */
class CloudConfigTaskTest extends Specification {

    def 'test create config'() {
        given:
        Project project = ProjectBuilder.builder().withName('figurate-test')
                .withProjectDir(new File('build', 'figurate-test')).build()
        project.task('cloudConfig', type: CloudConfigTask)

        and:
        project.tasks.cloudConfig.outputDir = project.file("$project.buildDir/test-config")

        and:
        project.tasks.cloudConfig.with {
            packages = ['git', 'ufw', 'logwatch']
//            timezone = 'Australia/Melbourne'
            users = [
                    [name: 'fortuna', 'ssh-authorized-keys': System.getenv('SSH_KEY'), groups: 'sudo', shell: '/bin/bash']
            ]
            runcmd = [
                    // configure ssh to deny root access and password login..
                    "sed -i -e '/^PermitRootLogin/s/^.*\$/PermitRootLogin no/' /etc/ssh/sshd_config",
                    "sed -i -e '/^PasswordAuthentication/s/^.*\$/PasswordAuthentication no/' /etc/ssh/sshd_config",
                    'restart ssh',

                    // enable firewall
                    'ufw --force enable'
            ]
        }

        when:
        project.tasks.cloudConfig.execute()

        then:
        def configFile = new File(project.buildDir, "test-config/$project.tasks.cloudConfig.outputFile.name")
        configFile.exists()

        and:
        configFile.text == getClass().getResourceAsStream('/templates/cloud-config.yml.template').text
    }
}
