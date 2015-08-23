package org.figurate.gradle.plugin.digitalocean

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 17/03/15.
 */
class DigitalOceanPluginTest extends Specification {

    Project project

    def setup() {
        project = ProjectBuilder.builder()
                .withName('digitalocean-test')
                .withProjectDir(new File('build', 'digitalocean-test')).build()
        project.apply plugin: 'digitalocean'
    }

    def 'verify list droplets'() {
        given: 'a configured project'

        when: 'the list droplets task is executed'
        project.tasks.droplets.execute()

        then: 'the response contains the specified keys'
        def expectedKeys = ['droplets', 'meta', 'links']
        project.tasks.droplets.serverResponse.data.keySet().containsAll(expectedKeys)
    }
}
