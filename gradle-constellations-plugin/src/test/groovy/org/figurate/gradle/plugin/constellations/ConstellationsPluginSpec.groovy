package org.figurate.gradle.plugin.constellations

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 29/07/14.
 */
class ConstellationsPluginSpec extends Specification {

    Project project

    def setup() {
        project = ProjectBuilder.builder()
                .withName('figurate-test')
                .withProjectDir(new File('build', 'figurate-test')).build()
        project.apply plugin: 'constellations'
    }

    def 'verify plugin is applied'() {
        expect:
        project.configurations.constellation$felix
    }
}
