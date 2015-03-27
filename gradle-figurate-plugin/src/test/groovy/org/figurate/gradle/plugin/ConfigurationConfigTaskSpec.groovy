package org.figurate.gradle.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 11/05/14.
 */
class ConfigurationConfigTaskSpec extends Specification {

    def 'test create config'() {
        given:
        Project project = ProjectBuilder.builder().withName('figurate-test')
                .withProjectDir(new File('build', 'figurate-test')).build()
        project.task('configuration', type: ConfigurationConfigTask)

        and:
        project.tasks.configuration.outputDir = project.file("$project.buildDir/test-config")

        and:
        project.tasks.configuration.configProps = [
                'com.example.bundle.1': [
                        testProp: 1,
                        testString: 'string'
                ]
        ]

        when:
        project.tasks.configuration.execute()

        then:
        def configFile = new File(project.buildDir, "test-config/$project.tasks.configuration.outputFilename")
        configFile.exists()

        and:
        configFile.text == '''[
    configProps: ['com.example.bundle.1': [
'testProp':1,
'testString':'string',
],
],

    factoryConfigProps: [],
]
'''
    }

}
