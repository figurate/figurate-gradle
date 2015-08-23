package org.figurate.gradle.plugin.digitalocean

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 17/03/15.
 */
class DigitalOceanTaskTest extends Specification {
    def 'verify action: list droplets'() {
        given:
        Project project = ProjectBuilder.builder().build()
        project.task('do', type: DigitalOceanTask)

        and:
        project.tasks.do.with {
            token = System.getenv()['DO_API_TOKEN']
            command = 'droplets'
        }

        when: 'the task is executed'
        project.tasks.do.execute()

        then: 'the response contains the specified keys'
        def expectedKeys = ['droplets', 'meta', 'links']
        project.tasks.do.serverResponse.data.keySet().containsAll(expectedKeys)
    }

    def 'verify action droplet snapshot'() {
        given: ' a project with a digital ocean task'
        Project project = ProjectBuilder.builder().build()
        project.task('do', type: DigitalOceanTask)

        and: 'the task requests a snapshot of the specified host'
        project.tasks.do.with {
            httpMethod = 'post'
            token = System.getenv()['DO_API_TOKEN']
            command = "droplets/${System.getenv()['DROPLET_ID']}/actions"
            requestBody = [type: 'snapshot', name: new Date().format('yyyyMMdd-HH:mm:ss') as String]
        }

        when: 'the task is executed'
        project.tasks.do.execute()

        then: 'the response contains the specified keys'
        def expectedKeys = ['droplets', 'meta', 'links']
        project.tasks.do.serverResponse.data.keySet().containsAll(expectedKeys)
    }
}
